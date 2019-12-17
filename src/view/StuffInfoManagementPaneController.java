package view;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.Stuff;
import org.controlsfx.control.ToggleSwitch;
import service.AlertDialog;
import service.CustomerIndexAndStringSwitch;
import service.StuffSearch;

import java.util.List;

public class StuffInfoManagementPaneController {
    @FXML
    private ComboBox levelSelectComboBox;
    @FXML
    private ComboBox secondLevelSelectComboBox;
    @FXML
    private TextField inputText;
    @FXML
    private Button searchBtn;
    @FXML
    private Button searchAllBtn;
    @FXML
    private TableView resList;
    @FXML
    private HBox modelHBox;
    @FXML
    private Label modelLebel;
    @FXML
    private Label stuffNumberLabel;
    @FXML
    private TextField stuffNameText;
    @FXML
    private ComboBox stuffLevelComboBox;
    @FXML
    private TextField addressText;
    @FXML
    private TextField phoneText;
    @FXML
    private TextField emailText;
    @FXML
    private TextField idNumberText;
    @FXML
    private ComboBox genderComboBox;
    @FXML
    private Button printBtn;
    @FXML
    private Button saveBtn;
    @FXML
    private Button deleteBtn;
    @FXML
    private HBox btnGroup;
    @FXML
    private HBox searchMenuHBox;
    @FXML
    private CheckBox isOnAddStuffModel;
    @FXML
    private Button addAllStuffBtn;
    @FXML
    private HBox printSaveDeleteBtnGroup;
    @FXML
    private VBox infoInputVBox;
    @FXML
    private ComboBox secondLevelComboBox;

    private ToggleSwitch toggleSwitch = new ToggleSwitch();
    private Label firstPWInputLabel = new Label();
    private TextField firstPWInputText = new TextField();
    private Label secondPWInputLabel = new Label();
    private PasswordField secondPWInputText = new PasswordField();
    private CheckBox useDefaultPW = new CheckBox();
    private VBox PWChangeVBox = new VBox();
    private Button addBtn = new Button();
    private ObservableList<Stuff> stuffObservableList = FXCollections.observableArrayList();
    private ObservableList<String> searchTypeComboBoxOptions = FXCollections.observableArrayList("按员工编号查询", "按姓名查询", "按部门查询", "按性别查询");
    private ObservableList<String> depSearchOptions = FXCollections.observableArrayList("老板", "销售部", "财务部", "生产计划科", "生产车间", "成品库", "原料库");
    private ObservableList<String> genderSearchOptions = FXCollections.observableArrayList("男", "女");
    private ObservableList<String> nullOptions = FXCollections.observableArrayList("无");
    private StuffSearch stuffSearch = new StuffSearch();
    private CustomerIndexAndStringSwitch customerIndexAndStringSwitch = new CustomerIndexAndStringSwitch();
    private Stuff currentStuff = new Stuff();
    private ObservableList<String> accLevelOptions = FXCollections.observableArrayList("老板", "经理", "员工");

