package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

//部门表
public class Department {
    private IntegerProperty departmentId = new SimpleIntegerProperty();//11 销售部
    private StringProperty departmentName= new SimpleStringProperty();
    private StringProperty departmentManager = new SimpleStringProperty();
    private IntegerProperty departmentSum= new SimpleIntegerProperty();
    private StringProperty departmentInfo= new SimpleStringProperty();

    public int getDepartmentId() {
        return departmentId.get();
    }

    public IntegerProperty departmentIdProperty() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId.set(departmentId);
    }

    public String getDepartmentName() {
        return departmentName.get();
    }

    public StringProperty departmentNameProperty() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName.set(departmentName);
    }

    public String getDepartmentManager() {
        return departmentManager.get();
    }

    public StringProperty departmentManagerProperty() {
        return departmentManager;
    }

    public void setDepartmentManager(String departmentManager) {
        this.departmentManager.set(departmentManager);
    }

    public int getDepartmentSum() {
        return departmentSum.get();
    }

    public IntegerProperty departmentSumProperty() {
        return departmentSum;
    }

    public void setDepartmentSum(int departmentSum) {
        this.departmentSum.set(departmentSum);
    }

    public String getDepartmentInfo() {
        return departmentInfo.get();
    }

    public StringProperty departmentInfoProperty() {
        return departmentInfo;
    }

    public void setDepartmentInfo(String departmentInfo) {
        this.departmentInfo.set(departmentInfo);
    }

    @Override
    public String toString() {
        return "Department{" +
                "departmentId=" + departmentId +
                ", departmentName=" + departmentName +
                ", departmentManager=" + departmentManager +
                ", departmentSum=" + departmentSum +
                ", departmentInfo=" + departmentInfo +
                '}';
    }
}
