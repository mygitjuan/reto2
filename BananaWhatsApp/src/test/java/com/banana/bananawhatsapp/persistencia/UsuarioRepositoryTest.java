package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.config.SpringConfig;

import com.banana.bananawhatsapp.modelos.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
@ActiveProfiles("prod")
class UsuarioRepositoryTest {
    @Autowired
    IUsuarioRepository usuarioRepo;

    @Autowired
    IMensajeRepository mensajeRepo;

    @Autowired
    private Environment environment;
    @Test
    void testBeans() {
        assertThat(usuarioRepo, notNullValue());
        System.out.println(usuarioRepo.toString());
    }

    @Test
    void dadoUnUsuarioValido_cuandoCrear_entoncesUsuarioValido() throws SQLException {
        Usuario u = new Usuario(null,"Persistencia3","persistencia3@email.com",LocalDate.now(),true);
        usuarioRepo.crear(u);
        System.out.println(u);
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

        Boolean soyDev = false;
        Boolean soyProd = false;
        if ((Arrays.stream(environment.getActiveProfiles()).anyMatch(
                env -> (env.equalsIgnoreCase("dev"))))) {

            soyDev = true;
        }
        else if ((Arrays.stream(environment.getActiveProfiles()).anyMatch(
                env -> (env.equalsIgnoreCase("prod"))))) {

            soyProd = true;
        }
        int identificador = 0;

        if (soyProd) {
            identificador = 33; /*prod*/

        } else if (soyDev) {
            identificador = 1; /*dev*/
        }


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

        Boolean soyDev = false;
        Boolean soyProd = false;
        if ((Arrays.stream(environment.getActiveProfiles()).anyMatch(
                env -> (env.equalsIgnoreCase("dev"))))) {

            soyDev = true;
        }
        else if ((Arrays.stream(environment.getActiveProfiles()).anyMatch(
                env -> (env.equalsIgnoreCase("prod"))))) {

            soyProd = true;
        }
        int identificador = 0;

        if (soyProd) {
            identificador = 32; /*prod*/

        } else if (soyDev) {
            identificador = 5; /*dev*/
        }

        final Integer identificadorFinal = identificador;


        assertThrows(Exception.class, () -> {
                usuarioRepo.extraerUsuario(identificadorFinal);
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

        Boolean soyDev = false;
        Boolean soyProd = false;
        if ((Arrays.stream(environment.getActiveProfiles()).anyMatch(
                env -> (env.equalsIgnoreCase("dev"))))) {

            soyDev = true;
        }
        else if ((Arrays.stream(environment.getActiveProfiles()).anyMatch(
                env -> (env.equalsIgnoreCase("prod"))))) {

            soyProd = true;
        }
        int identificador = 0;

        if (soyProd) {
            identificador = 41; /*prod*/

        } else if (soyDev) {
            identificador = 2; /*dev*/
        }

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
        System.out.println("Usuario actualizado:"+u.toString());

    }

    @Test
    void dadoUnUsuarioNOValido_cuandoActualizar_entoncesExcepcion() throws SQLException{

        Boolean soyDev = false;
        Boolean soyProd = false;
        if ((Arrays.stream(environment.getActiveProfiles()).anyMatch(
                env -> (env.equalsIgnoreCase("dev"))))) {

            soyDev = true;
        }
        else if ((Arrays.stream(environment.getActiveProfiles()).anyMatch(
                env -> (env.equalsIgnoreCase("prod"))))) {

            soyProd = true;
        }
        int identificador = 0;

        if (soyProd) {
            identificador = 41; /*prod*/

        } else if (soyDev) {
            identificador = 4; /*dev*/
        }


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
    void dadoUnUsuarioValido_cuandoObtenerPosiblesDestinatarios_entoncesLista() throws SQLException{

        Boolean soyDev = false;
        Boolean soyProd = false;
        if ((Arrays.stream(environment.getActiveProfiles()).anyMatch(
                env -> (env.equalsIgnoreCase("dev"))))) {

            soyDev = true;
        }
        else if ((Arrays.stream(environment.getActiveProfiles()).anyMatch(
                env -> (env.equalsIgnoreCase("prod"))))) {

            soyProd = true;
        }
        int identificador = 0;

        if (soyProd) {
            identificador = 1; /*prod*/

        } else if (soyDev) {
            identificador = 1; /*dev*/
        }

        Usuario u = usuarioRepo.extraerUsuario(identificador);
        System.out.println("Remitente:"+u.toString());
        assertNotNull(u.getId());
        assertThat(u.getId(),greaterThan(0));

        Set<Usuario> destinatarios = null;
        Integer max = 3;

        destinatarios = usuarioRepo.obtenerPosiblesDestinatarios(u.getId(),max);
        assertNotNull(destinatarios);
        System.out.println("Destinatarios:\n");
        destinatarios.forEach(System.out::println);


    }

    @Test
    void dadoUnUsuarioNOValido_cuandoObtenerPosiblesDestinatarios_entoncesExcepcion() throws SQLException{

        Boolean soyDev = false;
        Boolean soyProd = false;
        if ((Arrays.stream(environment.getActiveProfiles()).anyMatch(
                env -> (env.equalsIgnoreCase("dev"))))) {

            soyDev = true;
        }
        else if ((Arrays.stream(environment.getActiveProfiles()).anyMatch(
                env -> (env.equalsIgnoreCase("prod"))))) {

            soyProd = true;
        }
        int identificador = 0;

        if (soyProd) {
            identificador = 1; /*prod*/

        } else if (soyDev) {
            identificador = 1; /*dev*/
        }


        Usuario u = usuarioRepo.extraerUsuario(identificador);
        System.out.println("Remitente:"+u.toString());
        assertNotNull(u.getId());
        assertThat(u.getId(),greaterThan(0));

        //el usuario con id=19 no está activo, por lo que no es válido.
        final Usuario remitente = u;
        /*final Integer max = 10;*//*prod*/
        final Integer max = 5;/*dev*/

        assertThrows(Exception.class, () -> {
                usuarioRepo.obtenerPosiblesDestinatarios(remitente.getId(),max);
                }
        );


    }
// Añadido por Juan para leer usuarios
    @Test
    void comprobarSelectUsuarioExistente () throws SQLException {
        Boolean soyDev = false;
        Boolean soyProd = false;
        if ((Arrays.stream(environment.getActiveProfiles()).anyMatch(
                env -> (env.equalsIgnoreCase("dev"))))) {

            soyDev = true;
        }
        else if ((Arrays.stream(environment.getActiveProfiles()).anyMatch(
                env -> (env.equalsIgnoreCase("prod"))))) {

            soyProd = true;
        }
        int identificador = 0;

        if (soyProd) {
            identificador = 1; /*prod*/

        } else if (soyDev) {
            identificador = 1; /*dev*/
        }
        Usuario u = usuarioRepo.extraerUsuario(identificador);
        System.out.println(u);
        assertNotNull(u.getId());
    }

    @Test
    void comprobarSelectUsuarioNoExistente () throws SQLException {

        Boolean soyDev = false;
        Boolean soyProd = false;
        if ((Arrays.stream(environment.getActiveProfiles()).anyMatch(
                env -> (env.equalsIgnoreCase("dev"))))) {

            soyDev = true;
        }
        else if ((Arrays.stream(environment.getActiveProfiles()).anyMatch(
                env -> (env.equalsIgnoreCase("prod"))))) {

            soyProd = true;
        }
        int identificador = 0;

        if (soyProd) {
            identificador = 3; /*prod*/

        } else if (soyDev) {
            identificador = 13; /*dev*/
        }

        Usuario u = usuarioRepo.extraerUsuario(identificador);
        assertNull(u);
    }
}