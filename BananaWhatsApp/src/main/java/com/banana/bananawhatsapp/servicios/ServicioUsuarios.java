package com.banana.bananawhatsapp.servicios;

import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Usuario;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ServicioUsuarios implements IServicioUsuarios{
    @Override
    public Usuario crearUsuario(Usuario usuario) throws UsuarioException {
        return null;
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
}