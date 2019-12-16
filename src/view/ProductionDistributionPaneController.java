package view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import service.AddImageForComponent;

public class ProductionDistributionPaneController {
    @FXML
    private TextField planNumberInputText;
    @FXML
    private Button searchBtn;
    @FXML
    private Button searchAllBtn;
    @FXML
    private TableView plansList;
    @FXML
    private Label planNumberLabel;
    @FXML
    private Label goodsNumberLabel;
    @FXML
    private Label planTypeLabel;
    @FXML
    private Label cycleLabel;
    @FXML
    private HBox modelHBox;
    @FXML
    private Label modelLabel;
    @FXML
    private ComboBox pipelineAllocationComboBox;
    @FXML
    private ComboBox levelComboBox;
    @FXML
    private DatePicker beginDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private Label operatorLabel;
    @FXML
    private Button uploadBtn;
    @FXML
    private TableView pipelineStatusList;
    @FXML
    private Button refreshStatus;

    @FXML
    private void initialize() {
        searchBtn.setGraphic((new AddImageForComponent("img/search14x14.png",14)).getImageView());
        uploadBtn.setGraphic((new AddImageForComponent("img/check.png",14)).getImageView());
        //TODO
    }

    @FXML
    private void handleSearch() {
        //TODO
    }

    @FXML
    private void handleSearchAll() {
        //TODO
    }

    @FXML
    private void handleUpload() {
        //TODO
    }

    @FXML
    private void handleRefresh() {
        //TODO
    }
}
