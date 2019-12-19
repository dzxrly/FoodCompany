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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.OrderBookGoods;
import model.ProductPlan;
import service.*;

import java.util.List;
import java.util.regex.Pattern;

public class ProductionCheckPaneController {
    @FXML
    private TextField numberInputText;
    @FXML
    private Button searchBtn;
    @FXML
    private Label numberLabel;
    @FXML
    private Label sumLabel;
    @FXML
    private Label passRateLabel;
    @FXML
    private TextField checkNumberText;
    @FXML
    private TextField disqualifiedNumberText;
    @FXML
    private ComboBox isCheckedComboBox;
    @FXML
    private Label operatorLabel;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button uploadBtn;
    @FXML
    private VBox vBox;
    @FXML
    private TableView goodsList;
    @FXML
    private Label goodIdLabel;

    private ProductionManagement productionManagement = new ProductionManagement();
    private ProductPlan productPlan = new ProductPlan();
    private OrdersSearch ordersSearch = new OrdersSearch();
    private ObservableList<OrderBookGoods> orderBookGoods = FXCollections.observableArrayList();
    private DoubleFormatService doubleFormatService = new DoubleFormatService();
    private OrderBookGoods currentGood = new OrderBookGoods();

    @FXML
    private void initialize() {
        searchBtn.setGraphic((new AddImageForComponent("img/search14x14.png", 14)).getImageView());
        uploadBtn.setGraphic((new AddImageForComponent("img/check.png", 14)).getImageView());
        passRateLabel.setText("");
        passRateLabel.setTextFill(Color.BLACK);
        numberLabel.setText("");
        sumLabel.setText("");
        passRateLabel.setText("");

        TableColumn goodsIdCol = new TableColumn("商品编号");
        goodsIdCol.setSortable(true);
        goodsIdCol.setCellValueFactory(new PropertyValueFactory<>("goodsNumber"));
        TableColumn goodsNameCol = new TableColumn("商品名称");
        goodsNameCol.setSortable(false);
        goodsNameCol.setCellValueFactory(new PropertyValueFactory<>("goodsName"));
        TableColumn orderQuantityCol = new TableColumn("购买数量");
        orderQuantityCol.setSortable(true);
        orderQuantityCol.setCellValueFactory(new PropertyValueFactory<>("orderQuantity"));
        orderQuantityCol.setCellFactory(col -> {
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
        TableColumn priceCol = new TableColumn("单价");
        priceCol.setSortable(true);
        priceCol.setCellValueFactory(new PropertyValueFactory<>("goodsPrice"));
        priceCol.setCellFactory(col -> {
            TableCell<OrderBookGoods, Double> cell = new TableCell<>() {
                @Override
                public void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        int row = this.getIndex();
                        this.setText(doubleFormatService.getSubstringInputDouble(orderBookGoods.get(row).getGoodsPrice(), 2));
                    }
                }
            };
            return cell;
        });
        goodsList.getColumns().addAll(goodsIdCol, goodsNameCol, orderQuantityCol, priceCol);
        goodsList.setItems(orderBookGoods);

        checkNumberText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if ((isInteger(newValue) || isDouble(newValue)) && (isInteger(disqualifiedNumberText.getText()) || isDouble(disqualifiedNumberText.getText()))) {
                    if (Double.valueOf(disqualifiedNumberText.getText()) >= Double.valueOf(newValue)) {
                        Double disqualifiedRate = Double.valueOf(disqualifiedNumberText.getText()) / Double.valueOf(newValue);
                        passRateLabel.setText(String.valueOf((1.0 - disqualifiedRate) * 100).substring(0, 6) + "%");
                        passRateLabel.setTextFill(Color.BLACK);
                    } else {
                        passRateLabel.setText("抽检数量不能小于不合格数！");
                        passRateLabel.setTextFill(Color.web("#F56C6C"));
                    }
                } else {
                    passRateLabel.setText("抽检数量和不合格数只能填写数字！");
                    passRateLabel.setTextFill(Color.web("#F56C6C"));
                }
            }
        });

        disqualifiedNumberText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if ((isInteger(newValue) || isDouble(newValue)) && (isInteger(checkNumberText.getText()) || isDouble(checkNumberText.getText()))) {
                    if (Double.valueOf(checkNumberText.getText()) >= Double.valueOf(newValue)) {
                        Double disqualifiedRate = Double.valueOf(newValue) / Double.valueOf(checkNumberText.getText());
                        passRateLabel.setText(String.valueOf((1.0 - disqualifiedRate) * 100).substring(0, 6) + "%");
                        passRateLabel.setTextFill(Color.BLACK);
                    } else {
                        passRateLabel.setText("抽检数量不能小于不合格数！");
                        passRateLabel.setTextFill(Color.web("#F56C6C"));
                    }
                } else {
                    passRateLabel.setText("抽检数量和不合格数只能填写数字！");
                    passRateLabel.setTextFill(Color.web("#F56C6C"));
                }
            }
        });

        goodsList.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                currentGood = orderBookGoods.get((int) newValue);
                goodIdLabel.setText(String.valueOf(currentGood.getGoodsNumber()));
            }
        });
        //TODO
    }

    @FXML
    private void handleSearch() {
        if (!numberInputText.getText().equals("") && numberInputText.getText().matches("[0-9]+")) {
            orderBookGoods.clear();
            service_search.restart();
        } else {
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "单号输入有误", "请确认单号是否正确！");
            alertDialog.showAlert();
        }
    }

    @FXML
    private void handleUpload() {
        //TODO
    }

    //判断整数（int）
    private boolean isInteger(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    //判断String是否为浮点数
    private boolean isDouble(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?\\d*[.]\\d+$");
        return pattern.matcher(str).matches();
    }

    Service<Integer> service_search = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    List<ProductPlan> list = productionManagement.searchProductionPlan(numberInputText.getText());
                    if (!list.toString().equals("[]")) {
                        productPlan = list.get(0);
                        Platform.runLater(() -> numberLabel.setText(String.valueOf(productPlan.getPlanId())));
                        List<OrderBookGoods> orderBookGoodsList = ordersSearch.searchSpotOrBookOrder(String.valueOf(productPlan.getOrderId()), "1");
                        if (!orderBookGoodsList.toString().equals("[]")) {
                            for (int i = 0; i < orderBookGoodsList.size(); i++)
                                orderBookGoods.add(orderBookGoodsList.get(i));
                            Platform.runLater(() -> sumLabel.setText(String.valueOf(orderBookGoodsList.size())));
                        } else {
                            Platform.runLater(() -> goodsList.setPlaceholder(new Label("没有结果")));
                        }
                    } else {
                        Platform.runLater(() -> {
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "没有结果！", "请确认订单号是否正确！");
                            alertDialog.showAlert();
                        });
                    }
                    return null;
                }
            };
        }
    };
}
