package com.banana.bananawhatsapp.persistencia;

import com.banana.bananawhatsapp.config.SpringConfig;
import com.banana.bananawhatsapp.exceptions.MensajeException;
import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringConfig.class})
@ActiveProfiles("dev")
class MensajeRepositoryTest {

    @Autowired
    IMensajeRepository repoMen;
    @Autowired
    IUsuarioRepository repoUsu;
    @Autowired
    private Environment environment;


    @Test
    void dadoUnMensajeValido_cuandoCrear_entoncesMensajeValido() throws SQLException{

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
        int identificadorRemitente = 0; /*prod*/
        int identificadorDestinatario = 0;/*prod*/

        if (soyProd) {
            identificadorRemitente = 22; /*prod*/
            identificadorDestinatario = 21;/*prod*/
        } else if (soyDev) {
            identificadorRemitente = 3; /*dev*/
            identificadorDestinatario = 4;/*dev*/
        }


        Usuario remitente = null;
        try {
            remitente = repoUsu.extraerUsuario(identificadorRemitente);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        System.out.println("Remitente:" + remitente.toString());
        Usuario destinatario = null;
        try {
            destinatario = repoUsu.extraerUsuario(identificadorDestinatario);
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
        int identificadorRemitente = 0; /*prod*/
        int identificadorDestinatario = 0;/*prod*/

        if (soyProd) {
            identificadorRemitente = 1; /*prod*/
            identificadorDestinatario = 2;/*prod*/
        } else if (soyDev) {
            identificadorRemitente = 3; /*dev*/
            identificadorDestinatario = 4;/*dev*/
        }


        Usuario remitente = null;
        try {
            remitente = repoUsu.extraerUsuario(identificadorRemitente);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        System.out.println("Remitente:" + remitente.toString());
        Usuario destinatario = null;
        try {
            destinatario = repoUsu.extraerUsuario(identificadorDestinatario);
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
        int identificadorRemitente = 0; /*prod*/

        if (soyProd) {
            identificadorRemitente = 1; /*prod*/

        } else if (soyDev) {
            identificadorRemitente = 1; /*dev*/
        }

        Usuario remitente = null;
        try {
            remitente = repoUsu.extraerUsuario(identificadorRemitente);
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
    void dadoUnUsuarioNOValido_cuandoObtener_entoncesExcepcion() throws SQLException{

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
        int identificadorRemitente = 0; /*prod*/


        if (soyProd) {
            identificadorRemitente = 18; /*prod*/

        } else if (soyDev) {
            identificadorRemitente = 18; /*dev*/
        }


        Usuario remitente = null;
        try {
            remitente = repoUsu.extraerUsuario(identificadorRemitente);
        } catch (UsuarioException e) {
            e.printStackTrace();
            throw e;
        } catch (MensajeException e) {
            e.printStackTrace();
            throw e;
        } catch (SQLException e) {
            throw new SQLException(e);
        }

        final Usuario remitenteFinal = remitente;
        assertThrows(Exception.class, () -> {
                repoMen.obtener(remitenteFinal);
                }
        );

    }


    @Test
    void dadoUnUsuarioValido_cuandoBorrarTodos_entoncesOK() throws SQLException{

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
        int identificadorRemitente = 0; /*prod*/
        int identificadorDestinatario = 0;/*prod*/

        if (soyProd) {
            identificadorRemitente = 28; /*prod*/
            identificadorDestinatario = 27;/*prod*/
        } else if (soyDev) {
            identificadorRemitente = 1; /*dev*/
            identificadorDestinatario = 2;/*dev*/
        }


        Usuario remitente = null;
        try {
            remitente = repoUsu.extraerUsuario(identificadorRemitente);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        System.out.println("Remitente:" + remitente.toString());
        assertNotNull(remitente.getId());
        assertThat(remitente.getId(),greaterThan(0));

        Usuario destinatario = null;
        try {
            destinatario = repoUsu.extraerUsuario(identificadorDestinatario);
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
        int identificadorRemitente = 0; /*prod*/
        int identificadorDestinatario = 0;/*prod*/

        if (soyProd) {
            identificadorRemitente = 22; /*prod*/
            identificadorDestinatario = 2;/*prod*/
        } else if (soyDev) {
            identificadorRemitente = 3; /*dev*/
            identificadorDestinatario = 5;/*dev*/
        }


        Usuario remitente = null;
        try {
            remitente = repoUsu.extraerUsuario(identificadorRemitente);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
        /*System.out.println("Remitente:" + remitente.toString());*/
        assertNotNull(remitente.getId());
        assertThat(remitente.getId(),greaterThan(0));

        Usuario destinatario = null;

        if (soyProd) {
            try {
                destinatario = repoUsu.extraerUsuario(identificadorDestinatario);
            } catch (SQLException e) {
              throw new SQLException(e);
            }
        } else if (soyDev) {

            int identificadorDestinatarioFinal = identificadorDestinatario;
            assertThrows(Exception.class, () -> {
                        repoUsu.extraerUsuario(identificadorDestinatarioFinal);
                    }
            );
        }

        if (soyProd) {
            assertNotNull(destinatario.getId());
            assertThat(destinatario.getId(),greaterThan(0));

            final Usuario remitenteFinal = remitente;
            final Usuario destinatarioFinal = destinatario;

            assertThrows(Exception.class, () -> {
                        repoMen.borrarTodos(remitenteFinal, destinatarioFinal);
                    }
            );
        } else if (soyDev) {
            System.out.println("soy dev");
            assertNull(destinatario);
            final Usuario remitenteFinal = remitente;
            final Usuario destinatarioFinal = destinatario;

            assertThrows(Exception.class, () -> {
                        repoMen.borrarTodos(remitenteFinal, destinatarioFinal);
                    }
            );




        }

    }

    @Test
    void dadoUnUsuarioNOValido_cuandoObtener_entoncesExcepcionConcreto() throws SQLException{
        //añadido por juan para probar destinatario concreto

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
        int identificadorRemitente = 0; /*prod*/
        int identificadorDestinatario = 0;/*prod*/

        if (soyProd) {
            identificadorRemitente = 18; /*prod*/
            identificadorDestinatario = 19;/*prod*/
        } else if (soyDev) {
            identificadorRemitente = 3; /*dev*/
            identificadorDestinatario = 5;/*dev*/
        }

        Usuario remitente = null;
        try {
            remitente = repoUsu.extraerUsuario(identificadorRemitente );
        } catch (SQLException e) {
            throw new SQLException(e);
        }

        assertNotNull(remitente.getId());
        assertThat(remitente.getId(),greaterThan(0));

        Usuario destinatario = null;


        if (soyDev) {
            final int identificadorDestinatarioFinal = identificadorDestinatario;

            assertThrows(Exception.class, () -> {
                    repoUsu.extraerUsuario(identificadorDestinatarioFinal);
                    }
            );
        }
        else if (soyProd) {

            try {
                destinatario = repoUsu.extraerUsuario(identificadorDestinatario);
            } catch (SQLException e) {
                throw new SQLException(e);
            }
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
    void dadoUnUsuarioValido_cuandoObtener_entoncesListaMensajesConcreto() throws SQLException{
        //añadido por juan para probar destinatario concreto

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
        int identificadorRemitente = 0; /*prod*/
        int identificadorDestinatario = 0;/*prod*/

        if (soyProd) {
            identificadorRemitente = 18; /*prod*/
            identificadorDestinatario = 19;/*prod*/
        } else if (soyDev) {
            identificadorRemitente = 1; /*dev*/
            identificadorDestinatario = 2;/*dev*/
        }


        Usuario remitente = null;
        try {
            remitente = repoUsu.extraerUsuario(identificadorRemitente);
        } catch (SQLException e) {
            throw new SQLException(e);
        }

        assertNotNull(remitente.getId());
        assertThat(remitente.getId(),greaterThan(0));

        Usuario destinatario = null;
        try {
            destinatario = repoUsu.extraerUsuario(identificadorDestinatario);
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



}

