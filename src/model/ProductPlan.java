package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

//生产计划表
public class ProductPlan {
    private IntegerProperty planId = new SimpleIntegerProperty();
    private IntegerProperty stuffNumber = new SimpleIntegerProperty();
    private IntegerProperty goodsId = new SimpleIntegerProperty();
    private IntegerProperty goodsNumber = new SimpleIntegerProperty();
    private IntegerProperty goodsQuantity = new SimpleIntegerProperty();
    //0为季度 1为预定
    private IntegerProperty planType = new SimpleIntegerProperty();
    private IntegerProperty productionCycle = new SimpleIntegerProperty();

    public int getPlanId() {
        return planId.get();
    }

    public IntegerProperty planIdProperty() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId.set(planId);
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

    public int getGoodsId() {
        return goodsId.get();
    }

    public IntegerProperty goodsIdProperty() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId.set(goodsId);
    }

    public int getGoodsNumber() {
        return goodsNumber.get();
    }

    public IntegerProperty goodsNumberProperty() {
        return goodsNumber;
    }

    public void setGoodsNumber(int goodsNumber) {
        this.goodsNumber.set(goodsNumber);
    }

    public int getGoodsQuantity() {
        return goodsQuantity.get();
    }

    public IntegerProperty goodsQuantityProperty() {
        return goodsQuantity;
    }

    public void setGoodsQuantity(int goodsQuantity) {
        this.goodsQuantity.set(goodsQuantity);
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

    public int getProductionCycle() {
        return productionCycle.get();
    }

    public IntegerProperty productionCycleProperty() {
        return productionCycle;
    }

    public void setProductionCycle(int productionCycle) {
        this.productionCycle.set(productionCycle);
    }

    @Override
    public String toString() {
        return "productPlan{" +
                "planId=" + planId +
                ", stuffNumber=" + stuffNumber +
                ", goodsId=" + goodsId +
                ", goodsNumber=" + goodsNumber +
                ", goodsQuantity=" + goodsQuantity +
                ", planType=" + planType +
                ", productionCycle=" + productionCycle +
                '}';
    }
}
