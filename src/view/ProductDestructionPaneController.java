package view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import service.AddImageForComponent;

public class ProductDestructionPaneController {
    @FXML
    private ComboBox typeComboBox;
    @FXML
    private TextField inputText;
    @FXML
    private Button searchBtn;
    @FXML
    private Button searchAllBtn;
    @FXML
    private TableView listTable;
    @FXML
    private Label numberLabel;
    @FXML
    private Label reasonLabel;
    @FXML
    private Label operatorLabel;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextArea noteArea;
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
