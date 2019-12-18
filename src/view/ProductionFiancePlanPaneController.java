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
import model.MaterialToGoods;
import model.OrderStocks;
import model.ProductionForm;
import service.*;

import java.util.List;

public class ProductionFiancePlanPaneController {
    @FXML
    private ComboBox productionPlanDateComboBox;
    @FXML
    private Button searchBtn;
    @FXML
    private TableView planList;
    @FXML
    private Label planCountLabel;
    @FXML
    private TableView goodsList;
    @FXML
    private TableView rawMetarialList;
    @FXML
    private Label rawMaterialLabel;
    @FXML
    private Label operatorLabel;
    @FXML
    private Button uploadBtn;

    private ObservableList<String> options = FXCollections.observableArrayList("全部", "最近一个季度", "最近两个季度", "最近三个季度");
    private ProductionFormSearch productionFormSearch = new ProductionFormSearch();
    private ObservableList<ProductionForm> productionFormObservableList = FXCollections.observableArrayList();
    private ProductionForm currentProductionForm = new ProductionForm();
    private OrdersSearch ordersSearch = new OrdersSearch();
    private ObservableList<OrderStocks> orderStocksObservableList = FXCollections.observableArrayList();
    private MaterialToGoodsSearch materialToGoodsSearch = new MaterialToGoodsSearch();
    private OrderStocks currentGood = new OrderStocks();
    private ObservableList<MaterialToGoods> materialToGoodsObservableList = FXCollections.observableArrayList();
    private Double priceSum = 0.0;

