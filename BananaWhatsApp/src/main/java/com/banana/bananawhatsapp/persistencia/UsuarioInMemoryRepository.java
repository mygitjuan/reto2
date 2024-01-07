package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Usuario;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@ToString
public class UsuarioInMemoryRepository implements IUsuarioRepository{

    private final static List<Usuario> listaUsuarios;

    static {
        listaUsuarios = new ArrayList<>();
        try {
            listaUsuarios.add(new Usuario(1, "Juana", "juana@j.com", LocalDate.now(), true));
            listaUsuarios.add(new Usuario(2, "Luis", "luis@l.com", LocalDate.now(), true));
            listaUsuarios.add(new Usuario(3, "Bill", "bill@b.com", LocalDate.now(), true));
            listaUsuarios.add(new Usuario(4, "Pepe", "pepe@p.com", LocalDate.now(), true));
            listaUsuarios.add(new Usuario(5, "Inválido", "invalido@i.com", LocalDate.now(), false));
            listaUsuarios.add(new Usuario(6, "Barbara", "barbara@b.com", LocalDate.now(), true));

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
                        u.valido();
                        usuario = u;


                    }

                }


            } catch (UsuarioException e) {
            e.printStackTrace();
            throw new UsuarioException("Usuario no existe" + e.getMessage());
            }
        } else throw new UsuarioException("Usuario no existe: Valor nulo");
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

        try {
            if (usuario.valido()) {
                for (Usuario u : listaUsuarios) {
                    if (u.getId().equals(usuario.getId())) {
                        int indice = listaUsuarios.indexOf(u);
                        listaUsuarios.set(indice, usuario);

                    }
                }
            } else throw new UsuarioException("Usuario no valido");
        } catch (UsuarioException e) {
            e.printStackTrace();
            throw new UsuarioException("Usuario no existe" + e.getMessage());
         }
        return usuario;

    }

    @Override
    public Set<Usuario> obtenerPosiblesDestinatarios(Integer id, Integer max) throws SQLException {
        Usuario usuario = extraerUsuario(id);

        Set<Usuario> usuarioList = new HashSet<>();

        try  {

            for (int i=1;i<=max; i++){
                Usuario usuarioSeleccionado = (Usuario) listaUsuarios.get(i);
                if (usuarioSeleccionado.getId().equals(usuario.getId()));
                else {
                    usuarioSeleccionado.valido();
                    usuarioList.add(usuarioSeleccionado);
                }
            }

        } catch (UsuarioException e) {
            e.printStackTrace();
            throw new UsuarioException("Error en validación destinatario: " + e.getMessage());
        }
        return usuarioList;
    }
}
