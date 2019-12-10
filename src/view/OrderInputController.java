package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import service.AddImageForComponent;

public class OrderInputController {
    //订单信息录入控制类
    @FXML
    private Label lockStatus;
    @FXML
    private ComboBox orderType;
    @FXML
    private TextField goodsName;
    @FXML
    private Button searchBtn;
    @FXML
    private Button addSelectedToTable;
    @FXML
    private Button addAllToTable;
    @FXML
    private TableView searchResTable;
    @FXML
    private TableView goodsTable;
    @FXML
    private Button clearGoodsTable;
    @FXML
    private Button deleteSelectedGoodFromTable;
    @FXML
    private Button uploadOrder;
    @FXML
    private TextField customerName;
    @FXML
    private TextField customerPhone;
    @FXML
    private TextField customerAddress;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button printBtn;
    @FXML
    private TextField totalPrice;

    private ObservableList<String> orderTypeOptions = FXCollections.observableArrayList("现货订单", "预定订单");

    @FXML
    private void initialize() {
        orderType.setItems(orderTypeOptions);
        orderType.getSelectionModel().select(0);
        uploadOrder.setDisable(true);
        lockStatus.setText("订单类型未锁定");
        orderType.setDisable(false);
        lockStatus.setGraphic(new ImageView(new Image("img/unlock.png", 16, 16, false, false)));
        uploadOrder.setGraphic((new AddImageForComponent("img/check.png", 16)).getImageView());
        printBtn.setGraphic((new AddImageForComponent("img/download.png", 16)).getImageView());
        addSelectedToTable.setGraphic((new AddImageForComponent("img/cart_empty.png", 16)).getImageView());
        addAllToTable.setGraphic((new AddImageForComponent("img/cart.png", 16)).getImageView());
        //TODO
    }

    @FXML
    private void handleSearch() {
        System.out.println("search");
        //TODO
    }

    @FXML
    private void handleAddSelectedBtn() {
        lockStatus.setGraphic(new ImageView(new Image("img/lock.png", 16, 16, false, false)));
        lockStatus.setText("订单类型已锁定，解锁请移除所有所选商品");
        orderType.setDisable(true);
        uploadOrder.setDisable(false);
        //TODO
    }

    @FXML
    private void handleAddAllBtn() {
        lockStatus.setGraphic(new ImageView(new Image("img/lock.png", 16, 16, false, false)));
        lockStatus.setText("订单类型已锁定，解锁请移除所有所选商品");
        orderType.setDisable(true);
        uploadOrder.setDisable(false);
        //TODO
    }

    @FXML
    private void handleDeleteSelectedGood() {
        //TODO
    }

    @FXML
    private void handleClearAllGoods() {
        lockStatus.setGraphic(new ImageView(new Image("img/unlock.png", 16, 16, false, false)));
        lockStatus.setText("订单类型未锁定");
        orderType.setDisable(false);
        uploadOrder.setDisable(true);
        //TODO
    }

    @FXML
    private void handleSearchTextFieldKeyEvent(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            handleSearch();
        }
    }

    @FXML
    private void handleUploadOrder() {
        //TODO
    }

    @FXML
    private void handlePrint() {
        //TODO
    }
}
