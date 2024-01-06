package com.banana.bananawhatsapp.controladores;

import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Usuario;
import com.banana.bananawhatsapp.servicios.IServicioUsuarios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Set;


@Controller
public class ControladorUsuarios {


    @Autowired
    private IServicioUsuarios servicioUsuarios;


    public Usuario alta(Usuario nuevo) throws UsuarioException {
        try {
            System.out.println("Usuario entra: " + nuevo.toString());
            Usuario usuario = servicioUsuarios.crearUsuario(nuevo);
            System.out.println("Usuario creado: " + nuevo);
            return usuario;
        } catch (Exception e) {
            System.out.println("Ha habido un error: " + e.getMessage());
            throw e;
        }

    }

    public Usuario actualizar(Usuario usuario) {
        try {
            servicioUsuarios.actualizarUsuario(usuario);
            System.out.println("Usuario actualizado: " + usuario);
            return usuario;
        } catch (Exception e) {
            System.out.println("Ha habido un error: " + e.getMessage());
            throw e;
        }

    }

    public boolean baja(Usuario usuario) {
        try {
            boolean isOK = servicioUsuarios.borrarUsuario(usuario);
            System.out.println("Usuario borrado: " + usuario);
            return isOK;
        } catch (Exception e) {
            System.out.println("Ha habido un error: " + e.getMessage());
            throw e;
        }
    }

    public Usuario buscaId(Integer identificador) throws UsuarioException {
        try {
            System.out.println("Id entra: " + identificador.toString());
            Usuario usuario = servicioUsuarios.leerUsuario(identificador);
            System.out.println("Usuario encontrado: " + usuario.toString());
            return usuario;
        } catch (Exception e) {
            System.out.println("Ha habido un error: " + e.getMessage());
            throw e;
        }

    }

    public Set<Usuario> destinatarios(Usuario usuario, Integer max) throws UsuarioException {
        try {

            Set <Usuario> listaDestinatarios = servicioUsuarios.obtenerPosiblesDesinatarios(usuario,max);
            System.out.println("Destinatarios encontrados:\n");
            listaDestinatarios.forEach(System.out::println);
            return listaDestinatarios;
        } catch (Exception e) {
            System.out.println("Ha habido un error: " + e.getMessage());
            throw e;
        }

    }



}
