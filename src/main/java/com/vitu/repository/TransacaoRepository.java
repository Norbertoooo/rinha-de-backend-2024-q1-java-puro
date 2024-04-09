package com.vitu.repository;

import com.vitu.config.Database;
import com.vitu.domain.Transacao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransacaoRepository {

    private static final Logger logger = LoggerFactory.getLogger(TransacaoRepository.class);

    public List<Transacao> obterUltimasTransacoes(int clienteId) throws SQLException {
        logger.debug("TheadId: {}", Thread.currentThread().threadId());
        try (Connection connection = Database.getConnection()) {
            String extratoQuery = "select * from transacao where cliente_id = ? order by realizado_em desc limit 10";

            PreparedStatement preparedStatement = connection.prepareStatement(extratoQuery);
            preparedStatement.setLong(1, clienteId);
            ResultSet resultSet = preparedStatement.executeQuery();

            List<Transacao> transacoes = new ArrayList<>();

            while (resultSet.next()) {
                int valor = resultSet.getInt("valor");
                String tipo = resultSet.getString("tipo");
                String descricao = resultSet.getString("descricao");
                Timestamp realizadoEm = resultSet.getTimestamp("realizado_em");
                Transacao transacao = new Transacao(valor, tipo, descricao, ZonedDateTime.of(realizadoEm.toLocalDateTime(), ZoneId.of("America/Sao_Paulo")));
                transacoes.add(transacao);
            }
            connection.commit();

            return transacoes;
        } catch (SQLException sqlException) {
            logger.error("Erro: ", sqlException);
            throw sqlException;
        }
    }

    public void salvarTransacao(Transacao transacao, int clienteId) throws SQLException {
        logger.debug("TheadId: {}", Thread.currentThread().threadId());
        try (Connection connection = Database.getConnection()) {

            String insertTransacao = "insert into transacao(valor,tipo,descricao,realizado_em,cliente_id) values(?,?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(insertTransacao);
            preparedStatement.setLong(1, transacao.valor());
            preparedStatement.setString(2, transacao.tipo());
            preparedStatement.setString(3, transacao.descricao());
            preparedStatement.setTimestamp(4, Timestamp.from(Instant.now()));
            preparedStatement.setLong(5, clienteId);

            int i = preparedStatement.executeUpdate();

            connection.commit();

            logger.info("{}", i);

        } catch (SQLException sqlException) {
            logger.error("Erro: ", sqlException);
            throw sqlException;
        }
    }
}
