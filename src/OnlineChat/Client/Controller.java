package OnlineChat.Client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {


    @FXML private TextArea textArea;
    @FXML private TextField textField;
    @FXML private Button send;
    @FXML public ListView<String> userList;

    public void sendMessage(ActionEvent actionEvent) {
        if (!textField.getText().isEmpty()) {
            textArea.appendText(textField.getText());
            textArea.appendText(System.lineSeparator());
            textField.clear();
        }
    }
}