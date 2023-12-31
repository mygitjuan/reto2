package com.banana.bananawhatsapp.servicios;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
@ActiveProfiles("prod")
class ServicioMensajeriaTest {

    @Autowired
    IServicioMensajeria servMen;

    @Autowired
    IServicioUsuarios servUsu;
    @Test
    void dadoRemitenteYDestinatarioYTextoValido_cuandoEnviarMensaje_entoncesMensajeValido() {
        Usuario remitente = null;

        try {
            remitente= servUsu.leerUsuario(1);
        }  catch (UsuarioException e) {
            throw new UsuarioException("Error de remitente: " + e.getMessage());
        }

        assertNotNull(remitente.getId());
        assertThat(remitente.getId(),greaterThan(0));

        Usuario destinatario = null;

        try {
            destinatario= servUsu.leerUsuario(2);
        }  catch (UsuarioException e) {
            throw new UsuarioException("Error de destinatario: " + e.getMessage());
        }
        assertNotNull(destinatario.getId());
        assertThat(destinatario.getId(),greaterThan(0));

        System.out.println("Remitente: " + remitente.toString());
        System.out.println("Destinatario: " + destinatario.toString());

        Mensaje mensa = null;

        mensa = servMen.enviarMensaje(remitente,destinatario,"Mensaje de prueba servicio");
        assertNotNull(mensa.getId());
        assertThat(mensa.getId(),greaterThan(0));
    }

    @Test
    void dadoRemitenteYDestinatarioYTextoNOValido_cuandoEnviarMensaje_entoncesExcepcion() {
        Usuario remitente = null;

        try {
            remitente= servUsu.leerUsuario(1);
        }  catch (UsuarioException e) {
            throw new UsuarioException("Error de remitente: " + e.getMessage());
        }

        assertNotNull(remitente.getId());
        assertThat(remitente.getId(),greaterThan(0));

        Usuario destinatario = null;

        try {
            destinatario= servUsu.leerUsuario(2);
        }  catch (UsuarioException e) {
            throw new UsuarioException("Error de destinatario: " + e.getMessage());
        }
        assertNotNull(destinatario.getId());
        assertThat(destinatario.getId(),greaterThan(0));

        System.out.println("Remitente: " + remitente.toString());
        System.out.println("Destinatario: " + destinatario.toString());
        final Usuario remitenteFinal = remitente;
        final Usuario destinatarioFinal = destinatario;

        assertThrows(Exception.class, () -> {
                servMen.enviarMensaje(remitenteFinal,destinatarioFinal,"¡Holi!");
                }
        );

    }

    @Test
    void dadoRemitenteYDestinatarioValido_cuandoMostrarChatConUsuario_entoncesListaMensajes() {

        Usuario remitente = null;

        try {
            remitente= servUsu.leerUsuario(22);
        }  catch (UsuarioException e) {
            throw new UsuarioException("Error de remitente: " + e.getMessage());
        }

        assertNotNull(remitente.getId());
        assertThat(remitente.getId(),greaterThan(0));

        Usuario destinatario = null;

        try {
            destinatario= servUsu.leerUsuario(21);
        }  catch (UsuarioException e) {
            throw new UsuarioException("Error de destinatario: " + e.getMessage());
        }
        assertNotNull(destinatario.getId());
        assertThat(destinatario.getId(),greaterThan(0));

        System.out.println("Remitente: " + remitente.toString());
        System.out.println("Destinatario: " + destinatario.toString());

        List<Mensaje> mensajeList = servMen.mostrarChatConUsuario(remitente,destinatario);
        assertNotNull(mensajeList);
        assertFalse(mensajeList.isEmpty());
        System.out.println("Lista de mensajes:" + mensajeList.toString());

    }

    @Test
    void dadoRemitenteYDestinatarioNOValido_cuandoMostrarChatConUsuario_entoncesExcepcion() {
        Usuario remitente = null;

        try {
            remitente= servUsu.leerUsuario(18);
        }  catch (UsuarioException e) {
            throw new UsuarioException("Error de remitente: " + e.getMessage());
        }

        assertNotNull(remitente.getId());
        assertThat(remitente.getId(),greaterThan(0));
        System.out.println("Remitente: " + remitente.toString());

        Usuario destinatario = null;

        try {
            destinatario= servUsu.leerUsuario(19);
        }  catch (UsuarioException e) {
            throw new UsuarioException("Error de destinatario: " + e.getMessage());
        }
        assertNull(destinatario);

        final Usuario remitenteFinal = remitente;
        final Usuario destinatarioFinal = destinatario;

        assertThrows(Exception.class, () -> {
                servMen.mostrarChatConUsuario(remitenteFinal,destinatarioFinal);
                }
        );

    }

    @Test
    void dadoRemitenteYDestinatarioValido_cuandoBorrarChatConUsuario_entoncesOK() {
        Usuario remitente = null;

        try {
            remitente= servUsu.leerUsuario(27);
        }  catch (UsuarioException e) {
            throw new UsuarioException("Error de remitente: " + e.getMessage());
        }

        assertNotNull(remitente.getId());
        assertThat(remitente.getId(),greaterThan(0));

        Usuario destinatario = null;

        try {
            destinatario= servUsu.leerUsuario(26);
        }  catch (UsuarioException e) {
            throw new UsuarioException("Error de destinatario: " + e.getMessage());
        }
        assertNotNull(destinatario.getId());
        assertThat(destinatario.getId(),greaterThan(0));

        System.out.println("Remitente: " + remitente.toString());
        System.out.println("Destinatario: " + destinatario.toString());

        Boolean resp = false;
        resp=servMen.borrarChatConUsuario(remitente,destinatario);
        assertTrue(resp);
    }

    @Test
    void dadoRemitenteYDestinatarioNOValido_cuandoBorrarChatConUsuario_entoncesExcepcion() {
        Usuario remitente = null;

        try {
            remitente= servUsu.leerUsuario(22);
        }  catch (UsuarioException e) {
            throw new UsuarioException("Error de remitente: " + e.getMessage());
        }

        assertNotNull(remitente.getId());
        assertThat(remitente.getId(),greaterThan(0));

        Usuario destinatario = null;

        try {
            destinatario= servUsu.leerUsuario(2);
        }  catch (UsuarioException e) {
            throw new UsuarioException("Error de destinatario: " + e.getMessage());
        }
        assertNotNull(destinatario.getId());
        assertThat(destinatario.getId(),greaterThan(0));

        System.out.println("Remitente: " + remitente.toString());
        System.out.println("Destinatario: " + destinatario.toString());

        final Usuario remitenteFinal = remitente;
        final Usuario destinatarioFinal = destinatario;

        assertThrows(Exception.class, () -> {
                servMen.borrarChatConUsuario(remitenteFinal,destinatarioFinal);
                }
        );
    }
}