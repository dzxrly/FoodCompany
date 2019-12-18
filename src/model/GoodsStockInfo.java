package model;

import javafx.beans.property.*;

public class GoodsStockInfo {
    private IntegerProperty goodsId = new SimpleIntegerProperty();
    private StringProperty goodsName = new SimpleStringProperty();
    private IntegerProperty goodsQualityTime = new SimpleIntegerProperty();
    private DoubleProperty stocks = new SimpleDoubleProperty();
    private DoubleProperty requiredNumber = new SimpleDoubleProperty();
    private StringProperty goodUnit = new SimpleStringProperty();

    public GoodsStockInfo(int goodsId, String goodsName, int goodsQualityTime, Double stocks, Double requiredNumber, String goodUnit) {
        this.goodsId.set(goodsId);
        this.goodsName.set(goodsName);
        this.goodsQualityTime.set(goodsQualityTime);
        this.stocks.set(stocks);
        this.requiredNumber.set(requiredNumber);
        this.goodUnit.set(goodUnit);
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

    public int getGoodsQualityTime() {
        return goodsQualityTime.get();
    }

    public IntegerProperty goodsQualityTimeProperty() {
        return goodsQualityTime;
    }

    public void setGoodsQualityTime(int goodsQualityTime) {
        this.goodsQualityTime.set(goodsQualityTime);
    }

    public double getStocks() {
        return stocks.get();
    }

    public DoubleProperty stocksProperty() {
        return stocks;
    }

    public void setStocks(double stocks) {
        this.stocks.set(stocks);
    }

    public double getRequiredNumber() {
        return requiredNumber.get();
    }

    public DoubleProperty requiredNumberProperty() {
        return requiredNumber;
    }

    public void setRequiredNumber(double requiredNumber) {
        this.requiredNumber.set(requiredNumber);
    }

    public String getGoodUnit() {
        return goodUnit.get();
    }

    public StringProperty goodUnitProperty() {
        return goodUnit;
    }

    public void setGoodUnit(String goodUnit) {
        this.goodUnit.set(goodUnit);
    }
}
