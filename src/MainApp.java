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
import model.Customer;
import org.osgi.service.useradmin.User;
import service.CustomerCreator;
import service.CustomerSearch;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

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
//        c.createCustomer("李天添"," ",2,"山东大学","9999@ltt.com","13111313131")
//        c.createCustomer("秦逸然"," ",3,"山东大学","9998@ltt.com","13111313131");

        //test search
//        CustomerSearch cs=new CustomerSearch();
//        List list= cs.FuzzySearch("肖");
//        List list1=cs.AllSearch();

        //fuzzy search
//        for(int i=0; i<list.size();i++){
//            Customer customer=(Customer) list.get(i);
//            System.out.println("________________"+customer+"_____________________");
//        }
        //all search
//        for(int i=0;i<list1.size();i++){
//            Customer customer=(Customer) list1.get(i);
//            System.out.println("________________"+customer+"_____________________");
//        }


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


