package view;

import DAO.HibernateTest1;
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
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import java.beans.EventHandler;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UserLoginPaneController {
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
    private BorderPane progressBarPane;
    @FXML
    private ProgressBar progressBar;

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
        service.restart();
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
    private boolean checkPW(String number, String pw) {
        //TODO
        return true;
    }

    @FXML
    private void handleSet() {
        //TODO
    }

    public void initializeDB() {
        Connection con;
        //jdbc驱动
        String driver = "com.mysql.cj.jdbc.Driver";
        //数据库是FoodCompany todo：做一个前端得到域名填充
        String url = "jdbc:mysql://47.102.218.224:3306/FoodCompany?&useSSL=false&serverTimezone=UTC";
        String user = "root";
        String password = "cb990204";
        try {
            //注册jdbc驱动程序
            Class.forName(driver);
            //建立连接
            con = DriverManager.getConnection(url, user, password);
            if (!con.isClosed()) {
                System.out.println("successfully connected!");
            } else System.out.println("bad!");
            con.close();
        } catch (ClassNotFoundException e) {
            System.out.println("database driver was not loaded!");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("database connection failed");
        }
    }

    Service<Integer> service = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    HibernateTest1 h1 = new HibernateTest1();
                    h1.findCustomerByIdTest();
                    h1.saveCustomerTest();
                    return null;
                }
            };
        }
    };
}
