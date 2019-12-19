package model;

import javafx.beans.property.*;

//生产计划表
public class ProductPlan {

    private IntegerProperty planId = new SimpleIntegerProperty();
    private IntegerProperty productionId = new SimpleIntegerProperty();
    private IntegerProperty orderId = new SimpleIntegerProperty();
    private IntegerProperty stuffNumber = new SimpleIntegerProperty();
    private StringProperty startTime = new SimpleStringProperty();
    private StringProperty endTime = new SimpleStringProperty();
    private IntegerProperty planType = new SimpleIntegerProperty(); //0为季度 1为预定
    private DoubleProperty productionCycle = new SimpleDoubleProperty();
    private IntegerProperty productionState = new SimpleIntegerProperty();//表示是否投入生产 0表示未投入生产 1表示投入生产 2表示正在生产 3表示完成生产 4表示运输中 5表示已入库
    private StringProperty buildTime = new SimpleStringProperty();

    public int getOrderId() {
        return orderId.get();
    }

    public IntegerProperty orderIdProperty() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId.set(orderId);
    }

    public int getProductionState() {
        return productionState.get();
    }

    public IntegerProperty productionStateProperty() {
        return productionState;
    }

    public void setProductionState(int productionState) {
        this.productionState.set(productionState);
    }

    public int getStuffNumber() {
        return stuffNumber.get();
    }

    public double getProductionCycle() {
        return productionCycle.get();
    }

    public DoubleProperty productionCycleProperty() {
        return productionCycle;
    }

    public void setProductionCycle(double productionCycle) {
        this.productionCycle.set(productionCycle);
    }

    public int getProductionId() {
        return productionId.get();
    }

    public IntegerProperty productionIdProperty() {
        return productionId;
    }

    public void setProductionId(int productionId) {
        this.productionId.set(productionId);
    }

    public String getStartTime() {
        return startTime.get();
    }

    public StringProperty startTimeProperty() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime.set(startTime);
    }

    public String getEndTime() {
        return endTime.get();
    }

    public StringProperty endTimeProperty() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime.set(endTime);
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

    public IntegerProperty stuffNumberProperty() {
        return stuffNumber;
    }

    public void setStuffNumber(int stuffNumber) {
        this.stuffNumber.set(stuffNumber);
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

    public void setProductionCycle(int productionCycle) {
        this.productionCycle.set(productionCycle);
    }

    public String getBuildTime() {
        return buildTime.get();
    }

    public StringProperty buildTimeProperty() {
        return buildTime;
    }

    public void setBuildTime(String buildTime) {
        this.buildTime.set(buildTime);
    }

    @Override
    public String toString() {
        return "ProductPlan{" +
                "planId=" + planId +
                ", productionId=" + productionId +
                ", orderId=" + orderId +
                ", stuffNumber=" + stuffNumber +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", planType=" + planType +
                ", productionCycle=" + productionCycle +
                ", productionState=" + productionState +
                ", buildTime=" + buildTime +
                '}';
    }
}
