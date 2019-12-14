package view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import model.Stuff;
import service.AlertDialog;
import service.PersonalInfoGet;
import service.PropertiesOperation;

import java.io.IOException;

public class PersonalInfoPaneController {
    @FXML
    private ImageView imageView;
    @FXML
    private Label stuffLabel;
    @FXML
    private Label idCardNumberLabel;
    @FXML
    private Label stuffLevelLabel;
    @FXML
    private ComboBox genderComboBox;
    @FXML
    private TextField stuffNameText;
    @FXML
    private TextField phoneText;
    @FXML
    private TextField addressText;
    @FXML
    private TextField emailText;
    @FXML
    private Button beginChangeBtn;
    @FXML
    private Button changePWBtn;
    @FXML
    private Button saveBtn;
    @FXML
    private Button uploadBtn;

    private ObservableList<String> genderOptions = FXCollections.observableArrayList("男", "女");
    private Stuff currentStuff = new Stuff();
    private PropertiesOperation propertiesOperation = new PropertiesOperation();
    private PersonalInfoGet personalInfoGet = new PersonalInfoGet();

    @FXML
    private void initialize() {
        genderComboBox.setItems(genderOptions);
        genderComboBox.getSelectionModel().select(0);
        beginChangeBtn.setVisible(true);
        changePWBtn.setVisible(true);
        saveBtn.setVisible(false);
        stuffNameText.setDisable(true);
        genderComboBox.setDisable(true);
        phoneText.setDisable(true);
        addressText.setDisable(true);
        emailText.setDisable(true);

        service_search.restart();
        //TODO
    }

    @FXML
    private void handleBeginChange() {
        beginChangeBtn.setVisible(false);
        changePWBtn.setVisible(false);
        saveBtn.setVisible(true);
        stuffNameText.setDisable(false);
        genderComboBox.setDisable(false);
        phoneText.setDisable(false);
        addressText.setDisable(false);
        emailText.setDisable(false);
        //TODO
    }

    @FXML
    private void handleChangePW() {
        showChangePWPane();
    }

    @FXML
    private void handleSaveBtn() {
        if (!stuffNameText.getText().equals("") && !phoneText.getText().equals("") && !addressText.getText().equals("") && !emailText.getText().equals("")) {
            service_upload.restart();
            beginChangeBtn.setVisible(true);
            changePWBtn.setVisible(true);
            saveBtn.setVisible(false);
            stuffNameText.setDisable(true);
            genderComboBox.setDisable(true);
            phoneText.setDisable(true);
            addressText.setDisable(true);
            emailText.setDisable(true);
        } else {
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.createAlert(Alert.AlertType.ERROR, "失败", "个人信息不能为空！", "请输入个人信息！");
            alertDialog.showAlert();
        }
        //TODO
    }

    @FXML
    private void handleUploadBtn() {
        //TODO
    }

    private void showChangePWPane() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("PasswordChangePane.fxml"));
            AnchorPane anchorPane = (AnchorPane) fxmlLoader.load();
            Scene scene = new Scene(anchorPane);
            new JMetro(scene, Style.LIGHT);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("修改密码");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Service<Integer> service_search = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    currentStuff = personalInfoGet.stuffInfoView(Integer.valueOf(propertiesOperation.readValue("userConfig.properties", "LoginUserNumber")));
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            stuffLabel.setText(String.valueOf(currentStuff.getNumber()));
                            idCardNumberLabel.setText(currentStuff.getPersonalID());
                            stuffLevelLabel.setText(String.valueOf(currentStuff.getLevel()));
                            genderComboBox.getSelectionModel().select(currentStuff.getGender());
                            stuffNameText.setText(currentStuff.getPersonalName());
                            phoneText.setText(currentStuff.getPhoneNumber());
                            addressText.setText(currentStuff.getAddress());
                            emailText.setText(currentStuff.getEmail());
                        }
                    });
                    return null;
                }
            };
        }
    };

    Service<Integer> service_upload = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    int flag = personalInfoGet.stuffInfoUpdate(currentStuff.getNumber(), genderComboBox.getSelectionModel().getSelectedIndex(), stuffNameText.getText(), phoneText.getText(), addressText.getText(), emailText.getText());
                    if (flag == 0) {
                        Platform.runLater(() -> {
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.INFORMATION, "成功", "个人信息已修改!", "个人信息已修改！");
                            alertDialog.showAlert();
                            propertiesOperation.writeProperties("userConfig.properties", "LoginUserName", stuffNameText.getText());
                            service_search.restart();
                        });
                    } else {
                        Platform.runLater(() -> {
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.ERROR, "失败", "个人信息修改失败！", "请重新修改！");
                            alertDialog.showAlert();
                        });
                    }
                    return null;
                }
            };
        }
    };
}
