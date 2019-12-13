package model;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class GoodsInfo {
    private SimpleIntegerProperty goodsId;
    private SimpleStringProperty goodsName;
    private SimpleDoubleProperty goodsPrice;
    private SimpleIntegerProperty goodsQualityTime;
    private SimpleIntegerProperty stocks;//库存
    private SimpleDoubleProperty payNumber;//购买数量

    public GoodsInfo(int goodsId, String goodsName, Double goodsPrice, int goodsQualityTime, int stocks, Double payNumber) {
        this.goodsId = new SimpleIntegerProperty(goodsId);
        this.goodsName = new SimpleStringProperty(goodsName);
        this.goodsPrice = new SimpleDoubleProperty(goodsPrice);
        this.goodsQualityTime = new SimpleIntegerProperty(goodsQualityTime);
        this.stocks = new SimpleIntegerProperty(stocks);
        this.payNumber = new SimpleDoubleProperty(payNumber);
    }

    public double getPayNumber() {
        return payNumber.get();
    }

    public SimpleDoubleProperty payNumberProperty() {
        return payNumber;
    }

    public void setPayNumber(double payNumber) {
        this.payNumber.set(payNumber);
    }

    public int getGoodsId() {
        return goodsId.get();
    }

    public SimpleIntegerProperty goodsIdProperty() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId.set(goodsId);
    }

    public String getGoodsName() {
        return goodsName.get();
    }

    public SimpleStringProperty goodsNameProperty() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName.set(goodsName);
    }

    public double getGoodsPrice() {
        return goodsPrice.get();
    }

    public SimpleDoubleProperty goodsPriceProperty() {
        return goodsPrice;
    }

    public void setGoodsPrice(double goodsPrice) {
        this.goodsPrice.set(goodsPrice);
    }

    public int getGoodsQualityTime() {
        return goodsQualityTime.get();
    }

    public SimpleIntegerProperty goodsQualityTimeProperty() {
        return goodsQualityTime;
    }

    public void setGoodsQualityTime(int goodsQualityTime) {
        this.goodsQualityTime.set(goodsQualityTime);
    }

    public int getStocks() {
        return stocks.get();
    }

    public SimpleIntegerProperty stocksProperty() {
        return stocks;
    }

    public void setStocks(int stocks) {
        this.stocks.set(stocks);
    }
}
