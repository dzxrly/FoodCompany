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

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainApp extends Application {

    private Stage primaryStage;
    private AnchorPane progressBarPane;
    private BorderPane mainPane;
    private AnchorPane loginPane;

    Service<Integer> service = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    //---------------------------------
                    //连接数据库操作
                    initializeDB();
                    //---------------------------------
                    Platform.runLater(() -> {
                        primaryStage.hide();
                        showUserLoginPane();//跳转至登陆页面
                    });
                    return null;
                }
            };
        }
    };

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("食品公司管理系统");
        showLoginProgressBar();
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

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void showUserLoginPane() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(MainApp.class.getResource("view/userLoginPane.fxml"));
        try {
            loginPane = (AnchorPane) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(loginPane);
        new JMetro(scene, Style.LIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showLoginProgressBar() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(MainApp.class.getResource("view/loginProgressBar.fxml"));
            progressBarPane = (AnchorPane) fxmlLoader.load();

            Scene scene = new Scene(progressBarPane);
            new JMetro(scene, Style.LIGHT);
            primaryStage.setScene(scene);
            primaryStage.show();
            service.restart();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//加载页面

    public void showMainPane() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(MainApp.class.getResource("view/mainWindow.fxml"));
        mainPane = (BorderPane) fxmlLoader.load();

        Scene scene = new Scene(mainPane);
        new JMetro(scene, Style.LIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }//主页面
}


