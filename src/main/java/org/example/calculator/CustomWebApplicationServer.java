package org.example.calculator;

import lombok.extern.slf4j.Slf4j;
import org.example.ClientRequestHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class CustomWebApplicationServer {
    private final int port;

    // Thread pool 크기 지정
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public CustomWebApplicationServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            log.info("[CustomWebApplicationServer] started {} port.", port);

            Socket clientSocket;
            log.info("[CustomWebApplicationServer] waiting for client.");

            while ((clientSocket = serverSocket.accept()) != null) {
                log.info("[CustomWebApplicationServer] client connected!");

                executorService.execute(new ClientRequestHandler(clientSocket));
            }
        }
    }
}
