package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.Customer;
import service.Printer;

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
    @FXML
    private TextField customerTypeText;
    @FXML
    private TextField customerLevelText;
    @FXML
    private TextField companyNameText;
    @FXML
    private TextField personalNameText;
    @FXML
    private TextField phoneText;
    @FXML
    private TextField addressText;
    @FXML
    private TextField emailText;
    @FXML
    private VBox customerInfo;

    private ObservableList<Customer> searchData = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        TableColumn customerType = new TableColumn("客户类型");
//        customerType.setMinWidth(100);
        customerType.setCellValueFactory(new PropertyValueFactory<>("type"));
        TableColumn customerLevel = new TableColumn("客户星级");
//        customerLevel.setMinWidth(100);
        customerLevel.setCellValueFactory(new PropertyValueFactory<>("level"));
        TableColumn companyNameCol = new TableColumn("公司名称");
//        companyNameCol.setMinWidth(100);
        companyNameCol.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        TableColumn personalNameCol = new TableColumn("负责人名称");
//        personalNameCol.setMinWidth(100);
        personalNameCol.setCellValueFactory(new PropertyValueFactory<>("personalName"));
        TableColumn phoneCol = new TableColumn("联系电话");
//        phoneCol.setMinWidth(100);
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        TableColumn addressCol = new TableColumn("地址");
//        addressCol.setMinWidth(100);
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        TableColumn emailCol = new TableColumn("邮箱");
//        emailCol.setMinWidth(100);
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        searchResTable.setItems(searchData);
        searchResTable.getColumns().addAll(customerType, customerLevel, companyNameCol, personalNameCol, phoneCol, addressCol, emailCol);

        searchResTable.setPlaceholder(new Label("没有搜索结果"));
        searchResTable.setEditable(true);

        ObservableList<String> searchTypeOptions = FXCollections.observableArrayList("客户姓名", "公司/企业名称", "联系方式", "邮箱");
        searchIndexType.getItems().addAll(searchTypeOptions);
        searchIndexType.getSelectionModel().select(0);

        searchResTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Customer>() {
            @Override
            public void changed(ObservableValue<? extends Customer> observable, Customer oldValue, Customer newValue) {
                customerTypeText.setText(newValue.getType());
                customerLevelText.setText(newValue.getLevel());
                companyNameText.setText(newValue.getCompanyName());
                personalNameText.setText(newValue.getPersonalName());
                phoneText.setText(newValue.getPhoneNumber());
                addressText.setText(newValue.getAddress());
                emailText.setText(newValue.getEmail());
            }
        });
        //TODO
    }

    @FXML
    private void handleSearch() {
        //test
        searchData.addAll(
                new Customer("a", "A", "一星", "test1", "123@qq.com", "123456", "个人"),
                new Customer("b", "B", "二星", "test2", "456@qq.com", "231321", "公司/企业")
        );
        //TODO
    }

    @FXML
    private void handlePrint() {
        if (!personalNameText.getText().equals("")) {
            TextArea tempPrintTextArea = new TextArea();
            tempPrintTextArea.setText("客户类型：\n" +
                    customerTypeText.getText() + "\n" +
                    "客户星级：\n" +
                    customerLevelText.getText() + "\n" +
                    "公司名称：\n" +
                    companyNameText.getText() + "\n" +
                    "负责人名称：\n" +
                    personalNameText.getText() + "\n" +
                    "联系电话：\n" +
                    phoneText.getText() + "\n" +
                    "地址：\n" +
                    addressText.getText() + "\n" +
                    "邮箱：\n" +
                    emailText.getText());
            Printer p = new Printer();
            if (!p.doPrint(tempPrintTextArea)) {
                createAlert(Alert.AlertType.ERROR, "ERROR", "打印已被取消或出错！", "打印已被取消或出错！");
            }
        } else {
            createAlert(Alert.AlertType.ERROR, "ERROR", "打印错误！", "客户详细信息不存在！");
        }
    }

    public void createAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }
}

