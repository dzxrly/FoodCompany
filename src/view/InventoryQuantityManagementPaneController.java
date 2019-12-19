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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.ShippingDepartment;
import service.*;

import java.util.List;
import java.util.regex.Pattern;

public class InventoryQuantityManagementPaneController {
    @FXML
    private HBox selectModelHBox;
    @FXML
    private RadioButton searchModel;
    @FXML
    private RadioButton changeModel;
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
    private TextField numberLabel;
    @FXML
    private TextField nameLabel;
    @FXML
    private TextField stocksNumberLabel;
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
    @FXML
    private HBox btnGroup;
    @FXML
    private VBox vBox;

    private ToggleGroup toggleGroup = new ToggleGroup();
    private ObservableList<String> inOrOurTypeOptions = FXCollections.observableArrayList("出库", "入库");
    private ObservableList<String> searchTypeOptions = FXCollections.observableArrayList("按商品编号查询", "按商品名称查询");
    private ShippingDepOperation shippingDepOperation = new ShippingDepOperation();
    private ObservableList<ShippingDepartment> shippingDepartments = FXCollections.observableArrayList();
    private DoubleFormatService doubleFormatService = new DoubleFormatService();
    private ShippingDepartment currentGood = new ShippingDepartment();
    private PropertiesOperation propertiesOperation = new PropertiesOperation();

    @FXML
    private void initialize() {
        searchModel.setSelected(true);
        searchModel.setUserData(1);
        searchModel.setToggleGroup(toggleGroup);
        changeModel.setUserData(2);
        changeModel.setToggleGroup(toggleGroup);
        searchBtn.setGraphic((new AddImageForComponent("img/search14x14.png", 14)).getImageView());
        uploadBtn.setGraphic((new AddImageForComponent("img/check.png", 14)).getImageView());
        stocksNumberLabel.setText("0");
        warningLabel.setText("");
        warningLabel.setTextFill(Color.BLACK);
        inOrOutLabel.setText("出库数量：");
        inOrOutTypeComboBox.setItems(inOrOurTypeOptions);
        inOrOutTypeComboBox.getSelectionModel().select(0);
        numberLabel.setDisable(true);
        nameLabel.setDisable(true);
        stocksNumberLabel.setDisable(true);
        inOrOutTypeComboBox.setDisable(true);
        inputText.setDisable(true);
        uploadBtn.setVisible(false);
        typeComboBox.setItems(searchTypeOptions);
        typeComboBox.getSelectionModel().select(0);
        operatorLabel.setText(propertiesOperation.returnOperatorFromProperties("userConfig.properties"));

        TableColumn goodsIdCol = new TableColumn("商品编号");
        goodsIdCol.setSortable(true);
        goodsIdCol.setCellValueFactory(new PropertyValueFactory<>("goodsId"));
        TableColumn goodsNameCol = new TableColumn("商品名称");
        goodsNameCol.setSortable(false);
        goodsNameCol.setCellValueFactory(new PropertyValueFactory<>("goodsName"));
        TableColumn quantityTimeCol = new TableColumn("保质期");
        quantityTimeCol.setSortable(true);
        quantityTimeCol.setCellValueFactory(new PropertyValueFactory<>("goodsQualityTime"));
        TableColumn stocksCol = new TableColumn("库存");
        stocksCol.setSortable(false);
        stocksCol.setCellValueFactory(new PropertyValueFactory<>("stocks"));
        stocksCol.setCellFactory(col -> {
            TableCell<ShippingDepartment, Double> cell = new TableCell<>() {
                @Override
                public void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        int row = this.getIndex();
                        this.setText(doubleFormatService.getSubstringInputDouble(shippingDepartments.get(row).getStocks(), 3) + shippingDepartments.get(row).getGoodsUnit());
                    }
                }
            };
            return cell;
        });
        resList.getColumns().addAll(goodsIdCol, goodsNameCol, quantityTimeCol, stocksCol);
        resList.setItems(shippingDepartments);

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

        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if (newValue.getUserData().toString().equals("1")) {
                    numberLabel.setDisable(true);
                    nameLabel.setDisable(true);
                    stocksNumberLabel.setDisable(true);
                    inOrOutTypeComboBox.setDisable(true);
                    inputText.setDisable(true);
                    uploadBtn.setVisible(false);
                } else if (newValue.getUserData().toString().equals("2")) {
                    numberLabel.setDisable(true);
                    nameLabel.setDisable(true);
                    stocksNumberLabel.setDisable(true);
                    inOrOutTypeComboBox.setDisable(false);
                    inputText.setDisable(false);
                    uploadBtn.setVisible(true);
                } else {
                    numberLabel.setDisable(true);
                    nameLabel.setDisable(true);
                    stocksNumberLabel.setDisable(true);
                    inOrOutTypeComboBox.setDisable(true);
                    inputText.setDisable(true);
                    uploadBtn.setVisible(false);
                }
            }
        });

        resList.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                currentGood = shippingDepartments.get((int) newValue);
                numberLabel.setText(String.valueOf(currentGood.getGoodsId()));
                nameLabel.setText(currentGood.getGoodsName());
                stocksNumberLabel.setText(String.valueOf(currentGood.getStocks()));
            }
        });
    }

    private void clearSidePane() {
        numberLabel.setText("");
        nameLabel.setText("");
        stocksNumberLabel.setText("0");
        inOrOutTypeComboBox.getSelectionModel().select(0);
        inputText.setText("0");
    }

    @FXML
    private void handleSearch() {
        if (!numberInputText.getText().equals("")) {
            shippingDepartments.clear();
            service_search.restart();
        } else {
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "输入不能为空！", "请检查输入正确性！");
            alertDialog.showAlert();
        }
    }

    @FXML
    private void handleSearchAll() {
        shippingDepartments.clear();
        service_searchAll.restart();
    }

    @FXML
    private void handleUpload() {
        if (!inputText.getText().equals("")) service_upload.restart();
        else {
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "表单填写有误！", "请检查表单正确性！");
            alertDialog.showAlert();
        }
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

    Service<Integer> service_search = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            List<ShippingDepartment> list = shippingDepOperation.stockSearchByTest(typeComboBox.getSelectionModel().getSelectedIndex(), numberInputText.getText());
            if (!list.toString().equals("[]")) {
                for (int i = 0; i < list.size(); i++) shippingDepartments.add(list.get(i));
            } else Platform.runLater(() -> {
                AlertDialog alertDialog = new AlertDialog();
                alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "没有找到商品！", "请检查输入正确性！");
                alertDialog.showAlert();
            });
            return null;
        }
    };

    Service<Integer> service_searchAll = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    List<ShippingDepartment> list = shippingDepOperation.stockSearchAll();
                    if (!list.toString().equals("[]")) {
                        for (int i = 0; i < list.size(); i++) shippingDepartments.add(list.get(i));
                    } else Platform.runLater(() -> resList.setPlaceholder(new Label("没有结果")));
                    return null;
                }
            };
        }
    };

    Service<Integer> service_upload = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    int flag = shippingDepOperation.stockUpdate(currentGood.getGoodsId(), inOrOutTypeComboBox.getSelectionModel().getSelectedIndex(), Double.valueOf(inputText.getText()));
                    if (flag == 1) {
                        Platform.runLater(() -> {
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.INFORMATION, "成功", "提交成功！", "提交成功！");
                            alertDialog.showAlert();
                            shippingDepartments.clear();
                            clearSidePane();
                        });
                    } else {
                        Platform.runLater(() -> {
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "提交失败！", "提交失败！");
                            alertDialog.showAlert();
                        });
                    }
                    return null;
                }
            };
        }
    };
}
