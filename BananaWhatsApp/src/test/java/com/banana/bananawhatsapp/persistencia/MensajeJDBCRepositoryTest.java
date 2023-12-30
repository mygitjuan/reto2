package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;

class MensajeJDBCRepositoryTest {

    IMensajeRepository repoMen;
    IUsuarioRepository repoUsu;

    @Test
    void dadoUnMensajeValido_cuandoCrear_entoncesMensajeValido() {
        Usuario remitente = null;
        try {
            remitente = repoUsu.extraerUsuario(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Usuario destinatario = null;
        try {
            destinatario = repoUsu.extraerUsuario(2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Mensaje m = new Mensaje(null,remitente,destinatario,"Está es la prueba 1", LocalDate.now());

    }

    @Test
    void dadoUnMensajeNOValido_cuandoCrear_entoncesExcepcion() {
    }

    @Test
    void dadoUnUsuarioValido_cuandoObtener_entoncesListaMensajes() {
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