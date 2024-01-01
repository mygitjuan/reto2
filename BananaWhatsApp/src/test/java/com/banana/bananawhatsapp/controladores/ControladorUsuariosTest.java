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

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class ControladorUsuariosTest {

    @Autowired
    ControladorUsuarios controladorUsuarios;

    @Autowired
    ControladorMensajes controladorMensajes;

    @Test
    void dadoUsuarioValido_cuandoAlta_entoncesUsuarioValido() {
        Usuario u = new Usuario(null,"Controlador3","controlador3@email.com", LocalDate.now(),true);
        System.out.println(u);
        controladorUsuarios.alta(u);
        assertNotNull(u.getId());
        assertThat(u.getId(),greaterThan(0));

    }

    @Test
    void dadoUsuarioNOValido_cuandoAlta_entoncesExcepcion() {
        Usuario u = new Usuario(null,"Controlador","controlador¬email.com", LocalDate.now(),true);
        System.out.println(u);

        assertThrows(Exception.class, () -> {
                controladorUsuarios.alta(u);
                }
        );
    }



    @Test
    void dadoUsuarioValido_cuandoBaja_entoncesUsuarioValido() {
        Integer idParm = 42;
        Usuario u = controladorUsuarios.buscaId(idParm);
        System.out.println(u);

        Boolean mensajeDelete = false;
        mensajeDelete = controladorMensajes.eliminarChatConUsuario(idParm);
        assertTrue(mensajeDelete);

        Boolean usuarioDelete = false;
        usuarioDelete = controladorUsuarios.baja(u);
        assertTrue(usuarioDelete);


    }

    @Test
    void dadoUsuarioNOValido_cuandoBaja_entoncesExcepcion() {
        final Integer identificador = 32;

        assertThrows(Exception.class, () -> {
                controladorUsuarios.buscaId(identificador);
                }
        );

        assertThrows(Exception.class, () -> {
                controladorMensajes.eliminarChatConUsuario(identificador);
                }
        );

        final Usuario uFinal = null;

        assertThrows(Exception.class, () -> {
                controladorUsuarios.baja(uFinal);
                }
        );

    }

    //Juan: muevo actualizar después de los test de Baja para tenerlos en orden de casos de usuario
    @Test
    void dadoUsuarioValido_cuandoActualizar_entoncesUsuarioValido() {
        Integer idParm = 41;
        Usuario u = controladorUsuarios.buscaId(idParm);
        System.out.println(u);

        u.setNombre("Cisne");
        u.setEmail("cisne@c.com");
        u.setAlta(LocalDate.now());
        u.setActivo(true);

        Boolean usuarioValido = u.valido();
        assertTrue(usuarioValido);

        Usuario usuarioActualizado = controladorUsuarios.actualizar(u);
        assertNotNull(usuarioActualizado);
        assertEquals(usuarioActualizado.getId(), u.getId());
    }

    @Test
    void dadoUsuarioNOValido_cuandoActualizar_entoncesExcepcion() {
        Integer identificador = 41;
        Usuario u = controladorUsuarios.buscaId(identificador);
        System.out.println(u);
        assertNotNull(u.getId());
        assertThat(u.getId(),greaterThan(0));

        u.setNombre("Pegaso");
        u.setEmail("pegaso#p.com");
        u.setAlta(LocalDate.now());
        u.setActivo(true);

        assertThrows(Exception.class, () -> {
                controladorUsuarios.actualizar(u);
                }
        );
    }
// Añadido por Juan para leer usuarios
    @Test
    void buscaUsuariosValidos() throws UsuarioException {
        Usuario remitente = null;

        try {
            remitente= controladorUsuarios.buscaId(1);
        }  catch (UsuarioException e) {
            throw new UsuarioException("Error de remitente: " + e.getMessage());
        }

        assertNotNull(remitente.getId());
        assertThat(remitente.getId(),greaterThan(0));
    }
}