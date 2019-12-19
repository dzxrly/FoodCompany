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
import model.MaterialToGoods;
import model.OrderBookGoods;
import model.ProductPlan;
import service.*;

import java.util.List;

public class RawMaterialRequestPaneController {
    @FXML
    private TextField plan_searchInputText;
    @FXML
    private Button plan_searchBtn;
    @FXML
    private Button plan_searchAllBtn;
    @FXML
    private TableView plansTable;
    @FXML
    private TableView goodsSearchDataTable;
    @FXML
    private TableView searchMaterialTable;
    @FXML
    private Label planNumberLabel;
    @FXML
    private Label operatorLabel;
    @FXML
    private Button uploadBtn;

    private ObservableList<ProductPlan> productPlanObservableList = FXCollections.observableArrayList();
    private ProductionManagement productionManagement = new ProductionManagement();
    private CustomerIndexAndStringSwitch customerIndexAndStringSwitch = new CustomerIndexAndStringSwitch();
    private ObservableList<OrderBookGoods> orderBookGoods = FXCollections.observableArrayList();
    private DoubleFormatService doubleFormatService = new DoubleFormatService();
    private ProductPlan currentPlan = new ProductPlan();
    private ObservableList<MaterialToGoods> materialToGoodsObservableList = FXCollections.observableArrayList();
    private OrderBookGoods currentGood = new OrderBookGoods();
    private PropertiesOperation propertiesOperation = new PropertiesOperation();
    private String operatorNumber;

