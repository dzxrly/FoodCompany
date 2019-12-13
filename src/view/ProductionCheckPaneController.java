package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import service.AddImageForComponent;
import service.DoubleFormatService;

import java.util.regex.Pattern;

public class ProductionCheckPaneController {
    @FXML
    private TextField numberInputText;
    @FXML
    private Button searchBtn;
    @FXML
    private Label numberLabel;
    @FXML
    private Label sumLabel;
    @FXML
    private Label passRateLabel;
    @FXML
    private TextField checkNumberText;
    @FXML
    private TextField disqualifiedNumberText;
    @FXML
    private ComboBox isCheckedComboBox;
    @FXML
    private Label operatorLabel;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button uploadBtn;
    @FXML
    private VBox vBox;

    @FXML
    private void initialize() {
        searchBtn.setGraphic((new AddImageForComponent("img/search14x14.png",14)).getImageView());
        uploadBtn.setGraphic((new AddImageForComponent("img/check.png",14)).getImageView());
        passRateLabel.setText("");
        passRateLabel.setTextFill(Color.BLACK);
        checkNumberText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if ((isInteger(newValue) || isDouble(newValue)) && (isInteger(disqualifiedNumberText.getText()) || isDouble(disqualifiedNumberText.getText()))) {
                    if (Double.valueOf(disqualifiedNumberText.getText()) >= Double.valueOf(newValue)) {
                        Double disqualifiedRate = Double.valueOf(disqualifiedNumberText.getText()) / Double.valueOf(newValue);
                        passRateLabel.setText(String.valueOf((1.0 - disqualifiedRate) * 100).substring(0, 6) + "%");
                        passRateLabel.setTextFill(Color.BLACK);
                    } else {
                        passRateLabel.setText("抽检数量不能小于不合格数！");
                        passRateLabel.setTextFill(Color.web("#F56C6C"));
                    }
                } else {
                    passRateLabel.setText("抽检数量和不合格数只能填写数字！");
                    passRateLabel.setTextFill(Color.web("#F56C6C"));
                }
            }
        });
        disqualifiedNumberText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if ((isInteger(newValue) || isDouble(newValue)) && (isInteger(checkNumberText.getText()) || isDouble(checkNumberText.getText()))) {
                    if (Double.valueOf(checkNumberText.getText()) >= Double.valueOf(newValue)) {
                        Double disqualifiedRate = Double.valueOf(newValue) / Double.valueOf(checkNumberText.getText());
                        passRateLabel.setText(String.valueOf((1.0 - disqualifiedRate) * 100).substring(0, 6) + "%");
                        passRateLabel.setTextFill(Color.BLACK);
                    } else {
                        passRateLabel.setText("抽检数量不能小于不合格数！");
                        passRateLabel.setTextFill(Color.web("#F56C6C"));
                    }
                } else {
                    passRateLabel.setText("抽检数量和不合格数只能填写数字！");
                    passRateLabel.setTextFill(Color.web("#F56C6C"));
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
