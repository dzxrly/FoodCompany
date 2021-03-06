package view;

import javafx.application.Platform;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import javafx.util.StringConverter;
import model.Customer;
import model.Goods;
import model.GoodsInfo;
import model.Orders;
import service.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

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
    private ComboBox customerTypeComboBox;
    @FXML
    private TextField customerNameInputText;
    @FXML
    private Button customerSearchBtn;
    @FXML
    private TableView customerListTable;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button printBtn;
    @FXML
    private TextField totalPrice;
    @FXML
    private TextField payNumberText;
    @FXML
    private Label customerNameLabel;
    @FXML
    private Label customerNumberLabel;
    @FXML
    private Label customerPhoneLabel;

    private ObservableList<String> orderTypeOptions = FXCollections.observableArrayList("现货订单", "预定订单");
    private ObservableList<GoodsInfo> rightGoodsListData = FXCollections.observableArrayList();
    private String searchInput;
    private ObservableList<GoodsInfo> leftGoodsListData = FXCollections.observableArrayList();
    private ObservableList<String> searchIndexOptions = FXCollections.observableArrayList("个人", "公司/企业");
    private ObservableList<Customer> customerData = FXCollections.observableArrayList();
    private Customer currentCustomer;
    private final String pattern = "yyyy-MM-dd";
    private Double sum = 0.0;

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
        searchBtn.setGraphic((new AddImageForComponent("img/search14x14.png", 14)).getImageView());
        customerSearchBtn.setGraphic((new AddImageForComponent("img/search14x14.png", 14)).getImageView());
        deleteSelectedGoodFromTable.setGraphic((new AddImageForComponent("img/close14x14.png", 14)).getImageView());
        clearGoodsTable.setGraphic((new AddImageForComponent("img/close_filled.png", 14)).getImageView());
        customerTypeComboBox.setItems(searchIndexOptions);
        customerTypeComboBox.getSelectionModel().select(0);

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
        stocksCol.setSortable(false);
        TableColumn stockNumberCol = new TableColumn("数量");
        stockNumberCol.setSortable(false);
        stockNumberCol.setCellValueFactory(new PropertyValueFactory<>("stocks"));
        TableColumn stockUnitCol = new TableColumn("单位");
        stockUnitCol.setSortable(false);
        stockUnitCol.setCellValueFactory(new PropertyValueFactory<>("goodUnit"));
        stocksCol.getColumns().addAll(stockNumberCol, stockUnitCol);
        searchResTable.setItems(rightGoodsListData);
        searchResTable.getColumns().addAll(goodsIDCol, goodsNameCol, goodPriceCol, goodsQualityTimeCol, stocksCol);
        searchResTable.setPlaceholder(new Label("没有搜索结果"));
        searchResTable.setEditable(true);
        searchResTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        TableColumn left_goodsIDCol = new TableColumn("编号");
        left_goodsIDCol.setSortable(true);
        left_goodsIDCol.setCellValueFactory(new PropertyValueFactory<>("goodsId"));
        TableColumn left_goodsNameCol = new TableColumn("商品名");
        left_goodsNameCol.setSortable(false);
        left_goodsNameCol.setCellValueFactory(new PropertyValueFactory<>("goodsName"));
        TableColumn left_goodPriceCol = new TableColumn("单价");
        left_goodPriceCol.setSortable(true);
        left_goodPriceCol.setCellValueFactory(new PropertyValueFactory<>("goodsPrice"));
        TableColumn left_goodsQualityTimeCol = new TableColumn("保质期");
        left_goodsQualityTimeCol.setSortable(false);
        left_goodsQualityTimeCol.setCellValueFactory(new PropertyValueFactory<>("goodsQualityTime"));
        TableColumn left_stocksCol = new TableColumn("库存");
        left_stocksCol.setSortable(false);
        TableColumn left_stockNumberCol = new TableColumn("数量");
        left_stockNumberCol.setSortable(false);
        left_stockNumberCol.setCellValueFactory(new PropertyValueFactory<>("stocks"));
        TableColumn left_stockUnitCol = new TableColumn("单位");
        left_stockUnitCol.setSortable(false);
        left_stockUnitCol.setCellValueFactory(new PropertyValueFactory<>("goodUnit"));
        left_stocksCol.getColumns().addAll(left_stockNumberCol, left_stockUnitCol);
        TableColumn left_payNumber = new TableColumn("购买数量");
        left_payNumber.setSortable(false);
        TableColumn left_payCountCol = new TableColumn("数量");
        left_payCountCol.setSortable(false);
        left_payCountCol.setCellValueFactory(new PropertyValueFactory<>("payNumber"));
        TableColumn left_payUnitCol = new TableColumn("单位");
        left_payUnitCol.setSortable(false);
        left_payUnitCol.setCellValueFactory(new PropertyValueFactory<>("goodUnit"));
        left_payNumber.getColumns().addAll(left_payCountCol, left_payUnitCol);
        goodsTable.setItems(leftGoodsListData);
        goodsTable.getColumns().addAll(left_goodsIDCol, left_goodsNameCol, left_goodPriceCol, left_goodsQualityTimeCol, left_stocksCol, left_payNumber);
        goodsTable.setPlaceholder(new Label("没有结果"));
        goodsTable.setEditable(true);

        TableColumn customer_number = new TableColumn("客户编号");
        customer_number.setSortable(true);
        customer_number.setCellValueFactory(new PropertyValueFactory<>("number"));
        TableColumn customer_companyName = new TableColumn("公司/企业名称");
        customer_companyName.setSortable(false);
        customer_companyName.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        TableColumn customer_personalName = new TableColumn("客户名称");
        customer_personalName.setSortable(false);
        customer_personalName.setCellValueFactory(new PropertyValueFactory<>("personalName"));
        customerListTable.setItems(customerData);
        customerListTable.getColumns().addAll(customer_number, customer_companyName, customer_personalName);
        customerListTable.setPlaceholder(new Label("没有结果"));
        customerListTable.setEditable(true);

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

        leftGoodsListData.addListener(new ListChangeListener<GoodsInfo>() {
            @Override
            public void onChanged(Change<? extends GoodsInfo> c) {
                sum = 0.0;
                for (int i = 0; i < leftGoodsListData.size(); i++) {
                    GoodsInfo tempGoodsInfo = (GoodsInfo) leftGoodsListData.get(i);
                    Double goodPrice = tempGoodsInfo.getGoodsPrice();
                    Double number = tempGoodsInfo.getPayNumber();
                    sum += goodPrice * number;
                }
                DoubleFormatService doubleFormatService = new DoubleFormatService();
                totalPrice.setText(doubleFormatService.getSubstringPrice(sum, 8));
            }
        });

        customerListTable.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                currentCustomer = customerData.get(customerListTable.getSelectionModel().getSelectedIndex());
                customerNumberLabel.setText(String.valueOf(currentCustomer.getNumber()));
                if (!currentCustomer.getCompanyName().equals(""))
                    customerNameLabel.setText(currentCustomer.getCompanyName());
                else customerNameLabel.setText(currentCustomer.getPersonalName());
                customerPhoneLabel.setText(currentCustomer.getPhoneNumber());
            }
        });
    }

    @FXML
    private void handleSearch() {
        rightGoodsListData.clear();
        searchInput = goodsName.getText();
        service.restart();
    }

    @FXML
    private void handleAddSelectedBtn() {
        if (rightGoodsListData.size() != 0 && !payNumberText.getText().equals("")) {
            lockStatus.setGraphic(new ImageView(new Image("img/lock.png", 16, 16, false, false)));
            lockStatus.setText("订单类型已锁定，解锁请移除所有所选商品");
            orderType.setDisable(true);
            uploadOrder.setDisable(false);

            for (int i = 0; i < searchResTable.getSelectionModel().getSelectedIndices().size(); i++) {
                GoodsInfo tempGoodsInfo = (GoodsInfo) rightGoodsListData.get((int) searchResTable.getSelectionModel().getSelectedIndices().get(i));
                if (orderType.getSelectionModel().getSelectedIndex() == 0) {
                    if (tempGoodsInfo.getStocks() >= Double.valueOf(payNumberText.getText())) {
                        tempGoodsInfo.setPayNumber(Double.valueOf(payNumberText.getText()));
                        leftGoodsListData.add(tempGoodsInfo);
                    } else {
                        AlertDialog alertDialog = new AlertDialog();
                        alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "库存不足！", "现货订单购买数量不能大于库存数量！");
                        alertDialog.showAlert();
                        if (leftGoodsListData.size() == 0) {
                            lockStatus.setGraphic(new ImageView(new Image("img/unlock.png", 16, 16, false, false)));
                            lockStatus.setText("订单类型未锁定");
                            orderType.setDisable(false);
                            uploadOrder.setDisable(true);
                        }
                    }
                } else {
                    tempGoodsInfo.setPayNumber(Double.valueOf(payNumberText.getText()));
                    leftGoodsListData.add(tempGoodsInfo);
                }
            }
        } else {
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "无效数据！", "列表中没有信息或购买数量未填写！");
            alertDialog.showAlert();
        }
    }

    @FXML
    private void handleDeleteSelectedGood() {
        int selectedIndex = goodsTable.getSelectionModel().getSelectedIndex();
        leftGoodsListData.remove(selectedIndex, selectedIndex + 1);
        if (leftGoodsListData.size() == 0) {
            lockStatus.setGraphic(new ImageView(new Image("img/unlock.png", 16, 16, false, false)));
            lockStatus.setText("订单类型未锁定");
            orderType.setDisable(false);
            uploadOrder.setDisable(true);
        }
    }

    @FXML
    private void handleClearAllGoods() {
        lockStatus.setGraphic(new ImageView(new Image("img/unlock.png", 16, 16, false, false)));
        lockStatus.setText("订单类型未锁定");
        orderType.setDisable(false);
        uploadOrder.setDisable(true);
        leftGoodsListData.clear();
    }

    @FXML
    private void handleSearchTextFieldKeyEvent(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            handleSearch();
        }
    }

    @FXML
    private void handleCustomerSearch() {
        customerData.clear();
        customerNameLabel.setText("");
        customerNumberLabel.setText("");
        customerPhoneLabel.setText("");
        service_searchCustomerByName.restart();
    }

    private void clearAll() {
        totalPrice.setText("0.0");
        leftGoodsListData.clear();
        rightGoodsListData.clear();
        customerData.clear();
        customerNameLabel.setText("");
        customerNumberLabel.setText("");
        customerPhoneLabel.setText("");
        datePicker.setValue(LocalDate.now());
    }

    @FXML
    private void handleUploadOrder() {
        if (customerNumberLabel.getText().equals("")) {
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "提交出错!", "信息填写错误！");
            alertDialog.showAlert();
        } else service_submissionOrder.restart();
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
                        rightGoodsListData.add(new GoodsInfo((int) obj[0], (String) obj[1], (Double) obj[2], (int) obj[3], (Double) obj[4], 0.0, (String) obj[5]));
                    }
                    return null;
                }
            };
        }
    };

    Service<Integer> service_searchCustomerByName = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    CustomerSearch customerSearch = new CustomerSearch();
                    if (customerTypeComboBox.getSelectionModel().getSelectedIndex() == 0) {
                        List<Customer> list = customerSearch.NameFuzzySearch(customerNameInputText.getText());
                        for (int i = 0; i < list.size(); i++) {
                            customerData.add((Customer) list.get(i));
                        }
                    } else if (customerTypeComboBox.getSelectionModel().getSelectedIndex() == 1) {
                        List<Customer> list = customerSearch.CompanyFuzzySearch(customerNameInputText.getText());
                        for (int i = 0; i < list.size(); i++) {
                            customerData.add((Customer) list.get(i));
                        }
                    } else {
                        List<Customer> list = customerSearch.AllSearch();
                        for (int i = 0; i < list.size(); i++) {
                            customerData.add((Customer) list.get(i));
                        }
                    }
                    return null;
                }
            };
        }
    };

    Service<Integer> service_submissionOrder = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    OrdersSubmission ordersSubmission = new OrdersSubmission();
                    GoodsInfo[] goodsInfos = new GoodsInfo[leftGoodsListData.size()];
                    for (int i = 0; i < leftGoodsListData.size(); i++) {
                        goodsInfos[i] = leftGoodsListData.get(i);
                    }
                    if (orderType.getSelectionModel().getSelectedIndex() == 0) {
                        //现货订单
                        PropertiesOperation propertiesOperation = new PropertiesOperation();
                        int number = Integer.valueOf(propertiesOperation.readValue("userConfig.properties", "LoginUserNumber"));
                        Orders orders = ordersSubmission.createMainOrders(0, currentCustomer.getCompanyName(), currentCustomer.getPersonalName(), currentCustomer.getPhoneNumber(), currentCustomer.getAddress(), sum, number, datePicker.getValue().toString());
                        int[] flags = new int[leftGoodsListData.size()];
                        for (int i = 0; i < leftGoodsListData.size(); i++) {
                            flags[i] = ordersSubmission.createSpotOrders(orders, leftGoodsListData.get(i).getGoodsId(), leftGoodsListData.get(i).getGoodsName(), (Double) leftGoodsListData.get(i).getPayNumber());
                        }
                        int flagsEqualOneCount = 0;
                        for (int i = 0; i < flags.length; i++) {
                            if (flags[i] == 1) {
                                flagsEqualOneCount++;
                            } else continue;
                        }
                        if (flagsEqualOneCount == flags.length) {
                            Platform.runLater(() -> {
                                AlertDialog alertDialog = new AlertDialog();
                                alertDialog.createAlert(Alert.AlertType.INFORMATION, "成功", "提交完成!", "提交完成!");
                                alertDialog.show();
                                clearAll();
                            });
                        } else {
                            Platform.runLater(() -> {
                                AlertDialog alertDialog = new AlertDialog();
                                alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "提交出错!", "提交出错!");
                                alertDialog.show();
                            });
                        }
                    } else {
                        //预定订单
                        String recommendDate = ordersSubmission.Judge(goodsInfos, datePicker.getValue().toString());
                        if (recommendDate.equals("1")) {
                            PropertiesOperation propertiesOperation = new PropertiesOperation();
                            int number = Integer.valueOf(propertiesOperation.readValue("userConfig.properties", "LoginUserNumber"));
                            Orders orders = ordersSubmission.createMainOrders(1, currentCustomer.getCompanyName(), currentCustomer.getPersonalName(), currentCustomer.getPhoneNumber(), currentCustomer.getAddress(), sum, number, datePicker.getValue().toString());
                            int[] flags = new int[leftGoodsListData.size()];
                            for (int i = 0; i < leftGoodsListData.size(); i++) {
                                flags[i] = ordersSubmission.createBookOrders(orders, leftGoodsListData.get(i).getGoodsId(), leftGoodsListData.get(i).getGoodsName(), (Double) leftGoodsListData.get(i).getPayNumber(), leftGoodsListData.get(i).getStocks(), (Double) Math.max(0.0, leftGoodsListData.get(i).getPayNumber() - leftGoodsListData.get(i).getStocks()));
                            }
                            int flagsEqualOneCount = 0;
                            for (int i = 0; i < flags.length; i++) {
                                if (flags[i] == 1) {
                                    flagsEqualOneCount++;
                                } else continue;
                            }
                            if (flagsEqualOneCount == flags.length) {
                                Platform.runLater(() -> {
                                    AlertDialog alertDialog = new AlertDialog();
                                    alertDialog.createAlert(Alert.AlertType.INFORMATION, "成功", "提交完成!", "提交完成!");
                                    alertDialog.show();
                                    clearAll();
                                });
                            } else {
                                Platform.runLater(() -> {
                                    AlertDialog alertDialog = new AlertDialog();
                                    alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "提交出错!", "提交出错!");
                                    alertDialog.show();
                                });
                            }
                        } else {
                            Platform.runLater(() -> {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("提交失败");
                                alert.setHeaderText("指定截止日期前无法完成订单！");
                                alert.setContentText("检测到" + recommendDate + "前可以完成订单，是否应用？");

                                Optional<ButtonType> res = alert.showAndWait();
                                if (res.get() == ButtonType.OK) {
                                    datePicker.setValue(LocalDate.parse(recommendDate.substring(0, 10), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                                } else alert.close();
                            });
                        }
                    }
                    return null;
                }
            };
        }
    };
}
