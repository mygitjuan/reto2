package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.modelos.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class UsuarioJDBCRepositoryTest {
    @Autowired
    IUsuarioRepository repo;

    @Test
    void testBeans() {
        assertThat(repo, notNullValue());
        System.out.println(repo.toString());
    }

    @Test
    void dadoUnUsuarioValido_cuandoCrear_entoncesUsuarioValido() throws SQLException {
        Usuario u = new Usuario(null,"Persistencia2","persistencia2@email.com",LocalDate.now(),true);
        System.out.println(u);
        repo.crear(u);
        assertNotNull(u.getId());
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoCrear_entoncesExcepcion() throws SQLException {
        Usuario u = new Usuario(null,"Persistencia","persistencia&email.com",LocalDate.now(),true);
        System.out.println(u);

        assertThrows(Exception.class, () -> {
                repo.crear(u);
                }
        );
    }

    @Test
    void dadoUnUsuarioValido_cuandoActualizar_entoncesUsuarioValido() {
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoActualizar_entoncesExcepcion() {
    }

    @Test
    void dadoUnUsuarioValido_cuandoBorrar_entoncesOK() {
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoBorrar_entoncesExcepcion() {
    }

    @Test
    void dadoUnUsuarioValido_cuandoObtenerPosiblesDestinatarios_entoncesLista() {
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoObtenerPosiblesDestinatarios_entoncesExcepcion() {
    }
// Añadido por Juan para leer usuarios
    @Test
    void comprobarSelectUsuarioExistente () throws SQLException {
        Usuario u = repo.extraerUsuario(1);
        System.out.println(u);
        assertNotNull(u.getId());
    }

    @Test
    void comprobarSelectUsuarioNoExistente () throws SQLException {
        Usuario u = repo.extraerUsuario(3);
        assertNull(u);
    }
}