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
import javafx.util.StringConverter;
import model.CustomerOrder;
import model.Orders;
import service.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TransportInformationController {
    //物流信息页面控制类
    @FXML
    private TextField orderNumberInput;
    @FXML
    private Button searchBtn;
    @FXML
    private Label orderNumberLabel;
    @FXML
    private Label customerNumberLabel;
    @FXML
    private Label customerNameLabel;
    @FXML
    private Label customerLevelLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private ComboBox addressComboBox;
    @FXML
    private TextField addressText;
    @FXML
    private ComboBox transportCompanyComboBox;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextField transportNumberText;
    @FXML
    private ComboBox transportTypeComboBox;
    @FXML
    private Label operatorLabel;
    @FXML
    private Button uploadBtn;
    @FXML
    private Button printBtn;

    private ObservableList<String> addressOptions = FXCollections.observableArrayList("使用预填地址", "使用新增地址");
    private ObservableList<String> transportCompanyNameOptions = FXCollections.observableArrayList("EMS", "顺丰速运");
    private ObservableList<String> transportTypeOptions = FXCollections.observableArrayList("普通陆运", "空运", "冷冻运输");
    private String operatorName;
    private String operatorNumber;
    private CustomerOrder currentOrder = new CustomerOrder();
    private final String pattern = "yyyy-MM-dd";

    @FXML
    private void initialize() {
        addressText.setDisable(true);
        addressComboBox.setItems(addressOptions);
        addressComboBox.getSelectionModel().select(0);
        transportCompanyComboBox.setItems(transportCompanyNameOptions);
        transportCompanyComboBox.getSelectionModel().select(0);
        transportTypeComboBox.setItems(transportTypeOptions);
        transportTypeComboBox.getSelectionModel().select(0);
        searchBtn.setGraphic((new AddImageForComponent("img/search14x14.png", 14)).getImageView());
        uploadBtn.setGraphic((new AddImageForComponent("img/check.png", 14)).getImageView());
        printBtn.setGraphic((new AddImageForComponent("img/download.png", 14)).getImageView());
        orderNumberLabel.setText("");
        customerNameLabel.setText("");
        customerNumberLabel.setText("");
        customerLevelLabel.setText("");
        phoneLabel.setText("");
        emailLabel.setText("");
        addressText.setText("");
        PropertiesOperation propertiesOperation = new PropertiesOperation();
        operatorLabel.setText(propertiesOperation.returnOperatorFromProperties("userConfig.properties"));

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
        datePicker.setConverter(converter);
        datePicker.setPromptText(pattern.toLowerCase());
        datePicker.setValue(LocalDate.now());

        addressComboBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if ((int) newValue == 1) {
                    addressText.setDisable(false);
                } else addressText.setDisable(true);
            }
        });
    }

    @FXML
    private void handleSearch() {
        if (!orderNumberInput.getText().equals("")) {
            service_search.restart();
        } else {
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "订单号不能为空！", "请输入订单号！");
            alertDialog.showAlert();
        }
    }

    @FXML
    private void handleUpload() {
        if (!currentOrder.getPersonalName().equals("") && !transportNumberText.getText().equals("") && !addressText.getText().equals("")) {
            service_submission.restart();
        } else {
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "表单填写错误！", "订单号、地址或物流单号不能为空！");
            alertDialog.showAlert();
        }
    }

    @FXML
    private void handlePrint() {
        //TODO
    }

    Service<Integer> service_search = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    OrdersSearch ordersSearch = new OrdersSearch();
                    currentOrder = ordersSearch.searchCustomerAndOrder(orderNumberInput.getText());
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            if (currentOrder != null) {
                                orderNumberLabel.setText(String.valueOf(currentOrder.getOrderId()));
                                customerNumberLabel.setText(String.valueOf(currentOrder.getNumber()));
                                if (currentOrder.getCompanyName() != null)
                                    customerNameLabel.setText(currentOrder.getCompanyName());
                                else customerNameLabel.setText(currentOrder.getPersonalName());
                                CustomerIndexAndStringSwitch customerIndexAndStringSwitch = new CustomerIndexAndStringSwitch();
                                customerLevelLabel.setText(customerIndexAndStringSwitch.returnCustomerLevelByIndex(currentOrder.getLevel()));
                                phoneLabel.setText(currentOrder.getPhoneNumber());
                                emailLabel.setText(currentOrder.getEmail());
                                addressText.setText(currentOrder.getAddress());
                            } else {
                                AlertDialog alertDialog = new AlertDialog();
                                alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "没有搜索结果！", "请确认订单号是否正确！");
                                alertDialog.showAlert();
                            }
                        }
                    });
                    return null;
                }
            };
        }
    };

    Service<Integer> service_submission = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    LogisticsSubmission logisticsSubmission = new LogisticsSubmission();
                    logisticsSubmission.logisticsSubmit(transportNumberText.getText(), currentOrder.getOrderId(), transportTypeComboBox.getSelectionModel().getSelectedIndex(), transportCompanyComboBox.getValue().toString(), datePicker.getValue().toString(), addressText.getText());
                    Platform.runLater(() -> {
                        AlertDialog alertDialog = new AlertDialog();
                        alertDialog.createAlert(Alert.AlertType.INFORMATION, "成功", "物流单提交成功！", "物流单提交成功！");
                        alertDialog.showAlert();
                    });
                    return null;
                }
            };
        }
    };
}
