package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.exceptions.MensajeException;
import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.*;
import java.time.LocalDate;
import java.util.*;

@Setter
@Getter
@ToString
public class MensajeInMemoryRepository implements IMensajeRepository{
/*
    private Integer id;
    private Usuario remitente;
    private Usuario destinatario;
    private String cuerpo;
    private LocalDate fecha;
    (1, 'Hola, qué tal?', '2023-11-25', 1, 2),
    (2, 'Muy bien! y tu?', '2023-11-25', 2, 1),
    (3, 'Bien también...', '2023-11-25', 1, 2),
    (4, 'Chachi!', '2023-11-25', 2, 1);
 */

    private final static List<Mensaje> listaMensajes;
    private final static UsuarioInMemoryRepository repoUsuario;
    private static Usuario remitente, remitente_2;
    private static Usuario destinatario, destinatario_2;
    static {
        listaMensajes = new ArrayList<>();
        repoUsuario = new UsuarioInMemoryRepository();

        try {
            remitente = repoUsuario.extraerUsuario(1);
            destinatario = repoUsuario.extraerUsuario(2);
            remitente_2 = repoUsuario.extraerUsuario(3);
            destinatario_2 = repoUsuario.extraerUsuario(4);

        } catch (Exception e) {
            System.out.println("⚠ Error al consultar usuarios: " + e.getMessage());
        }

        try {
            listaMensajes.add(new Mensaje( 1,remitente,destinatario,"Hola, qué tal?", LocalDate.now()));
            listaMensajes.add(new Mensaje( 2,destinatario,remitente,"Muy bien! y tu?", LocalDate.now()));
            listaMensajes.add(new Mensaje( 3,remitente,destinatario,"Bien también...", LocalDate.now()));
            listaMensajes.add(new Mensaje( 4,destinatario,remitente,"Chachi!", LocalDate.now()));
            listaMensajes.add(new Mensaje( 5,remitente_2,destinatario_2,"A quién corrresponda", LocalDate.now()));
            listaMensajes.add(new Mensaje( 6,destinatario_2,remitente_2,"Saludos", LocalDate.now()));

        } catch (Exception e) {
            System.out.println("⚠ Error al crear mensajes: " + e.getMessage());
        }

    }



   /* private String db_url;*/
    @Override
    public Mensaje crear(Mensaje mensaje) throws SQLException {

        try  {
            mensaje.valido();
            mensaje.setId(listaMensajes.size() + 1);
            listaMensajes.add(mensaje);

        } catch (MensajeException e) {
          e.printStackTrace();
          throw e;
        }

        return mensaje;

    }

    @Override
    public List<Mensaje> obtener(Usuario usuario) throws SQLException {
     /*   List<Mensaje> mensajeList = new ArrayList<>();
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
        return mensajeList;*/ return null;
    }

    @Override
    public List<Mensaje> obtener(Usuario usuario, Usuario destinatario) throws SQLException {
     /*   List<Mensaje> mensajeList = new ArrayList<>();
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
        return mensajeList;*/ return null;
    }

    @Override
    public boolean borrarTodos(Usuario usuario) throws SQLException {
        System.out.println(listaMensajes);
        Set<Mensaje> mensajeSeleccionado = new HashSet<>();

        if (usuario.getId() > 0) {
         try {
          for (Mensaje m : listaMensajes) {
             if (m.getRemitente().getId().equals(usuario.getId())) mensajeSeleccionado.add(m);
             if (m.getDestinatario().getId().equals(usuario.getId())) mensajeSeleccionado.add(m);
          }
         } catch (MensajeException e) {
            e.printStackTrace();
            throw new MensajeException("Mensaje no existe" + e.getMessage());
         }
         try {
            Iterator<Mensaje> mensajeIterator = mensajeSeleccionado.iterator();
            while (mensajeIterator.hasNext()){
             listaMensajes.remove(mensajeIterator.next());
            }
          if (listaMensajes.isEmpty());
          else System.out.println("Despues de remover mensajes: " + listaMensajes);
         } catch (MensajeException e) {
            e.printStackTrace();
            throw new MensajeException("Mensaje no se ha podido remover: " + e.getMessage());
         }

        } else throw new MensajeException("Mensaje no existe: Valor nulo");
        return true;
    }

    @Override
    public boolean borrarTodos(Usuario usuario, Usuario destinatario) throws SQLException {
     /*   String sql = "DELETE FROM mensaje WHERE from_user=? AND to_user=?";

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

        return true;*/ return false;
    }

}
