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
import javafx.util.StringConverter;
import model.AssemblyLine;
import model.ProductPlan;
import service.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ProductionDistributionPaneController {
    @FXML
    private TextField planNumberInputText;
    @FXML
    private Button searchBtn;
    @FXML
    private Button searchAllBtn;
    @FXML
    private TableView plansList;
    @FXML
    private Label planNumberLabel;
    @FXML
    private Label goodsNumberLabel;
    @FXML
    private Label planTypeLabel;
    @FXML
    private Label cycleLabel;
    @FXML
    private HBox modelHBox;
    @FXML
    private Label modelLabel;
    @FXML
    private ComboBox pipelineAllocationComboBox;
    @FXML
    private DatePicker beginDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private Label operatorLabel;
    @FXML
    private Button uploadBtn;
    @FXML
    private TableView pipelineStatusList;
    @FXML
    private Button refreshStatus;
    @FXML
    private Button changePepilineStatus;
    @FXML
    private ComboBox alStatusComboBox;

    private ObservableList<ProductPlan> productPlanObservableList = FXCollections.observableArrayList();
    private ProductionManagement productionManagement = new ProductionManagement();
    private CustomerIndexAndStringSwitch customerIndexAndStringSwitch = new CustomerIndexAndStringSwitch();
    private DoubleFormatService doubleFormatService = new DoubleFormatService();
    private ProductPlan currentPlan = new ProductPlan();
    private final String pattern = "yyyy-MM-dd";
    private ObservableList<String> lineNumberOptions = FXCollections.observableArrayList("流水线-1", "流水线-2", "流水线-3", "流水线-4", "流水线-5", "流水线-6", "流水线-7", "流水线-8");
    private ObservableList<AssemblyLine> assemblyLines = FXCollections.observableArrayList();
    private AssemblyLine currentAssemblyLine = new AssemblyLine();
    private ObservableList<String> alStatus = FXCollections.observableArrayList("工作中", "已完成", "空闲");
    private String operatorNumber;
    private PropertiesOperation propertiesOperation = new PropertiesOperation();

    @FXML
    private void initialize() {
        searchBtn.setGraphic((new AddImageForComponent("img/search14x14.png", 14)).getImageView());
        uploadBtn.setGraphic((new AddImageForComponent("img/check.png", 14)).getImageView());
        pipelineAllocationComboBox.setItems(lineNumberOptions);
        pipelineAllocationComboBox.getSelectionModel().select(0);
        alStatusComboBox.setItems(alStatus);
        alStatusComboBox.getSelectionModel().select(0);
        operatorLabel.setText(propertiesOperation.returnOperatorFromProperties("userConfig.properties"));
        operatorNumber = propertiesOperation.readValue("userConfig.properties", "LoginUserNumber");

        TableColumn planId = new TableColumn("计划编号");
        planId.setSortable(true);
        planId.setCellValueFactory(new PropertyValueFactory<>("planId"));
        TableColumn formIdCol = new TableColumn("表单编号");
        formIdCol.setSortable(true);
        formIdCol.setCellValueFactory(new PropertyValueFactory<>("productionId"));
        TableColumn startTimeCol = new TableColumn("开始时间");
        startTimeCol.setSortable(false);
        startTimeCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        startTimeCol.setCellFactory(col -> {
            TableCell<ProductPlan, String> cell = new TableCell<>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        int row = this.getIndex();
                        this.setText(productPlanObservableList.get(row).getStartTime().substring(0, 10));
                    }
                }
            };
            return cell;
        });
        TableColumn endTimeCol = new TableColumn("截止时间");
        endTimeCol.setSortable(false);
        endTimeCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        endTimeCol.setCellFactory(col -> {
            TableCell<ProductPlan, String> cell = new TableCell<>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        int row = this.getIndex();
                        this.setText(productPlanObservableList.get(row).getEndTime().substring(0, 10));
                    }
                }
            };
            return cell;
        });
        TableColumn planTypeCol = new TableColumn("计划类型");
        planTypeCol.setSortable(false);
        planTypeCol.setCellValueFactory(new PropertyValueFactory<>("planType"));
        planTypeCol.setCellFactory(col -> {
            TableCell<ProductPlan, Integer> cell = new TableCell<>() {
                @Override
                public void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        int row = this.getIndex();
                        this.setText(customerIndexAndStringSwitch.getPlanTypeByIndex(productPlanObservableList.get(row).getPlanType()));
                    }
                }
            };
            return cell;
        });
        TableColumn cycleCol = new TableColumn("生产周期");
        cycleCol.setSortable(true);
        cycleCol.setCellValueFactory(new PropertyValueFactory<>("productionCycle"));
        plansList.getColumns().addAll(planId, formIdCol, startTimeCol, endTimeCol, planTypeCol);
        plansList.setItems(productPlanObservableList);

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

        TableColumn lineId = new TableColumn("流水线编号");
        lineId.setSortable(true);
        lineId.setCellValueFactory(new PropertyValueFactory<>("lineNumber"));
        TableColumn lineStatusCol = new TableColumn("流水线状态");
        lineStatusCol.setSortable(false);
        lineStatusCol.setCellValueFactory(new PropertyValueFactory<>("lineState"));
        lineStatusCol.setCellFactory(col -> {
            TableCell<ProductPlan, Integer> cell = new TableCell<>() {
                @Override
                public void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        int row = this.getIndex();
//                        this.getChildren().add(new Label(customerIndexAndStringSwitch.getLineStatusByIndex(assemblyLines.get(row).getLineState())));
                        this.setText(customerIndexAndStringSwitch.getLineStatusByIndex(assemblyLines.get(row).getLineState()));
                    }
                }
            };
            return cell;
        });
        pipelineStatusList.getColumns().addAll(lineId, lineStatusCol);
        pipelineStatusList.setItems(assemblyLines);
        handleRefresh();

        plansList.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                clearSidePane();
                currentPlan = productPlanObservableList.get((int) newValue);
                planNumberLabel.setText(String.valueOf(currentPlan.getPlanId()));
                goodsNumberLabel.setText(String.valueOf(currentPlan.getProductionId()));
                planTypeLabel.setText(customerIndexAndStringSwitch.getPlanTypeByIndex(currentPlan.getPlanType()));
                cycleLabel.setText(doubleFormatService.getSubstringInputDouble(currentPlan.getProductionCycle(), 3));
            }
        });

        pipelineStatusList.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                currentAssemblyLine = assemblyLines.get((int) newValue);
            }
        });
    }

    private void clearSidePane() {
        planNumberLabel.setText("");
        goodsNumberLabel.setText("");
        planTypeLabel.setText("");
        cycleLabel.setText("");
        pipelineAllocationComboBox.getSelectionModel().select(0);
        beginDate.setValue(LocalDate.now());
        endDate.setValue(LocalDate.now());
    }

    @FXML
    private void handleSearch() {
        if (!planNumberInputText.getText().equals("")) {
            clearSidePane();
            productPlanObservableList.clear();
            service_searchProductionPlanById.restart();
        } else {
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "输入不能为空！", "请输入编号!");
            alertDialog.showAlert();
        }
    }

    @FXML
    private void handleSearchAll() {
        clearSidePane();
        productPlanObservableList.clear();
        service_allPlans.restart();
    }

    @FXML
    private void handleUpload() {
        if (!planNumberLabel.getText().equals("") && !beginDate.getValue().toString().equals("") && !endDate.getValue().toString().equals("")) {
            if (assemblyLines.get(pipelineAllocationComboBox.getSelectionModel().getSelectedIndex()).getLineState() != 0) {
                service_upload.restart();
            } else {
                AlertDialog alertDialog = new AlertDialog();
                alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "流水线被占用！", "不能使用工作中的流水线！");
                alertDialog.showAlert();
            }
        } else {
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "表单填写有误！", "表单填写有误!");
            alertDialog.showAlert();
        }
    }

    @FXML
    private void handleRefresh() {
        assemblyLines.clear();
        service_refresh.restart();
    }

    @FXML
    private void handleChangePepilineStatus() {
        if (assemblyLines.size() != 0) {
            service_changeALStatus.restart();
        } else {
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "提交失败！", "没有选中流水线!");
            alertDialog.showAlert();
        }
    }

    Service<Integer> service_searchProductionPlanById = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    List<ProductPlan> list = productionManagement.searchProductionPlan(planNumberInputText.getText());
                    if (!list.toString().equals("[]")) {
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getProductionState() == 0)
                                productPlanObservableList.add(list.get(i));
                        }
                    } else Platform.runLater(() -> plansList.setPlaceholder(new Label("没有结果")));
                    return null;
                }
            };
        }
    };

    Service<Integer> service_allPlans = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    List<ProductPlan> list = productionManagement.searchAllProductionPlan();
                    if (!list.toString().equals("[]")) {
                        for (int i = 0; i < list.size(); i++) {
                            if (list.get(i).getProductionState() == 0)
                                productPlanObservableList.add(list.get(i));
                        }
                    } else Platform.runLater(() -> plansList.setPlaceholder(new Label("没有结果")));
                    return null;
                }
            };
        }
    };

    Service<Integer> service_refresh = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    List<AssemblyLine> lines = productionManagement.refreshAssembleLine();
                    if (!lines.toString().equals("[]"))
                        for (int i = 0; i < lines.size(); i++) assemblyLines.add(lines.get(i));
                    else Platform.runLater(() -> plansList.setPlaceholder(new Label("没有结果")));
                    return null;
                }
            };
        }
    };

    Service<Integer> service_changeALStatus = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    currentAssemblyLine.setLineState(alStatusComboBox.getSelectionModel().getSelectedIndex());
                    int flag = productionManagement.changeAssembleLine(currentAssemblyLine);
                    if (flag == 1) {
                        Platform.runLater(() -> {
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.INFORMATION, "成功", "提交成功！", "提交成功！");
                            alertDialog.showAlert();
                            handleRefresh();
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

    Service<Integer> service_upload = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    int flag = productionManagement.submitProductionPlanToAssemblyLine(String.valueOf(pipelineAllocationComboBox.getSelectionModel().getSelectedIndex()), String.valueOf(currentPlan.getPlanId()), beginDate.getValue().toString(), endDate.getValue().toString(), String.valueOf(currentPlan.getPlanType()), operatorNumber);
                    if (flag == 1) {
                        Platform.runLater(() -> {
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.INFORMATION, "成功", "提交成功！", "提交成功！");
                            alertDialog.showAlert();
                            productPlanObservableList.clear();
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
