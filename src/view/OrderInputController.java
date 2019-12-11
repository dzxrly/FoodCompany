package view;

import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.Goods;
import service.AddImageForComponent;
import service.GoodsSearch;

import java.util.List;

public class OrderInputController {
    //订单信息录入控制类
    @FXML
    private Label lockStatus;
    @FXML
    private ComboBox orderType;
    @FXML
    private TextField goodsName;
    @FXML
    private Button searchBtn;
    @FXML
    private Button addSelectedToTable;
    @FXML
    private Button addAllToTable;
    @FXML
    private TableView searchResTable;
    @FXML
    private TableView goodsTable;
    @FXML
    private Button clearGoodsTable;
    @FXML
    private Button deleteSelectedGoodFromTable;
    @FXML
    private Button uploadOrder;
    @FXML
    private TextField customerName;
    @FXML
    private TextField customerPhone;
    @FXML
    private TextField customerAddress;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button printBtn;
    @FXML
    private TextField totalPrice;

    private ObservableList<String> orderTypeOptions = FXCollections.observableArrayList("现货订单", "预定订单");
    private ObservableList<GoodsInfo> rightGoodsListData = FXCollections.observableArrayList();
    private String searchInput;

    @FXML
    private void initialize() {
        orderType.setItems(orderTypeOptions);
        orderType.getSelectionModel().select(0);
        uploadOrder.setDisable(true);
        lockStatus.setText("订单类型未锁定");
        orderType.setDisable(false);
        lockStatus.setGraphic(new ImageView(new Image("img/unlock.png", 16, 16, false, false)));
        uploadOrder.setGraphic((new AddImageForComponent("img/check.png", 16)).getImageView());
        printBtn.setGraphic((new AddImageForComponent("img/download.png", 16)).getImageView());
        addSelectedToTable.setGraphic((new AddImageForComponent("img/cart_empty.png", 16)).getImageView());
        addAllToTable.setGraphic((new AddImageForComponent("img/cart.png", 16)).getImageView());

        TableColumn goodsIDCol = new TableColumn("编号");
        goodsIDCol.setSortable(true);
        goodsIDCol.setCellValueFactory(new PropertyValueFactory<>("goodsId"));
        TableColumn goodsNameCol = new TableColumn("商品名");
        goodsNameCol.setSortable(false);
        goodsNameCol.setCellValueFactory(new PropertyValueFactory<>("goodsName"));
        TableColumn goodPriceCol = new TableColumn("单价");
        goodPriceCol.setSortable(true);
        goodPriceCol.setCellValueFactory(new PropertyValueFactory<>("goodsPrice"));
        TableColumn goodsQualityTimeCol = new TableColumn("保质期");
        goodsQualityTimeCol.setSortable(false);
        goodsQualityTimeCol.setCellValueFactory(new PropertyValueFactory<>("goodsQualityTime"));
        TableColumn stocksCol = new TableColumn("库存");
        stocksCol.setSortable(true);
        stocksCol.setCellValueFactory(new PropertyValueFactory<>("stocks"));
        searchResTable.setItems(rightGoodsListData);
        searchResTable.getColumns().addAll(goodsIDCol, goodsNameCol, goodPriceCol, goodsQualityTimeCol, stocksCol);
        searchResTable.setPlaceholder(new Label("没有搜索结果"));
        searchResTable.setEditable(true);
        //TODO
    }

    @FXML
    private void handleSearch() {
        System.out.println("SEARCH");
        searchInput = goodsName.getText();
        service.restart();
    }

    @FXML
    private void handleAddSelectedBtn() {
        lockStatus.setGraphic(new ImageView(new Image("img/lock.png", 16, 16, false, false)));
        lockStatus.setText("订单类型已锁定，解锁请移除所有所选商品");
        orderType.setDisable(true);
        uploadOrder.setDisable(false);
        //TODO
    }

    @FXML
    private void handleAddAllBtn() {
        lockStatus.setGraphic(new ImageView(new Image("img/lock.png", 16, 16, false, false)));
        lockStatus.setText("订单类型已锁定，解锁请移除所有所选商品");
        orderType.setDisable(true);
        uploadOrder.setDisable(false);
        //TODO
    }

    @FXML
    private void handleDeleteSelectedGood() {
        //TODO
    }

    @FXML
    private void handleClearAllGoods() {
        lockStatus.setGraphic(new ImageView(new Image("img/unlock.png", 16, 16, false, false)));
        lockStatus.setText("订单类型未锁定");
        orderType.setDisable(false);
        uploadOrder.setDisable(true);
        //TODO
    }

    @FXML
    private void handleSearchTextFieldKeyEvent(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            handleSearch();
        }
    }

    @FXML
    private void handleUploadOrder() {
        //TODO
    }

    @FXML
    private void handlePrint() {
        //TODO
    }

    Service<Integer> service = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    GoodsSearch goodsSearch = new GoodsSearch();
                    List<Object> list = goodsSearch.searchGoods(searchInput);
                    for (int i = 0; i < list.size(); i++) {
                        Object[] obj = (Object[]) list.get(i);
                        System.out.println((String) obj[1]);
                        rightGoodsListData.add(new GoodsInfo((int) obj[0], (String) obj[1], (Double) obj[2], (int) obj[3], (int) obj[4]));
                    }
                    return null;
                }
            };
        }
    };

    public static class GoodsInfo {
        private SimpleIntegerProperty goodsId;
        private SimpleStringProperty goodsName;
        private SimpleDoubleProperty goodsPrice;
        private SimpleIntegerProperty goodsQualityTime;
        private SimpleIntegerProperty stocks;

        private GoodsInfo(int goodsId, String goodsName, Double goodsPrice, int goodsQualityTime, int stocks) {
            this.goodsId = new SimpleIntegerProperty(goodsId);
            this.goodsName = new SimpleStringProperty(goodsName);
            this.goodsPrice = new SimpleDoubleProperty(goodsPrice);
            this.goodsQualityTime = new SimpleIntegerProperty(goodsQualityTime);
            this.stocks = new SimpleIntegerProperty(stocks);
        }

        public int getGoodsId() {
            return goodsId.get();
        }

        public SimpleIntegerProperty goodsIdProperty() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId.set(goodsId);
        }

        public String getGoodsName() {
            return goodsName.get();
        }

        public SimpleStringProperty goodsNameProperty() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName.set(goodsName);
        }

        public double getGoodsPrice() {
            return goodsPrice.get();
        }

        public SimpleDoubleProperty goodsPriceProperty() {
            return goodsPrice;
        }

        public void setGoodsPrice(double goodsPrice) {
            this.goodsPrice.set(goodsPrice);
        }

        public int getGoodsQualityTime() {
            return goodsQualityTime.get();
        }

        public SimpleIntegerProperty goodsQualityTimeProperty() {
            return goodsQualityTime;
        }

        public void setGoodsQualityTime(int goodsQualityTime) {
            this.goodsQualityTime.set(goodsQualityTime);
        }

        public int getStocks() {
            return stocks.get();
        }

        public SimpleIntegerProperty stocksProperty() {
            return stocks;
        }

        public void setStocks(int stocks) {
            this.stocks.set(stocks);
        }
    }
}
