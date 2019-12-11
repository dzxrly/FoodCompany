package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class ProductionPlansManagementPaneController {
    @FXML
    private ComboBox orderTypeComboBox;
    @FXML
    private Button searchBtn;
    @FXML
    private TableView orderList;
    @FXML
    private Button exportBtn;
    @FXML
    private TableView selectedOrderList;
    @FXML
    private Label orderNumberLabel;
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

        //TODO
    }

    @FXML
    private void handleSearch() {
        //TODO
    }

    @FXML
    private void handleExport() {
        //TODO
    }

    @FXML
    private void handleUpload() {
        //TODO
    }
}
