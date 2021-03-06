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
import javafx.scene.paint.Color;
import model.Goods;
import model.OrderStocks;
import model.Orders;
import model.ProductionForm;
import service.*;

import java.util.List;
import java.util.Optional;

public class OrderProductionCheckPaneController {
    //订单需求量检查类
    @FXML
    private TextField orderNumberInput;
    @FXML
    private Button searchBtn;
    @FXML
    private Button searchAllBtn;
    @FXML
    private TableView orderList;
    @FXML
    private Label orderNumberLabel;
    @FXML
    private Label deadlineLabel;
    @FXML
    private TableView goodsList;
    @FXML
    private Label operatorLabel;
    @FXML
    private Button uploadBtn;
    @FXML
    private Label goodNameLabel_inStock;
    @FXML
    private Label needProducedNumberLabel;
    @FXML
    private Label goodNameLabel_needed;
    @FXML
    private Label selectedGoodNameLabel;
    @FXML
    private Label buyNumberLabel;

    private ObservableList<Orders> ordersObservableList = FXCollections.observableArrayList();
    private OrdersSearch ordersSearch = new OrdersSearch();
    private ObservableList<OrderStocks> goodsObservableList = FXCollections.observableArrayList();
    private Orders currentOrders = new Orders();
    private OrderStocks currentGood = new OrderStocks();
    private ProductionFormSubmission productionFormSubmission = new ProductionFormSubmission();

