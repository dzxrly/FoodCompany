package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PasswordChangePaneController {
    @FXML
    private PasswordField oldPWText;
    @FXML
    private TextField newPWFirstInputText;
    @FXML
    private PasswordField newPWSecondInputText;
    @FXML
    private Button uploadBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private void initialize() {
        //TODO
    }

    @FXML
    private void handleUpload() {
        //TODO
    }

    @FXML
    private void handleCancel() {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }
}
