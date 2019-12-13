package model;

import javafx.beans.property.*;

//退款记录表
public class ReturnRecord {
    private IntegerProperty recordId = new SimpleIntegerProperty();
    private IntegerProperty orderId = new SimpleIntegerProperty();
    private IntegerProperty customerNumber = new SimpleIntegerProperty();
    private StringProperty customerName = new SimpleStringProperty();
    private IntegerProperty stuffNumber =new SimpleIntegerProperty();
    private DoubleProperty totalReturnPrice = new SimpleDoubleProperty();//总退款金额

    public int getRecordId() {
        return recordId.get();
    }

    public IntegerProperty recordIdProperty() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId.set(recordId);
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

    public int getCustomerNumber() {
        return customerNumber.get();
    }

    public IntegerProperty customerNumberProperty() {
        return customerNumber;
    }

    public void setCustomerNumber(int customerNumber) {
        this.customerNumber.set(customerNumber);
    }

    public String getCustomerName() {
        return customerName.get();
    }

    public StringProperty customerNameProperty() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName.set(customerName);
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

    public double getTotalReturnPrice() {
        return totalReturnPrice.get();
    }

    public DoubleProperty totalReturnPriceProperty() {
        return totalReturnPrice;
    }

    public void setTotalReturnPrice(double totalReturnPrice) {
        this.totalReturnPrice.set(totalReturnPrice);
    }

    @Override
    public String toString() {
        return "ReturnRecord{" +
                "recordId=" + recordId +
                ", orderId=" + orderId +
                ", customerNumber=" + customerNumber +
                ", customerName=" + customerName +
                ", stuffNumber=" + stuffNumber +
                ", totalReturnPrice=" + totalReturnPrice +
                '}';
    }
}
