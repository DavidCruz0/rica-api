package com.rica.rica_api.publicaciones;

public class LimiteAnualExcedidoException extends RuntimeException {
    public LimiteAnualExcedidoException(String mensaje) {
        super(mensaje);
    }
}
