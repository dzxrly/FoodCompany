package model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

//顾客
public class Customer {
    private StringProperty personalName; //姓名
    private StringProperty companyName; //公司名
    private StringProperty number; //顾客ID
    private StringProperty level; //客户星级，用数字表示，分为1~5，5为最高
    private StringProperty address; //住址
    private StringProperty email; //邮箱
    private StringProperty phoneNumber; //手机号
    private StringProperty type;//顾客类型
    private ObjectProperty<LocalDate> birthday; //生日
    private StringProperty accumulatedAmount;//累计交易额
    private StringProperty orderQuantity;//客户完成的订单数量

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

    public String getNumber() {
        return number.get();
    }

    public StringProperty numberProperty() {
        return number;
    }

    public void setNumber(String number) {
        this.number.set(number);
    }

    public String getLevel() {
        return level.get();
    }

    public StringProperty levelProperty() {
        return level;
    }

    public void setLevel(String level) {
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

    public String getType() {
        return type.get();
    }

    public StringProperty typeProperty() {
        return type;
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public LocalDate getBirthday() {
        return birthday.get();
    }

    public ObjectProperty<LocalDate> birthdayProperty() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday.set(birthday);
    }

    public String getAccumulatedAmount() {
        return accumulatedAmount.get();
    }

    public StringProperty accumulatedAmountProperty() {
        return accumulatedAmount;
    }

    public void setAccumulatedAmount(String accumulatedAmount) {
        this.accumulatedAmount.set(accumulatedAmount);
    }

    public String getOrderQuantity() {
        return orderQuantity.get();
    }

    public StringProperty orderQuantityProperty() {
        return orderQuantity;
    }

    public void setOrderQuantity(String orderQuantity) {
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
                ", birthday=" + birthday +
                ", accumulatedAmount=" + accumulatedAmount +
                ", orderQuantity=" + orderQuantity +
                '}';
    }
}
