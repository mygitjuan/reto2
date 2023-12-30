package com.banana.bananawhatsapp.servicios;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Usuario;
import com.banana.bananawhatsapp.persistencia.UsuarioJDBCRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class ServicioUsuariosTest {

    @Autowired
    IServicioUsuarios servicio;



    @Test
    void dadoUnUsuarioValido_cuandoCrearUsuario_entoncesUsuarioValido() throws UsuarioException {
        Usuario u = new Usuario(null,"Servicio","servicio@email.com", LocalDate.now(),true);
        System.out.println(u);
        servicio.crearUsuario(u);
        assertNotNull(u.getId());
        assertThat(u.getId(),greaterThan(0));
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoCrearUsuario_entoncesExcepcion() throws UsuarioException {
        Usuario u = new Usuario(null,"Servicio","servicio¬email.com", LocalDate.now(),true);
        System.out.println(u);

        assertThrows(Exception.class, () -> {
                servicio.crearUsuario(u);
                }
        );
    }

    @Test
    void dadoUnUsuarioValido_cuandoBorrarUsuario_entoncesUsuarioValido() {
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoBorrarUsuario_entoncesExcepcion() {
    }

    @Test
    void dadoUnUsuarioValido_cuandoActualizarUsuario_entoncesUsuarioValido() {
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoActualizarUsuario_entoncesExcepcion() {
    }

    @Test
    void dadoUnUsuarioValido_cuandoObtenerPosiblesDesinatarios_entoncesUsuarioValido() {
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoObtenerPosiblesDesinatarios_entoncesExcepcion() {
    }

    @Test
    void leerUsuariosValido() throws UsuarioException{
        Usuario remitente = null;

        try {
            remitente= servicio.leerUsuario(1);
        }  catch (UsuarioException e) {
            throw new UsuarioException("Error de remitente: " + e.getMessage());
        }

        assertNotNull(remitente.getId());
        assertThat(remitente.getId(),greaterThan(0));

        Usuario destinatario = null;

        try {
            destinatario= servicio.leerUsuario(2);
        }  catch (UsuarioException e) {
            throw new UsuarioException("Error de destinatario: " + e.getMessage());
        }
        assertNotNull(destinatario.getId());
        assertThat(destinatario.getId(),greaterThan(0));

        System.out.println("Remitente: " + remitente.toString());
        System.out.println("Destinatario: " + destinatario.toString());

    }
}