package com.banana.bananawhatsapp.servicios;

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
class ServicioUsuariosTest {

    @Autowired
    IServicioUsuarios usuarioServicio;

    @Autowired
    IServicioMensajeria mensajeriaServicio;


    @Test
    void dadoUnUsuarioValido_cuandoCrearUsuario_entoncesUsuarioValido() throws UsuarioException {
        Usuario u = new Usuario(null,"Servicio3","servicio3@email.com", LocalDate.now(),true);
        System.out.println(u);
        usuarioServicio.crearUsuario(u);
        assertNotNull(u.getId());
        assertThat(u.getId(),greaterThan(0));
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoCrearUsuario_entoncesExcepcion() throws UsuarioException {
        Usuario u = new Usuario(null,"Servicio","servicio¬email.com", LocalDate.now(),true);
        System.out.println(u);

        assertThrows(Exception.class, () -> {
                usuarioServicio.crearUsuario(u);
                }
        );
    }

    @Test
    void dadoUnUsuarioValido_cuandoBorrarUsuario_entoncesUsuarioValido() {
        Integer idParm = 36;
        Usuario u = usuarioServicio.leerUsuario(idParm);
        System.out.println(u);

        Boolean mensajeDelete = false;
        mensajeDelete = mensajeriaServicio.borrarChatConUsuario(u);
        assertTrue(mensajeDelete);

        Boolean usuarioDelete = false;
        usuarioDelete = usuarioServicio.borrarUsuario(u);
        assertTrue(usuarioDelete);

        u = usuarioServicio.leerUsuario(idParm);
        assertNull(u);

    }

    @Test
    void dadoUnUsuarioNOValido_cuandoBorrarUsuario_entoncesExcepcion() {
        final Integer identificador = 32;

        assertThrows(Exception.class, () -> {
                usuarioServicio.leerUsuario(identificador);
                }
        );


        final Usuario uFinal = null;

        assertThrows(Exception.class, () -> {
                mensajeriaServicio.borrarChatConUsuario(uFinal);
                }
        );

        assertThrows(Exception.class, () -> {
                usuarioServicio.borrarUsuario(uFinal);
                }
        );

    }
//Juan: muevo actualizar después de los test de Baja para tenerlos en orden de casos de usuario
    @Test
    void dadoUnUsuarioValido_cuandoActualizarUsuario_entoncesUsuarioValido() {
        Integer idParm = 41;
        Usuario u = usuarioServicio.leerUsuario(idParm);
        System.out.println(u);

        u.setNombre("Dragón");
        u.setEmail("dragon@d.com");
        u.setAlta(LocalDate.now());
        u.setActivo(true);

        Boolean usuarioValido = u.valido();
        assertTrue(usuarioValido);

        Usuario usuarioActualizado = usuarioServicio.actualizarUsuario(u);
        assertNotNull(usuarioActualizado);
        assertEquals(usuarioActualizado.getId(), u.getId());

    }

    @Test
    void dadoUnUsuarioNOValido_cuandoActualizarUsuario_entoncesExcepcion() {
        Integer identificador = 41;
        Usuario u = usuarioServicio.leerUsuario(identificador);
        System.out.println(u);
        assertNotNull(u.getId());
        assertThat(u.getId(),greaterThan(0));

        u.setNombre("Pegaso");
        u.setEmail("pegaso#p.com");
        u.setAlta(LocalDate.now());
        u.setActivo(true);

        assertThrows(Exception.class, () -> {
                usuarioServicio.actualizarUsuario(u);
                }
        );
    }

    @Test
    void dadoUnUsuarioValido_cuandoObtenerPosiblesDesinatarios_entoncesUsuarioValido() {
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoObtenerPosiblesDesinatarios_entoncesExcepcion() {
    }
// Añadido por Juan para leer usuarios
    @Test
    void leerUsuariosValido() throws UsuarioException{
        Usuario remitente = null;

        try {
            remitente= usuarioServicio.leerUsuario(1);
        }  catch (UsuarioException e) {
            throw new UsuarioException("Error de remitente: " + e.getMessage());
        }

        assertNotNull(remitente.getId());
        assertThat(remitente.getId(),greaterThan(0));

        Usuario destinatario = null;

        try {
            destinatario= usuarioServicio.leerUsuario(2);
        }  catch (UsuarioException e) {
            throw new UsuarioException("Error de destinatario: " + e.getMessage());
        }
        assertNotNull(destinatario.getId());
        assertThat(destinatario.getId(),greaterThan(0));

        System.out.println("Remitente: " + remitente.toString());
        System.out.println("Destinatario: " + destinatario.toString());

    }
}