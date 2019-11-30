package view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.io.IOException;

public class userLoginPaneController {
    //登陆页面控制类
    private String userNumber;
    private String userPassword;
    @FXML
    private TextField inputNumber;
    @FXML
    private PasswordField inputPW;
    @FXML
    private javafx.scene.control.Button loginBtn;
    @FXML
    private AnchorPane loginUI;

    @FXML
    private void initialize() {
        loginBtn.textProperty().addListener((observable, oldValue, newValue) -> {
            loginBtn.setDisable(newValue.trim().isEmpty());
        });
    }

    @FXML
    private void handleLoginBtn() {
        if (inputNumber.getText() != null && inputPW.getText() != null) {
            userNumber = inputNumber.getText();
            userPassword = inputPW.getText();

            Platform.runLater(() -> {
                Stage nowStage = (Stage) loginUI.getScene().getWindow();
                nowStage.hide();
                showMainPane();
            });
            //Test
            System.out.println(userNumber + "," + userPassword);
        }
    }

    @FXML
    private void handleCancel() {
        Platform.exit();
    }

    private void showMainPane() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("mainWindow.fxml"));
            BorderPane mainWindow = (BorderPane) fxmlLoader.load();
            Scene scene = new Scene(mainWindow);
            new JMetro(scene, Style.LIGHT);
            Stage mainWindowStage = new Stage();
            mainWindowStage.setScene(scene);
            mainWindowStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
