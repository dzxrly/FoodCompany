package view;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Pair;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import service.AddImageForComponent;
import service.PropertiesOperation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.time.LocalDate;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    @FXML
    private Label netStatus;

    private Node currentNode;
    private PropertiesOperation propertiesOperation = new PropertiesOperation();
    private String userLevel;
    private String userName;
    private String userNumber;
    private Timer timer = new Timer();

    @FXML
    private void initialize() {
        netStatus.setText("检测中...");
        service_checkNet.restart();
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
        TreeItem<String> rootItem = new TreeItem<>("功能列表");
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

        TreeItem<String> financialTreatment = new TreeItem<>("公司财务处理");
        financialTreatment.getChildren().add(new TreeItem<>("账单管理"));
        financialTreatment.getChildren().add(new TreeItem<>("财务报表"));

        itemFianceDep.getChildren().add(financialTreatment);
        //侧边栏生产计划科
        TreeItem<String> itemProductionPlanDep = new TreeItem<>("生产计划科");

        TreeItem<String> orderProductionCheck = new TreeItem<>("新增生产表单");
        itemProductionPlanDep.getChildren().add(orderProductionCheck);

        TreeItem<String> produceNumberManagement = new TreeItem<>("生产管理");
        produceNumberManagement.getChildren().add(new TreeItem<>("生产财务规划"));
        produceNumberManagement.getChildren().add(new TreeItem<>("生产计划管理"));
        produceNumberManagement.getChildren().add(new TreeItem<>("库存数量补充"));
        itemProductionPlanDep.getChildren().add(produceNumberManagement);
        //原料库
        TreeItem<String> itemRawMaterial = new TreeItem<>("原料库");
        itemRawMaterial.getChildren().add(new TreeItem<>("原料库存管理"));
        itemRawMaterial.getChildren().add(new TreeItem<>("商品原料管理"));
        //侧边栏生产车间
        TreeItem<String> itemWorkshop = new TreeItem<>("生产车间");
        itemWorkshop.getChildren().add(new TreeItem<>("原料申请"));

        TreeItem<String> produceProgress = new TreeItem<>("生产流程管理");
        produceProgress.getChildren().add(new TreeItem<>("生产分配"));
        produceProgress.getChildren().add(new TreeItem<>("抽检管理"));
        produceProgress.getChildren().add(new TreeItem<>("生产计划交付"));
        itemWorkshop.getChildren().add(produceProgress);

        TreeItem<String> productDestruction = new TreeItem<>("产品销毁");
        itemWorkshop.getChildren().add(productDestruction);
        //侧边栏成品库
        TreeItem<String> itemFinishedProductionDep = new TreeItem<>("成品库");
        itemFinishedProductionDep.getChildren().add(new TreeItem<>("交付接收管理"));
        itemFinishedProductionDep.getChildren().add(new TreeItem<>("出入库信息管理"));
        itemFinishedProductionDep.getChildren().add(new TreeItem<>("提货信息管理"));
        //用户管理
        TreeItem<String> itemUserManagement = new TreeItem<>("用户管理");
        itemUserManagement.getChildren().add(new TreeItem<>("员工信息管理"));
//        itemUserManagement.getChildren().add(new TreeItem<>("考勤管理"));
        //权限处理
        if (userLevel.substring(0, 1).equals("0")) {
            rootItem.getChildren().add(itemSaleDep);
            rootItem.getChildren().add(itemFianceDep);
            rootItem.getChildren().add(itemRawMaterial);
            rootItem.getChildren().add(itemProductionPlanDep);
            rootItem.getChildren().add(itemWorkshop);
            rootItem.getChildren().add(itemFinishedProductionDep);
            rootItem.getChildren().add(itemUserManagement);
        } else if (userLevel.substring(0, 1).equals("1")) {
            rootItem.getChildren().add(itemSaleDep);
            if (userLevel.substring(1).equals("1")) rootItem.getChildren().add(itemUserManagement);
        } else if (userLevel.substring(0, 1).equals("2")) {
            rootItem.getChildren().add(itemFianceDep);
            if (userLevel.substring(1).equals("1")) rootItem.getChildren().add(itemUserManagement);
        } else if (userLevel.substring(0, 1).equals("3")) {
            rootItem.getChildren().add(itemProductionPlanDep);
            if (userLevel.substring(1).equals("1")) rootItem.getChildren().add(itemUserManagement);
        } else if (userLevel.substring(0, 1).equals("4")) {
            rootItem.getChildren().add(itemWorkshop);
            if (userLevel.substring(1).equals("1")) rootItem.getChildren().add(itemUserManagement);
        } else if (userLevel.substring(0, 1).equals("5")) {
            rootItem.getChildren().add(itemFinishedProductionDep);
            if (userLevel.substring(1).equals("1")) rootItem.getChildren().add(itemUserManagement);
        } else if (userLevel.substring(0, 1).equals("6")) {
            rootItem.getChildren().add(itemRawMaterial);
            if (userLevel.substring(1).equals("1")) rootItem.getChildren().add(itemUserManagement);
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
                    } else if (currentSelectedItem.getValue().equals("账单管理")) {
                        try {
                            showInsidePane("BillManagement.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (currentSelectedItem.getValue().equals("财务报表")) {
                        try {
                            showInsidePane("FinancialStatementsPane.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (currentSelectedItem.getValue().equals("新增生产表单")) {
                        try {
                            showInsidePane("OrderProductionCheckPane.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (currentSelectedItem.getValue().equals("生产财务规划")) {
                        try {
                            showInsidePane("ProductionFiancePlanPane.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (currentSelectedItem.getValue().equals("生产计划管理")) {
                        try {
                            showInsidePane("ProductionPlansManagementPane.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (currentSelectedItem.getValue().equals("原料库存管理")) {
                        try {
                            showInsidePane("RawmetarialManagePane.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (currentSelectedItem.getValue().equals("生产计划交付")) {
                        try {
                            showInsidePane("ProductionManagePane.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (currentSelectedItem.getValue().equals("原料申请")) {
                        try {
                            showInsidePane("RawMaterialRequestPane.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (currentSelectedItem.getValue().equals("生产分配")) {
                        try {
                            showInsidePane("ProductionDistributionPane.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (currentSelectedItem.getValue().equals("抽检管理")) {
                        try {
                            showInsidePane("ProductionCheckPane.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (currentSelectedItem.getValue().equals("产品销毁")) {
                        try {
                            showInsidePane("ProductDestructionPane.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (currentSelectedItem.getValue().equals("出入库信息管理")) {
                        try {
                            showInsidePane("InventoryQuantityManagementPane.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (currentSelectedItem.getValue().equals("提货信息管理")) {
                        try {
                            showInsidePane("PickupInformationManagementPane.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (currentSelectedItem.getValue().equals("交付接收管理")) {
                        try {
                            showInsidePane("InboundQuantityManagementPane.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (currentSelectedItem.getValue().equals("员工信息管理")) {
                        try {
                            showInsidePane("StuffInfoManagementPane.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (currentSelectedItem.getValue().equals("考勤管理")) {
                        try {
                            showInsidePane("AttendanceManagePane.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (currentSelectedItem.getValue().equals("库存数量补充")) {
                        try {
                            showInsidePane("StockAddPane.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (currentSelectedItem.getValue().equals("商品原料管理")) {
                        try {
                            showInsidePane("GoodsRawMaterialManagePane.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        mainUI.getChildren().remove(currentNode); //清空主面板
                    }
                }
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

    private void showInfoPane() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("PersonalInfoPane.fxml"));
            AnchorPane anchorPane = (AnchorPane) fxmlLoader.load();
            Scene scene = new Scene(anchorPane);
            new JMetro(scene, Style.LIGHT);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("个人信息");
            stage.setResizable(false);
            stage.getIcons().add(new Image("img/icon.png"));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAboutUsPane() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("AboutUsPane.fxml"));
            AnchorPane anchorPane = (AnchorPane) fxmlLoader.load();
            Scene scene = new Scene(anchorPane);
            new JMetro(scene, Style.LIGHT);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("关于");
            stage.setResizable(false);
            stage.getIcons().add(new Image("img/icon.png"));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleExit() {
        System.exit(-1);
    }

    @FXML
    private void handleInfo() {
        showInfoPane();
    }

    @FXML
    private void handleAbout() {
        showAboutUsPane();
    }

    public boolean ping(String ipAddress, int pingTimes, int timeOut) {
        BufferedReader in = null;
        Runtime r = Runtime.getRuntime();  // 将要执行的ping命令,此命令是windows格式的命令
        String pingCommand = "ping " + ipAddress + " -n " + pingTimes + " -w " + timeOut;
        try {   // 执行命令并获取输出
            Process p = r.exec(pingCommand);
            if (p == null) {
                return false;
            }
            in = new BufferedReader(new InputStreamReader(p.getInputStream()));   // 逐行检查输出,计算类似出现=23ms TTL=62字样的次数
            int connectedCount = 0;
            String line = null;
            while ((line = in.readLine()) != null) {
                connectedCount += getCheckResult(line);
            }   // 如果出现类似=23ms TTL=62这样的字样,出现的次数=测试次数则返回真
            return connectedCount == pingTimes;
        } catch (Exception ex) {
            ex.printStackTrace();   // 出现异常则返回假
            return false;
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private int getCheckResult(String line) {  // System.out.println("控制台输出的结果为:"+line);
        Pattern pattern = Pattern.compile("(\\d+ms)(\\s+)(TTL=\\d+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(line);
        while (matcher.find()) {
            return 1;
        }
        return 0;
    }

    Service<Integer> service_checkNet = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            boolean[] booleans = new boolean[4];
                            int number = 0;
                            for (int i = 0; i < 4; i++) booleans[i] = ping("www.baidu.com", 1, 5000);
                            for (int i = 0; i < 4; i++) if (booleans[i]) number++;
                            if (number == 4 || number == 3) {
                                Platform.runLater(() -> {
                                    netStatus.setText("网络质量良好");
                                    netStatus.setTextFill(Color.web("#67C23A"));
                                });
                            } else if (number == 2) {
                                Platform.runLater(() -> {
                                    netStatus.setText("网络质量一般");
                                    netStatus.setTextFill(Color.web("#E6A23C"));
                                });
                            } else if (number == 1) {
                                Platform.runLater(() -> {
                                    netStatus.setText("网络质量很差");
                                    netStatus.setTextFill(Color.web("#F56C6C"));
                                });
                            } else {
                                Platform.runLater(() -> {
                                    netStatus.setText("网络已断开，请检查网络连接状况！");
                                    netStatus.setTextFill(Color.web("#F56C6C"));
                                });
                            }
                        }
                    }, 100, 30000);
                    return null;
                }
            };
        }
    };
}
