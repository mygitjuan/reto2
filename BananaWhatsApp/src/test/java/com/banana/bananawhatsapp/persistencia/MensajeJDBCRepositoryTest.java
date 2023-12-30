package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class MensajeJDBCRepositoryTest {

    IMensajeRepository repo;

    @Test
    void dadoUnMensajeValido_cuandoCrear_entoncesMensajeValido() {
        /*Usuario remitente = new Usuario()
        Mensaje m = new Mensaje(null,null,null,"Prueba 1", LocalDate.now());*/
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