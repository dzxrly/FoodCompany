package view;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class loginProgressBarController {

    @FXML
    private ProgressBar connectDBProgress;
    @FXML
    private AnchorPane anchorPane;

    @FXML
    private void initialize() throws InterruptedException {
        connectDBProgress.setProgress(-1);
    }
    //TODO


}
