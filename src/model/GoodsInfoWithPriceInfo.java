package model;

import javafx.beans.property.*;

public class GoodsInfoWithPriceInfo {
    private IntegerProperty goodsNumber = new SimpleIntegerProperty();
    private StringProperty goodsName = new SimpleStringProperty();
    private DoubleProperty orderQuantity = new SimpleDoubleProperty();//商品数量
    private DoubleProperty goodsPrice = new SimpleDoubleProperty();
    private DoubleProperty goodsTotalPrice = new SimpleDoubleProperty();

    public GoodsInfoWithPriceInfo(int goodsNumber, String goodsName, Double payNumber, Double goodsPrice) {
        this.goodsNumber.set(goodsNumber);
        this.goodsName.set(goodsName);
        this.goodsPrice.set(goodsPrice);
        this.orderQuantity.set(payNumber);
        this.goodsTotalPrice.set(payNumber * goodsPrice);
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

    public String getGoodsName() {
        return goodsName.get();
    }

    public StringProperty goodsNameProperty() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName.set(goodsName);
    }

    public double getOrderQuantity() {
        return orderQuantity.get();
    }

    public DoubleProperty orderQuantityProperty() {
        return orderQuantity;
    }

    public void setOrderQuantity(double orderQuantity) {
        this.orderQuantity.set(orderQuantity);
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity.set(orderQuantity);
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

    public double getGoodsTotalPrice() {
        return goodsTotalPrice.get();
    }

    public DoubleProperty goodsTotalPriceProperty() {
        return goodsTotalPrice;
    }

    public void setGoodsTotalPrice(double goodsTotalPrice) {
        this.goodsTotalPrice.set(goodsTotalPrice);
    }
}
