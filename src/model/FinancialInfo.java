package model;

import javafx.beans.property.*;
import service.BillTypeConvert;

public class FinancialInfo {
    private StringProperty type = new SimpleStringProperty();
    private IntegerProperty id = new SimpleIntegerProperty();
    private DoubleProperty price = new SimpleDoubleProperty();
    private StringProperty date = new SimpleStringProperty();
    private StringProperty accType = new SimpleStringProperty();
    private StringProperty project = new SimpleStringProperty();
    private IntegerProperty orderId = new SimpleIntegerProperty();

    public FinancialInfo(int type, int id, Double price, String date, int accType, String project) {
        this.type.set((new BillTypeConvert()).getTypeByIndex(type));
        this.id.set(id);
        this.price.set(price);
        this.date.set(date.substring(0, 10));
        this.accType.set((new BillTypeConvert()).getAccTypeByType(type, accType));
        this.project.set(project);
    }

    public int getOrderId() {
        return orderId.get();
    }

    public IntegerProperty orderIdProperty() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId.set(orderId);
    }

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public double getPrice() {
        return price.get();
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getAccType() {
        return accType.get();
    }

    public StringProperty accTypeProperty() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType.set(accType);
    }

    public String getProject() {
        return project.get();
    }

    public StringProperty projectProperty() {
        return project;
    }

    public void setProject(String project) {
        this.project.set(project);
    }
}