    @FXML
    private void initialize() {
        plan_searchBtn.setGraphic((new AddImageForComponent("img/search14x14.png", 14)).getImageView());
        uploadBtn.setGraphic((new AddImageForComponent("img/check.png", 14)).getImageView());
        operatorLabel.setText(propertiesOperation.returnOperatorFromProperties("userConfig.properties"));
        operatorNumber = propertiesOperation.readValue("userConfig.properties", "LoginUserNumber");
        clearSidePane();

        TableColumn planId = new TableColumn("计划编号");
        planId.setSortable(true);
        planId.setCellValueFactory(new PropertyValueFactory<>("planId"));
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
        plansTable.getColumns().addAll(planId, startTimeCol, endTimeCol, planTypeCol);
        plansTable.setItems(productPlanObservableList);

        TableColumn goodIdCol = new TableColumn("商品编号");
        goodIdCol.setSortable(true);
        goodIdCol.setCellValueFactory(new PropertyValueFactory<>("goodsNumber"));
        TableColumn goodNameCol = new TableColumn("商品名称");
        goodNameCol.setSortable(false);
        goodNameCol.setCellValueFactory(new PropertyValueFactory<>("goodsName"));
        TableColumn buyNumberCol = new TableColumn("购买数量");
        buyNumberCol.setSortable(false);
        buyNumberCol.setCellValueFactory(new PropertyValueFactory<>("orderQuantity"));
        buyNumberCol.setCellFactory(col -> {
            TableCell<OrderBookGoods, Double> cell = new TableCell<>() {
                @Override
                public void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        int row = this.getIndex();
                        this.setText(doubleFormatService.getSubstringInputDouble(orderBookGoods.get(row).getOrderQuantity(), 3) + orderBookGoods.get(row).getGoodsUnit());
                    }
                }
            };
            return cell;
        });
        goodsSearchDataTable.getColumns().addAll(goodIdCol, goodNameCol, buyNumberCol);
        goodsSearchDataTable.setItems(orderBookGoods);

        TableColumn materialIdCol = new TableColumn("原料编号");
        materialIdCol.setSortable(true);
        materialIdCol.setCellValueFactory(new PropertyValueFactory<>("materialId"));
        TableColumn materialNameCol = new TableColumn("原料名称");
        materialNameCol.setSortable(false);
        materialNameCol.setCellValueFactory(new PropertyValueFactory<>("materialName"));
        TableColumn perQuantityCol = new TableColumn("所需数量");
        perQuantityCol.setSortable(false);
        perQuantityCol.setCellValueFactory(new PropertyValueFactory<>("perQuantity"));
        perQuantityCol.setCellFactory(col -> {
            TableCell<MaterialToGoods, Double> cell = new TableCell<>() {
                @Override
                public void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        int row = this.getIndex();
                        this.setText(doubleFormatService.getSubstringInputDouble(materialToGoodsObservableList.get(row).getPerQuantity(), 3) + materialToGoodsObservableList.get(row).getMaterialUnit());
                    }
                }
            };
            return cell;
        });
        TableColumn stocksCol = new TableColumn("库存");
        stocksCol.setSortable(false);
        stocksCol.setCellValueFactory(new PropertyValueFactory<>("materialStocks"));
        stocksCol.setCellFactory(col -> {
            TableCell<MaterialToGoods, Double> cell = new TableCell<>() {
                @Override
                public void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        int row = this.getIndex();
                        this.setText(doubleFormatService.getSubstringInputDouble(materialToGoodsObservableList.get(row).getMaterialStocks(), 3) + materialToGoodsObservableList.get(row).getMaterialUnit());
                    }
                }
            };
            return cell;
        });
        searchMaterialTable.getColumns().addAll(materialIdCol, materialNameCol, perQuantityCol, stocksCol);
        searchMaterialTable.setItems(materialToGoodsObservableList);

        plansTable.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                clearSidePane();
                currentPlan = productPlanObservableList.get((int) newValue);
                orderBookGoods.clear();
                materialToGoodsObservableList.clear();
                service_searchGoods.restart();
                planNumberLabel.setText(String.valueOf(currentPlan.getPlanId()));
            }
        });

        goodsSearchDataTable.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                currentGood = orderBookGoods.get((int) newValue);
                materialToGoodsObservableList.clear();
                service_searchMaterial.restart();
            }
        });
    }

    private void clearSidePane() {
        planNumberLabel.setText("");
    }

    @FXML
    private void handlePlanSearch() {
        if (!plan_searchInputText.getText().equals("")) {
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
    private void handlePlanSearchAll() {
        clearSidePane();
        productPlanObservableList.clear();
        service_allPlans.restart();
    }

    @FXML
    private void handleUpload() {
        if (!planNumberLabel.getText().equals("")) service_upload.restart();
        else {
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "输入不能为空！", "请输入编号!");
            alertDialog.showAlert();
        }
    }

    Service<Integer> service_searchProductionPlanById = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    List<ProductPlan> list = productionManagement.searchProductionPlan(plan_searchInputText.getText());
                    if (!list.toString().equals("[]")) {
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getProductionState() == 0)
                                productPlanObservableList.add(list.get(i));
                        }
                    } else Platform.runLater(() -> plansTable.setPlaceholder(new Label("没有结果")));
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
                            if (list.get(i).getProductionState() == 0)
                                productPlanObservableList.add(list.get(i));
                        }
                    } else Platform.runLater(() -> plansTable.setPlaceholder(new Label("没有结果")));
                    return null;
                }
            };
        }
    };

    Service<Integer> service_searchGoods = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    OrdersSearch ordersSearch = new OrdersSearch();
                    List<OrderBookGoods> list = ordersSearch.searchSpotOrBookOrder(String.valueOf(currentPlan.getOrderId()), "1");
                    if (!list.toString().equals("[]")) {
                        for (int i = 0; i < list.size(); i++) orderBookGoods.add(list.get(i));
                    } else Platform.runLater(() -> plansTable.setPlaceholder(new Label("没有结果")));
                    return null;
                }
            };
        }
    };

    Service<Integer> service_searchMaterial = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    MaterialToGoodsSearch materialToGoodsSearch = new MaterialToGoodsSearch();
                    List<MaterialToGoods> list = materialToGoodsSearch.searchMaterialToGoods(String.valueOf(currentGood.getGoodsNumber()));
                    if (!list.toString().equals("[]"))
                        for (int i = 0; i < list.size(); i++) materialToGoodsObservableList.add(list.get(i));
                    else Platform.runLater(() -> plansTable.setPlaceholder(new Label("没有结果")));
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
                    MaterialApplySubmission materialApplySubmission = new MaterialApplySubmission();
                    MaterialToGoodsSearch materialToGoodsSearch = new MaterialToGoodsSearch();
                    int number = 0;
                    int temp = 0;
                    for (int i = 0; i < orderBookGoods.size(); i++) {
                        List<MaterialToGoods> list = materialToGoodsSearch.searchMaterialToGoods(String.valueOf(orderBookGoods.get(i).getGoodsNumber()));
                        if (!list.toString().equals("[]")) {
                            for (int j = 0; j < list.size(); j++) {
                                if (materialApplySubmission.submitMaterialApply(String.valueOf(currentPlan.getPlanId()), String.valueOf(list.get(j).getMaterialId()), String.valueOf(list.get(i).getPerQuantity())) == 1)
                                    number++;
                                temp++;
                            }
                        }
                    }
                    if (number == temp && number != 0 && temp != 0) {
                        Platform.runLater(() -> {
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.INFORMATION, "成功", "提交成功！", "提交成功!");
                            alertDialog.showAlert();
                            materialToGoodsObservableList.clear();
                            orderBookGoods.clear();
                            productPlanObservableList.clear();
                            clearSidePane();
                        });
                    } else {
                        Platform.runLater(() -> {
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "提交失败！", "请重新提交!");
                            alertDialog.showAlert();
                        });
                    }
                    return null;
                }
            };
        }
    };
}
