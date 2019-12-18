package view;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import model.Goods;
import service.AlertDialog;
import service.GoodsSearch;

import java.util.List;

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

    private ObservableList<String> searchTypeOptions = FXCollections.observableArrayList("按商品编号搜索", "按商品名称搜索");
    private ToggleGroup toggleGroup = new ToggleGroup();
    private GoodsSearch goodsSearch = new GoodsSearch();
    private ObservableList<Goods> goodsObservableList = FXCollections.observableArrayList();

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

        searchTypeComboBox.setItems(searchTypeOptions);
        searchTypeComboBox.getSelectionModel().select(0);

        TableColumn idCol = new TableColumn("商品编号");
        idCol.setSortable(true);
        idCol.setCellValueFactory(new PropertyValueFactory<>("goodsId"));
        TableColumn nameCol = new TableColumn("商品名称");
        nameCol.setSortable(false);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("goodsName"));
        TableColumn timeCol = new TableColumn("保质期(天)");
        timeCol.setSortable(true);
        timeCol.setCellValueFactory(new PropertyValueFactory<>("goodsQualityTime"));
        TableColumn unitCol = new TableColumn("计量单位");
        unitCol.setSortable(false);
        unitCol.setCellValueFactory(new PropertyValueFactory<>("goodsUnit"));
        searchList.getColumns().addAll(idCol, nameCol, timeCol, unitCol);
        searchList.setItems(goodsObservableList);

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
        if (searchTypeComboBox.getSelectionModel().getSelectedIndex() == 1) {
            goodsObservableList.clear();
            service_searchByName.restart();
        } else {
            if (!inputText.getText().equals("")) {
                goodsObservableList.clear();
                service_searchByNumber.restart();
            }
        }
    }

    @FXML
    private void handleSearchAll() {
        goodsObservableList.clear();
        service_searchAll.restart();
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

    Service<Integer> service_searchByName = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    List<Goods> list = goodsSearch.searchGoodsByName(inputText.getText());
                    if (!list.toString().equals("[]")) {
                        for (int i = 0; i < list.size(); i++) {
                            goodsObservableList.add(list.get(i));
                        }
                    } else Platform.runLater(() -> searchList.setPlaceholder(new Label("没有结果")));
                    return null;
                }
            };
        }
    };

    Service<Integer> service_searchByNumber = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    Goods goods = goodsSearch.searchGoodsById(Integer.valueOf(inputText.getText()));
                    if (goods != null) {
                        goodsObservableList.add(goods);
                    } else Platform.runLater(() -> searchList.setPlaceholder(new Label("没有结果")));
                    return null;
                }
            };
        }
    };

    Service<Integer> service_searchAll = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    List<Goods> list = goodsSearch.getAllGoods();
                    if (!list.toString().equals("[]")) {
                        for (int i = 0; i < list.size(); i++) {
                            goodsObservableList.add(list.get(i));
                        }
                    } else Platform.runLater(() -> searchList.setPlaceholder(new Label("没有结果")));
                    return null;
                }
            };
        }
    };
}
