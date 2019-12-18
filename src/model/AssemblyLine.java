package model;

import javafx.beans.property.*;

//流水线表
public class AssemblyLine {
    private IntegerProperty lineId = new SimpleIntegerProperty();

    private IntegerProperty lineNumber = new SimpleIntegerProperty(); //流水线编号
    private IntegerProperty planId = new SimpleIntegerProperty();

    private IntegerProperty lineState = new SimpleIntegerProperty(); //0为工作 1为完成 2为空闲
    private StringProperty startWorkTime = new SimpleStringProperty();
    private StringProperty endWorkTime = new SimpleStringProperty();

    private IntegerProperty priority = new SimpleIntegerProperty(); //0为季度 1为预定
    private IntegerProperty stuffNumber = new SimpleIntegerProperty();

    public int getLineId() {
        return lineId.get();
    }

    public IntegerProperty lineIdProperty() {
        return lineId;
    }

    public void setLineId(int lineId) {
        this.lineId.set(lineId);
    }

    public int getLineNumber() {
        return lineNumber.get();
    }

    public IntegerProperty lineNumberProperty() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber.set(lineNumber);
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

    public int getLineState() {
        return lineState.get();
    }

    public IntegerProperty lineStateProperty() {
        return lineState;
    }

    public void setLineState(int lineState) {
        this.lineState.set(lineState);
    }

    public String getStartWorkTime() {
        return startWorkTime.get();
    }

    public StringProperty startWorkTimeProperty() {
        return startWorkTime;
    }

    public void setStartWorkTime(String startWorkTime) {
        this.startWorkTime.set(startWorkTime);
    }

    public String getEndWorkTime() {
        return endWorkTime.get();
    }

    public StringProperty endWorkTimeProperty() {
        return endWorkTime;
    }

    public void setEndWorkTime(String endWorkTime) {
        this.endWorkTime.set(endWorkTime);
    }

    public int getPriority() {
        return priority.get();
    }

    public IntegerProperty priorityProperty() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority.set(priority);
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

    @Override
    public String toString() {
        return "AssemblyLine{" +
                "lineId=" + lineId +
                ", lineNumber=" + lineNumber +
                ", planId=" + planId +
                ", lineState=" + lineState +
                ", startWorkTime=" + startWorkTime +
                ", endWorkTime=" + endWorkTime +
                ", priority=" + priority +
                ", stuffNumber=" + stuffNumber +
                '}';
    }
}
