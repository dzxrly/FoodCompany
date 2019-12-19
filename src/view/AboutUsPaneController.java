package view;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import service.AddImageForComponent;


public class AboutUsPaneController {
    @FXML
    private ImageView imageView1;
    @FXML
    private ImageView imageView2;
    @FXML
    private ImageView imageView3;
    @FXML
    private Hyperlink hyperlink1;
    @FXML
    private Hyperlink hyperlink2;
    @FXML
    private Hyperlink hyperlink3;

    @FXML
    private void initialize() {
        imageView1.setImage(new Image("img/HYC_logo.jpg"));
        imageView2.setImage(new Image("img/CB_logo.jpg"));
        imageView3.setImage(new Image("img/LTT_logo.png"));

        hyperlink1.setGraphic(new AddImageForComponent("img/github.png", 14).getImageView());
        hyperlink2.setGraphic(new AddImageForComponent("img/github.png", 14).getImageView());
        hyperlink3.setGraphic(new AddImageForComponent("img/github.png", 14).getImageView());
    }
}
