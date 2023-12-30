package com.banana.bananawhatsapp.controladores;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Usuario;
import lombok.experimental.Accessors;
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

    @Test
    void dadoUsuarioValido_cuandoAlta_entoncesUsuarioValido() {
        Usuario u = new Usuario(null,"Controlador","controlador@email.com", LocalDate.now(),true);
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
    void dadoUsuarioValido_cuandoActualizar_entoncesUsuarioValido() {
    }

    @Test
    void dadoUsuarioNOValido_cuandoActualizar_entoncesExcepcion() {
    }

    @Test
    void dadoUsuarioValido_cuandoBaja_entoncesUsuarioValido() {
    }

    @Test
    void dadoUsuarioNOValido_cuandoBaja_entoncesExcepcion() {
    }

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