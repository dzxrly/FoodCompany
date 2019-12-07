package view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.beans.EventHandler;
import java.io.File;
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
    private ImageView imgView;
    @FXML
    private Button setBtn;

    @FXML
    private void initialize() {
        setBtn.setGraphic(new ImageView(new Image("img/setting.png", 16, 16, false, false)));
        Image img = new Image("img/loginBG.jpg");
        imgView.setImage(img);
        loginBtn.setDisable(true);
        inputNumber.textProperty().addListener((observable, oldValue, newValue) -> {
            loginBtn.setDisable(newValue.trim().isEmpty());
        });
    }

    @FXML
    private void handleLoginBtn() {
        if (inputNumber.getText() != null && inputPW.getText() != null) {
            userNumber = inputNumber.getText();
            userPassword = inputPW.getText();
            if (checkPW(userNumber, userPassword)) {
                Platform.runLater(() -> {
                    Stage nowStage = (Stage) loginUI.getScene().getWindow();
                    nowStage.hide();
                    showMainPane();
                });
            } else {
                //TODO
            }
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
            mainWindowStage.setResizable(true);
            mainWindowStage.setTitle("食品公司管理系统");
            mainWindowStage.setMinHeight(720);
            mainWindowStage.setMinWidth(1280);
            mainWindowStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEnterInput(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            handleLoginBtn();
        }
    }

    //密码验证
    private boolean checkPW(String number, String pw) {
        //TODO
        return true;
    }

    @FXML
    private void handleSet() {
        //TODO
    }
}
