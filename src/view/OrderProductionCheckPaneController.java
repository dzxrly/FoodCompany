package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import service.AddImageForComponent;
import service.PropertiesOperation;

public class OrderProductionCheckPaneController {
    //订单需求量检查类
    @FXML
    private TextField orderNumberInput;
    @FXML
    private Button searchBtn;
    @FXML
    private Button searchAllBtn;
    @FXML
    private TableView orderList;
    @FXML
    private Label orderNumberLabel;
    @FXML
    private Label deadlineLabel;
    @FXML
    private TableView goodsList;
    @FXML
    private Label countLabel;
    @FXML
    private Label operatorLabel;
    @FXML
    private ComboBox isCheckedComboBox;
    @FXML
    private Button uploadBtn;
    @FXML
    private Label goodNameLabel_inStock;
    @FXML
    private Label needProducedNumberLabel;
    @FXML
    private Label goodNameLabel_needed;

    private ObservableList<String> checkStatusOptions = FXCollections.observableArrayList("无法通过", "已通过");

    @FXML
    private void initialize() {
        searchBtn.setGraphic((new AddImageForComponent("img/search14x14.png", 14)).getImageView());
        uploadBtn.setText("提交");
        uploadBtn.setGraphic((new AddImageForComponent("img/check.png",14)).getImageView());
        isCheckedComboBox.setItems(checkStatusOptions);
        isCheckedComboBox.getSelectionModel().select(0);

        PropertiesOperation propertiesOperation = new PropertiesOperation();
        operatorLabel.setText(propertiesOperation.returnOperatorFromProperties("userConfig.properties"));

        isCheckedComboBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if ((int) newValue == 0 ) {
                    uploadBtn.setText("提交");
                } else uploadBtn.setText("生成生产表单");
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
}
