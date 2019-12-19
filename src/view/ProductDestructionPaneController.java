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
import model.ShippingDepartment;
import service.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ProductDestructionPaneController {
    @FXML
    private ComboBox typeComboBox;
    @FXML
    private TextField inputText;
    @FXML
    private Button searchBtn;
    @FXML
    private Button searchAllBtn;
    @FXML
    private TableView listTable;
    @FXML
    private Label numberLabel;
    @FXML
    private Label operatorLabel;
    @FXML
    private DatePicker datePicker;
    @FXML
    private TextArea noteArea;
    @FXML
    private Button uploadBtn;
    @FXML
    private Label inputNumberMaxLabel;

    private ObservableList<String> searchTypeOptions = FXCollections.observableArrayList("按照编号查询", "按照名称查询");
    private ObservableList<ShippingDepartment> shippingDepartments = FXCollections.observableArrayList();
    private DestroyOperation destroyOperation = new DestroyOperation();
    private DoubleFormatService doubleFormatService = new DoubleFormatService();
    private PropertiesOperation propertiesOperation = new PropertiesOperation();
    private String operatorNumber;
    private ShippingDepartment currentGood = new ShippingDepartment();
    private final String pattern = "yyyy-MM-dd";

    @FXML
    private void initialize() {
        searchBtn.setGraphic((new AddImageForComponent("img/search14x14.png", 14)).getImageView());
        uploadBtn.setGraphic((new AddImageForComponent("img/check.png", 14)).getImageView());

        typeComboBox.setItems(searchTypeOptions);
        typeComboBox.getSelectionModel().select(0);
        inputNumberMaxLabel.setText("0/128");
        inputNumberMaxLabel.setTextFill(Color.BLACK);
        noteArea.setWrapText(true);
        operatorLabel.setText(propertiesOperation.returnOperatorFromProperties("userConfig.properties"));
        operatorNumber = propertiesOperation.readValue("userConfig.properties", "LoginUserNumber");

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
        listTable.getColumns().addAll(goodsIdCol, goodsNameCol, quantityTimeCol, stocksCol);
        listTable.setItems(shippingDepartments);

        StringConverter converter = new StringConverter<LocalDate>() {
            DateTimeFormatter dateFormatter =
                    DateTimeFormatter.ofPattern(pattern);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);
                } else {
                    return null;
                }
            }
        };
        datePicker.setConverter(converter);
        datePicker.setPromptText(pattern.toLowerCase());

        noteArea.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.length() >= 128) {
                    inputNumberMaxLabel.setText(String.valueOf(newValue.length() + "/128 超过最大字数限制"));
                    inputNumberMaxLabel.setTextFill(Color.web("#F56C6C"));
                    uploadBtn.setDisable(true);
                } else {
                    inputNumberMaxLabel.setText(String.valueOf(newValue.length()) + "/128");
                    inputNumberMaxLabel.setTextFill(Color.BLACK);
                    uploadBtn.setDisable(false);
                }
            }
        });

        listTable.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                currentGood = shippingDepartments.get((int) newValue);
                numberLabel.setText(String.valueOf(currentGood.getGoodsId()));
            }
        });
    }

    private void clearSidePane() {
        numberLabel.setText("");
        datePicker.setValue(LocalDate.now());
        noteArea.setText("");
    }

    @FXML
    private void handleSearch() {
        if (!inputText.getText().equals("")) {
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
        if (!numberLabel.getText().equals("") && !datePicker.getValue().toString().equals("") && !noteArea.getText().equals("")) {
            service_upload.restart();
        } else {
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "表单填写有误！", "请检查表单正确性！");
            alertDialog.showAlert();
        }
    }

    Service<Integer> service_search = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            List<ShippingDepartment> list = destroyOperation.stockSearchByTest(typeComboBox.getSelectionModel().getSelectedIndex(), inputText.getText());
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
                    List<ShippingDepartment> list = destroyOperation.stockSearchAll();
                    if (!list.toString().equals("[]")) {
                        for (int i = 0; i < list.size(); i++) shippingDepartments.add(list.get(i));
                    } else Platform.runLater(() -> listTable.setPlaceholder(new Label("没有结果")));
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
                    int flag = destroyOperation.createDestroyRecord(currentGood.getGoodsName(), Integer.valueOf(operatorNumber), datePicker.getValue().toString(), noteArea.getText());
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
