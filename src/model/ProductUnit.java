package model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

//物品生产效率单位表
public class ProductUnit {
    private IntegerProperty unitId = new SimpleIntegerProperty();
    private IntegerProperty goodsId =new SimpleIntegerProperty();
    private DoubleProperty productQuantityPerTime =new SimpleDoubleProperty();

    public int getUnitId() {
        return unitId.get();
    }

    public IntegerProperty unitIdProperty() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId.set(unitId);
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

    public double getProductQuantityPerTime() {
        return productQuantityPerTime.get();
    }

    public DoubleProperty productQuantityPerTimeProperty() {
        return productQuantityPerTime;
    }

    public void setProductQuantityPerTime(double productQuantityPerTime) {
        this.productQuantityPerTime.set(productQuantityPerTime);
    }

    @Override
    public String toString() {
        return "ProductUnit{" +
                "unitId=" + unitId +
                ", goodsId=" + goodsId +
                ", productQuantityPerTime=" + productQuantityPerTime +
                '}';
    }
}
