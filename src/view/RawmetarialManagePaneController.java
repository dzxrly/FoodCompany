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
import javafx.scene.paint.Color;
import javafx.util.StringConverter;
import model.Material;
import service.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

public class RawmetarialManagePaneController {
    @FXML
    private TextField searchInputText;
    @FXML
    private Button searchBtn;
    @FXML
    private Button searchAllBtn;
    @FXML
    private TableView metarialList;
    @FXML
    private RadioButton searchModel;
    @FXML
    private RadioButton addModel;
    @FXML
    private RadioButton changeModel;
    @FXML
    private Label metarialNumberLabel;
    @FXML
    private TextField metarialNameText;
    @FXML
    private Label numberWarningLabel;
    @FXML
    private TextField resNumberLabel;
    @FXML
    private ComboBox metarialTypeComboBox;
    @FXML
    private TextField priceText;
    @FXML
    private Label operatorLabel;
    @FXML
    private Button uploadBtn;
    @FXML
    private Button printBtn;
    @FXML
    private TextField materialUnit;
    @FXML
    private TextField timeText;

    private ToggleGroup toggleGroup = new ToggleGroup();
    private ObservableList<String> metarialTypeOptions = FXCollections.observableArrayList("添加剂", "非添加剂");
    private MaterialManagement materialManagement = new MaterialManagement();
    private ObservableList<Material> materialObservableList = FXCollections.observableArrayList();
    private CustomerIndexAndStringSwitch customerIndexAndStringSwitch = new CustomerIndexAndStringSwitch();
    private DoubleFormatService doubleFormatService = new DoubleFormatService();
    private Material currentMaterial = new Material();
    private String operatorNumber;
    private final String pattern = "yyyy-MM-dd";
    private String stockAndPriceRegex = "[0-9.]+";
    private String timeRegex = "[0-9]+";

