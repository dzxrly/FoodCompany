package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;

public class ProductionFiancePlanPaneController {
    @FXML
    private ComboBox productionPlanDateComboBox;
    @FXML
    private Button searchBtn;
    @FXML
    private TableView planList;
    @FXML
    private Button exportBtn;
    @FXML
    private Label planCountLabel;
    @FXML
    private Label produceCostLabel;
    @FXML
    private Label rawMaterialLabel;
    @FXML
    private Label operatorLabel;
    @FXML
    private Button uploadBtn;

    private ObservableList<String> options = FXCollections.observableArrayList("全部","最近一个季度","最近两个季度","最近三个季度");

    @FXML
    private void initialize() {
        productionPlanDateComboBox.setItems(options);
        productionPlanDateComboBox.getSelectionModel().select(0);
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
