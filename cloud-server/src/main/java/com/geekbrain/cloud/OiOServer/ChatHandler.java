package com.geekbrain.cloud.OiOServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ChatHandler implements Runnable {

    // папка сервера
    private String serverDir = "server_files";

    private DataInputStream is;
    private DataOutputStream os;

    public ChatHandler(Socket socket) throws IOException {
        is = new DataInputStream(socket.getInputStream());
        os = new DataOutputStream(socket.getOutputStream());
        System.out.println("Client accepted");
    }

    @Override
    public void run() {
        try {
            while (true) {
                String msg = is.readUTF();// считываем сообщение в String
                System.out.println("received " + msg);
                os.writeUTF(msg);// отправляем сообщение в буфер InputStream-а
                os.flush();// очищаем буфер

            }
        } catch (Exception e) {
            // бесконечный цикл делаем внутри try/catch потому что если в бесконечном цикле мы попадем на Exception, то бесконечно будет выводиться "Connection was broken"
            System.err.println("Connection was broken");
        }
    }
}
