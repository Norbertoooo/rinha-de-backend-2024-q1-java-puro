package com.vitu.service;

import com.vitu.domain.Cliente;
import com.vitu.domain.Transacao;
import com.vitu.domain.TransacaoResponse;
import com.vitu.exception.ClienteNaoEncontradoException;
import com.vitu.exception.SaldoInconsistenteException;
import com.vitu.repository.ClienteRepository;
import com.vitu.repository.TransacaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class TransacaoService {

    private static final Logger logger = LoggerFactory.getLogger(TransacaoService.class);
    private final ClienteRepository clienteRepository = new ClienteRepository();
    private final ClienteService clienteService = new ClienteService();
    private final TransacaoRepository transacaoRepository = new TransacaoRepository();

    public TransacaoResponse processarTransacao(Transacao transacao, int clienteId) throws SQLException,
            SaldoInconsistenteException, ClienteNaoEncontradoException {
        logger.debug("TheadId: {}", Thread.currentThread().threadId());

        long saldo;
        Cliente cliente = clienteService.obterCliente(clienteId);

        if ("c".equals(transacao.tipo())) {
            logger.info("Operação de crédito: {} - clienteId: {}", transacao, clienteId);
            saldo = cliente.saldo() + transacao.valor();
        } else {
            logger.info("Operação de débito: {} - clienteId: {}", transacao, clienteId);
            if (cliente.saldo() - transacao.valor() < cliente.limite() * -1) {
                logger.error("Erro: Saldo inconsistente - clienteId: {}", clienteId);
                throw new SaldoInconsistenteException();
            }
            saldo = cliente.saldo() - transacao.valor();
        }

        transacaoRepository.salvarTransacao(transacao, clienteId);

        clienteRepository.atualizarSaldo(saldo, clienteId);
        long saldoAtualizado = clienteRepository.obterSaldo(clienteId);

        return new TransacaoResponse(cliente.limite(), saldoAtualizado);
    }


}
