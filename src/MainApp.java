import javafx.application.Application;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import view.loginProgressBarController;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane loginMainUIPane;
    private AnchorPane loginUIPane;
    private AnchorPane loginProgressBar;

    public void initLoginMainUIPane() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/loginMainUI.fxml"));
            loginMainUIPane = loader.load();

            Scene scene = new Scene(loginMainUIPane);
            new JMetro(scene, Style.LIGHT);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//初始化登录页面容器

    public void showLoginUIPane() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/loginUI.fxml"));
            loginUIPane = loader.load();
            loginMainUIPane.setCenter(loginUIPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//登录页面主页面（账号密码输入页面）

    public void showLoginProgressBar() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/loginProgressBar.fxml"));
            loginProgressBar = loader.load();
            loginMainUIPane.setCenter(loginProgressBar);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//登陆页面数据库连接进度条

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("登录");
        initLoginMainUIPane();
        showLoginProgressBar();
    }
}
