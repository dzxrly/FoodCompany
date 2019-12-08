package model;

import javafx.beans.property.*;

import java.time.LocalDate;

//顾客
public class Customer {
    //private IntegerProperty id = new SimpleIntegerProperty();//表的编号 自动增长 用于数据库的查询
    private StringProperty personalName = new SimpleStringProperty(); //姓名.
    private StringProperty companyName = new SimpleStringProperty(); //公司名
    private IntegerProperty  number = new SimpleIntegerProperty(); //顾客ID
    private IntegerProperty level = new SimpleIntegerProperty(); //客户星级，用数字表示，分为1~5，5为最高.
    private StringProperty address = new SimpleStringProperty(); //住址
    private StringProperty email = new SimpleStringProperty(); //邮箱.
    private StringProperty phoneNumber = new SimpleStringProperty(); //手机号.
    private IntegerProperty type = new SimpleIntegerProperty();//顾客类型.
    private DoubleProperty accumulatedAmount = new SimpleDoubleProperty();//累计交易额
    private DoubleProperty orderQuantity = new SimpleDoubleProperty();;//客户完成的订单数量

    public Customer() {
    }
/*
    public Customer(String personalName, String companyName, String level, String address, String email, String phoneNumber, String type) {
        setPersonalName(personalName);
        setCompanyName(companyName);
        setLevel(level);
        setAddress(address);
        setEmail(email);
        setPhoneNumber(phoneNumber);
        setType(type);
    } //构造函数 测试用


 */

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

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
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

    @Override
    public String toString() {
        return "Customer{" +
                "personalName=" + personalName +
                ", companyName=" + companyName +
                ", number=" + number +
                ", level=" + level +
                ", address=" + address +
                ", email=" + email +
                ", phoneNumber=" + phoneNumber +
                ", type=" + type +
                ", accumulatedAmount=" + accumulatedAmount +
                ", orderQuantity=" + orderQuantity +
                '}';
    }
}
