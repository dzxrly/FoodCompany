package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.util.regex.Pattern;

public class RawmetarialManagePaneController {
    @FXML
    private TextField searchInputText;
    @FXML
    private Button searchBtn;
    @FXML
    private Button searchAllBtn;
    @FXML
    private TableView metarialList;
    @FXML
    private RadioButton searchModel;
    @FXML
    private RadioButton addModel;
    @FXML
    private RadioButton changeModel;
    @FXML
    private Label metarialNumberLabel;
    @FXML
    private TextField metarialNameText;
    @FXML
    private TextField metarialMinText;
    @FXML
    private Label numberWarningLabel;
    @FXML
    private TextField resNumberLabel;
    @FXML
    private ComboBox metarialTypeComboBox;
    @FXML
    private TextField priceText;
    @FXML
    private Label operatorLabel;
    @FXML
    private Button uploadBtn;
    @FXML
    private Button printBtn;

    private ToggleGroup toggleGroup = new ToggleGroup();
    private ObservableList<String> metarialTypeOptions = FXCollections.observableArrayList("非添加剂", "添加剂");

    @FXML
    private void initialize() {
        metarialTypeComboBox.setItems(metarialTypeOptions);
        metarialTypeComboBox.getSelectionModel().select(0);
        searchModel.setToggleGroup(toggleGroup);
        searchModel.setUserData(1);
        searchModel.setSelected(true);
        addModel.setToggleGroup(toggleGroup);
        addModel.setUserData(2);
        changeModel.setToggleGroup(toggleGroup);
        changeModel.setUserData(3);
        metarialNameText.setDisable(true);
        metarialMinText.setDisable(true);
        numberWarningLabel.setVisible(false);
        resNumberLabel.setDisable(true);
        metarialTypeComboBox.setDisable(true);
        priceText.setDisable(true);
        uploadBtn.setDisable(true);
        printBtn.setDisable(false);

        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                RadioButton temp_radioBtn = (RadioButton) newValue;
                if (temp_radioBtn.getText().equals("查询")) {
                    metarialNameText.setDisable(true);
                    metarialMinText.setDisable(true);
                    resNumberLabel.setDisable(true);
                    metarialTypeComboBox.setDisable(true);
                    priceText.setDisable(true);
                    uploadBtn.setDisable(true);
                    printBtn.setDisable(false);
                } else if (temp_radioBtn.getText().equals("添加")) {
                    metarialNameText.setDisable(false);
                    metarialMinText.setDisable(false);
                    resNumberLabel.setDisable(false);
                    metarialTypeComboBox.setDisable(false);
                    priceText.setDisable(false);
                    uploadBtn.setDisable(false);
                    printBtn.setDisable(true);
                } else if (temp_radioBtn.getText().equals("修改")) {
                    metarialNameText.setDisable(false);
                    metarialMinText.setDisable(false);
                    resNumberLabel.setDisable(false);
                    metarialTypeComboBox.setDisable(false);
                    priceText.setDisable(false);
                    uploadBtn.setDisable(false);
                    printBtn.setDisable(true);
                } else {
                    metarialNameText.setDisable(true);
                    metarialMinText.setDisable(true);
                    numberWarningLabel.setVisible(true);
                    resNumberLabel.setDisable(true);
                    metarialTypeComboBox.setDisable(true);
                    priceText.setDisable(true);
                    uploadBtn.setDisable(true);
                    printBtn.setDisable(false);
                }
            }
        });

        metarialMinText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if ((isInteger(newValue) || isDouble(newValue)) && (isInteger(resNumberLabel.getText()) || isDouble(resNumberLabel.getText()))) {
                    if (Double.valueOf(newValue) > Double.valueOf(resNumberLabel.getText())) {
                        numberWarningLabel.setText("库存数量低于最低限度！");
                        numberWarningLabel.setTextFill(Color.web("#F56C6C"));
                        numberWarningLabel.setVisible(true);
                    } else numberWarningLabel.setVisible(false);
                } else numberWarningLabel.setVisible(false);
            }
        });

        resNumberLabel.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if ((isInteger(newValue) || isDouble(newValue)) && (isInteger(metarialMinText.getText()) || isDouble(metarialMinText.getText()))) {
                    if (Double.valueOf(newValue) < Double.valueOf(metarialMinText.getText())) {
                        numberWarningLabel.setText("库存数量低于最低限度！");
                        numberWarningLabel.setTextFill(Color.web("#F56C6C"));
                        numberWarningLabel.setVisible(true);
                    } else numberWarningLabel.setVisible(false);
                } else numberWarningLabel.setVisible(false);
            }
        });
        //TODO
    }

    @FXML
    private void handleSearch() {
        //TODO
    }

    @FXML
    private void handleSearchAll() {
        //TODO
    }

    @FXML
    private void handleUpload() {
        //TODO
    }

    @FXML
    private void handlePrint() {
        //TODO
    }

    //判断整数（int）
    private boolean isInteger(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    //判断String是否为浮点数
    private boolean isDouble(String str) {
        if (null == str || "".equals(str)) {
            return false;
        }
        Pattern pattern = Pattern.compile("^[-\\+]?\\d*[.]\\d+$");
        return pattern.matcher(str).matches();
    }
}
