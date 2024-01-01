package com.banana.bananawhatsapp.servicios;

import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Usuario;
import com.banana.bananawhatsapp.persistencia.IUsuarioRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Set;

@Setter
@Getter
@ToString
@Service
public class ServicioUsuarios implements IServicioUsuarios{

    @Autowired
    IUsuarioRepository repo;

    @Override
    public Usuario crearUsuario(Usuario usuario) throws UsuarioException{
        try {
            repo.crear(usuario);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UsuarioException("Error en la creación: "  + e.getMessage());
        }

        return usuario;
    }

    @Override
    public boolean borrarUsuario(Usuario usuario) throws UsuarioException {
        try {
            repo.borrar(usuario);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UsuarioException("Error en el borrado: "  + e.getMessage());
        }

        return true;
    }

    @Override
    public Usuario actualizarUsuario(Usuario usuario) throws UsuarioException {
        try {
            repo.actualizar(usuario);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UsuarioException("Error al actualizar: "  + e.getMessage());
        }
        return usuario;
    }

    @Override
    public Set<Usuario> obtenerPosiblesDesinatarios(Usuario usuario, int max) throws UsuarioException {
        return null;
    }

    @Override
    public Usuario leerUsuario(Integer identificador) throws UsuarioException {
        Usuario usuario = null;
        try {
           usuario = repo.extraerUsuario(identificador);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UsuarioException("Error en la creación: "  + e.getMessage());
        }

        return usuario;
    }
}
