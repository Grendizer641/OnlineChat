package ru.korneev.server.chat;

import javax.imageio.IIOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientHandler {

    private Server server;
    private final Socket clientSocket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public ClientHandler(Socket clientSocket, Server server) {
        this.clientSocket = clientSocket;
        this.server = server;
    }

    public void handle() throws IOException {
        try {
            dataInputStream = new DataInputStream(clientSocket.getInputStream());
            dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            try {
                readMessages();
            } catch (IOException e) {
                System.err.println("Не удалось прочитать сообщение от клиента");
                e.printStackTrace();
            } finally {
                try {
                    closeConnection();
                } catch (IOException e) {
                    System.err.println("Не удалось закрыть соединение");
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void readMessages() throws IOException {
        while (true) {
            String message = dataInputStream.readUTF().trim();
            System.out.println("message = " + message);
            if (message.startsWith("/end")) {
                return;
            } else {
                processMessage(message);
            }
        }
    }

    private void processMessage(String message) throws IOException {
        this.server.broadCastMessage(message, this);
    }

    public void sendMessage(String message) throws IOException {
        this.dataOutputStream.writeUTF(message);
    }

    private void closeConnection() throws IOException {
        server.unsubscribe(this);
        clientSocket.close();
    }
}
