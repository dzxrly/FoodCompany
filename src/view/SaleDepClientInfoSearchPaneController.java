package view;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
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
import service.*;

import java.util.List;
import java.util.Optional;

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
    @FXML
    private Label customerNumberLabel;

    private CustomerIndexAndStringSwitch customerIndexAndStringSwitch = new CustomerIndexAndStringSwitch();

    private HBox toggleSwitchBox;
    private ToggleSwitch infoOrChangeBtn;
    private Label toggleSwitchStatus;
    private ObservableList<Customer> searchData = FXCollections.observableArrayList();
    private ObservableList<String> customerTypeOptions = FXCollections.observableArrayList("个人", "公司/企业");
    private ObservableList<String> customerLevelOptions = FXCollections.observableArrayList("一星", "二星", "三星", "四星", "五星");
    private String searchByInput;
    private int searchOptionsIndex;

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

        TableColumn customerNumber = new TableColumn("客户编号");
        customerNumber.setSortable(true);
        customerNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        TableColumn customerType = new TableColumn("客户类型");
        customerType.setSortable(false);
        customerType.setCellValueFactory(new PropertyValueFactory<>("type"));
        TableColumn customerLevel = new TableColumn("客户星级");
        customerLevel.setSortable(true);
        customerLevel.setCellValueFactory(new PropertyValueFactory<>("level"));
        TableColumn companyNameCol = new TableColumn("公司名称");
        companyNameCol.setSortable(false);
        companyNameCol.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        TableColumn personalNameCol = new TableColumn("负责人名称");
        personalNameCol.setSortable(false);
        personalNameCol.setCellValueFactory(new PropertyValueFactory<>("personalName"));
        TableColumn phoneCol = new TableColumn("联系电话");
        phoneCol.setSortable(false);
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        TableColumn addressCol = new TableColumn("地址");
        addressCol.setSortable(false);
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        TableColumn emailCol = new TableColumn("邮箱");
        emailCol.setSortable(false);
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        searchResTable.setItems(searchData);
        searchResTable.getColumns().addAll(customerNumber, customerType, customerLevel, companyNameCol, personalNameCol, phoneCol, addressCol, emailCol);

        searchResTable.setPlaceholder(new Label("没有搜索结果"));
        searchResTable.setEditable(true);

        ObservableList<String> searchTypeOptions = FXCollections.observableArrayList("客户姓名", "公司/企业名称", "联系方式", "邮箱");
        searchIndexType.getItems().addAll(searchTypeOptions);
        searchIndexType.getSelectionModel().select(0);

        searchResTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Customer>() {
            @Override
            public void changed(ObservableValue<? extends Customer> observable, Customer oldValue, Customer newValue) {
                customerNumberLabel.setText(String.valueOf(newValue.getNumber()));
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
                    companyNameText.setEditable(true);
                    personalNameText.setEditable(true);
                    phoneText.setEditable(true);
                    emailText.setEditable(true);
                    addressText.setEditable(true);

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
                    companyNameText.setEditable(false);
                    personalNameText.setEditable(false);
                    phoneText.setEditable(false);
                    emailText.setEditable(false);
                    addressText.setEditable(false);
                }
            }
        });
    }


    @FXML
    private void handleSearch() {
        searchData.clear();
        searchByInput = searchIndexInput.getText();
        searchOptionsIndex = searchIndexType.getSelectionModel().getSelectedIndex();
        service_search.restart();
    }


    @FXML
    private void handlePrint() {
        if (!personalNameText.getText().equals("")) {
            TextArea tempPrintTextArea = new TextArea();
            tempPrintTextArea.setText("客户编号：\n" +
                    customerNumberLabel.getText() + "\n" +
                    "客户类型：\n" +
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
    private void handleSearchAll() {
        searchData.clear();
        service_searchAll.restart();
    }

    @FXML
    private void handleSave() {
        if (formCheck()) {
            service_save.restart();
            searchData.clear();
        } else {
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "填写信息有误！", "填写信息有误！");
            alertDialog.showAlert();
        }
    }

    @FXML
    private void handleDelete() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("确认");
        alert.setHeaderText("是否删除客户？");
        alert.setContentText("删除后无法撤销！");
        Optional<ButtonType> optionalButtonType = alert.showAndWait();
        if (optionalButtonType.get() == ButtonType.OK) {
            service_delete.restart();
            customerNumberLabel.setText("");
            customerTypeComboBox.getSelectionModel().select(0);
            customerLevelComboBox.getSelectionModel().select(0);
            companyNameText.setText("");
            personalNameText.setText("");
            phoneText.setText("");
            addressText.setText("");
            emailText.setText("");
            searchData.clear();
        } else alert.close();
    }

    Service<Integer> service_search = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    CustomerSearch customerSearch = new CustomerSearch();
                    if (searchOptionsIndex == 0) {
                        List<Customer> list = customerSearch.NameFuzzySearch(searchByInput);
                        for (int i = 0; i < list.size(); i++) {
                            searchData.add((Customer) list.get(i));
                        }
                    } else if (searchOptionsIndex == 1) {
                        List<Customer> list = customerSearch.CompanyFuzzySearch(searchByInput);
                        for (int i = 0; i < list.size(); i++) {
                            searchData.add((Customer) list.get(i));
                        }
                    } else if (searchOptionsIndex == 2) {
                        List<Customer> list = customerSearch.PhoneNumberFuzzySearch(searchByInput);
                        for (int i = 0; i < list.size(); i++) {
                            searchData.add((Customer) list.get(i));
                        }
                    } else {
                        List<Customer> list = customerSearch.EmailFuzzySearch(searchByInput);
                        for (int i = 0; i < list.size(); i++) {
                            searchData.add((Customer) list.get(i));
                        }
                    }
                    return null;
                }
            };
        }
    };

    Service<Integer> service_searchAll = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    CustomerSearch customerSearch = new CustomerSearch();
                    List<Customer> list = customerSearch.AllSearch();
                    for (int i = 0; i < list.size(); i++) {
                        searchData.add((Customer) list.get(i));
                    }
                    return null;
                }
            };
        }
    };

    Service<Integer> service_save = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    CustomerUpdate customerUpdate = new CustomerUpdate();
                    int updateRes = customerUpdate.updateCustomer(Integer.parseInt(customerNumberLabel.getText()), personalNameText.getText(), companyNameText.getText(), customerTypeComboBox.getSelectionModel().getSelectedIndex(), customerLevelComboBox.getSelectionModel().getSelectedIndex(), addressText.getText(), emailText.getText(), phoneText.getText());
                    Platform.runLater(() -> {
                        if (updateRes == 1) {
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.INFORMATION, "成功", "保存成功！", "信息更改已录入！");
                            alertDialog.show();
                        } else {
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "插入失败！", "信息更新失败！");
                            alertDialog.show();
                        }
                    });
                    return null;
                }
            };
        }
    };

    Service<Integer> service_delete = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    CustomerDelete customerDelete = new CustomerDelete();
                    if (customerDelete.deleteCustomer(searchResTable.getSelectionModel().getSelectedItem().getNumber())==1) {
                        Platform.runLater(()-> {
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.INFORMATION,"成功","删除成功！","客户已删除！");
                            alertDialog.show();
                        });
                    } else {
                        Platform.runLater(() -> {
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.ERROR,"错误","删除失败！","请重试！");
                            alertDialog.show();
                        });
                    }
                    return null;
                }
            };
        }
    };

    //表单验证
    private boolean formCheck() {
        String phoneNumberRegex = "\\d+";
        String emailRegex = "\\w+@[A-Za-z0-9_.]+";
        System.out.println(emailText.getText());
        if (!personalNameText.getText().equals("") &&
                !phoneText.getText().equals("") &&
                !emailText.getText().equals("") &&
                phoneText.getText().matches(phoneNumberRegex) &&
                emailText.getText().matches(emailRegex)) return true;
        else return false;
    }
}

