package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.config.SpringConfig;

import com.banana.bananawhatsapp.modelos.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
class UsuarioJDBCRepositoryTest {
    @Autowired
    IUsuarioRepository usuarioRepo;

    @Autowired
    IMensajeRepository mensajeRepo;

    @Test
    void testBeans() {
        assertThat(usuarioRepo, notNullValue());
        System.out.println(usuarioRepo.toString());
    }

    @Test
    void dadoUnUsuarioValido_cuandoCrear_entoncesUsuarioValido() throws SQLException {
        Usuario u = new Usuario(null,"Persistencia3","persistencia3@email.com",LocalDate.now(),true);
        System.out.println(u);
        usuarioRepo.crear(u);
        assertNotNull(u.getId());
    }

    @Test
    void dadoUnUsuarioNOValido_cuandoCrear_entoncesExcepcion() throws SQLException {
        Usuario u = new Usuario(null,"Persistencia","persistencia&email.com",LocalDate.now(),true);
        System.out.println(u);

        assertThrows(Exception.class, () -> {
                usuarioRepo.crear(u);
                }
        );
    }

    @Test
    void dadoUnUsuarioValido_cuandoBorrar_entoncesOK() throws SQLException{
        Integer identificador = 33;
        Usuario u = usuarioRepo.extraerUsuario(identificador);
        System.out.println(u);
        assertNotNull(u.getId());
        assertThat(u.getId(),greaterThan(0));

        Boolean mensajeDelete = false;
        mensajeDelete = mensajeRepo.borrarTodos(u);
        assertTrue(mensajeDelete);

        Boolean usuarioDelete = false;
        usuarioDelete = usuarioRepo.borrar(u);
        assertTrue(usuarioDelete);

        u = usuarioRepo.extraerUsuario(identificador);
        assertNull(u);

    }

    @Test
    void dadoUnUsuarioNOValido_cuandoBorrar_entoncesExcepcion() throws SQLException{
        final Integer identificador = 32;

        assertThrows(Exception.class, () -> {
                usuarioRepo.extraerUsuario(identificador);
                }
        );


        final Usuario uFinal = null;

        assertThrows(Exception.class, () -> {
                mensajeRepo.borrarTodos(uFinal);
                }
        );

        assertThrows(Exception.class, () -> {
                usuarioRepo.borrar(uFinal);
                }
        );


    }

//Juan: muevo actualizar después de los test de Baja para tenerlos en orden de casos de usuario
    @Test
    void dadoUnUsuarioValido_cuandoActualizar_entoncesUsuarioValido() throws SQLException{
        Integer identificador = 41;
        Usuario u = usuarioRepo.extraerUsuario(identificador);
        System.out.println(u);
        assertNotNull(u.getId());
        assertThat(u.getId(),greaterThan(0));

        u.setNombre("Pegaso");
        u.setEmail("pegaso@p.com");
        u.setAlta(LocalDate.now());
        u.setActivo(true);

        Boolean usuarioValido = u.valido();
        assertTrue(usuarioValido);

        Usuario usuarioActualizado = usuarioRepo.actualizar(u);
        assertNotNull(usuarioActualizado);
        assertEquals(usuarioActualizado.getId(), u.getId());

    }

    @Test
    void dadoUnUsuarioNOValido_cuandoActualizar_entoncesExcepcion() throws SQLException{
        Integer identificador = 41;
        Usuario u = usuarioRepo.extraerUsuario(identificador);
        System.out.println(u);
        assertNotNull(u.getId());
        assertThat(u.getId(),greaterThan(0));

        u.setNombre("Pegaso");
        u.setEmail("pegaso#p.com");
        u.setAlta(LocalDate.now());
        u.setActivo(true);

        assertThrows(Exception.class, () -> {
                usuarioRepo.actualizar(u);
                }
        );

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
        Usuario u = usuarioRepo.extraerUsuario(1);
        System.out.println(u);
        assertNotNull(u.getId());
    }

    @Test
    void comprobarSelectUsuarioNoExistente () throws SQLException {
        Usuario u = usuarioRepo.extraerUsuario(3);
        assertNull(u);
    }
}