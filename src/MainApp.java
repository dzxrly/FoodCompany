import DAO.HibernateTest1;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import service.CustomerCreator;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainApp extends Application {

    private Stage primaryStage;
    private AnchorPane progressBarPane;
    private BorderPane mainPane;
    private AnchorPane loginPane;

//    Service<Integer> service = new Service<Integer>() {
//        @Override
//        protected Task<Integer> createTask() {
//            return new Task<Integer>() {
//                @Override
//                protected Integer call() throws Exception {
//                    //---------------------------------
//                    //连接数据库操作
//                    initializeDB();
//                    //test();
//                    //---------------------------------
//                    Platform.runLater(() -> {
//                        primaryStage.hide();
//                    });
//                    return null;
//                }
//            };
//        }
//    };


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //test create Customer
//        CustomerCreator c = null;
//        c.createCustomer("李天添"," ",2,"山东大学","9999@ltt.com","13111313131");

        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("登录");
        showUserLoginPane();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void showUserLoginPane() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(MainApp.class.getResource("view/UserLoginPane.fxml"));
        try {
            loginPane = (AnchorPane) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(loginPane);
        new JMetro(scene, Style.LIGHT);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("登录");
        primaryStage.show();
    }//显示登陆页面
}


