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
import model.OrderStocks;
import model.ProductionForm;
import service.*;

import java.util.List;

public class ProductionFiancePlanPaneController {
    @FXML
    private ComboBox productionPlanDateComboBox;
    @FXML
    private Button searchBtn;
    @FXML
    private TableView planList;
    @FXML
    private Label planCountLabel;
    @FXML
    private TableView goodsList;
    @FXML
    private TableView rawMetarialList;
    @FXML
    private Label rawMaterialLabel;
    @FXML
    private Label operatorLabel;
    @FXML
    private Button uploadBtn;

    private ObservableList<String> options = FXCollections.observableArrayList("全部", "最近一个季度", "最近两个季度", "最近三个季度");
    private ProductionFormSearch productionFormSearch = new ProductionFormSearch();
    private ObservableList<ProductionForm> productionFormObservableList = FXCollections.observableArrayList();
    private ProductionForm currentProductionForm = new ProductionForm();
    private OrdersSearch ordersSearch = new OrdersSearch();
    private ObservableList<OrderStocks> orderStocksObservableList = FXCollections.observableArrayList();
    private MaterialToGoodsSearch materialToGoodsSearch = new MaterialToGoodsSearch();
    private OrderStocks currentGood = new OrderStocks();
    private ObservableList<MaterialToGoods> materialToGoodsObservableList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        productionPlanDateComboBox.setItems(options);
        productionPlanDateComboBox.getSelectionModel().select(0);
        searchBtn.setGraphic((new AddImageForComponent("img/search14x14.png", 14)).getImageView());
        uploadBtn.setGraphic((new AddImageForComponent("img/check.png", 14)).getImageView());
        PropertiesOperation propertiesOperation = new PropertiesOperation();
        operatorLabel.setText(propertiesOperation.returnOperatorFromProperties("userConfig.properties"));

        TableColumn productionFormIdCol = new TableColumn("表单编号");
        productionFormIdCol.setSortable(true);
        productionFormIdCol.setCellValueFactory(new PropertyValueFactory<>("productionId"));
        TableColumn orderIdCol = new TableColumn("订单编号");
        orderIdCol.setSortable(true);
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        TableColumn buildTimeCol = new TableColumn("创建日期");
        buildTimeCol.setSortable(false);
        buildTimeCol.setCellValueFactory(new PropertyValueFactory<>("buildTime"));
        buildTimeCol.setCellFactory(col -> {
            TableCell<ProductionForm, String> cell = new TableCell<>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        int rowIndex = this.getIndex();
                        String str = ((ProductionForm) planList.getItems().get(rowIndex)).getBuildTime().substring(0, 10);
                        this.setText(str);
                    }
                }
            };
            return cell;
        });
        TableColumn endTime = new TableColumn("截止日期");
        endTime.setSortable(false);
        endTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        endTime.setCellFactory(col -> {
            TableCell<ProductionForm, String> cell = new TableCell<>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        int rowIndex = this.getIndex();
                        String str = ((ProductionForm) planList.getItems().get(rowIndex)).getEndTime().substring(0, 10);
                        this.setText(str);
                    }
                }
            };
            return cell;
        });
        planList.getColumns().addAll(productionFormIdCol, orderIdCol, buildTimeCol, endTime);
        planList.setItems(productionFormObservableList);

        TableColumn goodIdCol = new TableColumn("商品编号");
        goodIdCol.setSortable(true);
        goodIdCol.setCellValueFactory(new PropertyValueFactory<>("goodsId"));
        TableColumn goodNameCol = new TableColumn("商品名称");
        goodNameCol.setSortable(false);
        goodNameCol.setCellValueFactory(new PropertyValueFactory<>("goodsName"));
        TableColumn buyNumberCol = new TableColumn("应生产数量");
        buyNumberCol.setSortable(true);
        buyNumberCol.setCellValueFactory(new PropertyValueFactory<>("produceQuantity"));
        TableColumn goodPrice = new TableColumn("单价");
        goodPrice.setSortable(true);
        goodPrice.setCellValueFactory(new PropertyValueFactory<>("goodsPrice"));
        goodsList.getColumns().addAll(goodIdCol, goodNameCol, buyNumberCol, goodPrice);
        goodsList.setItems(orderStocksObservableList);

        planList.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                currentProductionForm = productionFormObservableList.get((int) newValue);
                orderStocksObservableList.clear();
                service_searchGoods.restart();
            }
        });

        goodsList.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                currentGood = orderStocksObservableList.get((int) newValue);
                materialToGoodsObservableList.clear();
                service_searchMaterial.restart();
            }
        });
    }

    @FXML
    private void handleSearch() {
        service_search.restart();
    }

    @FXML
    private void handleUpload() {
        //TODO
    }

    Service<Integer> service_search = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    List<ProductionForm> list = productionFormSearch.searchProductionForm(productionPlanDateComboBox.getSelectionModel().getSelectedIndex());
                    if (!list.toString().equals("[]") && list != null) {
                        for (int i = 0; i < list.size(); i++) {
                            productionFormObservableList.add((ProductionForm) list.get(i));
                        }
                    } else {
                        Platform.runLater(() -> {
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "没有结果！", "没有结果！");
                        });
                    }
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
                    List list = ordersSearch.searchOrderAndStocks(String.valueOf(currentProductionForm.getOrderId()));
                    if (!list.toString().equals("[]") && list != null) {
                        for (int i = 0; i < list.size(); i++) {
                            Object[] objects = (Object[]) list.get(i);
                            OrderStocks orderStocks = new OrderStocks();
                            orderStocks.setGoodsId((int) objects[0]);
                            orderStocks.setGoodsName((String) objects[1]);
                            orderStocks.setOrderQuantity((Double) objects[2]);
                            orderStocks.setGoodsPrice((Double) objects[3]);
                            orderStocks.setStocks((Double) objects[4]);
                            orderStocks.setProduceQuantity((Double) objects[5]);
                            orderStocksObservableList.add(orderStocks);
                        }
                    } else {
                        Platform.runLater(() -> goodsList.setPlaceholder(new Label("没有结果")));
                    }
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
                    List<MaterialToGoods> list = materialToGoodsSearch.searchMaterialToGoods(String.valueOf(currentGood.getGoodsId()));
                    if (!list.toString().equals("[]") && list != null) {
                        for (int i = 0; i < list.size(); i++) materialToGoodsObservableList.add(list.get(i));
                    } else {
                        Platform.runLater(() -> rawMetarialList.setPlaceholder(new Label("没有结果")));
                    }
                    return null;
                }
            };
        }
    };
}
