package ru.korneev.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.function.Consumer;

public class Network<data> {

    public static final String LOCALHOST = "localhost";
    public static final int PORT = 8189;

    private int port;
    private String host;
    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public Network(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public Network() {
        this(LOCALHOST, PORT);
    }

    public boolean connect() {
        try {
            this.socket = new Socket(this.host, this.port);

            this.dataInputStream = new DataInputStream(socket.getInputStream());
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Ну удалось установить соединение");
            return false;
        }
    }

    public void sendMessage(String message) throws IOException {
        try {
            dataOutputStream.writeUTF(message);
        } catch (IOException e) {
            System.err.println("Не удалось отправить сообщение");
            throw e;
        }
    }

    public void readMessage(Consumer<String> messageHandler) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        String message = dataInputStream.readUTF();
                        messageHandler.accept(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.err.println(("Не удалось прочитать сообщение"));
                    }
                }
            }

        });
        thread.setDaemon(true);
        thread.start();
    }


    public void close() {
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

