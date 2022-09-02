package OnlineChat.Client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.function.Consumer;

public class Controller {


    @FXML private TextArea textArea;
    @FXML private TextField textField;
    @FXML private Button send;
    @FXML public ListView<String> userList;

    private Network network;
    private Chat application;

    public void sendMessage(ActionEvent actionEvent) {
        String message = textField.getText().trim();

        if (message.isEmpty()){
            textField.clear();
            return;
        }

        String sender = null;
        if (!userList.getSelectionModel().isEmpty()){
            sender = userList.getSelectionModel().getSelectedItem();
        }



        try {
            message = sender != null ? String.join(": ", sender, message) : message;
            network.sendMessage(message);
        } catch (IOException e) {
            application.showError("Ошибка передачи данных по сети");
            e.printStackTrace();
        }

        appendMessageToChat(message, "Я");

    }

    private void appendTimeAndDate() {
        textArea.appendText(DateFormat.getDateTimeInstance().format(new Date()));
        textArea.appendText(System.lineSeparator());

    }

    private void appendMessageToChat(String message, String sender) {
        appendTimeAndDate();

        if(sender != null) {
            textArea.appendText(sender + ":");
            textArea.appendText((System.lineSeparator()));
        }

        textArea.appendText(message);
        textArea.appendText((System.lineSeparator()));
        textArea.appendText((System.lineSeparator()));
        textField.setFocusTraversable(true);
        textField.clear();
    }

    public void setNetwork(Network network) {
        this.network = network;

        network.readMessage(new Consumer<String>() {
            @Override
            public void accept(String message) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        appendMessageToChat(message, "Сервер");
                    }
                });
            }
        });
    }

    public void setApplication(Chat application) {
        this.application = application;
    }
}