package ru.korneev.server.chat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {

    private final List<ClientHandler> clients = new ArrayList<>();

    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Сервер запущен");

            while (true) {
                waitAndProcessClientConnection(serverSocket);
            }
        } catch (IOException e) {

        }
    }

    private void waitAndProcessClientConnection(ServerSocket serverSocket) throws IOException {
        System.out.println("Ожидание подключения клиентов");
        Socket clientSocket = serverSocket.accept();
        System.out.println("Клиент подключился");
        ClientHandler clientHandler = new ClientHandler(clientSocket, this);
        this.clients.add(clientHandler);
        clientHandler.handle();

    }

    public void broadCastMessage(String message, ClientHandler sender) throws IOException {
        for (ClientHandler client : clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }

    public void unsubscribe(ClientHandler clientHandler) {
        this.clients.remove(clientHandler);
    }
}

