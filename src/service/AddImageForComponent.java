package service;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AddImageForComponent {
    //为控件添加图标
    private ImageView imageView;

    public AddImageForComponent(String URL) {
        imageView = new ImageView(new Image(URL, 16, 16, false, false));
    }
}
