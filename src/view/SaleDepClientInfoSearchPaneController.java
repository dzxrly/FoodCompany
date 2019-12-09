package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Callback;
import model.Customer;
import org.controlsfx.control.ToggleSwitch;
import service.AlertDialog;
import service.CustomerIndexAndStringSwitch;
import service.Printer;

public class SaleDepClientInfoSearchPaneController {
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
    private ComboBox customerTypeComboBox;
    @FXML
    private ComboBox customerLevelComboBox;
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
    @FXML
    private Button expBtn;
    @FXML
    private Button saveBtn;
    @FXML
    private Button deleteBtn;

    private CustomerIndexAndStringSwitch customerIndexAndStringSwitch = new CustomerIndexAndStringSwitch();

    private HBox toggleSwitchBox;
    private ToggleSwitch infoOrChangeBtn;
    private Label toggleSwitchStatus;
    private ObservableList<Customer> searchData = FXCollections.observableArrayList();
    private ObservableList<String> customerTypeOptions = FXCollections.observableArrayList("个人", "公司/企业");
    private ObservableList<String> customerLevelOptions = FXCollections.observableArrayList("一星", "二星", "三星", "四星", "五星");

    @FXML
    private void initialize() {
        toggleSwitchBox = new HBox();
        toggleSwitchStatus = new Label("查询模式");
        toggleSwitchStatus.setFont(new Font(14));
        infoOrChangeBtn = new ToggleSwitch();
        toggleSwitchBox.getChildren().addAll(infoOrChangeBtn, toggleSwitchStatus);
        customerInfo.getChildren().add(0, toggleSwitchBox);
        searchBtn.setGraphic(new ImageView(new Image("img/search.png", 16, 16, false, false)));
        expBtn.setGraphic(new ImageView(new Image("img/download.png", 16, 16, false, false)));
        saveBtn.setGraphic(new ImageView(new Image("img/check.png", 16, 16, false, false)));
        deleteBtn.setGraphic(new ImageView(new Image("img/close.png", 16, 16, false, false)));
        customerTypeComboBox.getItems().addAll(customerTypeOptions);
        customerLevelComboBox.getItems().addAll(customerLevelOptions);
        customerTypeComboBox.setDisable(true);
        customerLevelComboBox.setDisable(true);
        expBtn.setVisible(true);
        expBtn.setDisable(false);
        saveBtn.setVisible(false);
        saveBtn.setDisable(true);
        deleteBtn.setVisible(false);
        deleteBtn.setDisable(true);

        TableColumn customerType = new TableColumn("客户类型");
        customerType.setCellValueFactory(new PropertyValueFactory<>("type"));
        TableColumn customerLevel = new TableColumn("客户星级");
        customerLevel.setCellValueFactory(new PropertyValueFactory<>("level"));
        TableColumn companyNameCol = new TableColumn("公司名称");
        companyNameCol.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        TableColumn personalNameCol = new TableColumn("负责人名称");
        personalNameCol.setCellValueFactory(new PropertyValueFactory<>("personalName"));
        TableColumn phoneCol = new TableColumn("联系电话");
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        TableColumn addressCol = new TableColumn("地址");
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        TableColumn emailCol = new TableColumn("邮箱");
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
                customerTypeComboBox.getSelectionModel().select(newValue.getType());
                customerLevelComboBox.getSelectionModel().select(newValue.getLevel());
                companyNameText.setText(newValue.getCompanyName());
                personalNameText.setText(newValue.getPersonalName());
                phoneText.setText(newValue.getPhoneNumber());
                addressText.setText(newValue.getAddress());
                emailText.setText(newValue.getEmail());
            }
        });

        infoOrChangeBtn.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    toggleSwitchStatus.setText("修改模式");
                    customerTypeComboBox.setDisable(false);
                    customerLevelComboBox.setDisable(false);
                    expBtn.setVisible(false);
                    expBtn.setDisable(true);
                    saveBtn.setVisible(true);
                    saveBtn.setDisable(false);
                    deleteBtn.setVisible(true);
                    deleteBtn.setDisable(false);

                } else {
                    toggleSwitchStatus.setText("查询模式");
                    customerTypeComboBox.setDisable(true);
                    customerLevelComboBox.setDisable(true);
                    expBtn.setVisible(true);
                    expBtn.setDisable(false);
                    saveBtn.setVisible(false);
                    saveBtn.setDisable(true);
                    deleteBtn.setVisible(false);
                    deleteBtn.setDisable(true);
                }
                //TODO
            }
        });
        //TODO
    }


    @FXML
    private void handleSearch() {
        //test
        searchData.addAll(
                new Customer("a", "A", 0, "test1", "123@qq.com", "123456", 0),
                new Customer("b", "B", 1, "test2", "456@qq.com", "231321", 1)
        );
        //TODO
    }


    @FXML
    private void handlePrint() {
        if (!personalNameText.getText().equals("")) {
            TextArea tempPrintTextArea = new TextArea();
            tempPrintTextArea.setText("客户类型：\n" +
                    customerTypeComboBox.getValue().toString() + "\n" +
                    "客户星级：\n" +
                    customerLevelComboBox.getValue().toString() + "\n" +
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
                AlertDialog alertDialog = new AlertDialog();
                alertDialog.createAlert(Alert.AlertType.ERROR, "ERROR", "打印已被取消或出错！", "打印已被取消或出错！");
                alertDialog.showAlert();
            }
        } else {
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.createAlert(Alert.AlertType.ERROR, "ERROR", "打印错误！", "客户详细信息不存在！");
            alertDialog.showAlert();
        }
    }

    @FXML
    private void handleSave() {
        //TODO
    }

    @FXML
    private void handleDelete() {
        //TODO
    }
}

