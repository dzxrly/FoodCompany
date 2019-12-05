package view;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;

public class saleDepClientInfoSearchPaneController {
    //客户信息查询控制类

    @FXML
    private TextField searchIndexInput;
    @FXML
    private ComboBox searchIndexType;
    @FXML
    private Button searchBtn;
    @FXML
    private TableView<Customer> searchResTable;

    ObservableList<Customer> searchData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        TableColumn customerType = new TableColumn("客户类型");
        customerType.setMinWidth(100);
        customerType.setCellValueFactory(new PropertyValueFactory<>("type"));
        TableColumn customerLevel = new TableColumn("客户星级");
        customerLevel.setMinWidth(100);
        customerLevel.setCellValueFactory(new PropertyValueFactory<>("level"));
        TableColumn companyNameCol = new TableColumn("公司名称");
        companyNameCol.setMinWidth(100);
        companyNameCol.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        TableColumn personalNameCol = new TableColumn("负责人名称");
        personalNameCol.setMinWidth(100);
        personalNameCol.setCellValueFactory(new PropertyValueFactory<>("personalName"));
        TableColumn phoneCol = new TableColumn("联系电话");
        phoneCol.setMinWidth(100);
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        TableColumn addressCol = new TableColumn("地址");
        addressCol.setMinWidth(100);
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        TableColumn emailCol = new TableColumn("邮箱");
        emailCol.setMinWidth(100);
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        searchResTable.setItems(searchData);
        searchResTable.getColumns().addAll(customerType, customerLevel, companyNameCol, personalNameCol, phoneCol, addressCol, emailCol);

        searchResTable.setPlaceholder(new Label("没有搜索结果"));
        searchResTable.setEditable(true);

        ObservableList<String> searchTypeOptions = FXCollections.observableArrayList("客户姓名", "公司/企业名称", "联系方式", "邮箱");
        searchIndexType.getItems().addAll(searchTypeOptions);
        searchIndexType.getSelectionModel().select(0);
        //TODO
    }

    @FXML
    private void handleSearch() {
        //test

        //TODO
    }
}
