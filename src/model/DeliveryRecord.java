package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DeliveryRecord {
//    id int primary key auto_increment,
//    orderId int,
//    stuffNumber int,
//    outTime datetime

    private IntegerProperty id =new SimpleIntegerProperty();
    private  IntegerProperty orderId =new SimpleIntegerProperty();
    private  IntegerProperty stuffNumber =new SimpleIntegerProperty();
    private StringProperty outTime =new SimpleStringProperty();

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
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

    public int getStuffNumber() {
        return stuffNumber.get();
    }

    public IntegerProperty stuffNumberProperty() {
        return stuffNumber;
    }

    public void setStuffNumber(int stuffNumber) {
        this.stuffNumber.set(stuffNumber);
    }

    public String getOutTime() {
        return outTime.get();
    }

    public StringProperty outTimeProperty() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime.set(outTime);
    }

    @Override
    public String toString() {
        return "DeliveryRecord{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", stuffNumber=" + stuffNumber +
                ", outTime=" + outTime +
                '}';
    }
}
