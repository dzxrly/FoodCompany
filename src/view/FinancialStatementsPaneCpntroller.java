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
import model.FinancialInfo;
import model.Income;
import model.Payment;
import service.AddImageForComponent;
import service.AlertDialog;
import service.DoubleFormatService;
import service.FinancialSheetSearch;

import java.util.List;

public class FinancialStatementsPaneCpntroller {
    //财务报表页面控制类
    @FXML
    private ComboBox searchTypeComboBox;
    @FXML
    private TextField searchInputText;
    @FXML
    private Button searchBtn;
    @FXML
    private Button searchAllBtn;
    @FXML
    private ComboBox filterTypeComboBox;
    @FXML
    private TableView billList;
    @FXML
    private Label billNumberLabel;
    @FXML
    private Label incomingSum;
    @FXML
    private Label outcomingSum;
    @FXML
    private Label totalLabel;
    @FXML
    private Button printBtn;

    private ObservableList<String> searchTypeOptions = FXCollections.observableArrayList("仅查询收入账单", "仅查询支出账单");
    private ObservableList<String> filterOptions = FXCollections.observableArrayList("全部", "最近一个月", "最近半年", "最近一年");
    private ObservableList<FinancialInfo> financialInfos = FXCollections.observableArrayList();
    private ObservableList<Integer> currentSelects = FXCollections.observableArrayList();
    private Double sum = 0.0;
    private Double incomeSum = 0.0;
    private Double outcomeSum = 0.0;
    private DoubleFormatService doubleFormatService = new DoubleFormatService();

    @FXML
    private void initialize() {
        searchTypeComboBox.setItems(searchTypeOptions);
        searchTypeComboBox.getSelectionModel().select(0);
        filterTypeComboBox.setItems(filterOptions);
        filterTypeComboBox.getSelectionModel().select(0);
        searchInputText.setText("");
        searchInputText.setDisable(false);
        searchBtn.setGraphic((new AddImageForComponent("img/search14x14.png", 14)).getImageView());
        printBtn.setGraphic((new AddImageForComponent("img/download.png", 14)).getImageView());

//        TableColumn idCol = new TableColumn("账单编号");
//        idCol.setSortable(true);
//        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn typeCol = new TableColumn("账单类型");
        typeCol.setSortable(false);
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        TableColumn accTypeCol = new TableColumn("具体类型");
        accTypeCol.setSortable(false);
        accTypeCol.setCellValueFactory(new PropertyValueFactory<>("accType"));
        TableColumn projectCol = new TableColumn("项目名");
        projectCol.setSortable(false);
        projectCol.setCellValueFactory(new PropertyValueFactory<>("project"));
        TableColumn priceCol = new TableColumn("收入/支出");
        priceCol.setSortable(true);
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        TableColumn dateCol = new TableColumn("日期");
        dateCol.setSortable(false);
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        billList.getColumns().addAll(typeCol, accTypeCol, projectCol, priceCol, dateCol);
        billList.setItems(financialInfos);
        billList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        billList.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                currentSelects = billList.getSelectionModel().getSelectedIndices();
                sum = 0.0;
                incomeSum = 0.0;
                outcomeSum = 0.0;
                for (int i = 0; i < currentSelects.size(); i++) {
                    if (financialInfos.get(currentSelects.get(i).intValue()).getType().equals("收入账单")) {
                        incomeSum += financialInfos.get(currentSelects.get(i).intValue()).getPrice();
                    } else {
                        outcomeSum += financialInfos.get(currentSelects.get(i).intValue()).getPrice();
                    }
                }
                sum = incomeSum - outcomeSum;
                billNumberLabel.setText(String.valueOf(currentSelects.size()));
                incomingSum.setText(doubleFormatService.getSubstringPrice(incomeSum, 10));
                outcomingSum.setText(doubleFormatService.getSubstringPrice(outcomeSum, 10));
                totalLabel.setText(doubleFormatService.getSubstringPrice(sum, 10));
            }
        });

        filterTypeComboBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                clearAll();
                service_searchAll.restart();
            }
        });
    }

    private void clearAll() {
        financialInfos.clear();
        currentSelects.clear();
        billNumberLabel.setText("0");
        incomingSum.setText("0.0");
        outcomingSum.setText("0.0");
        totalLabel.setText("0.0");
        incomeSum = 0.0;
        outcomeSum = 0.0;
    }

    @FXML
    private void handleSearch() {
        if (!searchInputText.getText().equals("")) {
            clearAll();
            service_search.restart();
        } else {
            Platform.runLater(() -> {
                AlertDialog alertDialog = new AlertDialog();
                alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "提交失败！", "请输入订单号！");
                alertDialog.showAlert();
            });
        }
    }

    @FXML
    private void handleSearchAll() {
        clearAll();
        service_searchAll.restart();
    }

    @FXML
    private void handlePrint() {
        //TODO
    }

    Service<Integer> service_search = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    FinancialSheetSearch financialSheetSearch = new FinancialSheetSearch();
                    if (searchTypeComboBox.getSelectionModel().getSelectedIndex() == 0) {
                        List<Income> list = financialSheetSearch.searchId(searchInputText.getText(), 0);
                        if (!list.toString().equals("[]") && list != null) {
                            for (int i = 0; i < list.size(); i++) {
                                financialInfos.add(new FinancialInfo(0, list.get(i).getIncomeId(), list.get(i).getIncomePrice(), list.get(i).getIncomeTime(), list.get(i).getIncomeType(), list.get(i).getIncomeItem()));
                            }
                        } else {
                            Platform.runLater(() -> {
                                AlertDialog alertDialog = new AlertDialog();
                                alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "提交失败！", "订单号不存在！");
                                alertDialog.showAlert();
                            });
                        }
                    } else {
                        List<Payment> list = financialSheetSearch.searchId(searchInputText.getText(), 1);
                        if (!list.toString().equals("[]") && list != null) {
                            for (int i = 0; i < list.size(); i++) {
                                financialInfos.add(new FinancialInfo(1, list.get(i).getPaymentId(), list.get(i).getPaymentPrice(), list.get(i).getPayTime(), list.get(i).getPaymentType(), list.get(i).getPaymentItems()));
                            }
                        } else {
                            Platform.runLater(() -> {
                                AlertDialog alertDialog = new AlertDialog();
                                alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "提交失败！", "订单号不存在！");
                                alertDialog.showAlert();
                            });
                        }
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
                    FinancialSheetSearch financialSheetSearch = new FinancialSheetSearch();

                    List<Income> list1 = financialSheetSearch.searchAll(0, filterTypeComboBox.getSelectionModel().getSelectedIndex());
                    for (int i = 0; i < list1.size(); i++) {
                        financialInfos.add(new FinancialInfo(0, list1.get(i).getIncomeId(), list1.get(i).getIncomePrice(), list1.get(i).getIncomeTime(), list1.get(i).getIncomeType(), list1.get(i).getIncomeItem()));
                    }
                    List<Payment> list2 = financialSheetSearch.searchAll(1, filterTypeComboBox.getSelectionModel().getSelectedIndex());
                    for (int i = 0; i < list2.size(); i++) {
                        financialInfos.add(new FinancialInfo(1, list2.get(i).getPaymentId(), list2.get(i).getPaymentPrice(), list2.get(i).getPayTime(), list2.get(i).getPaymentType(), list2.get(i).getPaymentItems()));
                    }
                    return null;
                }
            };
        }
    };
}
