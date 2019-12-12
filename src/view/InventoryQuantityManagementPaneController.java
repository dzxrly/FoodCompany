package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

import java.util.regex.Pattern;

public class InventoryQuantityManagementPaneController {
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
    private Label numberLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label stocksNumberLabel;
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

    private ObservableList<String> inOrOurTypeOptions = FXCollections.observableArrayList("出库", "入库");

    @FXML
    private void initialize() {
        stocksNumberLabel.setText("0");
        warningLabel.setText("");
        warningLabel.setTextFill(Color.BLACK);
        inOrOutLabel.setText("出库数量：");
        inOrOutTypeComboBox.setItems(inOrOurTypeOptions);
        inOrOutTypeComboBox.getSelectionModel().select(0);

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
