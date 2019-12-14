package model;

import javafx.beans.property.*;

public class FinancialTable {

    private IntegerProperty id = new SimpleIntegerProperty();//incomeId或paymentId
    private IntegerProperty type = new SimpleIntegerProperty();//0表示收入 1表示支出
    private DoubleProperty price =new SimpleDoubleProperty();//收入或支出
    private StringProperty date = new SimpleStringProperty();//日期

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
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

    public int getType() {
        return type.get();
    }

    public IntegerProperty typeProperty() {
        return type;
    }

    public void setType(int type) {
        this.type.set(type);
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

    @Override
    public String toString() {
        return "FinancialTable{" +
                "id=" + id +
                ", type=" + type +
                ", price=" + price +
                ", date=" + date +
                '}';
    }
}
