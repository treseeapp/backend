package com.tresee.backend.controller;

import com.tresee.backend.enitty.ChatMensaje;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ChatController {

    @MessageMapping("/chat.send")
    @SendTo("/topic")
    public ChatMensaje enviarMensaje(@Payload ChatMensaje mensaje) {
        return mensaje;
    }

    @MessageMapping("/chat.register")
    @SendTo("/topic")
    public ChatMensaje registrarse(@Payload ChatMensaje mensaje, SimpMessageHeaderAccessor headerAccessor, HttpServletRequest request) {
        headerAccessor.getSessionAttributes().put("usuario", mensaje.getUsuario());
        return mensaje;
    }

}
