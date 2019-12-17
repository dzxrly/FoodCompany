package model;

import javafx.beans.property.*;

public class OrderStocks {
    private IntegerProperty goodsId = new SimpleIntegerProperty();
    private StringProperty goodsName = new SimpleStringProperty();
    private DoubleProperty orderQuantity = new SimpleDoubleProperty();//订单数量
    private DoubleProperty goodsPrice = new SimpleDoubleProperty();
    private DoubleProperty stocks = new SimpleDoubleProperty();//库存数量
    private DoubleProperty produceQuantity = new SimpleDoubleProperty();//应生产数量

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

    public double getOrderQuantity() {
        return orderQuantity.get();
    }

    public DoubleProperty orderQuantityProperty() {
        return orderQuantity;
    }

    public void setOrderQuantity(double orderQuantity) {
        this.orderQuantity.set(orderQuantity);
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

    public double getProduceQuantity() {
        return produceQuantity.get();
    }

    public DoubleProperty produceQuantityProperty() {
        return produceQuantity;
    }

    public void setProduceQuantity(double produceQuantity) {
        this.produceQuantity.set(produceQuantity);
    }

    @Override
    public String toString() {
        return "OrderStocks{" +
                "goodsId=" + goodsId +
                ", goodsName=" + goodsName +
                ", orderQuantity=" + orderQuantity +
                ", goodsPrice=" + goodsPrice +
                ", stocks=" + stocks +
                ", produceQuantity=" + produceQuantity +
                '}';
    }
}