    @FXML
    private void initialize() {
        stuffLevelComboBox.setItems(depSearchOptions);
        secondLevelSelectComboBox.setItems(nullOptions);
        secondLevelSelectComboBox.getSelectionModel().select(0);
        levelSelectComboBox.setItems(searchTypeComboBoxOptions);
        levelSelectComboBox.getSelectionModel().select(0);
        useDefaultPW.setText("是否使用默认密码(密码123)");
        useDefaultPW.setSelected(true);
        toggleSwitch.setSelected(false);
        modelHBox.getChildren().add(1, toggleSwitch);
        firstPWInputLabel.setText("请输入密码：");
        firstPWInputLabel.setFont(new Font(14));
        firstPWInputText.setText("123");
        secondPWInputLabel.setText("请重复输入：");
        secondPWInputLabel.setFont(new Font(14));
        secondPWInputText.setText("123");
        addBtn.setText("添加");
        addBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleAddBtn();
            }
        });
        firstPWInputText.setDisable(true);
        secondPWInputText.setDisable(true);
        PWChangeVBox.getChildren().addAll(useDefaultPW, firstPWInputLabel, firstPWInputText, secondPWInputLabel, secondPWInputText, addBtn);
        PWChangeVBox.setSpacing(8);

        modelLebel.setText("查询模式");
        stuffNameText.setDisable(true);
        stuffLevelComboBox.setDisable(true);
        addressText.setDisable(true);
        phoneText.setDisable(true);
        emailText.setDisable(true);
        idNumberText.setDisable(true);
        genderComboBox.setDisable(true);
        printBtn.setVisible(true);
        btnGroup.setVisible(false);
        addAllStuffBtn.setDisable(true);
        secondLevelSelectComboBox.setDisable(true);
        inputText.setDisable(false);

        TableColumn id = new TableColumn("员工编号");
        id.setSortable(true);
        id.setCellValueFactory(new PropertyValueFactory<>("number"));
        TableColumn name = new TableColumn("员工姓名");
        name.setSortable(false);
        name.setCellValueFactory(new PropertyValueFactory<>("personalName"));
        TableColumn gender = new TableColumn("性别");
        gender.setSortable(false);
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        gender.setCellFactory(col -> {
            TableCell<Stuff, Integer> cell = new TableCell<Stuff, Integer>() {
                @Override
                public void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        int rowIndex = this.getIndex();
                        this.setText(customerIndexAndStringSwitch.getGenderByIndex(stuffObservableList.get(rowIndex).getGender()));
                    }
                }
            };
            return cell;
        });
        TableColumn level = new TableColumn("权限");
        level.setSortable(false);
        TableColumn dep = new TableColumn("部门");
        dep.setSortable(false);
        dep.setCellValueFactory(new PropertyValueFactory<>("type"));
        dep.setCellFactory(col -> {
            TableCell<Stuff, Integer> cell = new TableCell<Stuff, Integer>() {
                @Override
                public void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        int rowIndex = this.getIndex();
                        this.setText(customerIndexAndStringSwitch.getDepNameByIndex(stuffObservableList.get(rowIndex).getType()));
                    }
                }
            };
            return cell;
        });
        TableColumn stuffLevel = new TableColumn("职位");
        stuffLevel.setSortable(false);
        stuffLevel.setCellValueFactory(new PropertyValueFactory<>("level"));
        stuffLevel.setCellFactory(col -> {
            TableCell<Stuff, Integer> cell = new TableCell<Stuff, Integer>() {
                @Override
                public void updateItem(Integer item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        int rowIndex = this.getIndex();
                        this.setText(customerIndexAndStringSwitch.getStuffLevelByIndex(stuffObservableList.get(rowIndex).getLevel()));
                    }
                }
            };
            return cell;
        });
        level.getColumns().addAll(dep, stuffLevel);
        TableColumn idCardNumberCol = new TableColumn("身份证号");
        idCardNumberCol.setSortable(false);
        idCardNumberCol.setCellValueFactory(new PropertyValueFactory<>("personalID"));
        TableColumn phone = new TableColumn("联系方式");
        phone.setSortable(false);
        phone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        resList.getColumns().addAll(id, name, gender, level, idCardNumberCol, phone);
        resList.setItems(stuffObservableList);

        stuffLevelComboBox.setItems(depSearchOptions);
        stuffLevelComboBox.getSelectionModel().select(0);
        secondLevelComboBox.setItems(accLevelOptions);
        secondLevelComboBox.getSelectionModel().select(0);
        genderComboBox.setItems(genderSearchOptions);
        genderComboBox.getSelectionModel().select(0);
        stuffNumberLabel.setText("");

        toggleSwitch.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    modelLebel.setText("修改模式");
                    stuffNameText.setDisable(false);
                    stuffLevelComboBox.setDisable(false);
                    addressText.setDisable(false);
                    phoneText.setDisable(false);
                    emailText.setDisable(false);
                    idNumberText.setDisable(false);
                    genderComboBox.setDisable(false);
                    printBtn.setVisible(false);
                    btnGroup.setVisible(true);
                    addAllStuffBtn.setDisable(true);
                } else {
                    modelLebel.setText("查询模式");
                    stuffNameText.setDisable(true);
                    stuffLevelComboBox.setDisable(true);
                    addressText.setDisable(true);
                    phoneText.setDisable(true);
                    emailText.setDisable(true);
                    idNumberText.setDisable(true);
                    genderComboBox.setDisable(true);
                    printBtn.setVisible(true);
                    btnGroup.setVisible(false);
                    addAllStuffBtn.setDisable(true);
                }
            }
        });

        isOnAddStuffModel.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    clearSidePane();
                    searchMenuHBox.setDisable(true);
                    modelHBox.setVisible(false);
                    toggleSwitch.setSelected(false);
                    stuffNameText.setDisable(false);
                    stuffLevelComboBox.setDisable(false);
                    addressText.setDisable(false);
                    phoneText.setDisable(false);
                    emailText.setDisable(false);
                    idNumberText.setDisable(false);
                    genderComboBox.setDisable(false);
                    printBtn.setVisible(false);
                    btnGroup.setVisible(false);
                    addAllStuffBtn.setDisable(false);
                    printSaveDeleteBtnGroup.setVisible(false);
                    infoInputVBox.getChildren().add(19, PWChangeVBox);
                } else {
                    searchMenuHBox.setDisable(false);
                    modelHBox.setVisible(true);
                    toggleSwitch.setSelected(false);
                    stuffNameText.setDisable(true);
                    stuffLevelComboBox.setDisable(true);
                    addressText.setDisable(true);
                    phoneText.setDisable(true);
                    emailText.setDisable(true);
                    idNumberText.setDisable(true);
                    genderComboBox.setDisable(true);
                    printBtn.setVisible(true);
                    btnGroup.setVisible(false);
                    addAllStuffBtn.setDisable(true);
                    printSaveDeleteBtnGroup.setVisible(true);
                    infoInputVBox.getChildren().remove(19);
                }
            }
        });

        useDefaultPW.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    firstPWInputText.setText("123");
                    secondPWInputText.setText("123");
                    firstPWInputText.setDisable(true);
                    secondPWInputText.setDisable(true);
                } else {
                    firstPWInputText.setText("");
                    secondPWInputText.setText("");
                    firstPWInputText.setDisable(false);
                    secondPWInputText.setDisable(false);
                }
            }
        });

        levelSelectComboBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if ((int) newValue == 0) {
                    secondLevelSelectComboBox.setDisable(true);
                    inputText.setDisable(false);
                    inputText.setText("");
                    secondLevelSelectComboBox.setItems(nullOptions);
                    secondLevelSelectComboBox.getSelectionModel().select(0);
                } else if ((int) newValue == 1) {
                    secondLevelSelectComboBox.setDisable(true);
                    secondLevelSelectComboBox.setItems(nullOptions);
                    secondLevelSelectComboBox.getSelectionModel().select(0);
                    inputText.setDisable(false);
                    inputText.setText("");
                } else if ((int) newValue == 2) {
                    secondLevelSelectComboBox.setDisable(false);
                    secondLevelSelectComboBox.setItems(depSearchOptions);
                    secondLevelSelectComboBox.getSelectionModel().select(0);
                    inputText.setDisable(true);
                    inputText.setText("");
                } else {
                    secondLevelSelectComboBox.setDisable(false);
                    secondLevelSelectComboBox.setItems(genderSearchOptions);
                    secondLevelSelectComboBox.getSelectionModel().select(0);
                    inputText.setDisable(true);
                    inputText.setText("");
                }
            }
        });

        resList.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                currentStuff = stuffObservableList.get((int) newValue);
                stuffNumberLabel.setText(String.valueOf(currentStuff.getNumber()));
                stuffNameText.setText(currentStuff.getPersonalName());
                stuffLevelComboBox.getSelectionModel().select(currentStuff.getType());
                secondLevelComboBox.getSelectionModel().select(currentStuff.getLevel());
                addressText.setText(currentStuff.getAddress());
                phoneText.setText(currentStuff.getPhoneNumber());
                emailText.setText(currentStuff.getEmail());
                idNumberText.setText(currentStuff.getPersonalID());
                genderComboBox.getSelectionModel().select(currentStuff.getGender());
            }
        });
        //TODO
    }

    private void clearSidePane() {
        stuffNumberLabel.setText("");
        stuffNameText.setText("");
        stuffLevelComboBox.getSelectionModel().select(0);
        secondLevelComboBox.getSelectionModel().select(0);
        addressText.setText("");
        phoneText.setText("");
        emailText.setText("");
        idNumberText.setText("");
        genderComboBox.getSelectionModel().select(0);
    }

    @FXML
    private void handleSearchBtn() {
        if (levelSelectComboBox.getSelectionModel().getSelectedIndex() == 0 || levelSelectComboBox.getSelectionModel().getSelectedIndex() == 1) {
            if (!inputText.getText().equals("")) {
                clearSidePane();
                stuffObservableList.clear();
                service_search.restart();
            } else {
                AlertDialog alertDialog = new AlertDialog();
                alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "编号或姓名不能为空！", "请输入编号或姓名！");
                alertDialog.showAlert();
            }
        } else {
            clearSidePane();
            stuffObservableList.clear();
            service_search.restart();
        }
    }

    @FXML
    private void handleSearchAllBtn() {
        clearSidePane();
        stuffObservableList.clear();
        service_searchAll.restart();
    }

    @FXML
    private void handlePrintBtn() {
        //TODO
    }

    @FXML
    private void handleSaveBtn() {
        //TODO
    }

    @FXML
    private void handleDeleteBtn() {
        //TODO
    }

    @FXML
    private void handleUploadAll() {
        //TODO
    }

    private void handleAddBtn() {
        //TODO
    }

    Service<Integer> service_search = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {
                    String searchInput;
                    if (secondLevelSelectComboBox.getValue().toString().equals("无")) searchInput = inputText.getText();
                    else searchInput = secondLevelSelectComboBox.getValue().toString();
                    List<Stuff> list = stuffSearch.searchByIndex(levelSelectComboBox.getSelectionModel().getSelectedIndex(), searchInput);
                    if (!list.toString().equals("[]") && list != null) {
                        for (int i = 0; i < list.size(); i++) stuffObservableList.add(list.get(i));
                    } else {
                        Platform.runLater(() -> {
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "没有结果！", "请重新输入！");
                            alertDialog.showAlert();
                        });
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
                    List<Stuff> list = stuffSearch.searchAll();
                    if (!list.toString().equals("[]") && list != null) {
                        for (int i = 0; i < list.size(); i++) stuffObservableList.add(list.get(i));
                    } else {
                        Platform.runLater(() -> {
                            AlertDialog alertDialog = new AlertDialog();
                            alertDialog.createAlert(Alert.AlertType.ERROR, "错误", "没有结果！", "请重新输入！");
                            alertDialog.showAlert();
                        });
                    }
                    return null;
                }
            };
        }
    };
}
