package com.vitu.repository;

import com.vitu.config.Database;
import com.vitu.domain.Cliente;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class ClienteRepository {

    private static final Logger logger = LoggerFactory.getLogger(ClienteRepository.class);

    public Optional<Cliente> obterCliente(int clienteId) throws SQLException {
        logger.debug("TheadId: {}", Thread.currentThread().threadId());

        try (Connection connection = Database.getConnection()) {
            String clienteQuery = "select * from cliente where id = ? for update";

            PreparedStatement preparedStatement = connection.prepareStatement(clienteQuery);
            preparedStatement.setLong(1, clienteId);
            ResultSet resultSet = preparedStatement.executeQuery();

            long id = 0, saldo = 0, limite = 0;

            while (resultSet.next()) {
                id = resultSet.getInt("id");
                saldo = resultSet.getInt("saldo");
                limite = resultSet.getInt("limite");
            }

            connection.commit();
            return id == 0 ? Optional.empty() : Optional.of(new Cliente(id, saldo, limite));
        } catch (SQLException sqlException) {
            logger.error("Erro: ", sqlException);
            throw sqlException;
        }

    }

    public void atualizarSaldo(Long saldo, int clienteId) throws SQLException {
        logger.debug("TheadId: {}", Thread.currentThread().threadId());

        try (Connection connection = Database.getConnection()) {

            String updateClienteSaldo = "update cliente set saldo = ? where id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(updateClienteSaldo);
            preparedStatement.setLong(1, saldo);
            preparedStatement.setLong(2, clienteId);
            preparedStatement.executeUpdate();

            connection.commit();

            logger.info("Saldo atualizado com sucesso! - clienteId: {}", clienteId);
        } catch (SQLException sqlException) {
            logger.error("Erro: ", sqlException);
            throw sqlException;
        }
    }

    public long obterSaldo(int clienteId) throws SQLException {
        logger.debug("TheadId: {}", Thread.currentThread().threadId());

        try (Connection connection = Database.getConnection()) {

            String clienteQuery = "select saldo from cliente where id = ? for update";

            PreparedStatement preparedStatement = connection.prepareStatement(clienteQuery);
            preparedStatement.setLong(1, clienteId);

            ResultSet resultSet = preparedStatement.executeQuery();

            long saldo = 0;

            while (resultSet.next()) {
                saldo = resultSet.getLong("saldo");
            }

            connection.commit();

            return saldo;
        } catch (SQLException sqlException) {
            logger.error("Erro: ", sqlException);
            throw sqlException;
        }
    }
}
