package view;

import javafx.application.Platform;
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
import model.Orders;
import org.controlsfx.control.ToggleSwitch;
import service.AddImageForComponent;
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

    @FXML
    private void initialize() {
        searchIndexComboBox.setItems(searchTypeOptions);
        searchIndexComboBox.getSelectionModel().select(0);
        toggleSwitch.setSelected(false);
        searchBtn.setGraphic((new AddImageForComponent("img/search14x14.png", 14)).getImageView());
        hBox.getChildren().add(1, toggleSwitch);
        printBtn.setDisable(false);
        printBtn.setVisible(true);
        saveChangeBtn.setDisable(true);
        saveChangeBtn.setVisible(false);
        deleteOrderBtn.setDisable(true);
        deleteOrderBtn.setVisible(false);
        orderTypeLabel.setText("");

        TableColumn orderIdCol = new TableColumn("订单编号");
        orderIdCol.setSortable(true);
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        TableColumn orderTypeCol = new TableColumn("订单类型");
        orderTypeCol.setSortable(false);
        orderTypeCol.setCellValueFactory(new PropertyValueFactory<>("orderType"));
        TableColumn companyNameCol = new TableColumn("公司/企业名称");
        companyNameCol.setSortable(false);
        companyNameCol.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        TableColumn personalName = new TableColumn("客户名称");
        personalName.setSortable(false);
        personalName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        TableColumn costCol = new TableColumn("总价格");
        costCol.setSortable(true);
        costCol.setCellValueFactory(new PropertyValueFactory<>("totalSum"));
        orderListTable.setItems(searchData);
        orderListTable.getColumns().addAll(orderIdCol, orderTypeCol, companyNameCol, personalName, costCol);

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
                    printBtn.setDisable(true);
                    printBtn.setVisible(false);
                    saveChangeBtn.setDisable(false);
                    saveChangeBtn.setVisible(true);
                    deleteOrderBtn.setDisable(false);
                    deleteOrderBtn.setVisible(true);
                } else {
                    printBtn.setDisable(false);
                    printBtn.setVisible(true);
                    saveChangeBtn.setDisable(true);
                    saveChangeBtn.setVisible(false);
                    deleteOrderBtn.setDisable(true);
                    deleteOrderBtn.setVisible(false);
                }
            }
        });

        orderListTable.getSelectionModel().getSelectedCells().addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change c) {
                Orders orders = searchData.get(orderListTable.getSelectionModel().getSelectedIndex());
                orderNumber.setText(String.valueOf(orders.getOrderId()));
                if (orders.getOrderType() == 1) orderTypeLabel.setText("现货订单");
                else orderTypeLabel.setText("预定订单");
                if (!orders.getCompanyName().equals("")) customerNameText.setText(orders.getCompanyName());
                else customerNameText.setText(orders.getCustomerName());
                customerPhoneText.setText(orders.getCustomerPhone());
                customerAddressText.setText(orders.getCustomerAddress());
                String date = orders.getEndDate();
                int spaceIndex = date.indexOf(" ");
                orderDatePicker.setValue(LocalDate.parse(date.substring(0, spaceIndex), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                //TODO
                totalPrice.setText(String.valueOf(orders.getTotalSum()));
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
}
