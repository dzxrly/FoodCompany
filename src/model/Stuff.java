package model;

import javafx.beans.property.*;
import sun.java2d.pipe.SpanShapeRenderer;

import java.time.LocalDate;

//员工信息
public class Stuff {
    private StringProperty personalName = new SimpleStringProperty(); //姓名.
    private IntegerProperty number =  new SimpleIntegerProperty(); //员工ID
    private StringProperty password = new SimpleStringProperty(); //登陆密码
    private IntegerProperty level = new SimpleIntegerProperty(); //权限等级，用数字表示，0为最高
    private StringProperty personalID = new SimpleStringProperty(); //身份证
    private StringProperty address = new SimpleStringProperty(); //住址
    private StringProperty phoneNumber =new SimpleStringProperty(); //手机号
    private StringProperty email = new SimpleStringProperty(); //邮箱.
    private IntegerProperty type =new SimpleIntegerProperty();//员工类型

    public String getPersonalName() {
        return personalName.get();
    }

    public StringProperty personalNameProperty() {
        return personalName;
    }

    public void setPersonalName(String personalName) {
        this.personalName.set(personalName);
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

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
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

    public String getPersonalID() {
        return personalID.get();
    }

    public StringProperty personalIDProperty() {
        return personalID;
    }

    public void setPersonalID(String personalID) {
        this.personalID.set(personalID);
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

    public String getPhoneNumber() {
        return phoneNumber.get();
    }

    public StringProperty phoneNumberProperty() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber.set(phoneNumber);
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
        return "Stuff{" +
                "personalName=" + personalName +
                ", number=" + number +
                ", password=" + password +
                ", level=" + level +
                ", personalID=" + personalID +
                ", address=" + address +
                ", phoneNumber=" + phoneNumber +
                ", email=" + email +
                ", type=" + type +
                '}';
    }
}
