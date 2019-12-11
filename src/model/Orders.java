package model;

import javafx.beans.property.*;
import org.hibernate.internal.util.StringHelper;

public class Orders {
    private IntegerProperty orderId=new SimpleIntegerProperty();
    private IntegerProperty orderType = new SimpleIntegerProperty();//0表示现货订单 1表示预售订单
    private  IntegerProperty stuffNumber=new SimpleIntegerProperty();
    private StringProperty customerName =new SimpleStringProperty();
    private  StringProperty companyName=new SimpleStringProperty();
    private StringProperty customerNumber=new SimpleStringProperty();
    private StringProperty customerPhone=new SimpleStringProperty();
    private StringProperty customerAddress=new SimpleStringProperty();
    private StringProperty buildDate=new SimpleStringProperty();
    private DoubleProperty totalSum=new SimpleDoubleProperty();
    private IntegerProperty orderState=new SimpleIntegerProperty();//0生产中、1运输中、2已到达
    private IntegerProperty paymentState=new SimpleIntegerProperty();//0表示预付款，1表示付全款

    public int getOrderId() {
        return orderId.get();
    }

    public IntegerProperty orderIdProperty() {
        return orderId;
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

    public int getStuffNumber() {
        return stuffNumber.get();
    }

    public IntegerProperty stuffNumberProperty() {
        return stuffNumber;
    }

    public void setStuffNumber(int stuffNumber) {
        this.stuffNumber.set(stuffNumber);
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

    public String getCustomerNumber() {
        return customerNumber.get();
    }

    public StringProperty customerNumberProperty() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
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

    @Override
    public String toString() {
        return "Orders{" +
                "orderId=" + orderId +
                ", orderType=" + orderType +
                ", stuffNumber=" + stuffNumber +
                ", customerName=" + customerName +
                ", companyName=" + companyName +
                ", customerNumber=" + customerNumber +
                ", customerPhone=" + customerPhone +
                ", customerAddress=" + customerAddress +
                ", buildDate=" + buildDate +
                ", totalSum=" + totalSum +
                ", orderState=" + orderState +
                ", paymentState=" + paymentState +
                '}';
    }
}
