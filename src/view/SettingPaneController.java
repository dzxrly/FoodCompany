package view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import service.AddImageForComponent;
import service.AlertDialog;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class SettingPaneController {
    //设置面板控制类

    @FXML
    private TextField JDBCDriverText;
    @FXML
    private TextField DBURLText;
    @FXML
    private TextField usernameText;
    @FXML
    private TextField passwordText;
    @FXML
    private Button confirmBtn;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button cancelBtn;

    @FXML
    private void initialize() {
        confirmBtn.setGraphic(new AddImageForComponent("img/check.png", 14).getImageView());
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            document = saxReader.read("src/hibernate.cfg.xml");
            Element root = document.getRootElement();
            Element session_factoryNode = root.element("session-factory");
            List<Element> list = session_factoryNode.elements("property");
            for (Iterator iterator = list.iterator(); iterator.hasNext(); ) {
                Element element = (Element) iterator.next();
                String a = element.attributeValue("name");
                if (a.equals("hibernate.connection.driver_class")) {
                    JDBCDriverText.setText(element.getTextTrim());
                } else if (a.equals("hibernate.connection.url")) {
                    DBURLText.setText(element.getTextTrim());
                } else if (a.equals("hibernate.connection.username")) {
                    usernameText.setText(element.getTextTrim());
                } else if (a.equals("hibernate.connection.password")) {
                    passwordText.setText(element.getTextTrim());
                } else continue;
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleConfirmBtn() {
        if (!JDBCDriverText.getText().equals("") &&
        !DBURLText.getText().equals("") &&
        !usernameText.getText().equals("") &&
        !passwordText.getText().equals("")) {
            SAXReader saxReader = new SAXReader();
            try {
                Document document = saxReader.read("src/hibernate.cfg.xml");
                Element root = document.getRootElement();
                Element session_factoryNode = root.element("session-factory");
                List<Element> list = session_factoryNode.elements("property");
                for (Iterator iterator = list.iterator(); iterator.hasNext(); ) {
                    Element element = (Element) iterator.next();
                    String a = element.attributeValue("name");
                    if (a.equals("hibernate.connection.driver_class")) {
                        element.setText(JDBCDriverText.getText());
                    } else if (a.equals("hibernate.connection.url")) {
                        element.setText(DBURLText.getText());
                    } else if (a.equals("hibernate.connection.username")) {
                        element.setText(usernameText.getText());
                    } else if (a.equals("hibernate.connection.password")) {
                        element.setText(passwordText.getText());
                    } else continue;
                }
                OutputFormat outputFormat = OutputFormat.createPrettyPrint();
                outputFormat.setEncoding("UTF-8");
                try {
                    XMLWriter writer = new XMLWriter(new FileWriter(new File("src/hibernate.cfg.xml")), outputFormat);
                    writer.write(document);
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.createAlert(Alert.AlertType.INFORMATION,"成功","修改成功！","修改成功，请重启程序！");
            alertDialog.showAlert();
            System.exit(-1);
        } else {
            AlertDialog alertDialog = new AlertDialog();
            alertDialog.createAlert(Alert.AlertType.ERROR,"错误","信息填写有误！","信息填写有误！");
            alertDialog.showAlert();
        }
    }

    @FXML
    private void handleCancel() {
        Stage stage = (Stage) anchorPane.getScene().getWindow();
        stage.close();
    }
}
