package service;

import javafx.scene.control.Alert;

public class AlertDialog {
    //对话框类
    private Alert alert;

    public void createAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
        alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
    }

    public void showAlert() {
        alert.showAndWait();
    }
}
