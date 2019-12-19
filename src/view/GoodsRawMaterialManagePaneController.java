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
import model.Material;
import model.MaterialToGoods;
import service.*;

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
    private Label stocksLabel;
    @FXML
    private TextField addCountText;
    @FXML
    private Button saveBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private HBox materialHBox;
    @FXML
    private ComboBox materialSearchTypeComboBox;

    private ObservableList<String> searchTypeOptions = FXCollections.observableArrayList("按商品编号搜索", "按商品名称搜索");
    private ToggleGroup toggleGroup = new ToggleGroup();
    private GoodsSearch goodsSearch = new GoodsSearch();
    private ObservableList<Goods> goodsObservableList = FXCollections.observableArrayList();
    private ObservableList<MaterialToGoods> materialToGoodsObservableList = FXCollections.observableArrayList();
    private MaterialToGoodsSearch materialToGoodsSearch = new MaterialToGoodsSearch();
    private Goods currentGoods = new Goods();
    private DoubleFormatService doubleFormatService = new DoubleFormatService();
    private MaterialToGoods currentMaterial = new MaterialToGoods();
    private ObservableList<Material> materialObservableList = FXCollections.observableArrayList();
    private MaterialManagement materialManagement = new MaterialManagement();
    private ObservableList<String> materialSearchTypeOptions = FXCollections.observableArrayList("按编号查询", "按名称查询");
    private String regex = "[0-9]+";
    private CustomerIndexAndStringSwitch customerIndexAndStringSwitch = new CustomerIndexAndStringSwitch();

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
        clearSidePane();

        searchTypeComboBox.setItems(searchTypeOptions);
        searchTypeComboBox.getSelectionModel().select(0);
        materialSearchTypeComboBox.setItems(materialSearchTypeOptions);
        materialSearchTypeComboBox.getSelectionModel().select(0);

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

        TableColumn materialIdCol = new TableColumn("原料编号");
        materialIdCol.setSortable(true);
        materialIdCol.setCellValueFactory(new PropertyValueFactory<>("materialId"));
        TableColumn materialNameCol = new TableColumn("原料名称");
        materialNameCol.setSortable(false);
        materialNameCol.setCellValueFactory(new PropertyValueFactory<>("materialName"));
        TableColumn materialPerQuantityCol = new TableColumn("所需原料数量");
        materialPerQuantityCol.setSortable(false);
        materialPerQuantityCol.setCellValueFactory(new PropertyValueFactory<>("perQuantity"));
        materialPerQuantityCol.setCellFactory(col -> {
            TableCell<MaterialToGoods, Double> cell = new TableCell<>() {
                @Override
                public void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        int rowIndex = getIndex();
                        this.setText(doubleFormatService.getSubstringInputDouble(materialToGoodsObservableList.get(rowIndex).getPerQuantity(), 3) + materialToGoodsObservableList.get(rowIndex).getMaterialUnit());
                    }
                }
            };
            return cell;
        });
        TableColumn materialStockCol = new TableColumn("库存");
        materialStockCol.setSortable(false);
        materialStockCol.setCellValueFactory(new PropertyValueFactory<>("materialStocks"));
        materialStockCol.setCellFactory(col -> {
            TableCell<MaterialToGoods, Double> cell = new TableCell<>() {
                @Override
                public void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        int rowIndex = getIndex();
                        this.setText(doubleFormatService.getSubstringInputDouble(materialToGoodsObservableList.get(rowIndex).getMaterialStocks(), 3) + materialToGoodsObservableList.get(rowIndex).getMaterialUnit());
                    }
                }
            };
            return cell;
        });
        materialList.getColumns().addAll(materialIdCol, materialNameCol, materialPerQuantityCol, materialStockCol);
        materialList.setItems(materialToGoodsObservableList);

        TableColumn search_materialIdCol = new TableColumn("原料编号");
        search_materialIdCol.setSortable(true);
        search_materialIdCol.setCellValueFactory(new PropertyValueFactory<>("materialId"));
        TableColumn search_materialNameCol = new TableColumn("原料名称");
        search_materialNameCol.setSortable(false);
        search_materialNameCol.setCellValueFactory(new PropertyValueFactory<>("materialName"));
        TableColumn search_materialStockCol = new TableColumn("库存");
        search_materialStockCol.setSortable(false);
        search_materialStockCol.setCellValueFactory(new PropertyValueFactory<>("stocks"));
        search_materialStockCol.setCellFactory(col -> {
            TableCell<MaterialToGoods, Double> cell = new TableCell<>() {
                @Override
                public void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        int rowIndex = getIndex();
                        this.setText(doubleFormatService.getSubstringInputDouble(materialObservableList.get(rowIndex).getStocks(), 3) + materialObservableList.get(rowIndex).getMaterialUnit());
                    }
                }
            };
            return cell;
        });
        TableColumn search_type = new TableColumn("原料类型");
        search_type.setSortable(false);
        search_type.setCellValueFactory(new PropertyValueFactory<>("materialType"));
        search_type.setCellFactory(col -> {
            TableCell<MaterialToGoods, Integer> cell = new TableCell<>() {
                @Override
                public void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        int rowIndex = getIndex();
                        this.setText(customerIndexAndStringSwitch.getMaterialTypeByIndex(materialObservableList.get(rowIndex).getMaterialType()));
                    }
                }
            };
            return cell;
        });
        materialSearchResList.getColumns().addAll(search_materialIdCol, search_materialNameCol, search_materialStockCol, search_type);
        materialSearchResList.setItems(materialObservableList);

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

        searchList.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                materialToGoodsObservableList.clear();
                currentGoods = goodsObservableList.get((int) newValue);
                service_getMaterialByGoodsId.restart();
            }
        });

        materialList.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                clearSidePane();
                currentMaterial = materialToGoodsObservableList.get((int) newValue);
                materialNumberLabel.setText(String.valueOf(currentMaterial.getMaterialId()));
                materialNameLabel.setText(currentMaterial.getMaterialName());
                materialPriceLabel.setText(doubleFormatService.getSubstringInputDouble(currentMaterial.getMaterialPrice(), 3));
                stocksLabel.setText(doubleFormatService.getSubstringInputDouble(currentMaterial.getMaterialStocks(), 3));
                addCountText.setText(doubleFormatService.getSubstringInputDouble(currentMaterial.getPerQuantity(), 4));
            }
        });
        //TODO
    }

    private void clearSidePane() {
        materialNumberLabel.setText("");
        materialNameLabel.setText("");
        materialPriceLabel.setText("");
        stocksLabel.setText("");
        addCountText.setText("");
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
        if (materialSearchTypeComboBox.getSelectionModel().getSelectedIndex() == 0) {
            if (!materialInfoInputText.getText().equals("") && materialInfoInputText.getText().matches(regex)) {
                materialObservableList.clear();
                service_materialById.restart();
            } else {
                AlertDialog alertDialog = new AlertDialog();
                alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "输入有误！", "请重新输入！");
                alertDialog.showAlert();
            }
        } else {
            materialObservableList.clear();
            service_materialByName.restart();
        }
    }

    @FXML
    private void handleMaterialSearchAll() {
        materialObservableList.clear();
        service_allMaterial.restart();
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

    Service<Integer> service_getMaterialByGoodsId = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    List<MaterialToGoods> list = materialToGoodsSearch.searchMaterialToGoods(String.valueOf(currentGoods.getGoodsId()));
                    if (!list.toString().equals("[]")) {
                        for (int i = 0; i < list.size(); i++) materialToGoodsObservableList.add(list.get(i));
                    } else Platform.runLater(() -> searchList.setPlaceholder(new Label("没有结果")));
                    return null;
                }
            };
        }
    };

    Service<Integer> service_materialById = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    Material material = materialManagement.searchMaterialById(Integer.valueOf(materialInfoInputText.getText()));
                    if (material != null) {
                        materialObservableList.add(material);
                    } else Platform.runLater(() -> materialSearchResList.setPlaceholder(new Label("没有结果")));
                    return null;
                }
            };
        }
    };

    Service<Integer> service_materialByName = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    List<Material> list = materialManagement.searchMaterialByName(materialInfoInputText.getText());
                    if (!list.toString().equals("[]")) {
                        for (int i = 0; i < list.size(); i++) {
                            materialObservableList.add(list.get(i));
                        }
                    } else Platform.runLater(() -> materialSearchResList.setPlaceholder(new Label("没有结果")));
                    return null;
                }
            };
        }
    };

    Service<Integer> service_allMaterial = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    List<Material> list = materialManagement.searchMaterialAll();
                    if (!list.toString().equals("[]")) {
                        for (int i = 0; i < list.size(); i++) {
                            materialObservableList.add(list.get(i));
                        }
                    } else Platform.runLater(() -> materialSearchResList.setPlaceholder(new Label("没有结果")));
                    return null;
                }
            };
        }
    };
}
