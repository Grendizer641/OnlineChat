package ru.korneev.client;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class Chat extends Application {

    public static final String LOCALHOST = "localhost";
    public static final int PORT = 8189;

    private Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("chatTemplate.fxml"));

        Parent load = fxmlLoader.load();
        Scene scene = new Scene(load);

        this.stage.setTitle("Сетевой Чат");
        this.stage.setScene(scene);

        Controller controller = fxmlLoader.getController();
        controller.userList.getItems().addAll("user 1", "user 2");

        stage.show();

        connectToServer(controller);
    }

    private void connectToServer(Controller controller) {
        Network network = new Network(LOCALHOST, PORT);
        boolean resultOfConnection = network.connect();

        if (!resultOfConnection) {
            String errorMessage = "Невозможно установить сетевое соединение";
            System.err.println(errorMessage);
            showError(errorMessage);
            return;
        }

        controller.setNetwork(network);
        controller.setApplication(this);

        this.stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                network.close();
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }

    public void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ошибка");
        alert.setContentText(message);
        alert.showAndWait();
    }
}