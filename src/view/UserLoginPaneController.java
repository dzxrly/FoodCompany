package view;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import service.AlertDialog;
import service.UserInfoCheck;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class UserLoginPaneController {
    //登陆页面控制类
    private String userNumber;
    private String userPassword;
    private UserInfoCheck userInfoCheck = new UserInfoCheck();
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
    private BorderPane progressBarPane;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label loginInfoLabel;

    @FXML
    private void initialize() {
        progressBar.setProgress(-1);
        progressBar.setVisible(false);
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
        progressBar.setVisible(true);
        loginBtn.setDisable(true);
        if (inputNumber.getText().equals("") || inputPW.getText().equals("")) {
            loginInfoLabel.setTextFill(Color.web("#F56C6C"));
            loginInfoLabel.setText("请输入用户名和密码");
            progressBar.setVisible(false);
            loginBtn.setDisable(false);
        } else {
            userNumber = inputNumber.getText();
            userPassword = inputPW.getText();
            service.restart();
        }
    }

    private void showMainPane() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("MainWindow.fxml"));
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
    private void handleCancel() {
        Platform.exit();
    }

    @FXML
    private void handleEnterInput(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            handleLoginBtn();
        }
    }

    //密码验证
    private int checkPW(String number, String pw) {
        //TODO
        System.out.println(userInfoCheck.isValidNumber(number, pw));
        if (userInfoCheck.isValidNumber(number, pw) == 0) return 0;
        else if (userInfoCheck.isValidNumber(number, pw) == 1) return 1;
        else return 2;
    }

    @FXML
    private void handleSet() {
        //TODO
    }

    Service<Integer> service = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    //0表示成功 1表示用户名不存在 2表示用户名有效但是密码错误
                    if (checkPW(userNumber, userPassword) == 0) {
                        Platform.runLater(() -> {
                            loginInfoLabel.setTextFill(Color.web("#67C23A"));
                            loginInfoLabel.setText("登陆成功！");
                            Stage nowStage = (Stage) loginUI.getScene().getWindow();
                            nowStage.hide();
                            showMainPane();
                        });
                    } else if (checkPW(userNumber, userPassword) == 1) {
                        progressBar.setVisible(false);
                        loginBtn.setDisable(false);
                        Platform.runLater(() -> {
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "用户不存在！", "用户不存在！");
                            alertDialog.show();
                        });
                    } else if (checkPW(userNumber, userPassword) == 2) {
                        progressBar.setVisible(false);
                        loginBtn.setDisable(false);
                        Platform.runLater(() -> {
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "密码错误！", "密码错误！");
                            alertDialog.show();
                        });
                    }
                    return null;
                }
            };
        }
    };
}
