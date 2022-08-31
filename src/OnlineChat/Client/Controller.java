package OnlineChat.Client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.function.Consumer;

public class Controller {


    @FXML private TextArea textArea;
    @FXML private TextField textField;
    @FXML private Button send;
    @FXML public ListView<String> userList;

    private Network network;

    private Chat application;


    public void sendMessage(ActionEvent actionEvent) {
        String message = textField.getText();
        appendMessageToChat(message);

        try {
            network.sendMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void appendMessageToChat(String message) {
        if (!message.isEmpty()) {
            textArea.appendText(message);
            textArea.appendText(System.lineSeparator());
            textField.clear();
        }
    }

    public void setNetwork(Network network) {
        this.network = network;

        network.readMessage(new Consumer<String>() {
            @Override
            public void accept(String message) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        appendMessageToChat(message);
                    }
                });
            }
        });
    }

    public void setApplication(Chat application) {
        this.application = application;
    }
}