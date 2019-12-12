package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import service.AddImageForComponent;

public class OrderCancelPaneController {
    //订单退货处理控制类

    @FXML
    private TextField orderNumberInput;
    @FXML
    private Button searchBtn;
    @FXML
    private Label orderNumberLabel;
    @FXML
    private Label orderTypeLabel;
    @FXML
    private Label transportationStatus;
    @FXML
    private Label productionStatus;
    @FXML
    private Label deadline;
    @FXML
    private Label customerNameLabel;
    @FXML
    private Label customerPhoneLabel;
    @FXML
    private Label customerAddressLabel;
    @FXML
    private ComboBox isHasProblem;
    @FXML
    private TextArea note;
    @FXML
    private Button uploadBtn;
    @FXML
    private Button clearBtn;

    private ObservableList<String> typeOptions = FXCollections.observableArrayList("是", "否");

    @FXML
    private void initialize() {
        isHasProblem.setItems(typeOptions);
        isHasProblem.getSelectionModel().select(0);
        searchBtn.setGraphic((new AddImageForComponent("img/search14x14.png", 14)).getImageView());
        uploadBtn.setGraphic((new AddImageForComponent("img/upload14x14.png", 14)).getImageView());
        clearBtn.setGraphic((new AddImageForComponent("img/close.png", 14)).getImageView());
        //TODO
    }

    @FXML
    private void handleSearch() {
        //TODO
    }

    @FXML
    private void handleSave() {
        //TODO
    }

    @FXML
    private void handleClear() {
        //TODO
    }
}
