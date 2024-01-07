package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class MensajeRepositoryTest {

    @Autowired
    IMensajeRepository repoMen;
    @Autowired
    IUsuarioRepository repoUsu;

    @Test
    void dadoUnMensajeValido_cuandoCrear_entoncesMensajeValido() throws SQLException{
        Usuario remitente = null;
        try {
            remitente = repoUsu.extraerUsuario(22);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        System.out.println("Remitente:" + remitente.toString());
        Usuario destinatario = null;
        try {
            destinatario = repoUsu.extraerUsuario(21);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        System.out.println("Destinatario:" + destinatario.toString());

        Mensaje m = new Mensaje(null,remitente,destinatario,"Está es la prueba repositorio", LocalDate.now());

        repoMen.crear(m);
        assertNotNull(m.getId());
        System.out.println("Mensaje:" + m.toString());
    }

    @Test
    void dadoUnMensajeNOValido_cuandoCrear_entoncesExcepcion() throws SQLException{
        Usuario remitente = null;
        try {
            remitente = repoUsu.extraerUsuario(1);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        System.out.println("Remitente:" + remitente.toString());
        Usuario destinatario = null;
        try {
            destinatario = repoUsu.extraerUsuario(2);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        System.out.println("Destinatario:" + destinatario.toString());

        Mensaje m = new Mensaje(null,remitente,destinatario,"¡Holi!", LocalDate.now());

        assertThrows(Exception.class, () -> {
                repoMen.crear(m);
                }
        );
    }

    @Test
    void dadoUnUsuarioValido_cuandoObtener_entoncesListaMensajes() throws SQLException{
        Usuario remitente = null;
        try {
            remitente = repoUsu.extraerUsuario(1);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        System.out.println("Remitente:" + remitente.toString());

        List<Mensaje> mensajeList = null;
        mensajeList = repoMen.obtener(remitente);
        assertNotNull(mensajeList);
        assertFalse(mensajeList.isEmpty());
        System.out.println("Lista de mensajes:" + mensajeList.toString());
    }

    @Test
    void dadoUnUsuarioValido_cuandoObtener_entoncesListaMensajesConcreto() throws SQLException{
        //añadido por juan para probar destinatario concreto
        Usuario remitente = null;
        try {
            remitente = repoUsu.extraerUsuario(1);
        } catch (SQLException e) {
            throw new SQLException(e);
        }

        assertNotNull(remitente.getId());
        assertThat(remitente.getId(),greaterThan(0));

        Usuario destinatario = null;
        try {
            destinatario = repoUsu.extraerUsuario(2);
        } catch (SQLException e) {
            throw new SQLException(e);
        }

        assertNotNull(destinatario.getId());
        assertThat(destinatario.getId(),greaterThan(0));

        List<Mensaje> mensajeList = null;
        mensajeList = repoMen.obtener(remitente,destinatario);
        assertNotNull(mensajeList);
        assertFalse(mensajeList.isEmpty());
        System.out.println("Lista de mensajes:" + mensajeList.toString());
    }


    @Test
    void dadoUnUsuarioNOValido_cuandoObtener_entoncesExcepcion() throws SQLException{
        Usuario remitente = null;
        try {
            remitente = repoUsu.extraerUsuario(18);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        System.out.println("Remitente:" + remitente.toString());

        final Usuario remitenteFinal = remitente;
        assertThrows(Exception.class, () -> {
                repoMen.obtener(remitenteFinal);
                }
        );

    }

    @Test
    void dadoUnUsuarioNOValido_cuandoObtener_entoncesExcepcionConcreto() throws SQLException{
        //añadido por juan para probar destinatario concreto
        Usuario remitente = null;
        try {
            remitente = repoUsu.extraerUsuario(18);
        } catch (SQLException e) {
            throw new SQLException(e);
        }

        assertNotNull(remitente.getId());
        assertThat(remitente.getId(),greaterThan(0));

        Usuario destinatario = null;
        try {
            destinatario = repoUsu.extraerUsuario(19);
        } catch (SQLException e) {
            throw new SQLException(e);
        }

        assertNull(destinatario);

        final Usuario remitenteFinal = remitente;
        final Usuario destinatarioFinal = destinatario;

        assertThrows(Exception.class, () -> {
                repoMen.obtener(remitenteFinal, destinatarioFinal);
                }
        );

    }


    @Test
    void dadoUnUsuarioValido_cuandoBorrarTodos_entoncesOK() throws SQLException{
        Usuario remitente = null;
        try {
            remitente = repoUsu.extraerUsuario(28);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        System.out.println("Remitente:" + remitente.toString());
        assertNotNull(remitente.getId());
        assertThat(remitente.getId(),greaterThan(0));

        Usuario destinatario = null;
        try {
            destinatario = repoUsu.extraerUsuario(27);
        } catch (SQLException e) {
            throw new SQLException(e);
        }

        assertNotNull(destinatario.getId());
        assertThat(destinatario.getId(),greaterThan(0));

        Boolean resp = false;
        resp = repoMen.borrarTodos(remitente,destinatario);
        assertTrue(resp);

    }

    @Test
    void dadoUnUsuarioNOValido_cuandoBorrarTodos_entoncesExcepcion() throws SQLException{
        Usuario remitente = null;
        try {
            remitente = repoUsu.extraerUsuario(22);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        System.out.println("Remitente:" + remitente.toString());
        assertNotNull(remitente.getId());
        assertThat(remitente.getId(),greaterThan(0));

        Usuario destinatario = null;
        try {
            destinatario = repoUsu.extraerUsuario(2);
        } catch (SQLException e) {
            throw new SQLException(e);
        }

        assertNotNull(destinatario.getId());
        assertThat(destinatario.getId(),greaterThan(0));

        final Usuario remitenteFinal = remitente;
        final Usuario destinatarioFinal = destinatario;

        assertThrows(Exception.class, () -> {
                repoMen.borrarTodos(remitenteFinal,destinatarioFinal);
                }
        );

    }



}

