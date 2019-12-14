package model;

import javafx.beans.property.*;


public class ProductionForm {
    private IntegerProperty productionId =new SimpleIntegerProperty();
    private IntegerProperty orderId = new SimpleIntegerProperty();
    private StringProperty buildTime = new SimpleStringProperty();//生产表单创建时间
    private StringProperty endTime = new SimpleStringProperty();//生产表单的结束时间
    private IntegerProperty stuffNumber = new SimpleIntegerProperty();
    private IntegerProperty planState = new SimpleIntegerProperty();//表示是否已经生成生产表单 0表示未加入 1表示加入
    private IntegerProperty productionState =new SimpleIntegerProperty();//表示是否已加入生产计划 0表示未加入 1表示加入



    public int getProductionId() {
        return productionId.get();
    }

    public IntegerProperty productionIdProperty() {
        return productionId;
    }

    public void setProductionId(int productionId) {
        this.productionId.set(productionId);
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

    public String getBuildTime() {
        return buildTime.get();
    }

    public StringProperty buildTimeProperty() {
        return buildTime;
    }

    public void setBuildTime(String buildTime) {
        this.buildTime.set(buildTime);
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

    public int getStuffNumber() {
        return stuffNumber.get();
    }

    public IntegerProperty stuffNumberProperty() {
        return stuffNumber;
    }

    public void setStuffNumber(int stuffNumber) {
        this.stuffNumber.set(stuffNumber);
    }

    public int getPlanState() {
        return planState.get();
    }

    public IntegerProperty planStateProperty() {
        return planState;
    }

    public void setPlanState(int planState) {
        this.planState.set(planState);
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

    @Override
    public String toString() {
        return "ProductionForm{" +
                "productionId=" + productionId +
                ", orderId=" + orderId +
                ", buildTime=" + buildTime +
                ", endTime=" + endTime +
                ", useTime=" +
                ", stuffNumber=" + stuffNumber +
                ", planState=" + planState +
                ", productionState=" + productionState +
                '}';
    }
}
