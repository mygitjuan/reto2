package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Usuario;
import com.banana.bananawhatsapp.Properties.PropertyValues;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.SQLException;
import java.util.Set;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
public class UsuarioJDBCRepository implements IUsuarioRepository{

    private String db_url;


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

            stmt.setBoolean(1, usuario.isActivo());
            stmt.setString(2, usuario.getAlta().toString());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getNombre());
            stmt.setInt(5, usuario.getId());

            int rows = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return usuario;
    }

    @Override
    public Set<Usuario> obtenerPosiblesDestinatarios(Integer id, Integer max) throws SQLException {
        return null;
    }

}
