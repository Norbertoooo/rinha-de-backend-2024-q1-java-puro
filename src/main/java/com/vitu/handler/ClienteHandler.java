package com.vitu.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.vitu.domain.Extrato;
import com.vitu.domain.Transacao;
import com.vitu.domain.TransacaoResponse;
import com.vitu.exception.ClienteNaoEncontradoException;
import com.vitu.exception.CorpoInvalidoException;
import com.vitu.service.ExtratoService;
import com.vitu.service.TransacaoService;
import com.vitu.validation.ValidationRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class ClienteHandler implements HttpHandler {

    public static final String GET_METHOD = "GET";
    public static final String POST_METHOD = "POST";
    public static final String EXTRATO_PATH = "extrato";
    public static final String TRANSACOES_PATH = "transacoes";
    private final ExtratoService extratoService = new ExtratoService();
    private final TransacaoService transacaoService = new TransacaoService();
    private static final Logger logger = LoggerFactory.getLogger(ClienteHandler.class);
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .registerModule(new Jdk8Module());

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        logger.debug("TheadId: {}", Thread.currentThread().threadId());

        URI requestURI = exchange.getRequestURI();

        logger.info("Recebendo requisição - método: {} - caminho: {}", exchange.getRequestMethod(), requestURI);

        String[] path = requestURI.normalize().toString().split("/");

        int clienteId = Integer.parseInt(path[2]);

        if (GET_METHOD.equals(exchange.getRequestMethod()) && EXTRATO_PATH.equals(path[3])) {
            try {
                Extrato extrato = extratoService.obterExtrato(clienteId);
                this.sendResponse(exchange, 200, extrato);
                return;
            } catch (SQLException e) {
                logger.error("Erro: ", e);
                this.returnUnprocessableEntity(exchange);
                return;
            } catch (ClienteNaoEncontradoException e) {
                logger.error("Erro: ", e);
                this.returnNotFound(exchange);
                return;
            }
        }

        if (POST_METHOD.equals(exchange.getRequestMethod()) && TRANSACOES_PATH.equals(path[3])) {
            try {
                Transacao transacao = this.obterRequestBody(exchange);

                ValidationRequest.validate(transacao);

                TransacaoResponse transacaoResponse = transacaoService.processarTransacao(transacao, clienteId);

                this.sendResponse(exchange, 200, transacaoResponse);
            } catch (SQLException | CorpoInvalidoException | IOException e) {
                logger.error("Erro: ", e);
                this.returnUnprocessableEntity(exchange);
            } catch (ClienteNaoEncontradoException e) {
                logger.error("Erro: ", e);
                this.returnNotFound(exchange);
            }
        }

    }

    private void sendResponse(HttpExchange exchange, int httpStatus, Object object) throws IOException {
        OutputStream responseBody = exchange.getResponseBody();

        String objectAsString = objectMapper.writeValueAsString(object);
        exchange.getResponseHeaders().set("Content-Type", "application/json");

        exchange.sendResponseHeaders(httpStatus, objectAsString.getBytes().length);
        responseBody.write(objectAsString.getBytes(StandardCharsets.UTF_8));
        responseBody.close();
        exchange.close();
    }

    private Transacao obterRequestBody(HttpExchange exchange) throws IOException {
        InputStream requestBody = exchange.getRequestBody();
        Transacao transacao = objectMapper.readValue(requestBody, Transacao.class);
        logger.info("Corpo da requisicao: {}", transacao);
        return transacao;
    }

    private void returnNotFound(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(404, -1);
        exchange.close();
    }

    private void returnUnprocessableEntity(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(422, -1);
        exchange.close();
    }
}
