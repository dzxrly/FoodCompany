package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

//登录信息表
public class LoginInfo {
    private IntegerProperty infoId = new SimpleIntegerProperty();
    private IntegerProperty stuffNumber = new SimpleIntegerProperty();
    private StringProperty stuffName = new SimpleStringProperty();
    private StringProperty loginTime = new SimpleStringProperty();
    private IntegerProperty stuffDepartment = new SimpleIntegerProperty();

    public int getInfoId() {
        return infoId.get();
    }

    public IntegerProperty infoIdProperty() {
        return infoId;
    }

    public void setInfoId(int infoId) {
        this.infoId.set(infoId);
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

    public String getStuffName() {
        return stuffName.get();
    }

    public StringProperty stuffNameProperty() {
        return stuffName;
    }

    public void setStuffName(String stuffName) {
        this.stuffName.set(stuffName);
    }

    public String getLoginTime() {
        return loginTime.get();
    }

    public StringProperty loginTimeProperty() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime.set(loginTime);
    }

    public int getStuffDepartment() {
        return stuffDepartment.get();
    }

    public IntegerProperty stuffDepartmentProperty() {
        return stuffDepartment;
    }

    public void setStuffDepartment(int stuffDepartment) {
        this.stuffDepartment.set(stuffDepartment);
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "infoId=" + infoId +
                ", stuffNumber=" + stuffNumber +
                ", stuffName=" + stuffName +
                ", loginTime=" + loginTime +
                ", stuffDepartment=" + stuffDepartment +
                '}';
    }
}
