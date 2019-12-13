package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import service.AddImageForComponent;

public class RawMaterialRequestPaneController {
    @FXML
    private TextField plan_searchInputText;
    @FXML
    private Button plan_searchBtn;
    @FXML
    private Button plan_searchAllBtn;
    @FXML
    private TableView plansTable;
    @FXML
    private TextField material_searchInputText;
    @FXML
    private Button material_searchBtn;
    @FXML
    private Button material_searchAllBtn;
    @FXML
    private TableView materialSearchDataTable;
    @FXML
    private Button exportBtn;
    @FXML
    private TableView selectedMaterialTable;
    @FXML
    private Button clearBtn;
    @FXML
    private Label planNumberLabel;
    @FXML
    private Label operatorLabel;
    @FXML
    private Button uploadBtn;

    @FXML
    private void initialize() {
        plan_searchBtn.setGraphic((new AddImageForComponent("img/search14x14.png",14)).getImageView());
        material_searchBtn.setGraphic((new AddImageForComponent("img/search14x14.png",14)).getImageView());
        uploadBtn.setGraphic((new AddImageForComponent("img/check.png",14)).getImageView());
        exportBtn.setGraphic((new AddImageForComponent("img/arrow_down.png",14)).getImageView());
        //TODO
    }

    @FXML
    private void handlePlanSearch() {
        //TODO
    }

    @FXML
    private void handlePlanSearchAll() {
        //TODO
    }

    @FXML
    private void handleMaterialSearch() {
        //TODO
    }

    @FXML
    private void handleMaterialSearchAll() {
        //TODO
    }

    @FXML
    private void handleExport() {
        //TODO
    }

    @FXML
    private void handleClear() {
        //TODO
    }

    @FXML
    private void handleUpload() {
        //TODO
    }
}
