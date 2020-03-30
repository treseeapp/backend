package com.tresee.backend.enitty;

import com.tresee.backend.enitty.enums.TipoChatMensaje;

public class ChatMensaje {
    private String contenido;
    private TipoChatMensaje tipo;
    private String usuario;

    public ChatMensaje() {
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public TipoChatMensaje getTipo() {
        return tipo;
    }

    public void setTipo(TipoChatMensaje tipo) {
        this.tipo = tipo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
