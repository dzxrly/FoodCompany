package model;

import javafx.beans.property.*;

public class MaterialToGoods {

    private IntegerProperty id = new SimpleIntegerProperty();//自增主键 不用管
    private IntegerProperty goodsId = new SimpleIntegerProperty();//商品Id
    private IntegerProperty materialId = new SimpleIntegerProperty();//材料Id
    private DoubleProperty perQuantity = new SimpleDoubleProperty();//每一件商品所需的原材料数量
    private StringProperty goodsName = new SimpleStringProperty();
    private StringProperty goodsUnit = new SimpleStringProperty();//商品单位
    private StringProperty materialName = new SimpleStringProperty();
    private DoubleProperty materialStocks = new SimpleDoubleProperty();//原料库存
    private StringProperty materialUnit = new SimpleStringProperty();//原料单位
    private DoubleProperty materialPrice = new SimpleDoubleProperty();

    public double getMaterialPrice() {
        return materialPrice.get();
    }

    public DoubleProperty materialPriceProperty() {
        return materialPrice;
    }

    public void setMaterialPrice(double materialPrice) {
        this.materialPrice.set(materialPrice);
    }

    public String getGoodsUnit() {
        return goodsUnit.get();
    }

    public StringProperty goodsUnitProperty() {
        return goodsUnit;
    }

    public void setGoodsUnit(String goodsUnit) {
        this.goodsUnit.set(goodsUnit);
    }

    public String getMaterialUnit() {
        return materialUnit.get();
    }

    public StringProperty materialUnitProperty() {
        return materialUnit;
    }

    public void setMaterialUnit(String materialUnit) {
        this.materialUnit.set(materialUnit);
    }

    public double getMaterialStocks() {
        return materialStocks.get();
    }

    public DoubleProperty materialStocksProperty() {
        return materialStocks;
    }

    public void setMaterialStocks(double materialStocks) {
        this.materialStocks.set(materialStocks);
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

    public String getMaterialName() {
        return materialName.get();
    }

    public StringProperty materialNameProperty() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName.set(materialName);
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

    public int getGoodsId() {
        return goodsId.get();
    }

    public IntegerProperty goodsIdProperty() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId.set(goodsId);
    }

    public int getMaterialId() {
        return materialId.get();
    }

    public IntegerProperty materialIdProperty() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId.set(materialId);
    }

    public double getPerQuantity() {
        return perQuantity.get();
    }

    public DoubleProperty perQuantityProperty() {
        return perQuantity;
    }

    public void setPerQuantity(double perQuantity) {
        this.perQuantity.set(perQuantity);
    }

    @Override
    public String toString() {
        return "MaterialToGoods{" +
                "id=" + id +
                ", goodsId=" + goodsId +
                ", materialId=" + materialId +
                ", perQuantity=" + perQuantity +
                ", goodsName=" + goodsName +
                ", goodsUnit=" + goodsUnit +
                ", materialName=" + materialName +
                ", materialStocks=" + materialStocks +
                ", materialUnit=" + materialUnit +
                ", materialPrice=" + materialPrice +
                '}';
    }
}
