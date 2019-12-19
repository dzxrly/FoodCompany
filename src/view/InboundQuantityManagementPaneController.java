package view;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.DeliveryRecord;
import model.ProductPlan;
import model.Stuff;
import model.WorkshopToStockRecord;
import service.*;

import java.util.List;

public class InboundQuantityManagementPaneController {
    @FXML
    private TextField numberInputText;
    @FXML
    private Button searchBtn;
    @FXML
    private Button searchAllBtn;
    @FXML
    private TableView listTable;
    @FXML
    private Label numberLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Label stuffNumberLabel;
    @FXML
    private Label stuffNameLabel;
    @FXML
    private Label operatorLabel;
    @FXML
    private Button markHasFinished;

    private ObservableList<ProductPlan> productPlanObservableList = FXCollections.observableArrayList();
    private ProductionManagement productionManagement = new ProductionManagement();
    private PropertiesOperation propertiesOperation = new PropertiesOperation();
    private String operatorNumber;
    private CustomerIndexAndStringSwitch customerIndexAndStringSwitch = new CustomerIndexAndStringSwitch();
    private DoubleFormatService doubleFormatService = new DoubleFormatService();
    private ProductPlan currentPlan = new ProductPlan();
    private ShippingDepOperation shippingDepOperation = new ShippingDepOperation();
    private StuffSearch stuffSearch = new StuffSearch();
    private WorkshopToStockRecord workshopToStockRecord = new WorkshopToStockRecord();

    @FXML
    private void initialize() {
        searchBtn.setGraphic((new AddImageForComponent("img/search14x14.png", 14)).getImageView());
        markHasFinished.setGraphic((new AddImageForComponent("img/check.png", 14)).getImageView());
        operatorLabel.setText(propertiesOperation.returnOperatorFromProperties("userConfig.properties"));
        operatorNumber = propertiesOperation.readValue("userConfig.properties", "LoginUserName");

        TableColumn planId = new TableColumn("计划编号");
        planId.setSortable(true);
        planId.setCellValueFactory(new PropertyValueFactory<>("planId"));
        TableColumn formIdCol = new TableColumn("订单编号");
        formIdCol.setSortable(true);
        formIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        TableColumn startTimeCol = new TableColumn("开始时间");
        startTimeCol.setSortable(false);
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        startTimeCol.setCellFactory(col -> {
            TableCell<ProductPlan, String> cell = new TableCell<>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        int row = this.getIndex();
                        this.setText(productPlanObservableList.get(row).getStartTime().substring(0, 10));
                    }
                }
            };
            return cell;
        });
        TableColumn endTimeCol = new TableColumn("截止时间");
        endTimeCol.setSortable(false);
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        endTimeCol.setCellFactory(col -> {
            TableCell<ProductPlan, String> cell = new TableCell<>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        int row = this.getIndex();
                        this.setText(productPlanObservableList.get(row).getEndTime().substring(0, 10));
                    }
                }
            };
            return cell;
        });
        TableColumn planTypeCol = new TableColumn("计划类型");
        planTypeCol.setSortable(false);
        planTypeCol.setCellValueFactory(new PropertyValueFactory<>("planType"));
        planTypeCol.setCellFactory(col -> {
            TableCell<ProductPlan, Integer> cell = new TableCell<>() {
                @Override
                public void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        int row = this.getIndex();
                        this.setText(customerIndexAndStringSwitch.getPlanTypeByIndex(productPlanObservableList.get(row).getPlanType()));
                    }
                }
            };
            return cell;
        });
        TableColumn cycleCol = new TableColumn("生产周期");
        cycleCol.setSortable(true);
        cycleCol.setCellValueFactory(new PropertyValueFactory<>("productionCycle"));
        listTable.getColumns().addAll(planId, formIdCol, startTimeCol, endTimeCol, planTypeCol);
        listTable.setItems(productPlanObservableList);

        listTable.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                currentPlan = productPlanObservableList.get((int) newValue);
                numberLabel.setText(String.valueOf(currentPlan.getPlanId()));
                typeLabel.setText(customerIndexAndStringSwitch.getPlanTypeByIndex(currentPlan.getPlanType()));
                System.out.println(currentPlan.getPlanId());
                service_searchStuff.restart();
            }
        });
    }

    private void clearSidePane() {
        numberLabel.setText("");
        typeLabel.setText("");
        stuffNumberLabel.setText("");
        stuffNameLabel.setText("");
    }

    @FXML
    private void handleSearch() {
        if (!numberInputText.getText().equals("")) {
            clearSidePane();
            productPlanObservableList.clear();
            service_search.restart();
        } else {
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "输入不能为空！", "请输入编号!");
            alertDialog.showAlert();
        }
    }

    @FXML
    private void handleSearchAll() {
        clearSidePane();
        productPlanObservableList.clear();
        service_allPlans.restart();
    }

    @FXML
    private void handleMarkHasFinished() {
        //TODO
    }

    Service<Integer> service_search = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    List<ProductPlan> list = productionManagement.searchProductionPlan(numberInputText.getText());
                    if (!list.toString().equals("[]")) {
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getProductionState() == 4)
                                productPlanObservableList.add(list.get(i));
                        }
                    } else Platform.runLater(() -> listTable.setPlaceholder(new Label("没有结果")));
                    return null;
                }
            };
        }
    };

    Service<Integer> service_allPlans = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    List<ProductPlan> list = productionManagement.searchAllProductionPlan();
                    if (!list.toString().equals("[]")) {
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getProductionState() == 4)
                                productPlanObservableList.add(list.get(i));
                        }
                    } else Platform.runLater(() -> listTable.setPlaceholder(new Label("没有结果")));
                    return null;
                }
            };
        }
    };

    Service<Integer> service_searchStuff = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    List<WorkshopToStockRecord> list = shippingDepOperation.getStuffByPlanIdHQL(String.valueOf(currentPlan.getPlanId()));
                    if (!list.toString().equals("[]")) {
                        Stuff stuff = stuffSearch.getStuffById(list.get(0).getDeliverStuffNumber());
                        if (stuff != null) {
                            Platform.runLater(() -> {
                                stuffNumberLabel.setText(String.valueOf(stuff.getNumber()));
                                stuffNameLabel.setText(stuff.getPersonalName());
                            });
                        } else {
                            Platform.runLater(() -> {
                                AlertDialog alertDialog = new AlertDialog();
                                alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "该员工不存在!", "请确认员工编号是否正确！");
                                alertDialog.showAlert();
                                clearSidePane();
                            });
                        }
                    } else {
                        Platform.runLater(() -> {
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "该订单没有被交付!", "请查询其他订单！");
                            alertDialog.showAlert();
                            clearSidePane();
                        });
                    }
                    return null;
                }
            };
        }
    };
}
