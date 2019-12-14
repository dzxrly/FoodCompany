package view;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.CustomerOrder;
import model.Orders;
import org.hibernate.annotations.FilterDef;
import service.*;

import java.util.List;
import java.util.Optional;

public class PaymentForCashOrdersController {
    //现货订单付款控制类
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
    private Label prepaymentsLabel;
    @FXML
    private Label restPaymentsLabel;
    @FXML
    private Label totalCost;
    @FXML
    private Label operatorLabel;
    @FXML
    private TableView goodsList;
    @FXML
    private Button upload;
    @FXML
    private Label orderTypeLabel;
    @FXML
    private Button printBtn;
    @FXML
    private TextField hasPaiedPrepaymentText;
    @FXML
    private TextField hasPaiedRestPaymentText;
    @FXML
    private TextField hasPaiedAllCostText;
    @FXML
    private TextField bankCardNumberText;
    @FXML
    private ComboBox payStatus;

    private String operator;
    private String operatorNumber;
    private CustomerOrder currentOrder;
    private Double prePayment = 0.0;
    private Double resPayment = 0.0;
    private Double totalPayment = 0.0;

    @FXML
    private void initialize() {
        PropertiesOperation propertiesOperation = new PropertiesOperation();
        operator = propertiesOperation.readValue("userConfig.properties", "LoginUserName");
        operatorNumber = propertiesOperation.readValue("userConfig.properties", "LoginUserNumber");
        operatorLabel.setText(operator + " [" + operatorNumber + "]");
        searchBtn.setGraphic((new AddImageForComponent("img/search14x14.png", 14)).getImageView());
        upload.setGraphic((new AddImageForComponent("img/upload14x14.png", 14)).getImageView());
        printBtn.setGraphic((new AddImageForComponent("img/download.png", 14)).getImageView());
    }

