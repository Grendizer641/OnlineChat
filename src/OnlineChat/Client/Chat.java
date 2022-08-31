package OnlineChat.Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Chat extends Application {

    public static final String LOCALHOST = "localhost";
    public static final int PORT = 8189;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("Template.fxml"));

        Parent load = fxmlLoader.load();
        Scene scene = new Scene(load);

        stage.setTitle("Сетевой Чат");
        stage.setScene(scene);

        Controller controller = fxmlLoader.getController();
        controller.userList.getItems().addAll("user 1","user 2");

        stage.show();

        connectToServer();
    }

    private void connectToServer() {
        try(Socket socket = new Socket(LOCALHOST, PORT)) {

        } catch (UnknownHostException e){

        } catch (IOException e){

        }
    }

    public static void main(String[] args) {
        launch();
    }
}