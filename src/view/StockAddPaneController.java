package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class StockAddPaneController {
    @FXML
    private ComboBox searchTypeComboBox;
    @FXML
    private TextField inputText;
    @FXML
    private Button search;
    @FXML
    private Button searchAll;
    @FXML
    private TableView searchList;
    @FXML
    private Button exportBtn;
    @FXML
    private TableView goodsList;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Label goodsNumberLabel;
    @FXML
    private Label operatorLabel;
    @FXML
    private Button uploadBtn;

    private ObservableList<String> searchTypeOptions = FXCollections.observableArrayList("按商品编号查询","按商品名查询");

    @FXML
    private void initialize() {
        searchTypeComboBox.setItems(searchTypeOptions);
        searchTypeComboBox.getSelectionModel().select(0);
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
    private void handleExport() {
        //TODO
    }

    @FXML
    private void handleUploadBtn() {
        //TODO
    }
}
