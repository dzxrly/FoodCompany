package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import service.AddImageForComponent;

public class InboundQuantityManagementPaneController {
    @FXML
    private TextField numberInputText;
    @FXML
    private Button searchBtn;
    @FXML
    private Button searchAllBtn;
    @FXML
    private TableView listTable;
    @FXML
    private Label numberLabel;
    @FXML
    private Label typeLabel;
    @FXML
    private Label pipeLineNumberLabel;
    @FXML
    private Label expectedNumberLabel;
    @FXML
    private Label producedNumberLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Label warningTextChangeLabel;
    @FXML
    private Label warningLabel;
    @FXML
    private Label operatorLabel;
    @FXML
    private Button markDONOTFinish;
    @FXML
    private Button markHasFinished;

    @FXML
    private void initialize() {
        searchBtn.setGraphic((new AddImageForComponent("img/search14x14.png",14)).getImageView());
        markDONOTFinish.setGraphic((new AddImageForComponent("img/close14x14.png",14)).getImageView());
        markHasFinished.setGraphic((new AddImageForComponent("img/check.png",14)).getImageView());
        //TODO
    }

    @FXML
    private void handleSearch() {
        //TODO
    }

    @FXML
    private void handleSearchAll() {
        //TODO
    }

    @FXML
    private void handleMarkDONOTFinish() {
        //TODO
    }

    @FXML
    private void handleMarkHasFinished() {
        //TODO
    }
}
