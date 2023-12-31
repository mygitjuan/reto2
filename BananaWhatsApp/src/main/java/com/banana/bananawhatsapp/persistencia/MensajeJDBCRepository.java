package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.exceptions.MensajeException;
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

                mensaje = new Mensaje(
                        rs.getInt("id"),
                        usuario,
                        destinatario,
                        rs.getString("cuerpo"),
                        rs.getDate("fecha").toLocalDate());

                mensajeList.add(mensaje);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error en la select: "  + e.getMessage());
        }
        return mensajeList;
    }

    @Override
    public boolean borrarTodos(Usuario usuario) throws SQLException {
        return false;
    }
}