    @FXML
    private void initialize() {
        metarialTypeComboBox.setItems(metarialTypeOptions);
        metarialTypeComboBox.getSelectionModel().select(0);
        searchModel.setToggleGroup(toggleGroup);
        searchModel.setUserData(1);
        searchModel.setSelected(true);
        addModel.setToggleGroup(toggleGroup);
        addModel.setUserData(2);
        changeModel.setToggleGroup(toggleGroup);
        changeModel.setUserData(3);
        metarialNameText.setDisable(true);
        numberWarningLabel.setVisible(false);
        resNumberLabel.setDisable(true);
        metarialTypeComboBox.setDisable(true);
        priceText.setDisable(true);
        uploadBtn.setDisable(true);
        printBtn.setDisable(false);
        materialUnit.setDisable(true);
        timeText.setDisable(true);
        clearSidePane();
        searchBtn.setGraphic((new AddImageForComponent("img/search14x14.png", 14)).getImageView());
        uploadBtn.setGraphic((new AddImageForComponent("img/check.png", 14)).getImageView());
        printBtn.setGraphic((new AddImageForComponent("img/download.png", 14)).getImageView());
        PropertiesOperation propertiesOperation = new PropertiesOperation();
        operatorLabel.setText(propertiesOperation.returnOperatorFromProperties("userConfig.properties"));
        operatorNumber = propertiesOperation.readValue("userConfig.properties", "LoginUserNumber");

        TableColumn idCol = new TableColumn("原料编号");
        idCol.setSortable(true);
        idCol.setCellValueFactory(new PropertyValueFactory<>("materialId"));
        TableColumn nameCol = new TableColumn("原料名称");
        nameCol.setSortable(false);
        nameCol.setCellValueFactory(new PropertyValueFactory<>("materialName"));
        TableColumn typeCol = new TableColumn("原料类型");
        typeCol.setSortable(false);
        typeCol.setCellValueFactory(new PropertyValueFactory<>("materialType"));
        typeCol.setCellFactory(col -> {
            TableCell<Material, Integer> cell = new TableCell<>() {
                @Override
                public void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        int rowIndex = this.getIndex();
                        this.setText(customerIndexAndStringSwitch.getMaterialTypeByIndex(materialObservableList.get(rowIndex).getMaterialType()));
                    }
                }
            };
            return cell;
        });
        TableColumn stocksCol = new TableColumn("原料库存");
        stocksCol.setSortable(true);
        stocksCol.setCellValueFactory(new PropertyValueFactory<>("stocks"));
        stocksCol.setCellFactory(col -> {
            TableCell<Material, Double> cell = new TableCell<>() {
                @Override
                public void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        int rowIndex = this.getIndex();
                        this.setText(doubleFormatService.getSubstringInputDouble(materialObservableList.get(rowIndex).getStocks(), 3) + " " + materialObservableList.get(rowIndex).getMaterialUnit());
                    }
                }
            };
            return cell;
        });
        TableColumn timeCol = new TableColumn("储存期限");
        timeCol.setSortable(false);
        timeCol.setCellValueFactory(new PropertyValueFactory<>("materialQualityTime"));
        timeCol.setCellFactory(col -> {
            TableCell<Material, Integer> cell = new TableCell<>() {
                @Override
                public void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        int rowIndex = this.getIndex();
                        this.setText(String.valueOf(materialObservableList.get(rowIndex).getMaterialQualityTime()) + "天");
                    }
                }
            };
            return cell;
        });
        metarialList.getColumns().addAll(idCol, nameCol, typeCol, stocksCol, timeCol);
        metarialList.setItems(materialObservableList);

        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                RadioButton temp_radioBtn = (RadioButton) newValue;
                if (temp_radioBtn.getText().equals("查询")) {
                    metarialNameText.setDisable(true);
                    resNumberLabel.setDisable(true);
                    metarialTypeComboBox.setDisable(true);
                    priceText.setDisable(true);
                    uploadBtn.setDisable(true);
                    printBtn.setDisable(false);
                    materialUnit.setDisable(true);
                    timeText.setDisable(true);
                } else if (temp_radioBtn.getText().equals("添加")) {
                    metarialNameText.setDisable(false);
                    resNumberLabel.setDisable(false);
                    metarialTypeComboBox.setDisable(false);
                    priceText.setDisable(false);
                    uploadBtn.setDisable(false);
                    printBtn.setDisable(true);
                    materialUnit.setDisable(false);
                    timeText.setDisable(false);
                    clearSidePane();
                } else if (temp_radioBtn.getText().equals("修改")) {
                    metarialNameText.setDisable(false);
                    resNumberLabel.setDisable(false);
                    metarialTypeComboBox.setDisable(false);
                    priceText.setDisable(false);
                    uploadBtn.setDisable(false);
                    printBtn.setDisable(true);
                    materialUnit.setDisable(false);
                    timeText.setDisable(false);
                } else {
                    metarialNameText.setDisable(true);
                    numberWarningLabel.setVisible(true);
                    resNumberLabel.setDisable(true);
                    metarialTypeComboBox.setDisable(true);
                    priceText.setDisable(true);
                    uploadBtn.setDisable(true);
                    printBtn.setDisable(false);
                    materialUnit.setDisable(true);
                    timeText.setDisable(false);
                }
            }
        });

        metarialList.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                clearSidePane();
                currentMaterial = materialObservableList.get((int) newValue);
                metarialNumberLabel.setText(String.valueOf(currentMaterial.getMaterialId()));
                metarialNameText.setText(currentMaterial.getMaterialName());
                resNumberLabel.setText(doubleFormatService.getSubstringInputDouble(currentMaterial.getStocks(), 3));
                metarialTypeComboBox.getSelectionModel().select(currentMaterial.getMaterialType());
                priceText.setText(doubleFormatService.getSubstringInputDouble(currentMaterial.getMaterialPrice(), 3));
                materialUnit.setText(currentMaterial.getMaterialUnit());
                timeText.setText(String.valueOf(currentMaterial.getMaterialQualityTime()));
            }
        });
    }

    private void clearSidePane() {
        metarialNumberLabel.setText("");
        metarialNameText.setText("");
        resNumberLabel.setText("0");
        metarialTypeComboBox.getSelectionModel().select(0);
        priceText.setText("0");
        materialUnit.setText("");
        timeText.setText("");
    }

    private boolean formCheck() {
        return (!metarialNameText.getText().equals("") &&
                !resNumberLabel.getText().equals("") &&
                resNumberLabel.getText().matches(stockAndPriceRegex) &&
                !priceText.getText().equals("") &&
                priceText.getText().matches(stockAndPriceRegex) &&
                !materialUnit.getText().equals("") &&
                !timeText.getText().equals("") &&
                timeText.getText().matches(timeRegex));
    }

    @FXML
    private void handleSearch() {
        if (!searchInputText.getText().equals("")) {
            clearSidePane();
            materialObservableList.clear();
            service_search.restart();
        } else {
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "表单填写有误！", "请重新填写！");
            alertDialog.showAlert();
        }
    }

    @FXML
    private void handleSearchAll() {
        clearSidePane();
        materialObservableList.clear();
        service_searchAll.restart();
    }

    @FXML
    private void handleUpload() {
        if (addModel.isSelected() &&
                !searchModel.isSelected() &&
                !changeModel.isSelected() &&
                formCheck()) {
            //添加
            service_add.restart();
        } else if (!addModel.isSelected() &&
                !searchModel.isSelected() &&
                changeModel.isSelected() &&
                formCheck()) {
            //修改
            service_change.restart();
        } else {
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "表单填写有误！", "请重新填写！");
            alertDialog.showAlert();
        }
    }

    @FXML
    private void handlePrint() {
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

    Service<Integer> service_search = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    List<Material> list = materialManagement.searchMaterial(searchInputText.getText());
                    if (!list.toString().equals("[]")) {
                        for (int i = 0; i < list.size(); i++) materialObservableList.add(list.get(i));
                    } else {
                        Platform.runLater(() -> metarialList.setPlaceholder(new Label("没有结果")));
                    }
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
                    List<Material> list = materialManagement.searchMaterialAll();
                    if (!list.toString().equals("[]")) {
                        for (int i = 0; i < list.size(); i++) materialObservableList.add(list.get(i));
                    } else Platform.runLater(() -> metarialList.setPlaceholder(new Label("没有结果")));
                    return null;
                }
            };
        }
    };

    Service<Integer> service_add = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    int flag = materialManagement.addMaterial(metarialNameText.getText(), Double.valueOf(priceText.getText()), Integer.valueOf(operatorNumber), Integer.valueOf(timeText.getText()), metarialTypeComboBox.getSelectionModel().getSelectedIndex(), Double.valueOf(resNumberLabel.getText()), materialUnit.getText());
                    if (flag == 1) {
                        Platform.runLater(() -> {
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.INFORMATION, "成功", "提交成功！", "提交成功！");
                            alertDialog.showAlert();
                            clearSidePane();
                            materialObservableList.clear();
                        });
                    } else {
                        Platform.runLater(() -> {
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "表单填写有误！", "请重新填写！");
                            alertDialog.showAlert();
                        });
                    }
                    return null;
                }
            };
        }
    };

    Service<Integer> service_change = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    int flag = materialManagement.updateMaterial(currentMaterial.getMaterialId(), metarialNameText.getText(), Double.valueOf(priceText.getText()), Integer.valueOf(operatorNumber), Integer.valueOf(timeText.getText()), metarialTypeComboBox.getSelectionModel().getSelectedIndex(), Double.valueOf(resNumberLabel.getText()), materialUnit.getText());
                    if (flag == 1) {
                        Platform.runLater(() -> {
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.INFORMATION, "成功", "提交成功！", "提交成功！");
                            alertDialog.showAlert();
                            clearSidePane();
                            materialObservableList.clear();
                        });
                    } else {
                        Platform.runLater(() -> {
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "表单填写有误！", "请重新填写！");
                            alertDialog.showAlert();
                        });
                    }
                    return null;
                }
            };
        }
    };
}
