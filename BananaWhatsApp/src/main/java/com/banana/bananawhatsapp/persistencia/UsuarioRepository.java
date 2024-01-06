package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Usuario;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@ToString
public class UsuarioRepository implements IUsuarioRepository{

    private String db_url;

    private final static List<Usuario> listaUsuarios;

    static {
        listaUsuarios = new ArrayList<>();
        try {
            listaUsuarios.add(new Usuario(1, 'Juana', 'juana@j.com', '2023-01-07', true));
            listaUsuarios.add(new Usuario(2, 'Luis', 'luis@l.com', '2023-01-07', true));

        } catch (Exception e) {
            System.out.println("⚠ Error al crear clientes: " + e.getMessage());
        }
    }


    @Override
    public Usuario crear(Usuario usuario) throws SQLException {
         String sql = "INSERT INTO usuario values (NULL,?,?,?,?)";

        try (
                Connection conn = DriverManager.getConnection(db_url);
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {

            usuario.valido();

            stmt.setBoolean(1, usuario.isActivo());
            stmt.setString(2, usuario.getAlta().toString());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getNombre());

            int rows = stmt.executeUpdate();

            ResultSet genKeys = stmt.getGeneratedKeys();
            if (genKeys.next()) {
                usuario.setId(genKeys.getInt(1));
            } else {
                throw new SQLException("Usuario creado erroneamente!!!");
            }

        } catch (UsuarioException e) {
            e.printStackTrace();
            throw e;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e);
        }

        return usuario;
    }

    @Override
    public Usuario extraerUsuario(Integer identificador) throws SQLException {
        Usuario user = null;

        String sql = "SELECT * FROM usuario u WHERE u.id=? AND activo=1";

        try (
                Connection conn = DriverManager.getConnection(db_url);
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setInt(1, identificador);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                /*System.out.println(rs);*/
                user = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getDate("alta").toLocalDate(),
                        rs.getBoolean("activo"));

                user.valido();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error en la select: "  + e.getMessage());
        }
        return user;
    }
//muevo de sitio los metodos para que queden en el orden igual que los casos
    @Override
    public boolean borrar(Usuario usuario) throws SQLException {
        String sql = "DELETE FROM usuario WHERE id=?";

        try (
                Connection conn = DriverManager.getConnection(db_url);
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setInt(1, usuario.getId());

            int rows = stmt.executeUpdate();
            System.out.println(rows);

            if(rows<=0){
                throw new SQLException();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error en el delete: "  + e.getMessage());

        }

        System.out.println("Salimos de borrar- UsuarioRepository");
        return true;
    }

    @Override
    public Usuario actualizar(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuario set activo=?, alta=?, email=?, nombre=? WHERE id=?";

        try (
                Connection conn = DriverManager.getConnection(db_url);
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {

            usuario.valido();

            stmt.setBoolean(1, usuario.isActivo());
            stmt.setString(2, usuario.getAlta().toString());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getNombre());
            stmt.setInt(5, usuario.getId());

            int rows = stmt.executeUpdate();

        } catch (UsuarioException e) {
            e.printStackTrace();
            throw new SQLException("Error en el update: "  + e.getMessage());

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return usuario;
    }

    @Override
    public Set<Usuario> obtenerPosiblesDestinatarios(Integer id, Integer max) throws SQLException {
        Set<Usuario> usuarioList = new HashSet<>();

        Usuario destinatario = null;

        String sql = "SELECT * FROM usuario u WHERE u.id<>? ORDER BY u.id LIMIT ?";

        try (
                Connection conn = DriverManager.getConnection(db_url);
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setInt(1, id);
            stmt.setInt(2, max);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                destinatario =new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getDate("alta").toLocalDate(),
                        rs.getBoolean("activo"));

                destinatario.valido();

                usuarioList.add(destinatario);
            }
        } catch (UsuarioException e) {
            e.printStackTrace();
            throw new SQLException("Error en validación destinatario: "  + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error en la select: "  + e.getMessage());
        }
        return usuarioList;

    }

}
