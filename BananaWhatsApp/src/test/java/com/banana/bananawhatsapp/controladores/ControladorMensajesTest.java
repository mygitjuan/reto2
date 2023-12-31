package com.banana.bananawhatsapp.controladores;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/*terminado hasta caso de usuario 2 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class ControladorMensajesTest {

    @Autowired
    ControladorMensajes controladorMensajes;

    @Autowired
    ControladorUsuarios controladorUsuarios;

    @Test
    void dadoRemitenteYDestinatarioYTextoValidos_cuandoEnviarMensaje_entoncesOK() {
        Usuario remitente = null;

        try {
            remitente= controladorUsuarios.buscaId(22);
        }  catch (UsuarioException e) {
            throw new UsuarioException("Error de remitente: " + e.getMessage());
        }

        assertNotNull(remitente.getId());
        assertThat(remitente.getId(),greaterThan(0));

        Usuario destinatario = null;

        try {
            destinatario= controladorUsuarios.buscaId(21);
        }  catch (UsuarioException e) {
            throw new UsuarioException("Error de destinatario: " + e.getMessage());
        }
        assertNotNull(destinatario.getId());
        assertThat(destinatario.getId(),greaterThan(0));

        System.out.println("Remitente: " + remitente.toString());
        System.out.println("Destinatario: " + destinatario.toString());

        Boolean mensa = controladorMensajes.enviarMensaje(remitente.getId(),destinatario.getId(),"Mensaje de prueba de controller");

        assertTrue(mensa);
    }

    @Test
    void dadoRemitenteYDestinatarioYTextoNOValidos_cuandoEnviarMensaje_entoncesExcepcion() {
        Usuario remitente = null;

        try {
            remitente= controladorUsuarios.buscaId(1);
        }  catch (UsuarioException e) {
            throw new UsuarioException("Error de remitente: " + e.getMessage());
        }

        assertNotNull(remitente.getId());
        assertThat(remitente.getId(),greaterThan(0));

        Usuario destinatario = null;

        try {
            destinatario= controladorUsuarios.buscaId(2);
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
                controladorMensajes.enviarMensaje(remitenteFinal.getId(),destinatarioFinal.getId(),"¡Holi!");
                }
        );

    }

    @Test
    void dadoRemitenteYDestinatarioValidos_cuandoMostrarChat_entoncesOK() {
        Usuario remitente = null;

        try {
            remitente= controladorUsuarios.buscaId(1);
        }  catch (UsuarioException e) {
            throw new UsuarioException("Error de remitente: " + e.getMessage());
        }

        assertNotNull(remitente.getId());
        assertThat(remitente.getId(),greaterThan(0));

        Usuario destinatario = null;

        try {
            destinatario= controladorUsuarios.buscaId(2);
        }  catch (UsuarioException e) {
            throw new UsuarioException("Error de destinatario: " + e.getMessage());
        }
        assertNotNull(destinatario.getId());
        assertThat(destinatario.getId(),greaterThan(0));

        System.out.println("Remitente: " + remitente.toString());
        System.out.println("Destinatario: " + destinatario.toString());

        Boolean mensa = controladorMensajes.mostrarChat(remitente.getId(),destinatario.getId());

        assertTrue(mensa);

    }

    @Test
    void dadoRemitenteYDestinatarioNOValidos_cuandoMostrarChat_entoncesExcepcion() {
        Usuario remitente = null;

        try {
            remitente= controladorUsuarios.buscaId(18);
        }  catch (UsuarioException e) {
            throw new UsuarioException("Error de remitente: " + e.getMessage());
        }

        assertNotNull(remitente.getId());
        assertThat(remitente.getId(),greaterThan(0));

        System.out.println("Remitente: " + remitente.toString());
        final Usuario remitenteFinal = remitente;

        assertThrows(Exception.class, () -> {
                controladorMensajes.mostrarChat(remitenteFinal.getId(), null);
                }
        );

    }

    @Test
    void dadoRemitenteYDestinatarioValidos_cuandoEliminarChatConUsuario_entoncesOK() {
        Usuario remitente = null;

        try {
            remitente= controladorUsuarios.buscaId(22);
        }  catch (UsuarioException e) {
            throw new UsuarioException("Error de remitente: " + e.getMessage());
        }

        assertNotNull(remitente.getId());
        assertThat(remitente.getId(),greaterThan(0));

        Usuario destinatario = null;

        try {
            destinatario= controladorUsuarios.buscaId(21);
        }  catch (UsuarioException e) {
            throw new UsuarioException("Error de destinatario: " + e.getMessage());
        }
        assertNotNull(destinatario.getId());
        assertThat(destinatario.getId(),greaterThan(0));

        System.out.println("Remitente: " + remitente.toString());
        System.out.println("Destinatario: " + destinatario.toString());

        Boolean mensa = false;
        mensa = controladorMensajes.eliminarChatConUsuario(remitente.getId(), destinatario.getId());

        assertTrue(mensa);

    }

    @Test
    void dadoRemitenteYDestinatarioNOValidos_cuandoEliminarChatConUsuario_entoncesExcepcion() {
        Usuario remitente = null;

        try {
            remitente= controladorUsuarios.buscaId(22);
        }  catch (UsuarioException e) {
            throw new UsuarioException("Error de remitente: " + e.getMessage());
        }

        assertNotNull(remitente.getId());
        assertThat(remitente.getId(),greaterThan(0));

        Usuario destinatario = null;

        try {
            destinatario= controladorUsuarios.buscaId(2);
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
                controladorMensajes.eliminarChatConUsuario(remitenteFinal.getId(), destinatarioFinal.getId());
                }
        );

    }


}