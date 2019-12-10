package view;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import service.AlertDialog;
import service.CustomerCreator;
import service.CustomerIndexAndStringSwitch;

import java.util.regex.*;

public class SaleDepClientCreatorPaneController {
    //创建客户页面控制类
    @FXML
    private TextField companyName;
    @FXML
    private TextField personalName;
    @FXML
    private TextField phoneNumber;
    @FXML
    private TextField address;
    @FXML
    private TextField email;
    @FXML
    private ComboBox customerType;
    @FXML
    private ComboBox customerLevel;
    @FXML
    private Button upBtn;
    @FXML
    private Button clearBtn;

    private CustomerIndexAndStringSwitch customerIndexAndStringSwitch = new CustomerIndexAndStringSwitch();
    int pushRes = 0;

    @FXML
    private void initialize() {
        upBtn.setGraphic(new ImageView(new Image("img/check.png", 16, 16, false, false)));
        clearBtn.setGraphic(new ImageView(new Image("img/close.png", 16, 16, false, false)));

        ObservableList<String> customerTypeOptions = FXCollections.observableArrayList("个人", "公司/企业");
        customerType.getItems().addAll(customerTypeOptions);
        customerType.getSelectionModel().select(0);

        ObservableList<String> customerLevelOptions = FXCollections.observableArrayList("一星", "二星", "三星", "四星", "五星");
        customerLevel.getItems().addAll(customerLevelOptions);
        customerLevel.getSelectionModel().select(0);

        companyName.setDisable(true);

        customerType.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if (newValue.equals("公司/企业")) {
                    companyName.setDisable(false);
                } else companyName.setDisable(true);
            }
        });
        //TODO
    }

    @FXML
    private void handleClearBtn() {
        companyName.setText("");
        personalName.setText("");
        phoneNumber.setText("");
        address.setText("");
        email.setText("");
        customerType.getSelectionModel().select(0);
        customerLevel.getSelectionModel().select(0);
    }

    @FXML
    private void handlePushBtn() {
        if (formCheck()) {
            service.restart();
        } else {
            System.out.println("error!");
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.createAlert(Alert.AlertType.ERROR,"错误","提交失败！","填写信息有误！");
            alertDialog.show();
        }
    }

    @FXML
    private void handleEnterInput(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            handlePushBtn();
        }
    }

    private boolean formCheck() {
        String phoneNumberRegex = "\\d+";
        String emailRegex = "\\w+@[A-Za-z0-9_.]+";
        if (customerIndexAndStringSwitch.returnCustomerTypeByIndex(customerType.getSelectionModel().getSelectedIndex()) != null &&
                customerIndexAndStringSwitch.returnCustomerLevelByIndex(customerLevel.getSelectionModel().getSelectedIndex()) != null &&
                !personalName.getText().equals("") &&
                phoneNumber.getText().matches(phoneNumberRegex) &&
                email.getText().matches(emailRegex)) {
            return true;
        } else return false;
    }//表单验证

    Service<Integer> service = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    CustomerCreator customerCreator = new CustomerCreator();
                    pushRes = customerCreator.createCustomer(personalName.getText(), companyName.getText(), customerType.getSelectionModel().getSelectedIndex(), customerLevel.getSelectionModel().getSelectedIndex(), address.getText(), email.getText(), phoneNumber.getText());
                    if (pushRes == 1) {
                        Platform.runLater(() -> {
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.INFORMATION,"信息","提交成功！","提交成功！");
                            alertDialog.show();
                        });
                    } else {
                        Platform.runLater(() -> {
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.ERROR,"错误","提交失败！","填写信息有误！");
                            alertDialog.show();
                        });
                    }
                    return null;
                }
            };
        }
    };
}
