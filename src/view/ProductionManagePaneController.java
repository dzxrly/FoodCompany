package view;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.PointLight;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.ProductPlan;
import model.Stuff;
import service.*;

import java.util.List;

public class ProductionManagePaneController {
    @FXML
    private TextField searchInputText;
    @FXML
    private Button searchBtn;
    @FXML
    private Button searchAllBtn;
    @FXML
    private TableView planList;
    @FXML
    private Label planNumberLabel;
    @FXML
    private Label typeLabe;
    @FXML
    private Label cycleLebal;
    @FXML
    private Button uploadBtn;
    @FXML
    private TextField stuffInfoInput;
    @FXML
    private Label stuffIdLabel;
    @FXML
    private Label stuffNameLabel;
    @FXML
    private Button searchStuff;
    @FXML
    private ComboBox planStatusComboBox;
    @FXML
    private Button uploadStatusChange;
    @FXML
    private Label planStatusLabel;

    private ObservableList<ProductPlan> productPlanObservableList = FXCollections.observableArrayList();
    private ProductionManagement productionManagement = new ProductionManagement();
    private CustomerIndexAndStringSwitch customerIndexAndStringSwitch = new CustomerIndexAndStringSwitch();
    private DoubleFormatService doubleFormatService = new DoubleFormatService();
    private ProductPlan currentPlan = new ProductPlan();
    private final String pattern = "yyyy-MM-dd";
    private ObservableList<String> planStatusOptions = FXCollections.observableArrayList("未生产", "待生产", "正在生产", "完成生产");
    private Stuff currentStuff = new Stuff();

    @FXML
    private void initialize() {
        searchBtn.setGraphic((new AddImageForComponent("img/search14x14.png", 14)).getImageView());
        uploadBtn.setGraphic((new AddImageForComponent("img/check.png", 14)).getImageView());
        clearSidePane();
        planStatusComboBox.setItems(planStatusOptions);
        planStatusComboBox.getSelectionModel().select(0);
        stuffNameLabel.setText("");
        stuffIdLabel.setText("");

        TableColumn planId = new TableColumn("计划编号");
        planId.setSortable(true);
        planId.setCellValueFactory(new PropertyValueFactory<>("planId"));
        TableColumn formIdCol = new TableColumn("生产状态");
        formIdCol.setSortable(true);
        formIdCol.setCellValueFactory(new PropertyValueFactory<>("productionState"));
        formIdCol.setCellFactory(col -> {
            TableCell<ProductPlan, Integer> cell = new TableCell<>() {
                @Override
                public void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        int row = this.getIndex();
                        this.setText(customerIndexAndStringSwitch.getPlanStatusByIndex(productPlanObservableList.get(row).getProductionState()));
                    }
                }
            };
            return cell;
        });
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
        planList.getColumns().addAll(planId, formIdCol, startTimeCol, endTimeCol, planTypeCol);
        planList.setItems(productPlanObservableList);

        planList.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                currentPlan = productPlanObservableList.get((int) newValue);
                clearSidePane();
                planNumberLabel.setText(String.valueOf(currentPlan.getPlanId()));
                typeLabe.setText(customerIndexAndStringSwitch.getPlanTypeByIndex(currentPlan.getPlanType()));
                planStatusLabel.setText(customerIndexAndStringSwitch.getPlanStatusByIndex(currentPlan.getProductionState()));
                cycleLebal.setText(doubleFormatService.getSubstringInputDouble(currentPlan.getProductionCycle(), 3));
            }
        });
    }

    public void clearSidePane() {
        planNumberLabel.setText("");
        typeLabe.setText("");
        planStatusLabel.setText("");
        cycleLebal.setText("");
        planStatusComboBox.getSelectionModel().select(0);
    }

    @FXML
    private void handleSearch() {
        if (!searchInputText.getText().equals("")) {
            clearSidePane();
            productPlanObservableList.clear();
            service_searchProductionPlanById.restart();
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
    private void handleSearchStuff() {
        if (!stuffInfoInput.getText().equals("")) service_searchStuff.restart();
        else {
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "提交失败！", "编号不能为空！");
            alertDialog.showAlert();
        }
    }

    @FXML
    private void handleStatusChange() {
        if (!planNumberLabel.getText().equals("")) {
            service_uploadStatusChange.restart();
        } else {
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "提交失败！", "没有选中订单!");
            alertDialog.showAlert();
        }
    }

    @FXML
    private void handleUpload() {
        if (!stuffIdLabel.getText().equals("")) service_upload.restart();
        else {
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "提交失败！", "没有监督人!");
            alertDialog.showAlert();
        }
    }

    Service<Integer> service_searchProductionPlanById = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    List<ProductPlan> list = productionManagement.searchProductionPlan(searchInputText.getText());
                    if (!list.toString().equals("[]")) {
                        for (int i = 0; i < list.size(); i++) {
                            productPlanObservableList.add(list.get(i));
                        }
                    } else Platform.runLater(() -> planList.setPlaceholder(new Label("没有结果")));
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
                            productPlanObservableList.add(list.get(i));
                        }
                    } else Platform.runLater(() -> planList.setPlaceholder(new Label("没有结果")));
                    return null;
                }
            };
        }
    };

    Service<Integer> service_uploadStatusChange = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    int flag = productionManagement.updatePlanStatus(currentPlan, planStatusComboBox.getSelectionModel().getSelectedIndex());
                    if (flag == 1) {
                        Platform.runLater(() -> {
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.INFORMATION, "成功", "提交成功！", "提交成功！");
                            alertDialog.showAlert();
                            productPlanObservableList.clear();
                            clearSidePane();
                        });
                    } else {
                        Platform.runLater(() -> {
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "提交失败！", "提交失败！");
                            alertDialog.showAlert();
                        });
                    }
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
                    StuffSearch stuffSearch = new StuffSearch();
                    currentStuff = stuffSearch.getStuffById(Integer.valueOf(stuffInfoInput.getText()));
                    if (currentStuff != null) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                stuffNameLabel.setText(currentStuff.getPersonalName());
                                stuffIdLabel.setText(String.valueOf(currentStuff.getNumber()));
                            }
                        });
                    } else {
                        Platform.runLater(() -> {
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "没有结果！", "请确认编号是否正确！");
                            alertDialog.showAlert();
                        });
                    }
                    return null;
                }
            };
        }
    };

    Service<Integer> service_upload = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    if (currentPlan.getProductionState() == 3) {
                        DeliveryToStock deliveryToStock = new DeliveryToStock();
                        int flag = deliveryToStock.delivery(currentPlan.getPlanId(), currentPlan.getPlanType(), currentPlan.getOrderId(), currentStuff.getNumber(), currentPlan.getProductionCycle());
                        if (flag == 1) {
                            Platform.runLater(() -> {
                                AlertDialog alertDialog = new AlertDialog();
                                alertDialog.createAlert(Alert.AlertType.INFORMATION, "成功", "提交成功！", "提交成功！");
                                alertDialog.showAlert();
                                productPlanObservableList.clear();
                                clearSidePane();
                            });
                        } else {
                            Platform.runLater(() -> {
                                AlertDialog alertDialog = new AlertDialog();
                                alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "提交失败！", "提交失败！");
                                alertDialog.showAlert();
                            });
                        }
                    } else {
                        Platform.runLater(() -> {
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "提交失败！", "该订单仍在生产中！");
                            alertDialog.showAlert();
                        });
                    }
                    return null;
                }
            };
        }
    };
}
