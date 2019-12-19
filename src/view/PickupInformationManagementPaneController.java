package view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.GoodsInfo;
import model.OrderBookGoods;
import model.OrderSpotGoods;
import model.Orders;
import service.*;

import java.util.List;

public class PickupInformationManagementPaneController {
    @FXML
    private TextField orderInputText;
    @FXML
    private Button searchBtn;
    @FXML
    private Label orderNumberLabel;
    @FXML
    private Label orderTypeLabel;
    @FXML
    private Label companyNameLabel;
    @FXML
    private Label customerNameLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label totalCostLabel;
    @FXML
    private Label operatorLabel;
    @FXML
    private TableView goodsList;
    @FXML
    private Button uploadBtn;

    private ShippingDepOperation shippingDepOperation = new ShippingDepOperation();
    private Orders currentOrder = new Orders();
    private CustomerIndexAndStringSwitch customerIndexAndStringSwitch = new CustomerIndexAndStringSwitch();
    private DoubleFormatService doubleFormatService = new DoubleFormatService();
    private PropertiesOperation propertiesOperation = new PropertiesOperation();
    private ObservableList<GoodsInfo> goodsInfos = FXCollections.observableArrayList();
    private String regex = "[0-9]+";
    private String operatorNumber;

    @FXML
    private void initialize() {
        searchBtn.setGraphic((new AddImageForComponent("img/search14x14.png", 14)).getImageView());
        uploadBtn.setGraphic((new AddImageForComponent("img/check.png", 14)).getImageView());
        operatorLabel.setText(propertiesOperation.returnOperatorFromProperties("userConfig.properties"));
        operatorNumber = propertiesOperation.readValue("userConfig.properties","LoginUserNumber");

        TableColumn idCol = new TableColumn("商品编号");
        idCol.setSortable(true);
        idCol.setCellValueFactory(new PropertyValueFactory<>("goodsId"));
        TableColumn nameCol = new TableColumn("商品名称");
        nameCol.setSortable(false);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("goodsName"));
        TableColumn priceCol = new TableColumn("单价");
        priceCol.setSortable(true);
        priceCol.setCellValueFactory(new PropertyValueFactory<>("goodsPrice"));
        priceCol.setCellFactory(col -> {
            TableCell<GoodsInfo, Double> cell = new TableCell<>() {
                @Override
                public void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        int row = this.getIndex();
                        this.setText(doubleFormatService.getSubstringInputDouble(goodsInfos.get(row).getGoodsPrice(), 2));
                    }
                }
            };
            return cell;
        });
        TableColumn countCol = new TableColumn("购买数量");
        countCol.setSortable(false);
        countCol.setCellValueFactory(new PropertyValueFactory<>("payNumber"));
        countCol.setCellFactory(col -> {
            TableCell<GoodsInfo, Double> cell = new TableCell<>() {
                @Override
                public void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        int row = this.getIndex();
                        this.setText(doubleFormatService.getSubstringInputDouble(goodsInfos.get(row).getPayNumber(), 3) + goodsInfos.get(row).getGoodUnit());
                    }
                }
            };
            return cell;
        });
        goodsList.getColumns().addAll(idCol, nameCol, priceCol, countCol);
        goodsList.setItems(goodsInfos);
    }

    private void clearSidePane() {
        orderNumberLabel.setText("");
        orderTypeLabel.setText("");
        companyNameLabel.setText("");
        customerNameLabel.setText("");
        phoneLabel.setText("");
        addressLabel.setText("");
        totalCostLabel.setText("");
    }

    @FXML
    private void handleSearch() {
        if (!orderInputText.getText().equals("") && orderInputText.getText().matches(regex)) {
            goodsInfos.clear();
            service_search.restart();
        } else {
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "订单号输入有误", "请确认订单号是否正确!");
            alertDialog.showAlert();
        }
    }

    @FXML
    private void handleUpload() {
        if (!orderNumberLabel.getText().equals("")) service_upload.restart();
        else {
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "没有订单！", "请输入订单号!");
            alertDialog.showAlert();
        }
    }

    Service<Integer> service_search = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    int flag = shippingDepOperation.judgeExist(Integer.valueOf(orderInputText.getText()));
                    if (flag == 0) {
                        currentOrder = shippingDepOperation.pickingManagement(Integer.valueOf(orderInputText.getText()));
                        if (currentOrder != null) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    orderNumberLabel.setText(String.valueOf(currentOrder.getOrderId()));
                                    orderTypeLabel.setText(customerIndexAndStringSwitch.returnOrderTypeByIndex(currentOrder.getOrderType()));
                                    if (currentOrder.getCompanyName() != null)
                                        companyNameLabel.setText(currentOrder.getCompanyName());
                                    else companyNameLabel.setText("无");
                                    customerNameLabel.setText(currentOrder.getCustomerName());
                                    phoneLabel.setText(currentOrder.getCustomerPhone());
                                    addressLabel.setText(currentOrder.getCustomerAddress());
                                    totalCostLabel.setText(doubleFormatService.getSubstringInputDouble(currentOrder.getTotalSum(), 2));
                                }
                            });
                            OrdersSearch ordersSearch = new OrdersSearch();
                            if (currentOrder.getOrderType() == 0) {
                                List<OrderSpotGoods> list = ordersSearch.searchSpotOrBookOrder(String.valueOf(currentOrder.getOrderId()), "0");
                                if (!list.toString().equals("[]")) {
                                    for (int i = 0; i < list.size(); i++) {
                                        goodsInfos.add(new GoodsInfo(list.get(i).getGoodsNumber(), list.get(i).getGoodsName(), list.get(i).getGoodsPrice(), 0, 0.0, list.get(i).getOrderQuantity(), list.get(i).getGoodsUnit()));
                                    }
                                } else {
                                    Platform.runLater(() -> goodsList.setPlaceholder(new Label("没有结果")));
                                }
                            } else {
                                List<OrderBookGoods> list = ordersSearch.searchSpotOrBookOrder(String.valueOf(currentOrder.getOrderId()), "1");
                                if (!list.toString().equals("[]")) {
                                    for (int i = 0; i < list.size(); i++) {
                                        goodsInfos.add(new GoodsInfo(list.get(i).getGoodsNumber(), list.get(i).getGoodsName(), list.get(i).getGoodsPrice(), 0, 0.0, list.get(i).getOrderQuantity(), list.get(i).getGoodsUnit()));
                                    }
                                } else {
                                    Platform.runLater(() -> goodsList.setPlaceholder(new Label("没有结果")));
                                }
                            }
                        } else {
                            Platform.runLater(() -> {
                                AlertDialog alertDialog = new AlertDialog();
                                alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "没有结果！", "请确认订单号是否正确!");
                                alertDialog.showAlert();
                            });
                        }
                    } else {
                        Platform.runLater(() -> {
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "没有结果！", "该订单已被提货");
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
                    int flag = shippingDepOperation.createDeliveryRecord(currentOrder.getOrderId(), Integer.valueOf(operatorNumber));
                    if (flag == 1) {
                        Platform.runLater(() -> {
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.INFORMATION, "成功", "提交成功！", "提交成功！");
                            alertDialog.showAlert();
                            goodsInfos.clear();
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
}
