package model;

import javafx.beans.property.*;

//生产计划详细表
public class ProductDetailPlan {
    private IntegerProperty pdpId = new SimpleIntegerProperty();
    private IntegerProperty planId = new SimpleIntegerProperty();
    private IntegerProperty planType = new SimpleIntegerProperty();
    private IntegerProperty goodsId = new SimpleIntegerProperty();
    private StringProperty goodsName = new SimpleStringProperty();
    private DoubleProperty quantity= new SimpleDoubleProperty();
    private IntegerProperty stuffNumber = new SimpleIntegerProperty();
    private StringProperty goodsUnit =new SimpleStringProperty();

    public String getGoodsUnit() {
        return goodsUnit.get();
    }

    public StringProperty goodsUnitProperty() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit.set(goodsUnit);
    }

    public int getPdpId() {
        return pdpId.get();
    }

    public IntegerProperty pdpIdProperty() {
        return pdpId;
    }

    public void setPdpId(int pdpId) {
        this.pdpId.set(pdpId);
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

    public int getGoodsId() {
        return goodsId.get();
    }

    public IntegerProperty goodsIdProperty() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId.set(goodsId);
    }

    public String getGoodsName() {
        return goodsName.get();
    }

    public StringProperty goodsNameProperty() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName.set(goodsName);
    }

    public double getQuantity() {
        return quantity.get();
    }

    public DoubleProperty quantityProperty() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity.set(quantity);
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
        return "ProductDetailPlan{" +
                "pdpId=" + pdpId +
                ", planId=" + planId +
                ", planType=" + planType +
                ", goodsId=" + goodsId +
                ", goodsName=" + goodsName +
                ", quantity=" + quantity +
                ", stuffNumber=" + stuffNumber +
                ", endTime=" +
                ", goodsUnit=" + goodsUnit +
                '}';
    }
}
