package com.banana.bananawhatsapp.servicios;

import com.banana.bananawhatsapp.exceptions.MensajeException;
import com.banana.bananawhatsapp.exceptions.UsuarioException;
import com.banana.bananawhatsapp.modelos.Mensaje;
import com.banana.bananawhatsapp.modelos.Usuario;
import com.banana.bananawhatsapp.persistencia.IMensajeRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

import java.sql.SQLException;
import java.util.List;

@Setter
@Getter
@ToString
@Service
public class ServicioMensajeria implements IServicioMensajeria{


    @Autowired
    IMensajeRepository mensaRep;

    @Override
    public Mensaje enviarMensaje(Usuario remitente, Usuario destinatario, String texto) throws UsuarioException, MensajeException {

        Mensaje mensaje = new Mensaje(null,remitente,destinatario, texto, LocalDate.now());

        try {
            mensaRep.crear(mensaje);
        } catch (SQLException e) {
            throw new MensajeException("Error de mensaje: " + e.getMessage());
        }

        return mensaje;
    }

    @Override
    public List<Mensaje> mostrarChatConUsuario(Usuario remitente, Usuario destinatario) throws UsuarioException, MensajeException {
        List<Mensaje> mensajeList = null;

        try {
            /*mensajeList = mensaRep.obtener(remitente);*/
            mensajeList = mensaRep.obtener(remitente,destinatario);

        } catch (SQLException e) {
            throw new MensajeException("Error de chat con usuario: " + e.getMessage());
        }
        return mensajeList;
    }

    @Override
    public boolean borrarChatConUsuario(Usuario remitente, Usuario destinatario) throws UsuarioException, MensajeException {
        Boolean resp = false;
        try {
            resp = mensaRep.borrarTodos(remitente,destinatario);
        } catch (SQLException e) {
            throw new MensajeException("Error delete chat con usuario: " + e.getMessage());
        }
        return resp;
    }
    //añado el siguiente metodo para controlar el borrado de mensajes cuando se da de baja el usuario
    @Override
    public boolean borrarChatConUsuario(Usuario remitente) throws UsuarioException, MensajeException {
        Boolean resp = false;
        try {
            resp = mensaRep.borrarTodos(remitente);
        } catch (SQLException e) {
            throw new MensajeException("Error delete chat con usuario: " + e.getMessage());
        }
        return resp;
    }

}
