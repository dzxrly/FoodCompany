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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import service.AddImageForComponent;
import service.PropertiesOperation;

import java.io.IOException;
import java.util.Optional;

public class MainWindowController {
    //主页面控制类

    @FXML
    private BorderPane mainUI;
    @FXML
    private TreeView<String> sideMenu;
    @FXML
    private Menu userMenu;
    @FXML
    private MenuItem userInfoMenuItem;
    @FXML
    private MenuItem exitMenuItem;
    @FXML
    private MenuItem aboutUs;

    private Node currentNode;
    private PropertiesOperation propertiesOperation = new PropertiesOperation();
    private String userLevel;
    private String userName;
    private String userNumber;

    @FXML
    private void initialize() {
        //读取权限姓名和编号
        userLevel = propertiesOperation.readValue("userConfig.properties", "UserLevel");
        userName = propertiesOperation.readValue("userConfig.properties", "LoginUserName");
        userNumber = propertiesOperation.readValue("userConfig.properties", "LoginUserNumber");
        userMenu.setText("当前用户：" + userName + " [" + userNumber + "]");
        userInfoMenuItem.setMnemonicParsing(true);
        userInfoMenuItem.setAccelerator(new KeyCodeCombination(KeyCode.I, KeyCombination.SHORTCUT_DOWN));
        aboutUs.setMnemonicParsing(true);
        aboutUs.setAccelerator(new KeyCodeCombination(KeyCode.U, KeyCombination.SHORTCUT_DOWN));
        //创建侧边栏
        TreeItem<String> rootItem = new TreeItem<>("部门");
        rootItem.setExpanded(true);
        //侧边栏警告
        TreeItem<String> itemWarning = new TreeItem<>("没有访问权限！");
        //侧边栏销售部
        TreeItem<String> itemSaleDep = new TreeItem<>("销售部");

        TreeItem<String> clientManage = new TreeItem<>("客户管理");
        clientManage.getChildren().add(new TreeItem<>("创建客户"));
        clientManage.getChildren().add(new TreeItem<>("客户信息查询/修改"));
        itemSaleDep.getChildren().add(clientManage);

        TreeItem<String> orderManage = new TreeItem<>("订单管理");
        orderManage.getChildren().add(new TreeItem<>("订单录入"));
        orderManage.getChildren().add(new TreeItem<>("订单信息"));
        orderManage.getChildren().add(new TreeItem<>("物流信息"));
        orderManage.getChildren().add(new TreeItem<>("退货处理"));
        itemSaleDep.getChildren().add(orderManage);
        //侧边栏财务部
        TreeItem<String> itemFianceDep = new TreeItem<>("财务部");

        TreeItem<String> existGoods = new TreeItem<>("订单管理");
        existGoods.getChildren().add(new TreeItem<>("订单付款"));
        existGoods.getChildren().add(new TreeItem<>("订单退款"));
        itemFianceDep.getChildren().add(existGoods);
        //侧边栏生产计划科
        TreeItem<String> itemProductionPlanDep = new TreeItem<>("生产计划科");
        //侧边栏生产车间
        TreeItem<String> itemWorkshop = new TreeItem<>("生产车间");
        //侧边栏成品库
        TreeItem<String> itemFinishedProductionDep = new TreeItem<>("成品库");
        //权限处理
        if (userLevel.equals("0")) {
            rootItem.getChildren().add(itemSaleDep);
            rootItem.getChildren().add(itemFianceDep);
            rootItem.getChildren().add(itemProductionPlanDep);
            rootItem.getChildren().add(itemWorkshop);
            rootItem.getChildren().add(itemFinishedProductionDep);
        } else if (userLevel.equals("1")) {
            rootItem.getChildren().add(itemSaleDep);
            rootItem.getChildren().add(itemFinishedProductionDep);
        } else {
            rootItem.getChildren().add(itemWarning);
        }
        sideMenu.setRoot(rootItem);

        //侧边栏监听器
        sideMenu.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<TreeItem<String>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue, TreeItem<String> newValue) {
                TreeItem<String> currentSelectedItem = (TreeItem<String>) newValue;
                if (currentSelectedItem != null) {
                    //TEST
                    System.out.println(currentSelectedItem.getValue());
                    //-----
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
                    } else if (currentSelectedItem.getValue().equals("订单信息")) {
                        try {
                            showInsidePane("OrderInfoSearch.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (currentSelectedItem.getValue().equals("物流信息")) {
                        try {
                            showInsidePane("TransportInformation.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (currentSelectedItem.getValue().equals("退货处理")) {
                        try {
                            showInsidePane("OrderCancelPane.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (currentSelectedItem.getValue().equals("订单付款")) {
                        try {
                            showInsidePane("PaymentForCashOrders.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (currentSelectedItem.getValue().equals("订单退款")) {
                        try {
                            showInsidePane("SpotOrderRefundPane.fxml");
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

    @FXML
    private void handleExit() {
        System.exit(-1);
    }

    @FXML
    private void handleInfo() {
        //TODO
    }

    @FXML
    private void handleAbout() {
        //TODO
    }
}
