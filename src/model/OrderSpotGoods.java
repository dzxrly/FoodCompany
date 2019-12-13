package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

//现货订单表
public class OrderSpotGoods {
    private IntegerProperty spotId= new SimpleIntegerProperty();
    private IntegerProperty orderId = new SimpleIntegerProperty();
    //0表示现货订单 1表示预定订单
    private IntegerProperty orderType = new SimpleIntegerProperty();
    private IntegerProperty stuffNumber = new SimpleIntegerProperty();
    private IntegerProperty customerNumber = new SimpleIntegerProperty();
    private IntegerProperty goodsNumber = new SimpleIntegerProperty();
    private StringProperty goodsName =new SimpleStringProperty();
    private IntegerProperty orderQuantity = new SimpleIntegerProperty();//商品数量
    private StringProperty buildDate = new SimpleStringProperty();

    public int getSpotId() {
        return spotId.get();
    }

    public IntegerProperty spotIdProperty() {
        return spotId;
    }

    public void setSpotId(int spotId) {
        this.spotId.set(spotId);
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

    public int getOrderQuantity() {
        return orderQuantity.get();
    }

    public IntegerProperty orderQuantityProperty() {
        return orderQuantity;
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

    @Override
    public String toString() {
        return "OrderSpotGoods{" +
                "spotId=" + spotId +
                ", orderId=" + orderId +
                ", orderType=" + orderType +
                ", stuffNumber=" + stuffNumber +
                ", customerNumber=" + customerNumber +
                ", goodsNumber=" + goodsNumber +
                ", goodsName=" + goodsName +
                ", orderQuantity=" + orderQuantity +
                ", buildDate=" + buildDate +
                '}';
    }
}
