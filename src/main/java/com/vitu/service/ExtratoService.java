package com.vitu.service;

import com.vitu.domain.Cliente;
import com.vitu.domain.Extrato;
import com.vitu.domain.Saldo;
import com.vitu.domain.Transacao;
import com.vitu.exception.ClienteNaoEncontradoException;
import com.vitu.repository.TransacaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.List;

public class ExtratoService {

    private static final Logger logger = LoggerFactory.getLogger(ExtratoService.class);
    private final TransacaoRepository transacaoRepository = new TransacaoRepository();
    private final ClienteService clienteService = new ClienteService();

    public Extrato obterExtrato(int clienteId) throws SQLException, ClienteNaoEncontradoException {
        logger.debug("TheadId: {}", Thread.currentThread().threadId());
        logger.info("Obtendo extrato para o clienteId: {}", clienteId);
        Cliente cliente = clienteService.obterCliente(clienteId);
        logger.info("Cliente: {}", cliente);
        List<Transacao> transacoes = transacaoRepository.obterUltimasTransacoes(clienteId);
        logger.info("Transacoes: {}", transacoes);
        return new Extrato(new Saldo(cliente.saldo(), ZonedDateTime.now(), cliente.limite()), transacoes);
    }
}
