package model;

import javafx.beans.property.*;

public class OrderStocks {
    private IntegerProperty goodsId = new SimpleIntegerProperty();
    private StringProperty goodsName = new SimpleStringProperty();
    private IntegerProperty orderQuantity = new SimpleIntegerProperty();//订单数量
    private DoubleProperty goodsPrice = new SimpleDoubleProperty();
    private IntegerProperty stocks = new SimpleIntegerProperty();//库存数量

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

    public double getOrderQuantity() {
        return orderQuantity.get();
    }

    public IntegerProperty orderQuantityProperty() {
        return orderQuantity;
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
        return "OrderStocks{" +
                "goodsId=" + goodsId +
                ", goodsName=" + goodsName +
                ", orderQuantity=" + orderQuantity +
                ", goodsPrice=" + goodsPrice +
                ", stocks=" + stocks +
                '}';
    }
}
