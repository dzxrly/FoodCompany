package model;

import javafx.beans.property.*;

//生产计划交付记录表（生产车间去成品库）
public class WorkshopToStockRecord {
    private IntegerProperty recordId = new SimpleIntegerProperty();
    private IntegerProperty planId = new SimpleIntegerProperty();
    private IntegerProperty planType = new SimpleIntegerProperty();
    private IntegerProperty orderId = new SimpleIntegerProperty();
    private IntegerProperty deliverStuffNumber = new SimpleIntegerProperty();
    private StringProperty outTime = new SimpleStringProperty();
    private DoubleProperty productCycle = new SimpleDoubleProperty();

    public int getRecordId() {
        return recordId.get();
    }

    public IntegerProperty recordIdProperty() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId.set(recordId);
    }

    public int getPlanId() {
        return planId.get();
    }

    public IntegerProperty planIdProperty() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId.set(planId);
    }

    public int getPlanType() {
        return planType.get();
    }

    public IntegerProperty planTypeProperty() {
        return planType;
    }

    public void setPlanType(int planType) {
        this.planType.set(planType);
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

    public int getDeliverStuffNumber() {
        return deliverStuffNumber.get();
    }

    public IntegerProperty deliverStuffNumberProperty() {
        return deliverStuffNumber;
    }

    public void setDeliverStuffNumber(int deliverStuffNumber) {
        this.deliverStuffNumber.set(deliverStuffNumber);
    }

    public String getOutTime() {
        return outTime.get();
    }

    public StringProperty outTimeProperty() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime.set(outTime);
    }

    public double getProductCycle() {
        return productCycle.get();
    }

    public DoubleProperty productCycleProperty() {
        return productCycle;
    }

    public void setProductCycle(double productCycle) {
        this.productCycle.set(productCycle);
    }

    @Override
    public String toString() {
        return "WorkshopToStockRecord{" +
                "recordId=" + recordId +
                ", planId=" + planId +
                ", planType=" + planType +
                ", orderId=" + orderId +
                ", deliverStuffNumber=" + deliverStuffNumber +
                ", outTime=" + outTime +
                ", productCycle=" + productCycle +
                '}';
    }
}
