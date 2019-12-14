package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import service.AddImageForComponent;
import service.PropertiesOperation;

public class ProductionPlansManagementPaneController {
    @FXML
    private ComboBox orderTypeComboBox;
    @FXML
    private Button searchBtn;
    @FXML
    private TableView orderList;
    @FXML
    private TableView selectedOrderList;
    @FXML
    private ComboBox planTypeComboBox;
    @FXML
    private DatePicker beginDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private Label operatorLabel;
    @FXML
    private Button uploadBtn;

    private ObservableList<String> orderTypeOptions = FXCollections.observableArrayList("全部","最近一个季度","最近三个季度");
    private ObservableList<String> planTypeOptions = FXCollections.observableArrayList("季度","预定");

    @FXML
    private void initialize() {
        orderTypeComboBox.setItems(orderTypeOptions);
        orderTypeComboBox.getSelectionModel().select(0);
        planTypeComboBox.setItems(planTypeOptions);
        planTypeComboBox.getSelectionModel().select(0);
        searchBtn.setGraphic((new AddImageForComponent("img/search14x14.png",14)).getImageView());
        uploadBtn.setGraphic((new AddImageForComponent("img/check.png",14)).getImageView());
        PropertiesOperation propertiesOperation = new PropertiesOperation();
        operatorLabel.setText(propertiesOperation.returnOperatorFromProperties("userConfig.properties"));
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
