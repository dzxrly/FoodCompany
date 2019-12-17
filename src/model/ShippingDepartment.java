package model;

import javafx.beans.InvalidationListener;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.time.LocalDate;
import java.time.LocalDateTime;

//成品库表
public class ShippingDepartment {
    private IntegerProperty goodsId = new SimpleIntegerProperty();
    private StringProperty productTime = new SimpleStringProperty();
    private IntegerProperty goodsQualityTime = new SimpleIntegerProperty();
    private DoubleProperty stocks = new SimpleDoubleProperty();
    private StringProperty storeTime = new SimpleStringProperty();
    private StringProperty goodsName = new SimpleStringProperty();
    public int getGoodsId() {
        return goodsId.get();
    }

    public IntegerProperty goodsIdProperty() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId.set(goodsId);
    }

    public String getProductTime() {
        return productTime.get();
    }

    public StringProperty productTimeProperty() {
        return productTime;
    }

    public void setProductTime(String productTime) {
        this.productTime.set(productTime);
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

    public void setStocks(int stocks) {
        this.stocks.set(stocks);
    }

    public String getStoreTime() {
        return storeTime.get();
    }

    public StringProperty storeTimeProperty() {
        return storeTime;
    }

    public void setStoreTime(String storeTime) {
        this.storeTime.set(storeTime);
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

    @Override
    public String toString() {
        return "ShippingDepartment{" +
                "goodsId=" + goodsId +
                ", productTime=" + productTime +
                ", goodsQualityTime=" + goodsQualityTime +
                ", stocks=" + stocks +
                ", storeTime=" + storeTime +
                ", goodsName=" + goodsName +
                '}';
    }
}
