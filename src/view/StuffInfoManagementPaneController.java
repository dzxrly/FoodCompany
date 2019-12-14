package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
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
    @FXML
    private HBox searchMenuHBox;
    @FXML
    private CheckBox isOnAddStuffModel;
    @FXML
    private Button addAllStuffBtn;
    @FXML
    private HBox printSaveDeleteBtnGroup;
    @FXML
    private VBox infoInputVBox;

    private ToggleSwitch toggleSwitch = new ToggleSwitch();
    private Label firstPWInputLabel = new Label();
    private TextField firstPWInputText = new TextField();
    private Label secondPWInputLabel = new Label();
    private PasswordField secondPWInputText = new PasswordField();
    private CheckBox useDefaultPW = new CheckBox();
    private VBox PWChangeVBox = new VBox();
    private Button addBtn = new Button();

    @FXML
    private void initialize() {
        useDefaultPW.setText("是否使用默认密码(密码123)");
        useDefaultPW.setSelected(true);
        toggleSwitch.setSelected(false);
        modelHBox.getChildren().add(1, toggleSwitch);
        firstPWInputLabel.setText("请输入密码：");
        firstPWInputLabel.setFont(new Font(14));
        firstPWInputText.setText("123");
        secondPWInputLabel.setText("请重复输入：");
        secondPWInputLabel.setFont(new Font(14));
        secondPWInputText.setText("123");
        addBtn.setText("添加");
        addBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleAddBtn();
            }
        });
        firstPWInputText.setDisable(true);
        secondPWInputText.setDisable(true);
        PWChangeVBox.getChildren().addAll(useDefaultPW, firstPWInputLabel, firstPWInputText, secondPWInputLabel, secondPWInputText, addBtn);
        PWChangeVBox.setSpacing(8);

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
        addAllStuffBtn.setDisable(true);

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
                    addAllStuffBtn.setDisable(true);
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
                    addAllStuffBtn.setDisable(true);
                }
            }
        });

        isOnAddStuffModel.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    searchMenuHBox.setDisable(true);
                    modelHBox.setVisible(false);
                    toggleSwitch.setSelected(false);
                    stuffNameText.setDisable(false);
                    stuffLevelComboBox.setDisable(false);
                    addressText.setDisable(false);
                    phoneText.setDisable(false);
                    emailText.setDisable(false);
                    idNumberText.setDisable(false);
                    genderComboBox.setDisable(false);
                    printBtn.setVisible(false);
                    btnGroup.setVisible(false);
                    addAllStuffBtn.setDisable(false);
                    printSaveDeleteBtnGroup.setVisible(false);
                    infoInputVBox.getChildren().add(17, PWChangeVBox);
                } else {
                    searchMenuHBox.setDisable(false);
                    modelHBox.setVisible(true);
                    toggleSwitch.setSelected(false);
                    stuffNameText.setDisable(true);
                    stuffLevelComboBox.setDisable(true);
                    addressText.setDisable(true);
                    phoneText.setDisable(true);
                    emailText.setDisable(true);
                    idNumberText.setDisable(true);
                    genderComboBox.setDisable(true);
                    printBtn.setVisible(true);
                    btnGroup.setVisible(false);
                    addAllStuffBtn.setDisable(true);
                    printSaveDeleteBtnGroup.setVisible(true);
                    infoInputVBox.getChildren().remove(17);
                }
            }
        });

        useDefaultPW.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    firstPWInputText.setText("123");
                    secondPWInputText.setText("123");
                    firstPWInputText.setDisable(true);
                    secondPWInputText.setDisable(true);
                } else {
                    firstPWInputText.setText("");
                    secondPWInputText.setText("");
                    firstPWInputText.setDisable(false);
                    secondPWInputText.setDisable(false);
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

    @FXML
    private void handleUploadAll() {
        //TODO
    }

    private void handleAddBtn() {
        //TODO
    }
}
