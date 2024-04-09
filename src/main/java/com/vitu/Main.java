package com.vitu;

import com.sun.net.httpserver.HttpServer;
import com.vitu.handler.ClienteHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        int porta = 8080;
        String appPort = System.getenv("APP_PORT");
        if (Objects.nonNull(appPort) && Boolean.FALSE.equals(appPort.isEmpty()))
            porta = Integer.parseInt(appPort);

        try {
            HttpServer httpServer = HttpServer.create(new InetSocketAddress(porta), 0);

            //httpServer.setExecutor(Executors.newFixedThreadPool(100));

            ExecutorService executorService = Executors.newFixedThreadPool(100);

            httpServer.setExecutor(executorService);

            httpServer.createContext("/clientes", new ClienteHandler());

            httpServer.start();
            logger.info("Aplicação iniciada na porta: " + porta);
        } catch (Exception exception) {
            logger.error("Erro ao subir aplicação: ", exception);
        }
    }
}