    @FXML
    private void initialize() {
        productionPlanDateComboBox.setItems(options);
        productionPlanDateComboBox.getSelectionModel().select(0);
        searchBtn.setGraphic((new AddImageForComponent("img/search14x14.png", 14)).getImageView());
        uploadBtn.setGraphic((new AddImageForComponent("img/check.png", 14)).getImageView());
        PropertiesOperation propertiesOperation = new PropertiesOperation();
        operatorLabel.setText(propertiesOperation.returnOperatorFromProperties("userConfig.properties"));
        planCountLabel.setText("");
        priceSum = 0.0;
        rawMaterialLabel.setText("");

        TableColumn productionFormIdCol = new TableColumn("表单编号");
        productionFormIdCol.setSortable(true);
        productionFormIdCol.setCellValueFactory(new PropertyValueFactory<>("productionId"));
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
                        String str = ((ProductionForm) planList.getItems().get(rowIndex)).getBuildTime().substring(0, 10);
                        this.setText(str);
                    }
                }
            };
            return cell;
        });
        TableColumn endTime = new TableColumn("截止日期");
        endTime.setSortable(false);
        endTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        endTime.setCellFactory(col -> {
            TableCell<ProductionForm, String> cell = new TableCell<>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        int rowIndex = this.getIndex();
                        String str = ((ProductionForm) planList.getItems().get(rowIndex)).getEndTime().substring(0, 10);
                        this.setText(str);
                    }
                }
            };
            return cell;
        });
        planList.getColumns().addAll(productionFormIdCol, orderIdCol, buildTimeCol, endTime);
        planList.setItems(productionFormObservableList);

        TableColumn goodIdCol = new TableColumn("商品编号");
        goodIdCol.setSortable(true);
        goodIdCol.setCellValueFactory(new PropertyValueFactory<>("goodsId"));
        TableColumn goodNameCol = new TableColumn("商品名称");
        goodNameCol.setSortable(false);
        goodNameCol.setCellValueFactory(new PropertyValueFactory<>("goodsName"));
        TableColumn buyNumberCol = new TableColumn("应生产数量");
        buyNumberCol.setSortable(false);
        TableColumn buyCountCol = new TableColumn("数量");
        buyCountCol.setSortable(false);
        buyCountCol.setCellValueFactory(new PropertyValueFactory<>("produceQuantity"));
        TableColumn buyUnitCol = new TableColumn("单位");
        buyUnitCol.setSortable(false);
        buyUnitCol.setCellValueFactory(new PropertyValueFactory<>("goodUnit"));
        buyNumberCol.getColumns().addAll(buyCountCol, buyUnitCol);
        TableColumn goodPrice = new TableColumn("单价");
        goodPrice.setSortable(true);
        goodPrice.setCellValueFactory(new PropertyValueFactory<>("goodsPrice"));
        goodsList.getColumns().addAll(goodIdCol, goodNameCol, buyNumberCol, goodPrice);
        goodsList.setItems(orderStocksObservableList);

        TableColumn materialIdCol = new TableColumn("原料编号");
        materialIdCol.setSortable(true);
        materialIdCol.setCellValueFactory(new PropertyValueFactory<>("materialId"));
        TableColumn materialQuantityCol = new TableColumn("所需数量");
        materialQuantityCol.setSortable(false);
        TableColumn materialNumberCol = new TableColumn("数量");
        materialNumberCol.setSortable(false);
        materialNumberCol.setCellValueFactory(new PropertyValueFactory<>("perQuantity"));
        TableColumn materialUnitCol = new TableColumn("单位");
        materialUnitCol.setSortable(false);
        materialUnitCol.setCellValueFactory(new PropertyValueFactory<>("materialUnit"));
        materialQuantityCol.getColumns().addAll(materialNumberCol, materialUnitCol);
        TableColumn materialNameCol = new TableColumn("原料名称");
        materialNameCol.setSortable(false);
        materialNameCol.setCellValueFactory(new PropertyValueFactory<>("materialName"));
        TableColumn materialStocksCol = new TableColumn("原料库存");
        materialStocksCol.setSortable(true);
        materialStocksCol.setCellValueFactory(new PropertyValueFactory<>("materialStocks"));
        rawMetarialList.getColumns().addAll(materialIdCol, materialNameCol, materialQuantityCol, materialStocksCol);
        rawMetarialList.setItems(materialToGoodsObservableList);

        planList.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                currentProductionForm = productionFormObservableList.get((int) newValue);
                planCountLabel.setText("");
                priceSum = 0.0;
                rawMaterialLabel.setText("");
                orderStocksObservableList.clear();
                materialToGoodsObservableList.clear();
                service_searchGoods.restart();
                planCountLabel.setText(String.valueOf(currentProductionForm.getProductionId()));
                service_sum.restart();
            }
        });

        goodsList.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                currentGood = orderStocksObservableList.get((int) newValue);
                materialToGoodsObservableList.clear();
                service_searchMaterial.restart();
            }
        });
    }

    @FXML
    private void handleSearch() {
        service_search.restart();
    }

    @FXML
    private void handleUpload() {
        if (currentProductionForm != null) {
            service_upload.restart();
        } else {
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.createAlert(Alert.AlertType.ERROR,"失败","表单填写有误！","请重新填写！");
            alertDialog.showAlert();
        }
    }

    Service<Integer> service_search = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    List<ProductionForm> list = productionFormSearch.searchProductionForm(productionPlanDateComboBox.getSelectionModel().getSelectedIndex());
                    if (!list.toString().equals("[]") && list != null) {
                        for (int i = 0; i < list.size(); i++) {
                            productionFormObservableList.add((ProductionForm) list.get(i));
                        }
                    } else {
                        Platform.runLater(() -> {
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "没有结果！", "没有结果！");
                        });
                    }
                    return null;
                }
            };
        }
    };

    Service<Integer> service_searchGoods = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    List list = ordersSearch.searchOrderAndStocks(String.valueOf(currentProductionForm.getOrderId()));
                    if (!list.toString().equals("[]") && list != null) {
                        for (int i = 0; i < list.size(); i++) {
                            Object[] objects = (Object[]) list.get(i);
                            OrderStocks orderStocks = new OrderStocks();
                            orderStocks.setGoodsId((int) objects[0]);
                            orderStocks.setGoodsName((String) objects[1]);
                            orderStocks.setOrderQuantity((Double) objects[2]);
                            orderStocks.setGoodsPrice((Double) objects[3]);
                            orderStocks.setStocks((Double) objects[4]);
                            orderStocks.setProduceQuantity((Double) objects[5]);
                            orderStocks.setGoodUnit((String) objects[6]);
                            orderStocksObservableList.add(orderStocks);
                        }
                    } else {
                        Platform.runLater(() -> goodsList.setPlaceholder(new Label("没有结果")));
                    }
                    return null;
                }
            };
        }
    };

    Service<Integer> service_searchMaterial = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    List<MaterialToGoods> list = materialToGoodsSearch.searchMaterialToGoods(String.valueOf(currentGood.getGoodsId()));
                    if (!list.toString().equals("[]") && list != null) {
                        for (int i = 0; i < list.size(); i++) {
                            materialToGoodsObservableList.add(list.get(i));
                        }
                    } else {
                        Platform.runLater(() -> rawMetarialList.setPlaceholder(new Label("没有结果")));
                    }
                    return null;
                }
            };
        }
    };

    Service<Integer> service_sum = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    List list = ordersSearch.searchOrderAndStocks(String.valueOf(currentProductionForm.getOrderId()));
                    if (!list.toString().equals("[]") && list != null) {
                        for (int i = 0; i < list.size(); i++) {
                            Object[] objects = (Object[]) list.get(i);
                            List<MaterialToGoods> materialList = materialToGoodsSearch.searchMaterialToGoods(String.valueOf((int) objects[0]));
                            Double temp_sum = 0.0;
                            if (!materialList.toString().equals("[]") && list != null) {
                                for (int j = 0; j < materialList.size(); j++)
                                    temp_sum += (materialList.get(j).getPerQuantity() * materialList.get(j).getMaterialPrice());
                                priceSum += temp_sum;
                            }
                        }
                        Platform.runLater(() -> rawMaterialLabel.setText(String.valueOf(priceSum)));
                    } else {
                        priceSum = 0.0;
                        rawMaterialLabel.setText(String.valueOf(priceSum));
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
                    ProductionFinanceSubmission productionFinanceSubmission = new ProductionFinanceSubmission();
                    PropertiesOperation propertiesOperation = new PropertiesOperation();
                    if (productionFinanceSubmission.submitProductionFinance(Integer.valueOf(propertiesOperation.readValue("userConfig.properties","LoginUserNumber")),priceSum) == 1) {
                        Platform.runLater(()->{
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.INFORMATION,"成功","提交成功！","提交成功！");
                            alertDialog.showAlert();
                            productionFormObservableList.clear();
                            orderStocksObservableList.clear();
                            materialToGoodsObservableList.clear();
                            priceSum=0.0;
                            planCountLabel.setText("");
                            rawMaterialLabel.setText("");
                        });
                    } else {
                        Platform.runLater(()->{
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.ERROR,"失败","提交失败！","请重新提交！");
                            alertDialog.showAlert();
                        });
                    }
                    return null;
                }
            };
        }
    };
}
