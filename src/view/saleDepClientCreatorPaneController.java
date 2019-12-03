package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class saleDepClientCreatorPaneController {
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
    private void initialize() {
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
        System.out.println("PUSHED");
        //TODO
    }

    @FXML
    private void handleEnterInput(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            handlePushBtn();
        }
    }
}
