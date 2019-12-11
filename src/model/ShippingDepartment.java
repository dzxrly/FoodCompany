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
    private ObjectProperty<LocalDate> productTime = new SimpleObjectProperty<>();
    private IntegerProperty goodsQualityTime = new SimpleIntegerProperty();
    private IntegerProperty stocks = new SimpleIntegerProperty();

    public int getGoodsId() {
        return goodsId.get();
    }

    public IntegerProperty goodsIdProperty() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId.set(goodsId);
    }

    public LocalDate getProductTime() {
        return productTime.get();
    }

    public ObjectProperty<LocalDate> productTimeProperty() {
        return productTime;
    }

    public void setProductTime(LocalDate productTime) {
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

    public int getStocks() {
        return stocks.get();
    }

    public IntegerProperty stocksProperty() {
        return stocks;
    }

    public void setStocks(int stocks) {
        this.stocks.set(stocks);
    }

    @Override
    public String toString() {
        return "ShippingDepartment{" +
                "goodsId=" + goodsId +
                ", productTime=" + productTime +
                ", goodsQualityTime=" + goodsQualityTime +
                ", stocks=" + stocks +
                '}';
    }
}
