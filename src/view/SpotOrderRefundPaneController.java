package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import service.AddImageForComponent;
import service.PropertiesOperation;

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
    private Label hsaPayResPaymentLabel;
    @FXML
    private Label totalCostLabel;
    @FXML
    private TextField needPayPrepaymentText;
    @FXML
    private TextField needPayResPaymentText;
    @FXML
    private TextField needPayTotalCostText;
    @FXML
    private Label finaceDepOperatorLabel;
    @FXML
    private Button uploadBtn;

    private String costRegex = "[0-9.]+";

    @FXML
    private void initialize() {
        searchBtn.setGraphic((new AddImageForComponent("img/search14x14.png", 14)).getImageView());
        uploadBtn.setGraphic((new AddImageForComponent("img/upload14x14.png", 14)).getImageView());
        String operatorName = (new PropertiesOperation()).readValue("userConfig.properties", "LoginUserName");
        String operatorNumber = (new PropertiesOperation()).readValue("userConfig.properties", "LoginUserNumber");
        finaceDepOperatorLabel.setText(operatorName + " [" + operatorNumber + "]");
        needPayPrepaymentText.setText("0.0");
        needPayResPaymentText.setText("0.0");
        needPayTotalCostText.setText("0.0");

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
        //TODO
    }

    @FXML
    private void handleUpload() {
        //TODO
    }
}
