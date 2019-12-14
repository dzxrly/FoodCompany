package view;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import model.Stuff;
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
        beginChangeBtn.setVisible(true);
        changePWBtn.setVisible(true);
        saveBtn.setVisible(false);
        stuffNameText.setDisable(true);
        genderComboBox.setDisable(true);
        phoneText.setDisable(true);
        addressText.setDisable(true);
        emailText.setDisable(true);
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
                    PersonalInfoGet personalInfoGet = new PersonalInfoGet();
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
}
