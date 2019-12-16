package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

public class GoodsRawMaterialManagePaneController {
    @FXML
    private ComboBox searchTypeComboBox;
    @FXML
    private TextField inputText;
    @FXML
    private Button search;
    @FXML
    private Button searchAll;
    @FXML
    private TableView searchList;
    @FXML
    private TableView materialList;
    @FXML
    private RadioButton searchModel;
    @FXML
    private RadioButton changeModel;
    @FXML
    private RadioButton addModel;
    @FXML
    private TextField materialInfoInputText;
    @FXML
    private Button materialSearchBtn;
    @FXML
    private Button materialSearchAllBtn;
    @FXML
    private TableView materialSearchResList;
    @FXML
    private Label materialNumberLabel;
    @FXML
    private Label materialNameLabel;
    @FXML
    private Label materialPriceLabel;
    @FXML
    private Label materialTimeLabel;
    @FXML
    private Label stocksLabel;
    @FXML
    private Label materialTypeLabel;
    @FXML
    private TextField addCountText;
    @FXML
    private Button saveBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private HBox materialHBox;

    private ObservableList<String> searchTypeOptions = FXCollections.observableArrayList("按商品编号搜索","按商品名称搜索");
    private ToggleGroup toggleGroup = new ToggleGroup();

    @FXML
    private void initialize() {
        searchModel.setSelected(true);
        searchModel.setUserData(1);
        searchModel.setToggleGroup(toggleGroup);
        changeModel.setUserData(2);
        changeModel.setToggleGroup(toggleGroup);
        addModel.setUserData(3);
        addModel.setToggleGroup(toggleGroup);

        addCountText.setDisable(true);
        saveBtn.setVisible(false);
        deleteBtn.setVisible(false);
        materialHBox.setDisable(true);
        materialSearchResList.setDisable(true);

        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (newValue.getUserData().toString().equals("1")) {
                    addCountText.setDisable(true);
                    saveBtn.setVisible(false);
                    deleteBtn.setVisible(false);
                    materialHBox.setDisable(true);
                    materialSearchResList.setDisable(true);
                } else if (newValue.getUserData().toString().equals("2")) {
                    addCountText.setDisable(false);
                    saveBtn.setVisible(true);
                    deleteBtn.setVisible(true);
                    materialHBox.setDisable(true);
                    materialSearchResList.setDisable(true);
                } else {
                    addCountText.setDisable(false);
                    saveBtn.setVisible(true);
                    deleteBtn.setVisible(false);
                    materialHBox.setDisable(false);
                    materialSearchResList.setDisable(false);
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
    private void handleMaterialSearch() {
        //TODO
    }

    @FXML
    private void handleMaterialSearchAll() {
        //TODO
    }

    @FXML
    private void handleSave() {
        //TODO
    }

    @FXML
    private void handleDel() {
        //TODO
    }
}
