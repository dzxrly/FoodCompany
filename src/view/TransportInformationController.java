package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import service.AddImageForComponent;
import service.PropertiesOperation;

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

    private ObservableList<String> addressOptions = FXCollections.observableArrayList("使用预填地址","使用新增地址");
    private ObservableList<String> transportCompanyNameOptions = FXCollections.observableArrayList("EMS","顺丰速运");
    private ObservableList<String> transportTypeOptions = FXCollections.observableArrayList("普通陆运","空运","冷冻运输");
    private String operatorName;
    private String operatorNumber;

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

        PropertiesOperation propertiesOperation = new PropertiesOperation();
        operatorLabel.setText(propertiesOperation.returnOperatorFromProperties("userConfig.properties"));

        addressComboBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if ((int) newValue == 1) {
                    addressText.setDisable(false);
                } else addressText.setDisable(true);
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

    @FXML
    private void handlePrint() {
        //TODO
    }
}
