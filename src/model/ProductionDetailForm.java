package model;

import javafx.beans.property.*;

import javax.persistence.criteria.CriteriaBuilder;

public class ProductionDetailForm {

    private IntegerProperty id =new SimpleIntegerProperty();//细节表编号
    private IntegerProperty productionId=new SimpleIntegerProperty();//生产表单主表编号
    private IntegerProperty orderId = new SimpleIntegerProperty();//预定订单编号
    private IntegerProperty goodsId =new SimpleIntegerProperty();//商品编号
    private StringProperty goodsName = new SimpleStringProperty();//商品名称
    private DoubleProperty productionQuantity = new SimpleDoubleProperty();//预定订单数量-库存的数量 如果订单数量小于库存数量则为0
    private DoubleProperty stocks = new SimpleDoubleProperty();//库存数量
    private StringProperty goodsUnit = new SimpleStringProperty();

    public String getGoodsUnit() {
        return goodsUnit.get();
    }

    public StringProperty goodsUnitProperty() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit.set(goodsUnit);
    }

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getProductionId() {
        return productionId.get();
    }

    public IntegerProperty productionIdProperty() {
        return productionId;
    }

    public void setProductionId(int productionId) {
        this.productionId.set(productionId);
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

    public double getProductionQuantity() {
        return productionQuantity.get();
    }

    public DoubleProperty productionQuantityProperty() {
        return productionQuantity;
    }

    public void setProductionQuantity(double productionQuantity) {
        this.productionQuantity.set(productionQuantity);
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

    @Override
    public String toString() {
        return "ProductionDetailForm{" +
                "id=" + id +
                ", productionId=" + productionId +
                ", orderId=" + orderId +
                ", goodsId=" + goodsId +
                ", goodsName=" + goodsName +
                ", productionQuantity=" + productionQuantity +
                ", stocks=" + stocks +
                ", goodsUnit=" + goodsUnit +
                '}';
    }
}
