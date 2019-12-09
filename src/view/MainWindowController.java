package view;

import javafx.application.Platform;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;
import java.util.Optional;

public class MainWindowController {
    //主页面控制类

    @FXML
    private BorderPane mainUI;

    @FXML
    private TreeView<String> sideMenu;

    private Node currentNode;

    @FXML
    private void initialize() {
        //创建侧边栏
        TreeItem<String> rootItem = new TreeItem<>("部门");
        rootItem.setExpanded(true);
        //侧边栏销售部
        TreeItem<String> itemSaleDep = new TreeItem<>("销售部");

        TreeItem<String> clientManage = new TreeItem<>("客户管理");
        clientManage.setGraphic(new ImageView(new Image("img/sub_account.png", 16, 16, false, false)));
        clientManage.getChildren().add(new TreeItem<>("创建客户"));
        clientManage.getChildren().add(new TreeItem<>("客户信息查询/修改"));
        itemSaleDep.getChildren().add(clientManage);

        TreeItem<String> orderManage = new TreeItem<>("订单管理");
        orderManage.setGraphic(new ImageView(new Image("img/order.png", 16, 16, false, false)));
        orderManage.getChildren().add(new TreeItem<>("订单录入"));
        orderManage.getChildren().add(new TreeItem<>("订单信息"));
        itemSaleDep.getChildren().add(orderManage);
        //侧边栏财务部
        TreeItem<String> itemFianceDep = new TreeItem<>("财务部");
        //侧边栏生产计划科
        TreeItem<String> itemProductionPlanDep = new TreeItem<>("生产计划科");
        //侧边栏生产车间
        TreeItem<String> itemWorkshop = new TreeItem<>("生产车间");
        //侧边栏成品库
        TreeItem<String> itemFinishedProductionDep = new TreeItem<>("成品库");
        rootItem.getChildren().add(itemSaleDep);
        rootItem.getChildren().add(itemFianceDep);
        rootItem.getChildren().add(itemProductionPlanDep);
        rootItem.getChildren().add(itemWorkshop);
        rootItem.getChildren().add(itemFinishedProductionDep);

        sideMenu.setRoot(rootItem);

        //侧边栏监听器
        sideMenu.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue, TreeItem<String> newValue) {
                TreeItem<String> currentSelectedItem = (TreeItem<String>) newValue;
                //Test
                if (currentSelectedItem != null) {
                    System.out.println(currentSelectedItem.getValue());

                    if (currentSelectedItem.getValue().equals("创建客户")) {
                        try {
                            showInsidePane("SaleDepClientCreatorPane.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (currentSelectedItem.getValue().equals("客户信息查询/修改")) {
                        try {
                            showInsidePane("SaleDepClientInfoSearchPane.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (currentSelectedItem.getValue().equals("订单录入")) {
                        try {
                            showInsidePane("OrderInput.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        mainUI.getChildren().remove(currentNode); //清空主面板
                    }
                }
                //TODO
            }
        });
    }

    //显示内部页面
    private void showInsidePane(String fxmlPath) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource(fxmlPath));
        AnchorPane anchorPane = (AnchorPane) fxmlLoader.load();
        mainUI.setCenter(anchorPane);
        Stage nowStage = (Stage) mainUI.getScene().getWindow();
        anchorPane.prefWidthProperty().bind(nowStage.widthProperty());
        anchorPane.prefHeightProperty().bind(nowStage.heightProperty());
        currentNode = (Node) anchorPane;
    }
}
