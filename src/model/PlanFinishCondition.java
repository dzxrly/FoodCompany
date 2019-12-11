package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

//计划完成情况表
public class PlanFinishCondition {
    private IntegerProperty conditionId = new SimpleIntegerProperty();
    private IntegerProperty planId = new SimpleIntegerProperty();
    private DoubleProperty  productQuantity= new SimpleDoubleProperty();
    private IntegerProperty finishState = new SimpleIntegerProperty();

    public int getConditionId() {
        return conditionId.get();
    }

    public IntegerProperty conditionIdProperty() {
        return conditionId;
    }

    public void setConditionId(int conditionId) {
        this.conditionId.set(conditionId);
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

    public double getProductQuantity() {
        return productQuantity.get();
    }

    public DoubleProperty productQuantityProperty() {
        return productQuantity;
    }

    public void setProductQuantity(double productQuantity) {
        this.productQuantity.set(productQuantity);
    }

    public int getFinishState() {
        return finishState.get();
    }

    public IntegerProperty finishStateProperty() {
        return finishState;
    }

    public void setFinishState(int finishState) {
        this.finishState.set(finishState);
    }

    @Override
    public String toString() {
        return "PlanFinishCondition{" +
                "conditionId=" + conditionId +
                ", planId=" + planId +
                ", productQuantity=" + productQuantity +
                ", finishState=" + finishState +
                '}';
    }
}
