package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import service.AddImageForComponent;

import java.util.regex.Pattern;

public class InventoryQuantityManagementPaneController {
    @FXML
    private HBox selectModelHBox;
    @FXML
    private RadioButton searchModel;
    @FXML
    private RadioButton changeModel;
    @FXML
    private ComboBox typeComboBox;
    @FXML
    private TextField inputText;
    @FXML
    private Button searchBtn;
    @FXML
    private Button searchAllBtn;
    @FXML
    private TableView resList;
    @FXML
    private TextField numberLabel;
    @FXML
    private TextField nameLabel;
    @FXML
    private TextField stocksNumberLabel;
    @FXML
    private ComboBox inOrOutTypeComboBox;
    @FXML
    private Label warningLabel;
    @FXML
    private Label inOrOutLabel;
    @FXML
    private TextField numberInputText;
    @FXML
    private Label operatorLabel;
    @FXML
    private Button uploadBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private HBox btnGroup;
    @FXML
    private VBox vBox;

    private ToggleGroup toggleGroup = new ToggleGroup();
    private ObservableList<String> inOrOurTypeOptions = FXCollections.observableArrayList("出库", "入库");

    @FXML
    private void initialize() {
        searchModel.setSelected(true);
        searchModel.setUserData(1);
        searchModel.setToggleGroup(toggleGroup);
        changeModel.setUserData(2);
        changeModel.setToggleGroup(toggleGroup);
        searchBtn.setGraphic((new AddImageForComponent("img/search14x14.png", 14)).getImageView());
        uploadBtn.setGraphic((new AddImageForComponent("img/check.png", 14)).getImageView());
        stocksNumberLabel.setText("0");
        warningLabel.setText("");
        warningLabel.setTextFill(Color.BLACK);
        inOrOutLabel.setText("出库数量：");
        inOrOutTypeComboBox.setItems(inOrOurTypeOptions);
        inOrOutTypeComboBox.getSelectionModel().select(0);
        numberLabel.setDisable(true);
        nameLabel.setDisable(true);
        stocksNumberLabel.setDisable(true);
        inOrOutTypeComboBox.setDisable(true);
        inputText.setDisable(true);
        deleteBtn.setVisible(false);
        uploadBtn.setVisible(false);

        inOrOutTypeComboBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if ((int) newValue == 1) {
                    inOrOutLabel.setText("入库数量：");
                } else inOrOutLabel.setText("出库数量：");
            }
        });

        inputText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (isDouble(newValue) || isInteger(newValue)) {
                    if (inOrOutTypeComboBox.getSelectionModel().getSelectedIndex() == 0) {
                        if (Double.valueOf(stocksNumberLabel.getText()) < Double.valueOf(newValue)) {
                            warningLabel.setText("出库数量不能大于库存！");
                            warningLabel.setTextFill(Color.web("#F56C6C"));
                        } else {
                            warningLabel.setText("");
                            warningLabel.setTextFill(Color.BLACK);
                        }
                    } else {
                        warningLabel.setText("");
                        warningLabel.setTextFill(Color.BLACK);
                    }
                } else {
                    warningLabel.setText("必须为数字！");
                    warningLabel.setTextFill(Color.web("#F56C6C"));
                }
            }
        });

        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (newValue.getUserData().toString().equals("1")) {
                    numberLabel.setDisable(true);
                    nameLabel.setDisable(true);
                    stocksNumberLabel.setDisable(true);
                    inOrOutTypeComboBox.setDisable(true);
                    inputText.setDisable(true);
                    deleteBtn.setVisible(false);
                    uploadBtn.setVisible(false);
                } else if (newValue.getUserData().toString().equals("2")) {
                    numberLabel.setDisable(true);
                    nameLabel.setDisable(true);
                    stocksNumberLabel.setDisable(false);
                    inOrOutTypeComboBox.setDisable(false);
                    inputText.setDisable(false);
                    deleteBtn.setVisible(true);
                    uploadBtn.setVisible(true);
                } else {
                    numberLabel.setDisable(true);
                    nameLabel.setDisable(true);
                    stocksNumberLabel.setDisable(true);
                    inOrOutTypeComboBox.setDisable(true);
                    inputText.setDisable(true);
                    deleteBtn.setVisible(false);
                    uploadBtn.setVisible(false);
                }
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
    private void handleDelete() {
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
