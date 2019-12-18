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
import javafx.util.StringConverter;
import model.ProductPlan;
import model.ProductionForm;
import service.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ProductionPlansManagementPaneController {
    @FXML
    private ComboBox orderTypeComboBox;
    @FXML
    private Button searchBtn;
    @FXML
    private TableView orderList;
    @FXML
    private ComboBox planTypeComboBox;
    @FXML
    private DatePicker beginDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private Label operatorLabel;
    @FXML
    private Button uploadBtn;
    @FXML
    private Label formNumberLabel;
    @FXML
    private Label orderIdLabel;
    @FXML
    private Label buildTimeLabel;
    @FXML
    private Label endTimeLabel;
    @FXML
    private Label speedLabel;

    private ObservableList<String> orderTypeOptions = FXCollections.observableArrayList("全部", "最近一个月", "最近半年", "最近一年");
    private ObservableList<String> planTypeOptions = FXCollections.observableArrayList("季度", "预定");
    private ObservableList<ProductionForm> productionFormObservableList = FXCollections.observableArrayList();
    private ProductionFormSearch productionFormSearch = new ProductionFormSearch();
    private ProductionForm currentProductionForm = new ProductionForm();
    private DoubleFormatService doubleFormatService = new DoubleFormatService();
    private ProductionPlanCreator productionPlanCreator = new ProductionPlanCreator();
    private PropertiesOperation propertiesOperation = new PropertiesOperation();
    private final String pattern = "yyyy-MM-dd";

    @FXML
    private void initialize() {
        orderTypeComboBox.setItems(orderTypeOptions);
        orderTypeComboBox.getSelectionModel().select(0);
        planTypeComboBox.setItems(planTypeOptions);
        planTypeComboBox.getSelectionModel().select(0);
        searchBtn.setGraphic((new AddImageForComponent("img/search14x14.png", 14)).getImageView());
        uploadBtn.setGraphic((new AddImageForComponent("img/check.png", 14)).getImageView());
        PropertiesOperation propertiesOperation = new PropertiesOperation();
        operatorLabel.setText(propertiesOperation.returnOperatorFromProperties("userConfig.properties"));

        TableColumn idCol = new TableColumn("表单编号");
        idCol.setSortable(true);
        idCol.setCellValueFactory(new PropertyValueFactory<>("productionId"));
        TableColumn orderIdCol = new TableColumn("订单编号");
        orderIdCol.setSortable(true);
        orderIdCol.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        TableColumn buildTimeCol = new TableColumn("创建日期");
        buildTimeCol.setSortable(false);
        buildTimeCol.setCellValueFactory(new PropertyValueFactory<>("buildTime"));
        buildTimeCol.setCellFactory(col -> {
            TableCell<ProductionForm, String> cell = new TableCell<>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        int rowIndex = this.getIndex();
                        String str = productionFormObservableList.get(rowIndex).getBuildTime().substring(0, 10);
                        this.setText(str);
                    }
                }
            };
            return cell;
        });
        TableColumn endTimeCol = new TableColumn("截止日期");
        endTimeCol.setSortable(false);
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        endTimeCol.setCellFactory(col -> {
            TableCell<ProductionForm, String> cell = new TableCell<>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        int rowIndex = this.getIndex();
                        String str = productionFormObservableList.get(rowIndex).getEndTime().substring(0, 10);
                        this.setText(str);
                    }
                }
            };
            return cell;
        });
        TableColumn speedCol = new TableColumn("生产速度");
        speedCol.setSortable(true);
        speedCol.setCellValueFactory(new PropertyValueFactory<>("userTime"));
        speedCol.setCellFactory(col -> {
            TableCell<ProductionForm, Double> cell = new TableCell<>() {
                @Override
                public void updateItem(Double item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        int rowIndex = this.getIndex();
                        BigDecimal bigDecimal = new BigDecimal(productionFormObservableList.get(rowIndex).getUserTime());
                        String string = bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "/天";
                        this.setText(string);
                    }
                }
            };
            return cell;
        });
        orderList.getColumns().addAll(idCol, orderIdCol, buildTimeCol, endTimeCol, speedCol);
        orderList.setItems(productionFormObservableList);

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
        beginDate.setConverter(converter);
        beginDate.setPromptText(pattern.toLowerCase());
        endDate.setConverter(converter);
        endDate.setPromptText(pattern.toLowerCase());

        orderList.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                currentProductionForm = productionFormObservableList.get((int) newValue);
                formNumberLabel.setText(String.valueOf(currentProductionForm.getProductionId()));
                orderIdLabel.setText(String.valueOf(currentProductionForm.getOrderId()));
                buildTimeLabel.setText(currentProductionForm.getBuildTime());
                endTimeLabel.setText(currentProductionForm.getEndTime());
                speedLabel.setText(doubleFormatService.getSubstringInputDouble(currentProductionForm.getUserTime(), 3) + "/天");
            }
        });
    }

    public void clearSidePane() {
        formNumberLabel.setText("");
        orderIdLabel.setText("");
        buildTimeLabel.setText("");
        endTimeLabel.setText("");
        speedLabel.setText("");
        planTypeComboBox.getSelectionModel().select(0);
        beginDate.setValue(LocalDate.now());
        endDate.setValue(LocalDate.now());
    }

    @FXML
    private void handleSearch() {
        clearSidePane();
        productionFormObservableList.clear();
        service_search.restart();
    }

    @FXML
    private void handleUpload() {
        if (!orderIdLabel.getText().equals("") && !beginDate.getValue().toString().equals("") && !endDate.getValue().toString().equals("")) {
            service_upload.restart();
        } else {
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.createAlert(Alert.AlertType.ERROR, "失败", "表单填写有误！", "请重新填写！");
            alertDialog.showAlert();
        }
    }

    Service<Integer> service_search = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    List<ProductionForm> list = productionFormSearch.searchProductionFormPlanStateEqualZero(orderTypeComboBox.getSelectionModel().getSelectedIndex());
                    if (!list.toString().equals("[]") && list != null) {
                        for (int i = 0; i < list.size(); i++) {
                            productionFormObservableList.add(list.get(i));
                        }
                    } else Platform.runLater(() -> orderList.setPlaceholder(new Label("没有结果")));
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
                    ProductPlan productPlan = productionPlanCreator.createProductionPlan(currentProductionForm.getProductionId(), Integer.valueOf(propertiesOperation.readValue("userConfig.properties", "LoginUserNumber")), beginDate.getValue().toString(), endDate.getValue().toString(), String.valueOf(currentProductionForm.getOrderId()));
                    if (productPlan != null) {
                        productionPlanCreator.createProductionDetailPlan(productPlan, String.valueOf(currentProductionForm.getProductionId()));
                        Platform.runLater(() -> {
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.INFORMATION, "成功", "提交成功！", "提交成功！");
                            alertDialog.showAlert();
                            clearSidePane();
                            productionFormObservableList.clear();
                        });
                    } else {
                        Platform.runLater(() -> {
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.ERROR, "失败", "提交失败！", "请重新提交！");
                            alertDialog.showAlert();
                        });
                    }
                    return null;
                }
            };
        }
    };
}
