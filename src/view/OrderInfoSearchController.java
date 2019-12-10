package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import org.controlsfx.control.ToggleSwitch;
import service.AddImageForComponent;

public class OrderInfoSearchController {
    //订单信息查询修改控制类

    @FXML
    private ComboBox searchIndexComboBox;
    @FXML
    private TextField searchContentInput;
    @FXML
    private Button searchBtn;
    @FXML
    private Button searchAll;
    @FXML
    private TableView orderListTable;
    @FXML
    private Label orderNumber;
    @FXML
    private TextField customerNameText;
    @FXML
    private TextField customerPhoneText;
    @FXML
    private TextField customerAddressText;
    @FXML
    private DatePicker orderDatePicker;
    @FXML
    private TableView goodsListTable;
    @FXML
    private Label totalPrice;
    @FXML
    private Button printBtn;
    @FXML
    private Button saveChangeBtn;
    @FXML
    private Button deleteOrderBtn;
    @FXML
    private HBox hBox;
    @FXML
    private Label modelLabel;

    private ToggleSwitch toggleSwitch = new ToggleSwitch();

    @FXML
    private void initialize() {
        toggleSwitch.setSelected(false);
        searchBtn.setGraphic((new AddImageForComponent("img/search14x14.png", 14)).getImageView());
        hBox.getChildren().add(1, toggleSwitch);
        printBtn.setDisable(false);
        printBtn.setVisible(true);
        saveChangeBtn.setDisable(true);
        saveChangeBtn.setVisible(false);
        deleteOrderBtn.setDisable(true);
        deleteOrderBtn.setVisible(false);

        toggleSwitch.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    printBtn.setDisable(true);
                    printBtn.setVisible(false);
                    saveChangeBtn.setDisable(false);
                    saveChangeBtn.setVisible(true);
                    deleteOrderBtn.setDisable(false);
                    deleteOrderBtn.setVisible(true);
                } else {
                    printBtn.setDisable(false);
                    printBtn.setVisible(true);
                    saveChangeBtn.setDisable(true);
                    saveChangeBtn.setVisible(false);
                    deleteOrderBtn.setDisable(true);
                    deleteOrderBtn.setVisible(false);
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
    private void handlePrint() {
        //TODO
    }

    @FXML
    private void handleSave() {
        //TODO
    }

    @FXML
    private void handleDelete() {
        //TODO
    }
}
