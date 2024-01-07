package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Usuario;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@ToString
public class UsuarioInMemoryRepository implements IUsuarioRepository{

   /*private String db_url;*/

    private final static List<Usuario> listaUsuarios;

    static {
        listaUsuarios = new ArrayList<>();
        try {
            listaUsuarios.add(new Usuario(1, "Juana", "juana@j.com", LocalDate.now(), true));
            listaUsuarios.add(new Usuario(2, "Luis", "luis@l.com", LocalDate.now(), true));
            listaUsuarios.add(new Usuario(3, "Bill", "bill@b.com", LocalDate.now(), true));
            listaUsuarios.add(new Usuario(4, "Pepe", "pepe@p.com", LocalDate.now(), true));
            listaUsuarios.add(new Usuario(5, "Inválido", "invalido@i.com", LocalDate.now(), false));

        } catch (Exception e) {
            System.out.println("⚠ Error al crear clientes: " + e.getMessage());
        }
    }


    @Override
    public Usuario crear(Usuario usuario) throws SQLException {

        try  {

            usuario.valido();
            usuario.setId(listaUsuarios.size() + 1);
            listaUsuarios.add(usuario);

        } catch (UsuarioException e) {
            e.printStackTrace();
            throw e;
        }

        return usuario;
    }

    @Override
    public Usuario extraerUsuario(Integer identificador) throws SQLException {
        Usuario usuario = null;

        if (listaUsuarios != null) {
            try {
                for (Usuario u : listaUsuarios) {
                    if (u.getId() == identificador) {
                        usuario = u;
                        usuario.valido();
                    }
                }
            } catch (UsuarioException e) {
            e.printStackTrace();
            throw new UsuarioException("Cliente no existe" + e.getMessage());
            }
        } else throw new UsuarioException("Cliente no existe: Valor nulo");
        return usuario;
    }
//muevo de sitio los metodos para que queden en el orden igual que los casos
    @Override
    public boolean borrar(Usuario usuario) throws SQLException {
        System.out.println("Lista Usuarios:"+listaUsuarios);
        Usuario usuarioSeleccionado = null;

        if (usuario.getId() > 0) {
         try {
          for (Usuario u : listaUsuarios) {
             if (u.getId().equals(usuario.getId())) usuarioSeleccionado=u;
          }

          listaUsuarios.remove(usuarioSeleccionado);

          if (listaUsuarios.isEmpty()) System.out.println("No quedan usuarios en memoria");
          else System.out.println("Despues de remover usuario: " + listaUsuarios);


         } catch (UsuarioException e) {
            e.printStackTrace();
            throw new UsuarioException("Usuario no existe" + e.getMessage());
         }

        } else throw new UsuarioException("Usuario no existe: Valor nulo");
        return true;

    }

    @Override
    public Usuario actualizar(Usuario usuario) throws SQLException {
       /* String sql = "UPDATE usuario set activo=?, alta=?, email=?, nombre=? WHERE id=?";

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

        return usuario;*/ return null;
    }

    @Override
    public Set<Usuario> obtenerPosiblesDestinatarios(Integer id, Integer max) throws SQLException {
    /*    Set<Usuario> usuarioList = new HashSet<>();

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
        return usuarioList;*/ return null;

    }

}
