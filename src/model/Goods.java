package model;

import javafx.beans.property.*;
import javafx.css.SimpleStyleableIntegerProperty;

import java.time.LocalDateTime;

//商品表
public class Goods {
    private IntegerProperty goodsId =new SimpleIntegerProperty();
    private StringProperty goodsName=new SimpleStringProperty();
    private DoubleProperty goodsPrice =new SimpleDoubleProperty();
    private IntegerProperty goodsQualityTime = new SimpleIntegerProperty();
    private DoubleProperty requiredQuantity = new SimpleDoubleProperty();
    //private ObjectProperty<LocalDateTime> = new ObjectProperty<LocalDateTime>

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

    public double getGoodsPrice() {
        return goodsPrice.get();
    }

    public DoubleProperty goodsPriceProperty() {
        return goodsPrice;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice.set(goodsPrice);
    }

    public int getGoodsQualityTime() {
        return goodsQualityTime.get();
    }

    public IntegerProperty goodsQualityTimeProperty() {
        return goodsQualityTime;
    }

    public void setGoodsQualityTime(int goodsQualityTime) {
        this.goodsQualityTime.set(goodsQualityTime);
    }

    public double getRequiredQuantity() {
        return requiredQuantity.get();
    }

    public DoubleProperty requiredQuantityProperty() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(double requiredQuantity) {
        this.requiredQuantity.set(requiredQuantity);
    }

    public void setRequiredQuantity(int requiredQuantity) {
        this.requiredQuantity.set(requiredQuantity);
    }

    @Override
    public String toString() {
        return "Goods{" +
                "goodsId=" + goodsId +
                ", goodsName=" + goodsName +
                ", goodsPrice=" + goodsPrice +
                ", goodsQualityTime=" + goodsQualityTime +
                ", requiredQuantity=" + requiredQuantity +
                '}';
    }
}
