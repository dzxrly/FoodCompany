package view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.hibernate.annotations.FilterDef;
import service.AddImageForComponent;
import service.PropertiesOperation;

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
    private CheckBox isPrePay;
    @FXML
    private CheckBox isRestPay;
    @FXML
    private CheckBox isTotalPay;
    @FXML
    private TableView goodsList;
    @FXML
    private Button upload;
    @FXML
    private Label orderTimeLabel;
    @FXML
    private Label orderTypeLabel;
    @FXML
    private Button printBtn;

    private String operator;
    private String operatorNumber;

    @FXML
    private void initialize() {
        PropertiesOperation propertiesOperation = new PropertiesOperation();
        operator = propertiesOperation.readValue("userConfig.properties", "LoginUserName");
        operatorNumber = propertiesOperation.readValue("userConfig.properties", "LoginUserNumber");
        operatorLabel.setText(operator + " [" + operatorNumber + "]");
        searchBtn.setGraphic((new AddImageForComponent("img/search14x14.png", 14)).getImageView());
        upload.setGraphic((new AddImageForComponent("img/upload14x14.png", 14)).getImageView());
        printBtn.setGraphic((new AddImageForComponent("img/download.png", 14)).getImageView());
        //TODO
    }

    @FXML
    private void handleUpload() {
        //TODO
    }

    @FXML
    private void handleSearchBtn() {
        //TODO
    }

    @FXML
    private void handlePrint() {
        //TODO
    }
}
