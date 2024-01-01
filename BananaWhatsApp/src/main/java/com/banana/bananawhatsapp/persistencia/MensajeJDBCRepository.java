package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.exceptions.MensajeException;
import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@ToString
public class MensajeJDBCRepository implements IMensajeRepository{

    private String db_url;
    @Override
    public Mensaje crear(Mensaje mensaje) throws SQLException {
    String sql = "INSERT INTO mensaje values (NULL,?,?,?,?)";

        try (
                Connection conn = DriverManager.getConnection(db_url);
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ) {

            mensaje.valido();

            stmt.setString(1, mensaje.getCuerpo());
            stmt.setString(2, mensaje.getFecha().toString());
            stmt.setInt(3, mensaje.getRemitente().getId());
            stmt.setInt(4, mensaje.getDestinatario().getId());

            int rows = stmt.executeUpdate();

            ResultSet genKeys = stmt.getGeneratedKeys();
            if (genKeys.next()) {
                mensaje.setId(genKeys.getInt(1));
            } else {
                throw new SQLException("Usuario creado erroneamente!!!");
            }

        } catch (MensajeException e) {
            e.printStackTrace();
            throw e;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e);
        }

        return mensaje;
    }

    @Override
    public List<Mensaje> obtener(Usuario usuario) throws SQLException {
        List<Mensaje> mensajeList = new ArrayList<>();
        Mensaje mensaje = null;
        Usuario destinatario = null;

        String sql = "SELECT m.*, u.* FROM mensaje m LEFT JOIN usuario u ON m.to_user = u.id WHERE m.from_user=?";

        try (
                Connection conn = DriverManager.getConnection(db_url);
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setInt(1, usuario.getId());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                destinatario =new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getDate("alta").toLocalDate(),
                        rs.getBoolean("activo"));

                destinatario.valido();

                mensaje = new Mensaje(
                        rs.getInt("id"),
                        usuario,
                        destinatario,
                        rs.getString("cuerpo"),
                        rs.getDate("fecha").toLocalDate());

                mensajeList.add(mensaje);
            }
        } catch (UsuarioException e) {
            e.printStackTrace();
            throw new SQLException("Error en validación destinatario: "  + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error en la select: "  + e.getMessage());
        }
        return mensajeList;
    }

    @Override
    public List<Mensaje> obtener(Usuario usuario, Usuario destinatario) throws SQLException {
        List<Mensaje> mensajeList = new ArrayList<>();
        Mensaje mensaje = null;

        String sql = "SELECT m.*, u.*, v.* FROM mensaje m LEFT JOIN (usuario u, usuario v) ON m.from_user = u.id and m.to_user = v.id WHERE m.from_user=? AND m.to_user=?";

        try (
                Connection conn = DriverManager.getConnection(db_url);
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setInt(1, usuario.getId());
            stmt.setInt(2, destinatario.getId());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Usuario remitenteDB = new Usuario(
                        rs.getInt(6),
                        rs.getString(10),
                        rs.getString(9),
                        rs.getDate(8).toLocalDate(),
                        rs.getBoolean(7));

                remitenteDB.valido();

                Usuario destinatarioDB = new Usuario(
                        rs.getInt(11),
                        rs.getString(15),
                        rs.getString(14),
                        rs.getDate(13).toLocalDate(),
                        rs.getBoolean(12));

                destinatarioDB.valido();

                mensaje = new Mensaje(
                        rs.getInt("id"),
                        remitenteDB,
                        destinatarioDB,
                        rs.getString("cuerpo"),
                        rs.getDate("fecha").toLocalDate());

                mensajeList.add(mensaje);
            }
        } catch (UsuarioException e) {
            e.printStackTrace();
            throw new SQLException("Error en validación destinatario: "  + e.getMessage());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error en la select: "  + e.getMessage());
        }
        return mensajeList;
    }

    @Override
    public boolean borrarTodos(Usuario usuario) throws SQLException {
        String sql = "DELETE FROM mensaje WHERE from_user=? OR to_user=?";

        try (
                Connection conn = DriverManager.getConnection(db_url);
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setInt(1, usuario.getId());
            stmt.setInt(2, usuario.getId());

            int rows = stmt.executeUpdate();
            System.out.println(rows);

            if(rows<=0){
                throw new SQLException();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error en el delete: "  + e.getMessage());

        }
        System.out.println("Salimos de borrarTodos- MensajeRepository");
        return true;



    }

    @Override
    public boolean borrarTodos(Usuario usuario, Usuario destinatario) throws SQLException {
        String sql = "DELETE FROM mensaje WHERE from_user=? AND to_user=?";

        try (
                Connection conn = DriverManager.getConnection(db_url);
                PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            stmt.setInt(1, usuario.getId());
            stmt.setInt(2, destinatario.getId());

            int rows = stmt.executeUpdate();
            System.out.println(rows);

            if(rows<=0){
                throw new SQLException();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error en el delete: "  + e.getMessage());

        }

        return true;
    }

}
