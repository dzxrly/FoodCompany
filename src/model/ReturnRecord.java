package model;

import javafx.beans.property.*;

//退款记录表
public class ReturnRecord {
    private IntegerProperty recordId = new SimpleIntegerProperty();
    private IntegerProperty orderId = new SimpleIntegerProperty();
    private IntegerProperty orderType = new SimpleIntegerProperty();//0为现货订单 1为预定订单
    private IntegerProperty customerNumber = new SimpleIntegerProperty();
    private StringProperty customerName = new SimpleStringProperty();
    private IntegerProperty customerLevel= new SimpleIntegerProperty(); //客户星级，用数字表示，分为1~5，5为最高.
    private StringProperty returnTime = new SimpleStringProperty();
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

    public int getOrderType() {
        return orderType.get();
    }

    public IntegerProperty orderTypeProperty() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType.set(orderType);
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

    public int getCustomerLevel() {
        return customerLevel.get();
    }

    public IntegerProperty customerLevelProperty() {
        return customerLevel;
    }

    public void setCustomerLevel(int customerLevel) {
        this.customerLevel.set(customerLevel);
    }

    public String getReturnTime() {
        return returnTime.get();
    }

    public StringProperty returnTimeProperty() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime.set(returnTime);
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
                ", orderType=" + orderType +
                ", customerNumber=" + customerNumber +
                ", customerName=" + customerName +
                ", customerLevel=" + customerLevel +
                ", returnTime=" + returnTime +
                ", stuffNumber=" + stuffNumber +
                ", totalReturnPrice=" + totalReturnPrice +
                '}';
    }
}
