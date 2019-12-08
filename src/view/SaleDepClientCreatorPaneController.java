package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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
        if (formCheck()) System.out.println("PUSHED");
        else System.out.println("REFUSED");
        //TODO
    }

    @FXML
    private void handleEnterInput(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            handlePushBtn();
        }
    }

    private boolean formCheck() {
        String phoneNumberRegex = "\\d+";
        String emailRegex = "\\w+@\\w+";
        if (returnCustomerTypeByIndex(customerType.getSelectionModel().getSelectedIndex()) != null &&
                returnCustomerLevelByIndex(customerLevel.getSelectionModel().getSelectedIndex()) != null &&
                !personalName.getText().equals("") &&
                phoneNumber.getText().matches(phoneNumberRegex) &&
                email.getText().matches(emailRegex)) {
            return true;
        } else return false;
    }//表单验证

    private String returnCustomerTypeByIndex(int index) {
        switch (index) {
            default:
                return null;
            case 0:
                return "个人";
            case 1:
                return "公司/企业";
        }
    }

    private String returnCustomerLevelByIndex(int index) {
        switch (index) {
            default:
                return null;
            case 0:
                return "一星";
            case 1:
                return "二星";
            case 2:
                return "三星";
            case 3:
                return "四星";
            case 4:
                return "五星";
        }
    }
}
