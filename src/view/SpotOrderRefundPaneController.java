package view;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.CustomerOrder;
import service.*;

public class SpotOrderRefundPaneController {
    //现货订单退款控制类

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
    private Label saleDepOperatoerLabel;
    @FXML
    private Label hasPayPrepaymentLabel;
    @FXML
    private Label hasPayResPaymentLabel;
    @FXML
    private Label totalCostLabel;
    @FXML
    private TextField needPayPrepaymentText;
    @FXML
    private TextField needPayResPaymentText;
    @FXML
    private TextField needPayTotalCostText;
    @FXML
    private Label fianceDepOperatorLabel;
    @FXML
    private Button uploadBtn;
    @FXML
    private Label orderTypeLabel;
    @FXML
    private Label customerLevelLabel;
    @FXML
    private Button printBtn;

    private String costRegex = "[0-9.]+";
    private CustomerOrder currentOrder = new CustomerOrder();
    private String operatorName;
    private String operatorNumber;
    private Double needPayPrepayment = 0.0;
    private Double needPayResPayment = 0.0;
    private Double needTotalCost = 0.0;

    @FXML
    private void initialize() {
        searchBtn.setGraphic((new AddImageForComponent("img/search14x14.png", 14)).getImageView());
        uploadBtn.setGraphic((new AddImageForComponent("img/upload14x14.png", 14)).getImageView());
        printBtn.setGraphic((new AddImageForComponent("img/download.png", 14)).getImageView());
        operatorName = (new PropertiesOperation()).readValue("userConfig.properties", "LoginUserName");
        operatorNumber = (new PropertiesOperation()).readValue("userConfig.properties", "LoginUserNumber");
        fianceDepOperatorLabel.setText(operatorName + " [" + operatorNumber + "]");
        handleClearAll();

        needPayPrepaymentText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.equals("") &&
                        !needPayResPaymentText.getText().equals("") &&
                        newValue.matches(costRegex) &&
                        needPayResPaymentText.getText().matches(costRegex))
                    needPayTotalCostText.setText(String.valueOf(Double.valueOf(needPayPrepaymentText.getText()) + Double.valueOf(needPayResPaymentText.getText())));
            }
        });
        needPayResPaymentText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.equals("") &&
                        !needPayPrepaymentText.getText().equals("") &&
                        newValue.matches(costRegex) &&
                        needPayPrepaymentText.getText().matches(costRegex))
                    needPayTotalCostText.setText(String.valueOf(Double.valueOf(needPayPrepaymentText.getText()) + Double.valueOf(needPayResPaymentText.getText())));
            }
        });
        //TODO
    }

    @FXML
    private void handleSearch() {
        if (!orderNumberInput.getText().equals("")) {
            handleClearAll();
            service_search.restart();
        } else {
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "订单号不能为空！", "请输入订单号！");
            alertDialog.showAlert();
        }
        //TODO
    }

    @FXML
    private void handleUpload() {
        //TODO
    }

    @FXML
    private void handlePrint() {
        //TODO
    }

    private void handleClearAll() {
        needPayPrepayment = 0.0;
        needPayResPayment = 0.0;
        needTotalCost = 0.0;
        currentOrder = null;
        orderTypeLabel.setText("");
        orderNumberLabel.setText("");
        customerNumberLabel.setText("");
        customerNameLabel.setText("");
        customerLevelLabel.setText("");
        saleDepOperatoerLabel.setText("");
        hasPayPrepaymentLabel.setText("");
        hasPayResPaymentLabel.setText("");
        totalCostLabel.setText("");
        needPayPrepaymentText.setText("0.0");
        needPayResPaymentText.setText("0.0");
        needPayTotalCostText.setText("0.0");
        needPayPrepaymentText.setDisable(false);
        needPayResPaymentText.setDisable(false);
        needPayTotalCostText.setDisable(false);
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
                        RefundQuestCheck refundQuestCheck = new RefundQuestCheck(currentOrder.getOrderId());
                        if (refundQuestCheck.getCheckRes()) {
                            CustomerIndexAndStringSwitch customerIndexAndStringSwitch = new CustomerIndexAndStringSwitch();
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    orderTypeLabel.setText(customerIndexAndStringSwitch.returnOrderTypeByIndex(currentOrder.getOrderType()));
                                    orderNumberLabel.setText(String.valueOf(currentOrder.getOrderId()));
                                    customerNumberLabel.setText(String.valueOf(currentOrder.getNumber()));
                                    if (currentOrder.getCompanyName().equals(""))
                                        customerNameLabel.setText(currentOrder.getPersonalName());
                                    else customerNameLabel.setText(currentOrder.getCompanyName());
                                    customerLevelLabel.setText(customerIndexAndStringSwitch.returnCustomerLevelByIndex(currentOrder.getLevel()));
                                    fianceDepOperatorLabel.setText(String.valueOf(currentOrder.getStuffNumber()));
                                    if (currentOrder.getPaymentState() == 0) {
                                        needPayPrepayment = 0.0;
                                        needPayResPayment = 0.0;
                                        needTotalCost = 0.0;
                                        hasPayPrepaymentLabel.setText("0.0");
                                        hasPayResPaymentLabel.setText("0.0");
                                        totalCostLabel.setText("0.0");
                                        needPayPrepaymentText.setDisable(true);
                                        needPayResPaymentText.setDisable(true);
                                        needPayTotalCostText.setDisable(true);
                                    } else if (currentOrder.getPaymentState() == 1) {
                                        needPayPrepayment = customerIndexAndStringSwitch.getPrepaymentBySumAndLevel(currentOrder.getTotalSum(), currentOrder.getLevel());
                                        needPayResPayment = 0.0;
                                        needTotalCost = 0.0;
                                        hasPayPrepaymentLabel.setText(String.valueOf(needPayPrepayment));
                                        hasPayResPaymentLabel.setText("0.0");
                                        totalCostLabel.setText("0.0");
                                        needPayPrepaymentText.setDisable(false);
                                        needPayResPaymentText.setDisable(true);
                                        needPayTotalCostText.setDisable(true);
                                    } else {
                                        needPayPrepayment = customerIndexAndStringSwitch.getPrepaymentBySumAndLevel(currentOrder.getTotalSum(), currentOrder.getLevel());
                                        needPayResPayment = customerIndexAndStringSwitch.getRespaymentBySumAndLevel(currentOrder.getTotalSum(), currentOrder.getLevel());
                                        needTotalCost = currentOrder.getTotalSum();
                                        hasPayPrepaymentLabel.setText(String.valueOf(needPayPrepayment));
                                        hasPayResPaymentLabel.setText(String.valueOf(needPayResPayment));
                                        totalCostLabel.setText(String.valueOf(needTotalCost));
                                        needPayPrepaymentText.setDisable(false);
                                        needPayResPaymentText.setDisable(false);
                                        needPayTotalCostText.setDisable(false);
                                    }
                                }
                            });
                        } else {
                            Platform.runLater(() -> {
                                AlertDialog alertDialog = new AlertDialog();
                                alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "无法退款！", "订单不存在或已退款！");
                                alertDialog.showAlert();
                            });
                        }
                    } else {
                        Platform.runLater(() -> {
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "无法退款！", "找不到订单或销售部未通过退款请求！");
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

                    return null;
                }
            };
        }
    };
}
