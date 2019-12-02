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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.io.IOException;
import java.util.Optional;

public class mainWindowController {
    //主页面控制类

    @FXML
    private BorderPane mainUI;

    @FXML
    private TreeView<String> sideMenu;

    @FXML
    private void initialize() {
        //创建侧边栏
        TreeItem<String> rootItem = new TreeItem<>("部门");
        rootItem.setExpanded(true);
        //侧边栏销售部
        TreeItem<String> itemSaleDep = new TreeItem<>("销售部");

        TreeItem<String> clientManage = new TreeItem<>("客户管理");
        clientManage.getChildren().add(new TreeItem<>("创建客户"));
        clientManage.getChildren().add(new TreeItem<>("客户信息查询"));
        clientManage.getChildren().add(new TreeItem<>("客户信息修改"));
        itemSaleDep.getChildren().add(clientManage);

        TreeItem<String> orderManage = new TreeItem<>("订单管理");
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

                    if(currentSelectedItem.getValue().equals("创建客户")) {
                        try {
                            showSaleDepClientCreatorPane();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

                //TODO
            }
        });
    }

    //显示创建客户页面
    private void showSaleDepClientCreatorPane() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("saleDepClientCreatorPane.fxml"));
        AnchorPane sdccpAnchorPane = (AnchorPane) fxmlLoader.load();
        mainUI.setCenter(sdccpAnchorPane);
    }
}
