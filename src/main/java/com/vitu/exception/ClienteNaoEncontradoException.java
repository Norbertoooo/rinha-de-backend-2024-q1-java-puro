package com.vitu.exception;

public class ClienteNaoEncontradoException extends RuntimeException {
    public ClienteNaoEncontradoException(int clienteId) {
        super("Cliente não encontrado para o id: " + clienteId);
    }
}
