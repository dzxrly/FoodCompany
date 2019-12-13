package model;

import javafx.beans.property.*;

public class CustomerOrder {
    private StringProperty personalName = new SimpleStringProperty(); //姓名.
    private StringProperty companyName = new SimpleStringProperty(); //公司名
    private IntegerProperty number = new SimpleIntegerProperty(); //顾客ID
    private IntegerProperty level = new SimpleIntegerProperty(); //客户星级，用数字表示，分为1~5，5为最高.
    private StringProperty address = new SimpleStringProperty(); //住址
    private StringProperty email = new SimpleStringProperty(); //邮箱.
    private StringProperty phoneNumber = new SimpleStringProperty(); //手机号.
    private IntegerProperty type = new SimpleIntegerProperty();//顾客类型.
    private DoubleProperty accumulatedAmount = new SimpleDoubleProperty();//累计交易额
    private DoubleProperty orderQuantity = new SimpleDoubleProperty();//客户完成的订单数量

    private IntegerProperty orderId = new SimpleIntegerProperty();
    private IntegerProperty orderType = new SimpleIntegerProperty(); //0为现货订单 1为预定订单
    private StringProperty endDate = new SimpleStringProperty();
    private DoubleProperty totalSum= new SimpleDoubleProperty();
    private IntegerProperty orderState =new SimpleIntegerProperty();//订单状态 0为未生产，1为生产中，2为运输中，3为已到达
    private IntegerProperty paymentState= new SimpleIntegerProperty(); //支付状态 0为未付款 1为付了预付款 2为付了全款
    private IntegerProperty stuffNumber = new SimpleIntegerProperty();
//        private CustomerIndexAndStringSwitch customerIndexAndStringSwitch = new CustomerIndexAndStringSwitch();//过滤器类

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public double getAccumulatedAmount() {
        return accumulatedAmount.get();
    }

    public DoubleProperty accumulatedAmountProperty() {
        return accumulatedAmount;
    }

    public void setAccumulatedAmount(double accumulatedAmount) {
        this.accumulatedAmount.set(accumulatedAmount);
    }

    public double getOrderQuantity() {
        return orderQuantity.get();
    }

    public DoubleProperty orderQuantityProperty() {
        return orderQuantity;
    }

    public void setOrderQuantity(double orderQuantity) {
        this.orderQuantity.set(orderQuantity);
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

    public String getEndDate() {
        return endDate.get();
    }

    public StringProperty endDateProperty() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate.set(endDate);
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

    public int getOrderId() {
        return orderId.get();
    }

    public IntegerProperty orderIdProperty() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId.set(orderId);
    }

    public String getPersonalName() {
        return personalName.get();
    }

    public StringProperty personalNameProperty() {
        return personalName;
    }

    public void setPersonalName(String personalName) {
        this.personalName.set(personalName);
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

    public int getNumber() {
        return number.get();
    }

    public IntegerProperty numberProperty() {
        return number;
    }

    public void setNumber(int number) {
        this.number.set(number);
    }

    public int getLevel() {
        return level.get();
    }

    public IntegerProperty levelProperty() {
        return level;
    }

    public void setLevel(int level) {
        this.level.set(level);
    }

    public String getEmail() {
        return email.get();
    }

    public StringProperty emailProperty() {
        return email;
    }

    public void setEmail(String email) {
        this.email.set(email);
    }

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
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

    @Override
    public String toString() {
        return "CustomerOrder{" +
                "orderId=" + orderId +
                ", personalName=" + personalName +
                ", companyName=" + companyName +
                ", number=" + number +
                ", level=" + level +
                ", email=" + email +
                ", phoneNumber=" + phoneNumber +
                ", type=" + type +
                '}';
    }
}