    @FXML
    private void handleUpload() {
        if (!bankCardNumberText.getText().equals("")) service_upload.restart();
        else {
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "银行卡号不能为空！", "请重新提交！");
            alertDialog.showAlert();
        }
    }

    @FXML
    private void handleSearchBtn() {
        if (!orderNumberInput.getText().equals("")) {
            clearAll();
            service_search.restart();
        } else {
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "订单号不能为空！", "请输入订单号！");
            alertDialog.showAlert();
        }
    }

    @FXML
    private void handlePrint() {
        //TODO
    }

    private void clearAll() {
        currentOrder = null;
        prePayment = 0.0;
        resPayment = 0.0;
        totalPayment = 0.0;
        orderTypeLabel.setText("");
        orderNumberLabel.setText("");
        customerNumberLabel.setText("");
        customerNameLabel.setText("");
        customerLevelLabel.setText("");
        prepaymentsLabel.setText("");
        restPaymentsLabel.setText("");
        totalCost.setText("");
        hasPaiedPrepaymentText.setText("");
        hasPaiedPrepaymentText.setDisable(false);
        hasPaiedRestPaymentText.setText("");
        hasPaiedRestPaymentText.setDisable(false);
        hasPaiedAllCostText.setText("");
        hasPaiedAllCostText.setDisable(false);
        bankCardNumberText.setText("");
    }

    Service<Integer> service_search = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    OrdersSearch ordersSearch = new OrdersSearch();
                    currentOrder = ordersSearch.searchCustomerAndOrder(orderNumberInput.getText());
                    if (currentOrder != null) {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                CustomerIndexAndStringSwitch customerIndexAndStringSwitch = new CustomerIndexAndStringSwitch();
                                orderTypeLabel.setText(customerIndexAndStringSwitch.returnOrderTypeByIndex(currentOrder.getOrderType()));
                                orderNumberLabel.setText(String.valueOf(currentOrder.getOrderId()));
                                customerNumberLabel.setText(String.valueOf(currentOrder.getNumber()));
                                if (!currentOrder.getCompanyName().equals(""))
                                    customerNameLabel.setText(currentOrder.getCompanyName());
                                else customerNameLabel.setText(currentOrder.getPersonalName());
                                customerLevelLabel.setText(customerIndexAndStringSwitch.returnCustomerLevelByIndex(currentOrder.getLevel()));
                                prePayment = customerIndexAndStringSwitch.getPrepaymentBySumAndLevel(currentOrder.getTotalSum(), currentOrder.getLevel());
                                resPayment = customerIndexAndStringSwitch.getRespaymentBySumAndLevel(currentOrder.getTotalSum(), currentOrder.getLevel());
                                totalPayment = currentOrder.getTotalSum();
                                DoubleFormatService doubleFormatService = new DoubleFormatService();
                                prepaymentsLabel.setText(doubleFormatService.getSubstringPrice(prePayment, 8));
                                restPaymentsLabel.setText(doubleFormatService.getSubstringPrice(resPayment, 8));
                                totalCost.setText(doubleFormatService.getSubstringPrice(totalPayment, 8));
                                if (currentOrder.getPaymentState() == 0) {
                                    hasPaiedPrepaymentText.setText(doubleFormatService.getSubstringPrice(prePayment, 8));
                                    hasPaiedPrepaymentText.setDisable(false);
                                    hasPaiedRestPaymentText.setText(doubleFormatService.getSubstringPrice(resPayment, 8));
                                    hasPaiedRestPaymentText.setDisable(false);
                                    hasPaiedAllCostText.setText(doubleFormatService.getSubstringPrice(totalPayment, 8));
                                    hasPaiedAllCostText.setDisable(false);
                                } else if (currentOrder.getPaymentState() == 1) {
                                    hasPaiedPrepaymentText.setText(doubleFormatService.getSubstringPrice(prePayment, 8));
                                    hasPaiedPrepaymentText.setDisable(true);
                                    hasPaiedRestPaymentText.setText(doubleFormatService.getSubstringPrice(resPayment, 8));
                                    hasPaiedRestPaymentText.setDisable(false);
                                    hasPaiedAllCostText.setText(doubleFormatService.getSubstringPrice(totalPayment, 8));
                                    hasPaiedAllCostText.setDisable(false);
                                } else {
                                    hasPaiedPrepaymentText.setText(doubleFormatService.getSubstringPrice(prePayment, 8));
                                    hasPaiedPrepaymentText.setDisable(true);
                                    hasPaiedRestPaymentText.setText(doubleFormatService.getSubstringPrice(resPayment, 8));
                                    hasPaiedRestPaymentText.setDisable(true);
                                    hasPaiedAllCostText.setText(doubleFormatService.getSubstringPrice(totalPayment, 8));
                                    hasPaiedAllCostText.setDisable(true);
                                }
                            }
                        });
                    } else {
                        Platform.runLater(() -> {
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "没有找到订单！", "请确认订单是否正确！");
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
                    OrderPaymentSubmission orderPaymentSubmission = new OrderPaymentSubmission();
                    if (currentOrder.getPaymentState() == 0) {
                        if (!hasPaiedAllCostText.getText().equals(totalCost.getText())) {
                            Platform.runLater(() -> {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("确认");
                                alert.setHeaderText("当前提交的是订单预付款记录");
                                alert.setContentText("是否确认提交？提交后无法更改");
                                Optional<ButtonType> optionalButtonType = alert.showAndWait();
                                int flag = 0;
                                if (optionalButtonType.get() == ButtonType.OK) {
                                    flag = orderPaymentSubmission.submitOrderPayment(0, Integer.valueOf(operatorNumber), prePayment, currentOrder.getOrderId(), bankCardNumberText.getText());
                                    if (flag == 0) {
                                        AlertDialog alertDialog = new AlertDialog();
                                        alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "提交失败！", "请重新提交！");
                                        alertDialog.showAlert();
                                    } else {
                                        AlertDialog alertDialog = new AlertDialog();
                                        alertDialog.createAlert(Alert.AlertType.INFORMATION, "成功", "提交成功！", "订单付款记录已提交！");
                                        alertDialog.showAlert();
                                        clearAll();
                                    }
                                } else alert.close();
                            });
                        } else {
                            Platform.runLater(() -> {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("确认");
                                alert.setHeaderText("当前提交的是订单全款记录");
                                alert.setContentText("是否确认提交？提交后无法更改，若要提交预付款记录请清空“已缴纳全款”输入框！");
                                Optional<ButtonType> optionalButtonType = alert.showAndWait();
                                int flag = 0;
                                if (optionalButtonType.get() == ButtonType.OK) {
                                    flag = orderPaymentSubmission.submitOrderPayment(2, Integer.valueOf(operatorNumber), totalPayment, currentOrder.getOrderId(), bankCardNumberText.getText());
                                    if (flag == 0) {
                                        AlertDialog alertDialog = new AlertDialog();
                                        alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "提交失败！", "请重新提交！");
                                        alertDialog.showAlert();
                                    } else {
                                        AlertDialog alertDialog = new AlertDialog();
                                        alertDialog.createAlert(Alert.AlertType.INFORMATION, "成功", "提交成功！", "订单付款记录已提交！");
                                        alertDialog.showAlert();
                                        clearAll();
                                    }
                                } else alert.close();
                            });
                        }
                    } else {
                        Platform.runLater(() -> {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("确认");
                            alert.setHeaderText("当前提交的是订单尾款记录");
                            alert.setContentText("是否确认提交？提交后无法更改，若要提交全款记录请保证“应缴纳全款”与“已缴纳全款”输入款中的内容相同！");
                            Optional<ButtonType> optionalButtonType = alert.showAndWait();
                            int flag = 0;
                            if (optionalButtonType.get() == ButtonType.OK) {
                                flag = orderPaymentSubmission.submitOrderPayment(0, Integer.valueOf(operatorNumber), resPayment, currentOrder.getOrderId(), bankCardNumberText.getText());
                                if (flag == 0) {
                                    AlertDialog alertDialog = new AlertDialog();
                                    alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "提交失败！", "请重新提交！");
                                    alertDialog.showAlert();
                                } else {
                                    AlertDialog alertDialog = new AlertDialog();
                                    alertDialog.createAlert(Alert.AlertType.INFORMATION, "成功", "提交成功！", "订单付款记录已提交！");
                                    alertDialog.showAlert();
                                    clearAll();
                                }
                            } else alert.close();
                        });
                    }
                    return null;
                }
            };
        }
    };
}
