package model;

import javafx.beans.property.*;

//预定订单表
public class OrderBookGoods {
    private IntegerProperty bookId= new SimpleIntegerProperty();
    private IntegerProperty orderId = new SimpleIntegerProperty();
    //0表示现货订单 1表示预定订单
    private IntegerProperty orderType = new SimpleIntegerProperty();
    private IntegerProperty stuffNumber = new SimpleIntegerProperty();
    private IntegerProperty customerNumber = new SimpleIntegerProperty();
    private IntegerProperty goodsNumber = new SimpleIntegerProperty();
    private StringProperty goodsName =new SimpleStringProperty();
    private DoubleProperty orderQuantity = new SimpleDoubleProperty();//商品数量
    private StringProperty buildDate = new SimpleStringProperty();
    private DoubleProperty producedQuantity =new SimpleDoubleProperty();
    private StringProperty goodsUnit =  new SimpleStringProperty();
    private DoubleProperty goodsPrice = new SimpleDoubleProperty();

    public String getGoodsUnit() {
        return goodsUnit.get();
    }

    public StringProperty goodsUnitProperty() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit.set(goodsUnit);
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

    public int getBookId() {
        return bookId.get();
    }

    public IntegerProperty bookIdProperty() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId.set(bookId);
    }

    public int getOrderId() {
        return orderId.get();
    }

    public IntegerProperty orderIdProperty() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId.set(orderId);
    }

    public int getOrderType() {
        return orderType.get();
    }

    public IntegerProperty orderTypeProperty() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType.set(orderType);
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

    public int getCustomerNumber() {
        return customerNumber.get();
    }

    public IntegerProperty customerNumberProperty() {
        return customerNumber;
    }

    public void setCustomerNumber(int customerNumber) {
        this.customerNumber.set(customerNumber);
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

    public String getBuildDate() {
        return buildDate.get();
    }

    public StringProperty buildDateProperty() {
        return buildDate;
    }

    public void setBuildDate(String buildDate) {
        this.buildDate.set(buildDate);
    }

    public double getProducedQuantity() {
        return producedQuantity.get();
    }

    public DoubleProperty producedQuantityProperty() {
        return producedQuantity;
    }

    public void setProducedQuantity(double producedQuantity) {
        this.producedQuantity.set(producedQuantity);
    }

    @Override
    public String toString() {
        return "OrderBookGoods{" +
                "bookId=" + bookId +
                ", orderId=" + orderId +
                ", orderType=" + orderType +
                ", stuffNumber=" + stuffNumber +
                ", customerNumber=" + customerNumber +
                ", goodsNumber=" + goodsNumber +
                ", goodsName=" + goodsName +
                ", orderQuantity=" + orderQuantity +
                ", buildDate=" + buildDate +
                ", producedQuantity=" + producedQuantity +
                ", goodsUnit=" + goodsUnit +
                ", goodsPrice=" + goodsPrice +
                '}';
    }
}
