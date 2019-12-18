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
import javafx.util.StringConverter;
import model.*;
import service.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private ObservableList<GoodsStockInfo> select_goodsList = FXCollections.observableArrayList();
    private PropertiesOperation propertiesOperation = new PropertiesOperation();
    private String operatorNumber;
    private final String pattern = "yyyy-MM-dd";
    private ProductionFormSubmission productionFormSubmission = new ProductionFormSubmission();

    @FXML
    private void initialize() {
        operatorLabel.setText(propertiesOperation.returnOperatorFromProperties("userConfig.properties"));
        operatorNumber = propertiesOperation.readValue("userConfig.properties", "LoginUserNumber");
        searchTypeComboBox.setItems(searchTypeOptions);
        searchTypeComboBox.getSelectionModel().select(0);
        goodsNumberLabel.setText("");

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
        searchList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        TableColumn select_goodsIdCol = new TableColumn("商品编号");
        select_goodsIdCol.setSortable(true);
        select_goodsIdCol.setCellValueFactory(new PropertyValueFactory<>("goodsId"));
        TableColumn select_goodsNameCol = new TableColumn("商品名称");
        select_goodsNameCol.setSortable(false);
        select_goodsNameCol.setCellValueFactory(new PropertyValueFactory<>("goodsName"));
        TableColumn select_goodsTimeCol = new TableColumn("保质期");
        select_goodsTimeCol.setSortable(false);
        select_goodsTimeCol.setCellValueFactory(new PropertyValueFactory<>("goodsQualityTime"));
        TableColumn select_requiredCol = new TableColumn("所需数量");
        select_requiredCol.setSortable(true);
        select_requiredCol.setCellValueFactory(new PropertyValueFactory<>("requiredNumber"));
        select_requiredCol.setCellFactory(col -> {
            TableCell<GoodsStockInfo, Double> cell = new TableCell<>() {
                @Override
                public void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        int rowIndex = this.getIndex();
                        this.setText(doubleFormatService.getSubstringInputDouble(select_goodsList.get(rowIndex).getRequiredNumber(), 3) + " " + select_goodsList.get(rowIndex).getGoodUnit());
                    }
                }
            };
            return cell;
        });
        TableColumn select_stocksCol = new TableColumn("库存");
        select_stocksCol.setSortable(true);
        select_stocksCol.setCellValueFactory(new PropertyValueFactory<>("stocks"));
        select_stocksCol.setCellFactory(col -> {
            TableCell<GoodsInfo, Double> cell = new TableCell<>() {
                @Override
                public void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        int rowIndex = this.getIndex();
                        GoodsStockInfo temp_goodsStockInfo = select_goodsList.get(rowIndex);
                        this.setText(doubleFormatService.getSubstringInputDouble(temp_goodsStockInfo.getStocks(), 3) + " " + temp_goodsStockInfo.getGoodUnit());
                    }
                }
            };
            return cell;
        });
        goodsList.getColumns().addAll(select_goodsIdCol, select_goodsNameCol, select_goodsTimeCol, select_requiredCol, select_stocksCol);
        goodsList.setItems(select_goodsList);

        StringConverter converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter =
                    DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        datePicker.setConverter(converter);
        datePicker.setPromptText(pattern.toLowerCase());
        //TODO
    }

    public void clearSidePane() {
        datePicker.setValue(LocalDate.now());
        goodsNumberLabel.setText("");
    }

    @FXML
    private void handleSearch() {
        if (!inputText.getText().equals("")) {
            if (searchTypeComboBox.getSelectionModel().getSelectedIndex() == 0) {
                goodsObservableList.clear();
                select_goodsList.clear();
                clearSidePane();
                service_searchByNumber.restart();
            } else {
                goodsObservableList.clear();
                select_goodsList.clear();
                clearSidePane();
                getService_searchByName.restart();
            }
        } else {
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "没有编号或姓名！", "请输入编号或姓名！");
            alertDialog.showAlert();
        }
    }

    @FXML
    private void handleSearchAll() {
        clearSidePane();
        goodsObservableList.clear();
        select_goodsList.clear();
        service_searchAll.restart();
    }

    @FXML
    private void handleExport() {
        clearSidePane();
        select_goodsList.clear();
        for (Object object : searchList.getSelectionModel().getSelectedIndices()) {
            select_goodsList.add(goodsObservableList.get((int) object));
        }
        goodsNumberLabel.setText(String.valueOf(select_goodsList.size()));
    }

    @FXML
    private void handleUploadBtn() {
        if (select_goodsList.size() != 0 &&
                !datePicker.getValue().toString().equals("")) {
            service_upload.restart();
        } else {
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "表单填写有误！", "表单填写有误！");
            alertDialog.showAlert();
        }
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

    Service<Integer> service_searchAll = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    List list = goodsSearch.searchAllGoods();
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

    Service<Integer> service_upload = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    ProductionForm productionForm = productionFormSubmission.createProductionForm(0, datePicker.getValue().toString() + " 23:59:59", Integer.valueOf(operatorNumber));
                    if (productionForm != null) {
                        int[] flags = new int[select_goodsList.size()];
                        for (int i = 0; i < select_goodsList.size(); i++) {
                            Double productionQuantity = (select_goodsList.get(i).getRequiredNumber() > select_goodsList.get(i).getStocks() ? (select_goodsList.get(i).getRequiredNumber() - select_goodsList.get(i).getStocks()) : 0.0);
                            flags[i] = productionFormSubmission.createProductionDetailForm(productionForm, select_goodsList.get(i).getGoodsId(), select_goodsList.get(i).getGoodsName(), productionQuantity, select_goodsList.get(i).getStocks());
                        }
                        int number = 0;
                        for (int i = 0; i < flags.length; i++) if (flags[i] == 1) number++;
                        if (number == select_goodsList.size()) {
                            Platform.runLater(() -> {
                                AlertDialog alertDialog = new AlertDialog();
                                alertDialog.createAlert(Alert.AlertType.INFORMATION, "成功", "提交成功！", "提交成功！");
                                alertDialog.showAlert();
                                clearSidePane();
                                select_goodsList.clear();
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
