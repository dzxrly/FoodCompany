package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class FinancialTable {
    private IntegerProperty id = new SimpleIntegerProperty();//incomeId或paymentId
    private IntegerProperty type = new SimpleIntegerProperty();//0表示收入 1表示支出
    private DoubleProperty price =new SimpleDoubleProperty();//收入或支出

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
                '}';
    }
}
