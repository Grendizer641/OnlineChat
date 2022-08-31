package OnlineChat.Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

    public static final int PORT = 8189;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT);){
            System.out.println("Сервер начал работу, ожидается подключение");
            Socket clientSocket = serverSocket.accept();
            DataInputStream dataInputStream = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
            System.out.println("Клиент подключился");

            while (true) {
                String message = dataInputStream.readUTF();
                if (message.equals("/end")) {
                    break;
                }
                dataOutputStream.writeUTF("Echo " + message);
            }
        } catch (IOException e) {
            System.err.println("Ошибка при подключении к порту " + PORT);
            e.printStackTrace();
        }
    }
}
