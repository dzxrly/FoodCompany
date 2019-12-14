package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import service.AddImageForComponent;

public class FinancialStatementsPaneCpntroller {
    //财务报表页面控制类
    @FXML
    private ComboBox searchTypeComboBox;
    @FXML
    private TextField searchInputText;
    @FXML
    private Button searchBtn;
    @FXML
    private Button searchAllBtn;
    @FXML
    private ComboBox filterTypeComboBox;
    @FXML
    private TableView billList;
    @FXML
    private Label billNumberLabel;
    @FXML
    private Label incomingSum;
    @FXML
    private Label outcomingSum;
    @FXML
    private Label totalLabel;
    @FXML
    private Button printBtn;

    private ObservableList<String> searchTypeOptions = FXCollections.observableArrayList("仅查询收入账单", "仅查询支出账单");
    private ObservableList<String> filterOptions = FXCollections.observableArrayList("全部", "最近一个月", "最近半年", "最近一年");

    @FXML
    private void initialize() {
        searchTypeComboBox.setItems(searchTypeOptions);
        searchTypeComboBox.getSelectionModel().select(0);
        filterTypeComboBox.setItems(filterOptions);
        filterTypeComboBox.getSelectionModel().select(0);
        searchInputText.setText("");
        searchInputText.setDisable(false);
        searchBtn.setGraphic((new AddImageForComponent("img/search14x14.png", 14)).getImageView());
        printBtn.setGraphic((new AddImageForComponent("img/download.png", 14)).getImageView());

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
    private void handlePrint() {
        //TODO
    }
}
