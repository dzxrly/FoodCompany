package view;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.AlertDialog;
import service.PersonalInfoGet;
import service.PropertiesOperation;

public class PasswordChangePaneController {
    @FXML
    private PasswordField oldPWText;
    @FXML
    private TextField newPWFirstInputText;
    @FXML
    private PasswordField newPWSecondInputText;
    @FXML
    private Button uploadBtn;
    @FXML
    private Button cancelBtn;
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private void initialize() {
        //TODO
    }

    @FXML
    private void handleUpload() {
        if (!oldPWText.getText().equals("") && !newPWFirstInputText.getText().equals("") && !newPWSecondInputText.getText().equals("")) {
            if (newPWFirstInputText.getText().equals(newPWSecondInputText.getText())) {
                service.restart();
            } else {
                AlertDialog alertDialog = new AlertDialog();
                alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "两次密码输入不一致！", "请重新输入！");
                alertDialog.showAlert();
            }
        } else {
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "旧密码和新密码不能为空！", "请重新输入！");
            alertDialog.showAlert();
        }
    }

    @FXML
    private void handleCancel() {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }

    Service<Integer> service = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    PersonalInfoGet personalInfoGet = new PersonalInfoGet();
                    PropertiesOperation propertiesOperation = new PropertiesOperation();
                    int flag = personalInfoGet.stuffUpdatePsw(Integer.valueOf(propertiesOperation.readValue("userConfig.properties", "LoginUserNumber")), oldPWText.getText(), newPWSecondInputText.getText());
                    if (flag == 0) {
                        Platform.runLater(() -> {
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.INFORMATION, "成功", "密码修改成功！", "请重新登录！");
                            alertDialog.showAlert();
                            System.exit(-1);
                        });
                    } else {
                        Platform.runLater(() -> {
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "旧密码错误！", "请重新输入！");
                            alertDialog.showAlert();
                        });
                    }
                    return null;
                }
            };
        }
    };
}