    @FXML
    private void initialize() {
        orderNumberLabel.setText("未选中订单");
        deadlineLabel.setText("未选中订单");
        selectedGoodNameLabel.setText("未选中商品");
        selectedGoodNameLabel.setTextFill(Color.BLACK);
        buyNumberLabel.setText("未选中商品");
        needProducedNumberLabel.setText("未选中商品");

        searchBtn.setGraphic((new AddImageForComponent("img/search14x14.png", 14)).getImageView());
        uploadBtn.setGraphic((new AddImageForComponent("img/check.png", 14)).getImageView());

        PropertiesOperation propertiesOperation = new PropertiesOperation();
        operatorLabel.setText(propertiesOperation.returnOperatorFromProperties("userConfig.properties"));

        TableColumn orderIdCol = new TableColumn("订单编号");
        orderIdCol.setSortable(true);
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        TableColumn companyNameCol = new TableColumn("公司/企业名称");
        companyNameCol.setSortable(false);
        companyNameCol.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        TableColumn personalNameCol = new TableColumn("客户名称");
        personalNameCol.setSortable(false);
        personalNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        TableColumn buildDateCol = new TableColumn("下单日期");
        buildDateCol.setSortable(false);
        buildDateCol.setCellValueFactory(new PropertyValueFactory<>("buildDate"));
        buildDateCol.setCellFactory(col -> {
            TableCell<Orders, String> cell = new TableCell<>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        int rowIndex = this.getIndex();
                        String str = ((Orders) orderList.getItems().get(rowIndex)).getBuildDate();
                        this.setText(str.substring(0, 10));
                    }
                }
            };
            return cell;
        });
        TableColumn endDateCol = new TableColumn("截止日期");
        endDateCol.setSortable(false);
        endDateCol.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        endDateCol.setCellFactory(col -> {
            TableCell<Orders, String> cell = new TableCell<>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        int rowIndex = this.getIndex();
                        String str = ((Orders) orderList.getItems().get(rowIndex)).getEndDate();
                        this.setText(str.substring(0, 10));
                    }
                }
            };
            return cell;
        });
        orderList.getColumns().addAll(orderIdCol, companyNameCol, personalNameCol, buildDateCol, endDateCol);
        orderList.setItems(ordersObservableList);

        TableColumn goodsIdCol = new TableColumn("商品编号");
        goodsIdCol.setSortable(true);
        goodsIdCol.setCellValueFactory(new PropertyValueFactory<>("goodsId"));
        TableColumn goodsNameCol = new TableColumn("商品名称");
        goodsNameCol.setSortable(false);
        goodsNameCol.setCellValueFactory(new PropertyValueFactory<>("goodsName"));
        TableColumn orderQuantityCol = new TableColumn("购买数量");
        orderQuantityCol.setSortable(false);
        TableColumn payNumberCol = new TableColumn("数量");
        payNumberCol.setSortable(false);
        payNumberCol.setCellValueFactory(new PropertyValueFactory<>("orderQuantity"));
        TableColumn payUnitCol = new TableColumn("单位");
        payUnitCol.setSortable(false);
        payUnitCol.setCellValueFactory(new PropertyValueFactory<>("goodUnit"));
        orderQuantityCol.getColumns().addAll(payNumberCol, payUnitCol);
        TableColumn stocksCol = new TableColumn("库存数量");
        stocksCol.setSortable(false);
        TableColumn stocksNumberCol = new TableColumn("数量");
        stocksNumberCol.setSortable(false);
        stocksNumberCol.setCellValueFactory(new PropertyValueFactory<>("stocks"));
        TableColumn stocksUnitCol = new TableColumn("单位");
        stocksUnitCol.setSortable(false);
        stocksUnitCol.setCellValueFactory(new PropertyValueFactory<>("goodUnit"));
        stocksCol.getColumns().addAll(stocksNumberCol, stocksUnitCol);
        goodsList.getColumns().addAll(goodsIdCol, goodsNameCol, orderQuantityCol, stocksCol);
        goodsList.setItems(goodsObservableList);

        orderList.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                goodsObservableList.clear();
                selectedGoodNameLabel.setText("未选中商品");
                selectedGoodNameLabel.setTextFill(Color.BLACK);
                buyNumberLabel.setText("未选中商品");
                needProducedNumberLabel.setText("未选中商品");
                currentOrders = ordersObservableList.get((int) newValue);
                orderNumberLabel.setText(String.valueOf(currentOrders.getOrderId()));
                deadlineLabel.setText(currentOrders.getEndDate().substring(0, 10));
                service_searchGoods.restart();
            }
        });

        goodsList.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                currentGood = goodsObservableList.get((int) newValue);
                selectedGoodNameLabel.setText(currentGood.getGoodsName());
                buyNumberLabel.setText(String.valueOf(currentGood.getOrderQuantity()));
                if (currentGood.getProduceQuantity() > 0) {
                    needProducedNumberLabel.setText(String.valueOf(currentGood.getProduceQuantity()));
                    selectedGoodNameLabel.setTextFill(Color.web("#F56C6C"));
                } else {
                    needProducedNumberLabel.setText("库存充足，无需生产");
                    selectedGoodNameLabel.setTextFill(Color.web("#67C23A"));
                }
            }
        });
    }

    private void clearAll() {
        orderNumberLabel.setText("未选中订单");
        deadlineLabel.setText("未选中订单");
        selectedGoodNameLabel.setText("未选中商品");
        selectedGoodNameLabel.setTextFill(Color.BLACK);
        buyNumberLabel.setText("未选中商品");
        needProducedNumberLabel.setText("未选中商品");
        ordersObservableList.clear();
        goodsObservableList.clear();
    }

    @FXML
    private void handleSearch() {
        if (!orderNumberInput.getText().equals("")) {
            clearAll();
            service_search.restart();
        } else {
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "订单号不能为空！", "请输入订单号!");
            alertDialog.showAlert();
        }
    }

    @FXML
    private void handleSearchAll() {
        clearAll();
        service_searchAll.restart();
    }

    @FXML
    private void handleUpload() {
        if (!orderNumberLabel.getText().equals("未选中订单")) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("确认");
            alert.setHeaderText("是否创建生产表单？");
            alert.setContentText("提交后无法撤销！");
            Optional<ButtonType> optionalButtonType = alert.showAndWait();
            if (optionalButtonType.get() == ButtonType.OK) {
                service_upload.restart();
            } else alert.close();
        } else {
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "没有选中订单！", "请选择要处理的订单！");
            alertDialog.showAlert();
        }
    }

    Service<Integer> service_search = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    List<Orders> list = ordersSearch.OrderFuzzySearch(0, orderNumberInput.getText());
                    if (!list.toString().equals("[]") && list != null) {
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getOrderType() == 1 && list.get(i).getOrderState() == 0 && list.get(i).getPaymentState() != 4)
                                ordersObservableList.add(list.get(i));
                        }
                        if (ordersObservableList.size() == 0) {
                            Platform.runLater(() -> {
                                AlertDialog alertDialog = new AlertDialog();
                                alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "没有结果！", "请检查订单号是否正确！");
                                alertDialog.showAlert();
                            });
                        }
                    } else {
                        Platform.runLater(() -> {
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "没有结果！", "请检查订单号是否正确！");
                            alertDialog.showAlert();
                        });
                    }
                    return null;
                }
            };
        }
    };

    Service<Integer> service_searchAll = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    List<Orders> list = ordersSearch.searchAllOrders();
                    if (!list.toString().equals("[]") && list != null) {
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getOrderType() == 1 && list.get(i).getOrderState() == 0 && list.get(i).getPaymentState() != 4)
                                ordersObservableList.add(list.get(i));
                        }
                        if (ordersObservableList.size() == 0) {
                            Platform.runLater(() -> {
                                AlertDialog alertDialog = new AlertDialog();
                                alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "没有结果！", "请检查订单号是否正确！");
                                alertDialog.showAlert();
                            });
                        }
                    } else {
                        Platform.runLater(() -> {
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "没有结果！", "请检查订单号是否正确！");
                            alertDialog.showAlert();
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
                    List list = ordersSearch.searchOrderAndStocks(String.valueOf(currentOrders.getOrderId()));
                    if (!list.toString().equals("[]") && list != null) {
                        for (int i = 0; i < list.size(); i++) {
                            Object[] objects = (Object[]) list.get(i);
                            OrderStocks os = new OrderStocks();
                            os.setGoodsId((int) objects[0]);
                            os.setGoodsName((String) objects[1]);
                            os.setOrderQuantity((Double) objects[2]);
                            os.setGoodsPrice((Double) objects[3]);
                            os.setStocks((Double) objects[4]);
                            os.setProduceQuantity((Double) objects[5]);
                            os.setGoodUnit((String) objects[6]);
                            goodsObservableList.add(os);
                        }
                    } else {
                        Platform.runLater(() -> goodsList.setPlaceholder(new Label("没有结果！")));
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
                    PropertiesOperation propertiesOperation = new PropertiesOperation();
                    ProductionForm productionForm = productionFormSubmission.createProductionForm(currentOrders.getOrderId(), currentOrders.getEndDate(), Integer.valueOf(propertiesOperation.readValue("userConfig.properties", "LoginUserNumber")));
                    if (productionForm != null) {
                        int[] flags = new int[goodsObservableList.size()];
                        for (int i = 0; i < goodsObservableList.size(); i++) {
                            flags[i] = productionFormSubmission.createProductionDetailForm(productionForm, goodsObservableList.get(i).getGoodsId(), goodsObservableList.get(i).getGoodsName(), goodsObservableList.get(i).getProduceQuantity(), goodsObservableList.get(i).getStocks());
                        }
                        int number = 0;
                        for (int i = 0; i < flags.length; i++)
                            if (flags[i] == 1) number++;
                        if (number == goodsObservableList.size()) {
                            Platform.runLater(() -> {
                                AlertDialog alertDialog = new AlertDialog();
                                alertDialog.createAlert(Alert.AlertType.INFORMATION, "成功", "提交成功！", "提交成功！");
                                alertDialog.showAlert();
                                clearAll();
                            });
                        } else {
                            Platform.runLater(() -> {
                                AlertDialog alertDialog = new AlertDialog();
                                alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "提交失败！", "请重新提交！");
                                alertDialog.showAlert();
                            });
                        }
                    } else {
                        Platform.runLater(() -> {
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "提交失败！", "请重新提交！");
                            alertDialog.showAlert();
                        });
                    }
                    return null;
                }
            };
        }
    };
}
