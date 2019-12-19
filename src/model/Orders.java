package model;

import javafx.beans.property.*;
import javassist.Loader;

//订单总表
public class Orders {
    private IntegerProperty orderId = new SimpleIntegerProperty();
    private IntegerProperty orderType = new SimpleIntegerProperty(); //0为现货订单 1为预定订单
    private StringProperty companyName = new SimpleStringProperty();
    private StringProperty customerName = new SimpleStringProperty();
    private IntegerProperty customerNumber = new SimpleIntegerProperty();
    private StringProperty customerPhone = new SimpleStringProperty();
    private StringProperty customerAddress = new SimpleStringProperty();
    private StringProperty buildDate = new SimpleStringProperty();
    private StringProperty endDate = new SimpleStringProperty();
    private DoubleProperty totalSum = new SimpleDoubleProperty();
    private IntegerProperty orderState = new SimpleIntegerProperty();//订单状态 0为未生产，1为生产中，2已完成，3为运输中，4为已到达
    private IntegerProperty paymentState = new SimpleIntegerProperty(); //支付状态 0为未付款 1为付了预付款 2为付了全款 4为已退款
    private IntegerProperty stuffNumber = new SimpleIntegerProperty();

    public String getEndDate() {
        return endDate.get();
    }

    public StringProperty endDateProperty() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate.set(endDate);
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

    public String getCompanyName() {
        return companyName.get();
    }

    public StringProperty companyNameProperty() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
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

    public int getCustomerNumber() {
        return customerNumber.get();
    }

    public IntegerProperty customerNumberProperty() {
        return customerNumber;
    }

    public void setCustomerNumber(int customerNumber) {
        this.customerNumber.set(customerNumber);
    }

    public String getCustomerPhone() {
        return customerPhone.get();
    }

    public StringProperty customerPhoneProperty() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone.set(customerPhone);
    }

    public String getCustomerAddress() {
        return customerAddress.get();
    }

    public StringProperty customerAddressProperty() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress.set(customerAddress);
    }

    public String getBuildDate() {
        return buildDate.get();
    }

    public StringProperty buildDateProperty() {
        return buildDate;
    }

    public void setBuildDate(String buildDate) {
        this.buildDate.set(buildDate);
    }

    public double getTotalSum() {
        return totalSum.get();
    }

    public DoubleProperty totalSumProperty() {
        return totalSum;
    }

    public void setTotalSum(double totalSum) {
        this.totalSum.set(totalSum);
    }

    public int getOrderState() {
        return orderState.get();
    }

    public IntegerProperty orderStateProperty() {
        return orderState;
    }

    public void setOrderState(int orderState) {
        this.orderState.set(orderState);
    }

    public int getPaymentState() {
        return paymentState.get();
    }

    public IntegerProperty paymentStateProperty() {
        return paymentState;
    }

    public void setPaymentState(int paymentState) {
        this.paymentState.set(paymentState);
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

    @Override
    public String toString() {
        return "Orders{" +
                "orderId=" + orderId +
                ", orderType=" + orderType +
                ", companyName=" + companyName +
                ", customerName=" + customerName +
                ", customerNumber=" + customerNumber +
                ", customerPhone=" + customerPhone +
                ", customerAddress=" + customerAddress +
                ", buildDate=" + buildDate +
                ", endDate=" + endDate +
                ", totalSum=" + totalSum +
                ", orderState=" + orderState +
                ", paymentState=" + paymentState +
                ", stuffNumber=" + stuffNumber +
                '}';
    }
}
