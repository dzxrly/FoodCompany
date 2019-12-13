package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import org.controlsfx.control.ToggleSwitch;

public class StuffInfoManagementPaneController {
    @FXML
    private ComboBox levelSelectComboBox;
    @FXML
    private TextField inputText;
    @FXML
    private Button searchBtn;
    @FXML
    private Button searchAllBtn;
    @FXML
    private TableView resList;
    @FXML
    private HBox modelHBox;
    @FXML
    private Label modelLebel;
    @FXML
    private Label stuffNumberLabel;
    @FXML
    private TextField stuffNameText;
    @FXML
    private ComboBox stuffLevelComboBox;
    @FXML
    private TextField addressText;
    @FXML
    private TextField phoneText;
    @FXML
    private TextField emailText;
    @FXML
    private TextField idNumberText;
    @FXML
    private ComboBox genderComboBox;
    @FXML
    private Button printBtn;
    @FXML
    private Button saveBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private HBox btnGroup;

    private ToggleSwitch toggleSwitch = new ToggleSwitch();

    @FXML
    private void initialize() {
        toggleSwitch.setSelected(false);
        modelHBox.getChildren().add(1, toggleSwitch);

        modelLebel.setText("查询模式");
        stuffNameText.setDisable(true);
        stuffLevelComboBox.setDisable(true);
        addressText.setDisable(true);
        phoneText.setDisable(true);
        emailText.setDisable(true);
        idNumberText.setDisable(true);
        genderComboBox.setDisable(true);
        printBtn.setVisible(true);
        btnGroup.setVisible(false);

        toggleSwitch.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    modelLebel.setText("修改模式");
                    stuffNameText.setDisable(false);
                    stuffLevelComboBox.setDisable(false);
                    addressText.setDisable(false);
                    phoneText.setDisable(false);
                    emailText.setDisable(false);
                    idNumberText.setDisable(false);
                    genderComboBox.setDisable(false);
                    printBtn.setVisible(false);
                    btnGroup.setVisible(true);
                } else {
                    modelLebel.setText("查询模式");
                    stuffNameText.setDisable(true);
                    stuffLevelComboBox.setDisable(true);
                    addressText.setDisable(true);
                    phoneText.setDisable(true);
                    emailText.setDisable(true);
                    idNumberText.setDisable(true);
                    genderComboBox.setDisable(true);
                    printBtn.setVisible(true);
                    btnGroup.setVisible(false);
                }
            }
        });
        //TODO
    }

    @FXML
    private void handleSearchBtn() {
        //TODO
    }

    @FXML
    private void handleSearchAllBtn() {
        //TODO
    }

    @FXML
    private void handlePrintBtn() {
        //TODO
    }

    @FXML
    private void handleSaveBtn() {
        //TODO
    }

    @FXML
    private void handleDeleteBtn() {
        //TODO
    }
}
