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
        return false;
    }

    @Override
    public Usuario actualizarUsuario(Usuario usuario) throws UsuarioException {
        return null;
    }

    @Override
    public Usuario obtenerPosiblesDesinatarios(Usuario usuario, int max) throws UsuarioException {
        return null;
    }

    @Override
    public Usuario leerUsuario(Usuario usuario) throws UsuarioException { //Juan
        /*try {
           repo.crear(usuario);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new UsuarioException("Error en la creación: "  + e.getMessage());
        }

        return usuario;*/ return null;
    }
}
