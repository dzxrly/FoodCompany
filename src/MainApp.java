import DAO.HibernateTest1;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

import model.*;
import service.*;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;


public class MainApp extends Application {

    private Stage primaryStage;
    private AnchorPane progressBarPane;
    private BorderPane mainPane;
    private AnchorPane loginPane;

    public static void main(String[] args) {
//        ProductWorkshop pw= new ProductWorkshop();

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws ParseException {
//        OrdersSubmission Obs = new OrdersSubmission();
//        Orders od = Obs.createMainOrders(0, "test", "李天添", "17863107716", "上海", 7300.0, 10002, "购物广场");
//        Obs.createSpotOrders(od, 1, "香蕉", 100.0);
//        Obs.createSpotOrders(od, 2, "苹果", 200.0);
//        Obs.createSpotOrders(od, 3, "猪肉", 100.0);


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
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                //exit(-1);
            }
        });
    }//显示登陆页面


}


