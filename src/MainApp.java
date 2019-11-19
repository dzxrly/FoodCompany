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

public class MainApp extends Application {

    private Stage primaryStage;
    private AnchorPane progressBarPane;
    private BorderPane mainPane;

    Service<Integer> service = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    //---------------------------------
                    //临时代码，替换连接数据库操作
                    for (int i = 0; i < 30; i++) {
                        Thread.sleep(100);
                        System.out.println(i + 1);
                    }
                    //---------------------------------
                    Platform.runLater(()->{
                        primaryStage.hide();
                        try {
                            showMainPane();//跳转至主页面
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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

    public Stage getPrimaryStage() {
        return primaryStage;
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


