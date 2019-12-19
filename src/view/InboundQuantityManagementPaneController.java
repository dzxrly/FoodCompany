package view;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
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
    private Label stuffNumberLabel;
    @FXML
    private Label stuffNameLabel;
    @FXML
    private Label operatorLabel;
    @FXML
    private Button markHasFinished;

    @FXML
    private void initialize() {
        searchBtn.setGraphic((new AddImageForComponent("img/search14x14.png",14)).getImageView());
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

    Service<Integer> service_search = new Service<Integer>() {
        @Override
        protected Task<Integer> createTask() {
            return new Task<Integer>() {
                @Override
                protected Integer call() throws Exception {

                    return null;
                }
            };
        }
    };
}
