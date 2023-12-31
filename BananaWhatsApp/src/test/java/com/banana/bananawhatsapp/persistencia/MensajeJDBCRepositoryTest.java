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
import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class MensajeJDBCRepositoryTest {

    @Autowired
    IMensajeRepository repoMen;
    @Autowired
    IUsuarioRepository repoUsu;

    @Test
    void dadoUnMensajeValido_cuandoCrear_entoncesMensajeValido() throws SQLException{
        Usuario remitente = null;
        try {
            remitente = repoUsu.extraerUsuario(18);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        System.out.println("Remitente:" + remitente.toString());
        Usuario destinatario = null;
        try {
            destinatario = repoUsu.extraerUsuario(19);
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
    void dadoUnUsuarioNOValido_cuandoObtener_entoncesExcepcion() {
    }

    @Test
    void dadoUnUsuarioValido_cuandoBorrarTodos_entoncesOK() {
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoBorrarTodos_entoncesExcepcion() {
    }



}

