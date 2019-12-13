package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ReturnBill {
    private IntegerProperty returnId = new SimpleIntegerProperty();
    private IntegerProperty stuffNumber = new SimpleIntegerProperty();
    private IntegerProperty customerId = new SimpleIntegerProperty();
    private IntegerProperty orderId= new SimpleIntegerProperty();
    private StringProperty note = new SimpleStringProperty();
    private  IntegerProperty isQuality = new SimpleIntegerProperty();//0 表示是质量问题 1 表示不是质量问题

    public int getIsQuality() {
        return isQuality.get();
    }

    public IntegerProperty isQualityProperty() {
        return isQuality;
    }

    public void setIsQuality(int isQuality) {
        this.isQuality.set(isQuality);
    }

    public int getReturnId() {
        return returnId.get();
    }

    public IntegerProperty returnIdProperty() {
        return returnId;
    }

    public void setReturnId(int returnId) {
        this.returnId.set(returnId);
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

    public int getCustomerId() {
        return customerId.get();
    }

    public IntegerProperty customerIdProperty() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId.set(customerId);
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

    public String getNote() {
        return note.get();
    }

    public StringProperty noteProperty() {
        return note;
    }

    public void setNote(String note) {
        this.note.set(note);
    }

    @Override
    public String toString() {
        return "ReturnBill{" +
                "returnId=" + returnId +
                ", stuffNumber=" + stuffNumber +
                ", customerId=" + customerId +
                ", orderId=" + orderId +
                ", note=" + note +
                ", isQuality=" + isQuality +
                '}';
    }
}
