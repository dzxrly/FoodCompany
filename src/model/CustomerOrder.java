package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CustomerOrder {
    private IntegerProperty orderId = new SimpleIntegerProperty();
    private StringProperty personalName = new SimpleStringProperty(); //姓名.
    private StringProperty companyName = new SimpleStringProperty(); //公司名
    private IntegerProperty number = new SimpleIntegerProperty(); //顾客ID
    private IntegerProperty level = new SimpleIntegerProperty(); //客户星级，用数字表示，分为1~5，5为最高.
    private StringProperty email = new SimpleStringProperty(); //邮箱.
    private StringProperty phoneNumber = new SimpleStringProperty(); //手机号.
    private IntegerProperty type = new SimpleIntegerProperty();//顾客类型.
//        private CustomerIndexAndStringSwitch customerIndexAndStringSwitch = new CustomerIndexAndStringSwitch();//过滤器类

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
