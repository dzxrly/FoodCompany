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
import javafx.scene.paint.Color;
import model.Orders;
import service.*;

import java.util.List;

public class OrderCancelPaneController {
    //订单退货处理控制类

    @FXML
    private TextField orderNumberInput;
    @FXML
    private Button searchBtn;
    @FXML
    private Label orderNumberLabel;
    @FXML
    private Label orderTypeLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Label customerNameLabel;
    @FXML
    private Label customerPhoneLabel;
    @FXML
    private Label customerAddressLabel;
    @FXML
    private ComboBox isHasProblem;
    @FXML
    private TextArea note;
    @FXML
    private Button uploadBtn;
    @FXML
    private Button clearBtn;
    @FXML
    private Label inputNumberMaxLabel;
    @FXML
    private Label operatorLabel;

    private ObservableList<String> typeOptions = FXCollections.observableArrayList("是", "否");
    private Orders currtenOrder = new Orders();
    private int stuffId;

    @FXML
    private void initialize() {
        isHasProblem.setItems(typeOptions);
        isHasProblem.getSelectionModel().select(0);
        searchBtn.setGraphic((new AddImageForComponent("img/search14x14.png", 14)).getImageView());
        uploadBtn.setGraphic((new AddImageForComponent("img/upload14x14.png", 14)).getImageView());
        clearBtn.setGraphic((new AddImageForComponent("img/close.png", 14)).getImageView());
        PropertiesOperation propertiesOperation = new PropertiesOperation();
        stuffId = Integer.valueOf(propertiesOperation.readValue("userConfig.properties","LoginUserNumber"));
        operatorLabel.setText(propertiesOperation.returnOperatorFromProperties("userConfig.properties"));

        orderNumberLabel.setText("");
        orderTypeLabel.setText("");
        statusLabel.setText("");
        customerNameLabel.setText("");
        customerAddressLabel.setText("");
        customerPhoneLabel.setText("");
        note.setWrapText(true);
        inputNumberMaxLabel.setText("0/128");
        inputNumberMaxLabel.setTextFill(Color.BLACK);

        note.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length()>=128) {
                    inputNumberMaxLabel.setText(String.valueOf(newValue.length()+"/128 超过最大字数限制"));
                    inputNumberMaxLabel.setTextFill(Color.web("#F56C6C"));
                    uploadBtn.setDisable(true);
                } else {
                    inputNumberMaxLabel.setText(String.valueOf(newValue.length()) + "/128");
                    inputNumberMaxLabel.setTextFill(Color.BLACK);
                    uploadBtn.setDisable(false);
                }
            }
        });
        //TODO
    }

    @FXML
    private void handleSearch() {
        if (!orderNumberInput.getText().equals("")) {
            service_search.restart();
        } else {
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "订单号填写错误！", "请输入订单号！");
            alertDialog.showAlert();
        }
    }

    @FXML
    private void handleSave() {
        if (!orderTypeLabel.getText().equals("")) {
            service_upload.restart();
        } else {
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "没有找到订单！", "请输入订单号！");
            alertDialog.showAlert();
        }
    }

    @FXML
    private void handleClear() {
        isHasProblem.getSelectionModel().select(0);
        orderNumberLabel.setText("");
        orderTypeLabel.setText("");
        statusLabel.setText("");
        customerNameLabel.setText("");
        customerAddressLabel.setText("");
        customerPhoneLabel.setText("");
        note.setText("");
        inputNumberMaxLabel.setText("0/128");
        inputNumberMaxLabel.setTextFill(Color.BLACK);
    }

    Service<Integer> service_search = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    OrdersSearch ordersSearch = new OrdersSearch();
                    List<Orders> list = ordersSearch.OrderFuzzySearch(0, orderNumberInput.getText());
                    if (!list.toString().equals("[]") && (list != null)) {
                        currtenOrder = list.get(0);
                        RefundQuestCheck refundQuestCheck = new RefundQuestCheck(currtenOrder.getOrderId());
                        if (refundQuestCheck.getCheckRes()) {
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    orderNumberLabel.setText(String.valueOf(currtenOrder.getOrderId()));
                                    CustomerIndexAndStringSwitch customerIndexAndStringSwitch = new CustomerIndexAndStringSwitch();
                                    orderTypeLabel.setText(customerIndexAndStringSwitch.returnCustomerTypeByIndex(currtenOrder.getOrderType()));
                                    statusLabel.setText(customerIndexAndStringSwitch.returnStatusByIndex(currtenOrder.getOrderState()));
                                    if (!currtenOrder.getCompanyName().equals(""))
                                        customerNameLabel.setText(currtenOrder.getCompanyName());
                                    else customerNameLabel.setText(currtenOrder.getCustomerName());
                                    customerAddressLabel.setText(currtenOrder.getCustomerAddress());
                                    customerPhoneLabel.setText(currtenOrder.getCustomerPhone());
                                }
                            });
                        }else {
                            Platform.runLater(() -> {
                                AlertDialog alertDialog = new AlertDialog();
                                alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "没有找到订单或订单已退款！", "请确认订单号是否正确！");
                                alertDialog.showAlert();
                            });
                        }
                    } else {
                        Platform.runLater(() -> {
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "没有找到订单！", "请确认订单号是否正确！");
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
                    ReturnBillSubmission returnBillSubmission = new ReturnBillSubmission();
                    int flag = returnBillSubmission.submitReturnBill(stuffId,currtenOrder.getCustomerNumber(),currtenOrder.getOrderId(),note.getText(),isHasProblem.getSelectionModel().getSelectedIndex());
                    if (flag == 1) {
                        Platform.runLater(() -> {
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.INFORMATION, "成功", "退货记录已提交！", "退货记录已提交！");
                            alertDialog.showAlert();
                            handleClear();
                        });
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
