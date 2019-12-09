package view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

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
    private void initialize() {
        lockStatus.setText("订单类型未锁定");
        orderType.setDisable(false);
        lockStatus.setGraphic(new ImageView(new Image("img/unlock.png", 16, 16, false, false)));
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
        //TODO
    }

    @FXML
    private void handleAddAllBtn() {
        lockStatus.setGraphic(new ImageView(new Image("img/lock.png", 16, 16, false, false)));
        lockStatus.setText("订单类型已锁定，解锁请移除所有所选商品");
        orderType.setDisable(true);
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
        //TODO
    }

    @FXML
    private void handleSearchTextFieldKeyEvent(KeyEvent e) {
        if (e.getCode() == KeyCode.ENTER) {
            handleSearch();
        }
    }
}
