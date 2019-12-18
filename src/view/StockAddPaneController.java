package view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import model.Goods;
import model.GoodsInfo;
import model.GoodsStockInfo;
import model.ShippingDepartment;
import service.AlertDialog;
import service.DoubleFormatService;
import service.GoodsSearch;

import java.util.List;

public class StockAddPaneController {
    @FXML
    private ComboBox searchTypeComboBox;
    @FXML
    private TextField inputText;
    @FXML
    private Button search;
    @FXML
    private Button searchAll;
    @FXML
    private TableView searchList;
    @FXML
    private Button exportBtn;
    @FXML
    private TableView goodsList;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label goodsNumberLabel;
    @FXML
    private Label operatorLabel;
    @FXML
    private Button uploadBtn;

    private ObservableList<String> searchTypeOptions = FXCollections.observableArrayList("按商品编号查询", "按商品名查询");
    private ObservableList<GoodsStockInfo> goodsObservableList = FXCollections.observableArrayList();
    private GoodsSearch goodsSearch = new GoodsSearch();
    private DoubleFormatService doubleFormatService = new DoubleFormatService();

    @FXML
    private void initialize() {
        searchTypeComboBox.setItems(searchTypeOptions);
        searchTypeComboBox.getSelectionModel().select(0);

        TableColumn goodsIdCol = new TableColumn("商品编号");
        goodsIdCol.setSortable(true);
        goodsIdCol.setCellValueFactory(new PropertyValueFactory<>("goodsId"));
        TableColumn goodsNameCol = new TableColumn("商品名称");
        goodsNameCol.setSortable(false);
        goodsNameCol.setCellValueFactory(new PropertyValueFactory<>("goodsName"));
        TableColumn goodsTimeCol = new TableColumn("保质期");
        goodsTimeCol.setSortable(false);
        goodsTimeCol.setCellValueFactory(new PropertyValueFactory<>("goodsQualityTime"));
        TableColumn requiredCol = new TableColumn("所需数量");
        requiredCol.setSortable(true);
        requiredCol.setCellValueFactory(new PropertyValueFactory<>("requiredNumber"));
        requiredCol.setCellFactory(col -> {
            TableCell<GoodsStockInfo, Double> cell = new TableCell<>() {
                @Override
                public void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        int rowIndex = this.getIndex();
                        this.setText(doubleFormatService.getSubstringInputDouble(goodsObservableList.get(rowIndex).getRequiredNumber(), 3) + " " + goodsObservableList.get(rowIndex).getGoodUnit());
                    }
                }
            };
            return cell;
        });
        TableColumn stocksCol = new TableColumn("库存");
        stocksCol.setSortable(true);
        stocksCol.setCellValueFactory(new PropertyValueFactory<>("stocks"));
        stocksCol.setCellFactory(col -> {
            TableCell<GoodsInfo, Double> cell = new TableCell<>() {
                @Override
                public void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        int rowIndex = this.getIndex();
                        GoodsStockInfo temp_goodsStockInfo = goodsObservableList.get(rowIndex);
                        if (temp_goodsStockInfo.getStocks() < temp_goodsStockInfo.getRequiredNumber()) {
                            this.getTableRow().setStyle("-fx-background-color: #F56C6C");
                        } else if (temp_goodsStockInfo.getStocks() == temp_goodsStockInfo.getRequiredNumber()) {
                            this.getTableRow().setStyle("-fx-background-color: #E6A23C");
                        } else {
                            this.getTableRow().setStyle("-fx-background-color: #67C23A");
                        }
                        this.setText(doubleFormatService.getSubstringInputDouble(temp_goodsStockInfo.getStocks(), 3) + " " + temp_goodsStockInfo.getGoodUnit());
                    }
                }
            };
            return cell;
        });
        searchList.getColumns().addAll(goodsIdCol, goodsNameCol, goodsTimeCol, requiredCol, stocksCol);
        searchList.setItems(goodsObservableList);
        //TODO
    }

    @FXML
    private void handleSearch() {
        if (!inputText.getText().equals("")) {
            if (searchTypeComboBox.getSelectionModel().getSelectedIndex() == 0) {
                goodsObservableList.clear();
                service_searchByNumber.restart();
            } else {
                goodsObservableList.clear();
                getService_searchByName.restart();
            }
        } else {
            //TODO
        }
    }

    @FXML
    private void handleSearchAll() {
        //TODO
    }

    @FXML
    private void handleExport() {
        //TODO
    }

    @FXML
    private void handleUploadBtn() {
        //TODO
    }

    Service<Integer> service_searchByNumber = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    Goods goods = goodsSearch.searchGoodsById(Integer.valueOf(inputText.getText()));
                    ShippingDepartment shippingDepartment = goodsSearch.searchStockInfoByGoodsId(Integer.valueOf(inputText.getText()));
                    if (goods != null && shippingDepartment != null) {
                        goodsObservableList.add(new GoodsStockInfo(goods.getGoodsId(), goods.getGoodsName(), goods.getGoodsQualityTime(), shippingDepartment.getStocks(), goods.getRequiredQuantity(), goods.getGoodsUnit()));
                    } else {
                        Platform.runLater(() -> searchList.setPlaceholder(new Label("没有结果")));
                    }
                    return null;
                }
            };
        }
    };

    Service<Integer> getService_searchByName = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    List list = goodsSearch.searchGoods(inputText.getText());
                    if (!list.toString().equals("[]") && list != null) {
                        for (int i = 0; i < list.size(); i++) {
                            Object[] objects = (Object[]) list.get(i);
                            goodsObservableList.add(new GoodsStockInfo((int) objects[0], (String) objects[1], (int) objects[3], (Double) objects[4], (Double) objects[6], (String) objects[5]));
                        }
                    } else Platform.runLater(() -> searchList.setPlaceholder(new Label("没有结果")));
                    return null;
                }
            };
        }
    };
}
