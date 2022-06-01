package com.geekbrain.cloud.OiOServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    public static void main(String[] args) throws IOException {

        // try with resources
        try (ServerSocket server = new ServerSocket(8189)) {
            System.out.println("Server started");
            while(true) {
                Socket socket = server.accept();// ждем соединения
                System.out.println("User connected");
                ChatHandler handler = new ChatHandler(socket);// соединение отдаем в хендлер
                new Thread(handler).start();
            }
        }
    }
}
