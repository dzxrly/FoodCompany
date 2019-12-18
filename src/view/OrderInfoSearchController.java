package view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
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
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;
import model.OrderBookGoods;
import model.OrderSpotGoods;
import model.Orders;
import org.controlsfx.control.ToggleSwitch;
import service.AddImageForComponent;
import service.CustomerSearch;
import service.DoubleFormatService;
import service.OrdersSearch;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class OrderInfoSearchController {
    //订单信息查询修改控制类
    @FXML
    private ComboBox searchIndexComboBox;
    @FXML
    private TextField searchContentInput;
    @FXML
    private Button searchBtn;
    @FXML
    private Button searchAll;
    @FXML
    private TableView orderListTable;
    @FXML
    private Label orderNumber;
    @FXML
    private TextField customerNameText;
    @FXML
    private TextField customerPhoneText;
    @FXML
    private TextField customerAddressText;
    @FXML
    private DatePicker orderDatePicker;
    @FXML
    private TableView goodsListTable;
    @FXML
    private Label totalPrice;
    @FXML
    private Button printBtn;
    @FXML
    private Button saveChangeBtn;
    @FXML
    private Button deleteOrderBtn;
    @FXML
    private HBox hBox;
    @FXML
    private Label modelLabel;
    @FXML
    private Label orderTypeLabel;

    private ToggleSwitch toggleSwitch = new ToggleSwitch();
    private ObservableList<String> searchTypeOptions = FXCollections.observableArrayList("订单号", "客户名称", "公司/企业名称");
    private ObservableList<Orders> searchData = FXCollections.observableArrayList();
    private final String pattern = "yyyy-MM-dd";
    private ObservableList<OrderBookGoods> orderBookGoodsList = FXCollections.observableArrayList();
    private ObservableList<OrderSpotGoods> orderSpotGoodsList = FXCollections.observableArrayList();
    private Orders currentSelected;

    @FXML
    private void initialize() {
        searchIndexComboBox.setItems(searchTypeOptions);
        searchIndexComboBox.getSelectionModel().select(0);
        toggleSwitch.setSelected(false);
        searchBtn.setGraphic((new AddImageForComponent("img/search14x14.png", 14)).getImageView());
        printBtn.setGraphic((new AddImageForComponent("img/download.png", 14)).getImageView());
        hBox.getChildren().add(1, toggleSwitch);
        printBtn.setDisable(false);
        printBtn.setVisible(true);
        saveChangeBtn.setDisable(true);
        saveChangeBtn.setVisible(false);
        deleteOrderBtn.setDisable(true);
        deleteOrderBtn.setVisible(false);
        orderTypeLabel.setText("");
        customerNameText.setDisable(true);
        customerAddressText.setDisable(true);
        customerPhoneText.setDisable(true);
        orderDatePicker.setDisable(true);
        modelLabel.setText("查询模式");

        TableColumn orderIdCol = new TableColumn("订单编号");
        orderIdCol.setSortable(true);
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        TableColumn orderTypeCol = new TableColumn("订单类型");
        orderTypeCol.setSortable(false);
        orderTypeCol.setCellFactory(col -> {
            TableCell<Orders, IntegerProperty> cell = new TableCell<Orders, IntegerProperty>() {
                @Override
                public void updateItem(IntegerProperty item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        int rowIndex = this.getIndex();
                        int type = ((Orders) orderListTable.getItems().get(rowIndex)).getOrderType();
                        if (type == 0) this.setText("现货订单");
                        else this.setText("预定订单");
                    }
                }
            };
            return cell;
        });
        TableColumn companyNameCol = new TableColumn("公司/企业名称");
        companyNameCol.setSortable(false);
        companyNameCol.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        TableColumn personalName = new TableColumn("客户名称");
        personalName.setSortable(false);
        personalName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        TableColumn costCol = new TableColumn("总价格");
        costCol.setSortable(true);
        costCol.setCellValueFactory(new PropertyValueFactory<>("totalSum"));
//        costCol.setCellFactory(col -> {
//            TableCell<Orders, DoubleProperty> cell = new TableCell<>() {
//                @Override
//                public void updateItem(DoubleProperty item, boolean empty) {
//                    super.updateItem(item, empty);
//                    this.setText(null);
//                    this.setGraphic(null);
//                    if (!empty) {
//                        int rowIndex = this.getIndex();
//                        DoubleFormatService doubleFormatService = new DoubleFormatService();
//                        double res = doubleFormatService.numberFormat((Double) ((Orders) orderListTable.getItems().get(rowIndex)).getTotalSum(), 5);
//                        this.setText(String.valueOf(res));
//                    }
//                }
//            };
//            return cell;
//        });
        orderListTable.setItems(searchData);
        orderListTable.getColumns().addAll(orderIdCol, orderTypeCol, companyNameCol, personalName, costCol);

        TableColumn goodsIdCol = new TableColumn("商品编号");
        goodsIdCol.setSortable(true);
        goodsIdCol.setCellValueFactory(new PropertyValueFactory<>("goodsNumber"));
        TableColumn goodsNameCol = new TableColumn("商品名称");
        goodsNameCol.setSortable(false);
        goodsNameCol.setCellValueFactory(new PropertyValueFactory<>("goodsName"));
        TableColumn goodsNumberCol = new TableColumn("购买数量");
        goodsNumberCol.setSortable(true);
        goodsNumberCol.setCellValueFactory(new PropertyValueFactory<>("orderQuantity"));
        goodsListTable.getColumns().addAll(goodsIdCol, goodsNameCol, goodsNumberCol);

        StringConverter converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);

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
        orderDatePicker.setConverter(converter);
        orderDatePicker.setPromptText(pattern.toLowerCase());

        toggleSwitch.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    modelLabel.setText("修改模式");
                    printBtn.setDisable(true);
                    printBtn.setVisible(false);
                    saveChangeBtn.setDisable(false);
                    saveChangeBtn.setVisible(true);
                    deleteOrderBtn.setDisable(false);
                    deleteOrderBtn.setVisible(true);
                    customerNameText.setDisable(false);
                    customerAddressText.setDisable(false);
                    customerPhoneText.setDisable(false);
                    orderDatePicker.setDisable(false);
                } else {
                    modelLabel.setText("查询模式");
                    printBtn.setDisable(false);
                    printBtn.setVisible(true);
                    saveChangeBtn.setDisable(true);
                    saveChangeBtn.setVisible(false);
                    deleteOrderBtn.setDisable(true);
                    deleteOrderBtn.setVisible(false);
                    customerNameText.setDisable(false);
                    customerAddressText.setDisable(true);
                    customerPhoneText.setDisable(false);
                    orderDatePicker.setDisable(true);
                }
            }
        });

        orderListTable.getSelectionModel().getSelectedCells().addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change c) {
                orderSpotGoodsList.clear();
                orderBookGoodsList.clear();
                currentSelected = searchData.get(orderListTable.getSelectionModel().getSelectedIndex());
                orderNumber.setText(String.valueOf(currentSelected.getOrderId()));
                if (currentSelected.getOrderType() == 1) orderTypeLabel.setText("现货订单");
                else orderTypeLabel.setText("预定订单");
                if (!currentSelected.getCompanyName().equals(""))
                    customerNameText.setText(currentSelected.getCompanyName());
                else customerNameText.setText(currentSelected.getCustomerName());
                customerPhoneText.setText(currentSelected.getCustomerPhone());
                customerAddressText.setText(currentSelected.getCustomerAddress());
                String date = currentSelected.getEndDate();
                int spaceIndex = date.indexOf(" ");
                orderDatePicker.setValue(LocalDate.parse(date.substring(0, spaceIndex), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                if (currentSelected.getOrderType()==0) goodsListTable.setItems(orderSpotGoodsList);
                else goodsListTable.setItems(orderBookGoodsList);
                service_getGoodsList.restart();
                totalPrice.setText(String.valueOf(currentSelected.getTotalSum()));
            }
        });
        //TODO
    }

    @FXML
    private void handleSearch() {
        searchData.clear();
        if (searchContentInput.getText().equals("")) handleSearchAll();
        else service_search.restart();
    }

    @FXML
    private void handleSearchAll() {
        searchData.clear();
        service_searchAll.restart();
    }

    @FXML
    private void handlePrint() {
        //TODO
    }

    @FXML
    private void handleSave() {
        //TODO
    }

    @FXML
    private void handleDelete() {
        //TODO
    }

    Service<Integer> service_search = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    OrdersSearch ordersSearch = new OrdersSearch();
                    int index = searchIndexComboBox.getSelectionModel().getSelectedIndex();
                    String input = searchContentInput.getText();
                    List<Orders> list = ordersSearch.OrderFuzzySearch(index, input);
                    for (int i = 0; i < list.size(); i++) {
                        searchData.add((Orders) list.get(i));
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
                    OrdersSearch ordersSearch = new OrdersSearch();
                    List<Orders> list = ordersSearch.searchAllOrders();
                    for (int i = 0; i < list.size(); i++) {
                        searchData.add((Orders) list.get(i));
                    }
                    return null;
                }
            };
        }
    };

    Service<Integer> service_getGoodsList = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    OrdersSearch ordersSearch = new OrdersSearch();
                    if (currentSelected.getOrderType() == 0) {
                        List<OrderSpotGoods> list = ordersSearch.searchSpotOrBookOrder(String.valueOf(currentSelected.getOrderId()), "0");
                        for (int i = 0; i < list.size(); i++) {
                            orderSpotGoodsList.add(list.get(i));
                        }
                    } else {
                        List<OrderBookGoods> list = ordersSearch.searchSpotOrBookOrder(String.valueOf(currentSelected.getOrderId()), "1");
                        for (int i = 0; i < list.size(); i++) {
                            orderBookGoodsList.add(list.get(i));
                        }
                    }
                    return null;
                }
            };
        }
    };
}
