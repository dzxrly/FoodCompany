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
import service.AddImageForComponent;
import service.AlertDialog;
import service.BillSubmission;
import service.PropertiesOperation;

import java.util.Optional;

public class BillManagementController {
    //账单管理控制类
    @FXML
    private ComboBox billTypeComboBox;
    @FXML
    private TextField billNumberText;
    @FXML
    private TextField priceText;
    @FXML
    private ComboBox incomingOrOutcomingTypeComboBox;
    @FXML
    private TextField projectNameText;
    @FXML
    private TextField cardNumberText;
    @FXML
    private Label operatorLabel;
    @FXML
    private Button previewBtn;
    @FXML
    private Button uploadBtn;
    @FXML
    private Button printBtn;
    @FXML
    private Label billTypeLabel;
    @FXML
    private Label billNumberLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Label incomingOrOutcomingTypeLabel;
    @FXML
    private Label projectLabel;
    @FXML
    private Label cardNumberLabel;
    @FXML
    private Label addBill_incomingOrOutcomingLabel;
    @FXML
    private Label addBill_incomingOrOutcomingTypeLabel;
    @FXML
    private Label addBill_projectTypeLabel;
    @FXML
    private Label preview_incomingOrOutcomingLabel;
    @FXML
    private Label preview_incomingOrOutcomingTypeLabel;
    @FXML
    private Label preview_projectTypeLabel;
    @FXML
    private Label warningInfoLabel;

    private ObservableList<String> billTypeOptions = FXCollections.observableArrayList("收入账单", "支出账单");
    private ObservableList<String> incomingBillTypeOptions = FXCollections.observableArrayList("预付款", "尾款", "全款");
    private ObservableList<String> outcomingBillTypeOptions = FXCollections.observableArrayList("税务", "原料");
    private BillSubmission billSubmission = new BillSubmission();
    private int recommendOrderNumber = 0;
    private String orderNumberRegex = "[0-9]+";
    private String paymentRegex = "[0-9.]+";

    @FXML
    private void initialize() {
        warningInfoLabel.setText("该订单号已处理，无法创建账单！");
        warningInfoLabel.setTextFill(Color.web("#F56C6C"));
        warningInfoLabel.setVisible(false);
        previewBtn.setGraphic((new AddImageForComponent("img/image.png", 14)).getImageView());
        uploadBtn.setGraphic((new AddImageForComponent("img/check.png", 14)).getImageView());
        printBtn.setGraphic((new AddImageForComponent("img/download.png", 14)).getImageView());
        addBill_incomingOrOutcomingLabel.setText("收入金额：");
        addBill_incomingOrOutcomingTypeLabel.setText("收入类型：");
        addBill_projectTypeLabel.setText("收入项目：");
        preview_incomingOrOutcomingLabel.setText("收入金额：");
        preview_incomingOrOutcomingTypeLabel.setText("收入类型：");
        preview_projectTypeLabel.setText("收入项目：");
        billTypeComboBox.setItems(billTypeOptions);
        billTypeComboBox.getSelectionModel().select(0);
        incomingOrOutcomingTypeComboBox.setItems(incomingBillTypeOptions);
        incomingOrOutcomingTypeComboBox.getSelectionModel().select(0);

        PropertiesOperation propertiesOperation = new PropertiesOperation();
        operatorLabel.setText(propertiesOperation.returnOperatorFromProperties("userConfig.properties"));

        billTypeComboBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if ((int) newValue == 1) {
                    billNumberText.setText("");
                    billNumberText.setDisable(true);
                    addBill_incomingOrOutcomingLabel.setText("支出金额：");
                    addBill_incomingOrOutcomingTypeLabel.setText("支出类型：");
                    incomingOrOutcomingTypeComboBox.setItems(outcomingBillTypeOptions);
                    incomingOrOutcomingTypeComboBox.getSelectionModel().select(0);
                    addBill_projectTypeLabel.setText("支出项目：");
                    cardNumberText.setText("");
                    cardNumberText.setDisable(true);
                    preview_incomingOrOutcomingLabel.setText("支出金额：");
                    preview_incomingOrOutcomingTypeLabel.setText("支出类型：");
                    preview_projectTypeLabel.setText("支出项目：");
                } else {
                    billNumberText.setText("");
                    billNumberText.setDisable(false);
                    addBill_incomingOrOutcomingLabel.setText("收入金额：");
                    addBill_incomingOrOutcomingTypeLabel.setText("收入类型：");
                    incomingOrOutcomingTypeComboBox.setItems(incomingBillTypeOptions);
                    incomingOrOutcomingTypeComboBox.getSelectionModel().select(0);
                    addBill_projectTypeLabel.setText("收入项目：");
                    cardNumberText.setText("");
                    cardNumberText.setDisable(false);
                    preview_incomingOrOutcomingLabel.setText("收入金额：");
                    preview_incomingOrOutcomingTypeLabel.setText("收入类型：");
                    preview_projectTypeLabel.setText("收入项目：");
                }
            }
        });
        //TODO
    }

    @FXML
    private void handlePreview() {
        //TODO
    }

    @FXML
    private void handleUploadBtn() {
        if (billTypeComboBox.getSelectionModel().getSelectedIndex() == 0) {
            if (!billNumberText.getText().equals("") &&
                    billNumberText.getText().matches(orderNumberRegex) &&
                    !priceText.getText().equals("") &&
                    priceText.getText().matches(paymentRegex) &&
                    !projectNameText.getText().equals("") &&
                    !cardNumberText.getText().equals("") &&
                    cardNumberText.getText().matches(orderNumberRegex)) {
                service_upload.restart();
                //clear();
            } else {
                AlertDialog alertDialog = new AlertDialog();
                alertDialog.createAlert(Alert.AlertType.ERROR,"错误","表单填写有误！","请重新填写！");
                alertDialog.showAlert();
            }
        }
        //TODO
    }

    @FXML
    private void handlePrintBtn() {
        //TODO
    }

    Service<Integer> service_upload = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    int flag = billSubmission.Judge(billNumberText.getText());
                    if (flag == -1) {
                        //continue
                    } else {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                alert.setTitle("确认");
                                alert.setHeaderText("订单号重复！");
                                alert.setContentText("是否使用推荐的订单号(" + flag + ")？");
                                Optional<ButtonType> optionalButtonType = alert.showAndWait();
                                if (optionalButtonType.get() == ButtonType.OK) {
                                    billNumberText.setText(String.valueOf(flag));
                                } else {
                                    billNumberText.setText("");
                                    alert.close();
                                }
                            }
                        });
                    }
                    return null;
                }
            };
        }
    };
}
