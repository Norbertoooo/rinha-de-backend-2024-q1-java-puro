package com.vitu.service;

import com.vitu.domain.Cliente;
import com.vitu.exception.ClienteNaoEncontradoException;
import com.vitu.repository.ClienteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class ClienteService {

    private static final Logger logger = LoggerFactory.getLogger(ClienteService.class);
    private final ClienteRepository clienteRepository = new ClienteRepository();

    public Cliente obterCliente(int clienteId) throws SQLException {
        logger.debug("TheadId: {}", Thread.currentThread().threadId());
        logger.info("Obtendo cliente pelo id: {}", clienteId);
        return clienteRepository.obterCliente(clienteId).orElseThrow(()-> new ClienteNaoEncontradoException(clienteId));
    }
}
