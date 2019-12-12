package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import service.AddImageForComponent;

public class ProductionManagePaneController {
    @FXML
    private TextField searchInputText;
    @FXML
    private Button searchBtn;
    @FXML
    private Button searchAllBtn;
    @FXML
    private TableView planList;
    @FXML
    private Label planNumberLabel;
    @FXML
    private Label numberLabel;
    @FXML
    private Label typeLabe;
    @FXML
    private Label cycleLebal;
    @FXML
    private Button uploadBtn;

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
}
