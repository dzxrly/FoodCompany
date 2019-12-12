package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import service.AddImageForComponent;
import service.PropertiesOperation;

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

    private ObservableList<String> billTypeOptions = FXCollections.observableArrayList("收入账单", "支出账单");
    private ObservableList<String> incomingBillTypeOptions = FXCollections.observableArrayList("预付款","尾款","全款");
    private ObservableList<String> outcomingBillTypeOptions = FXCollections.observableArrayList("税务","原料");

    @FXML
    private void initialize() {
        previewBtn.setGraphic((new AddImageForComponent("img/image.png",14)).getImageView());
        uploadBtn.setGraphic((new AddImageForComponent("img/check.png",14)).getImageView());
        printBtn.setGraphic((new AddImageForComponent("img/download.png",14)).getImageView());
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
        //TODO
    }

    @FXML
    private void handlePrintBtn() {
        //TODO
    }
}
