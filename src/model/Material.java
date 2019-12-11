package model;

import javafx.beans.property.*;

//原料表
public class Material {
    private IntegerProperty materialId = new SimpleIntegerProperty();
    private StringProperty materialName = new SimpleStringProperty();
    private DoubleProperty materialPrice = new SimpleDoubleProperty();
    private  IntegerProperty stuffNumber = new SimpleIntegerProperty();
    private IntegerProperty materialQualityTime = new SimpleIntegerProperty();
    //0为添加剂，1为非添加剂
    private IntegerProperty materialType = new SimpleIntegerProperty();
    private DoubleProperty stock = new SimpleDoubleProperty();
    private StringProperty stockTime = new SimpleStringProperty();

    public int getMaterialId() {
        return materialId.get();
    }

    public IntegerProperty materialIdProperty() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId.set(materialId);
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

    public double getMaterialPrice() {
        return materialPrice.get();
    }

    public DoubleProperty materialPriceProperty() {
        return materialPrice;
    }

    public void setMaterialPrice(double materialPrice) {
        this.materialPrice.set(materialPrice);
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

    public int getMaterialQualityTime() {
        return materialQualityTime.get();
    }

    public IntegerProperty materialQualityTimeProperty() {
        return materialQualityTime;
    }

    public void setMaterialQualityTime(int materialQualityTime) {
        this.materialQualityTime.set(materialQualityTime);
    }

    public int getMaterialType() {
        return materialType.get();
    }

    public IntegerProperty materialTypeProperty() {
        return materialType;
    }

    public void setMaterialType(int materialType) {
        this.materialType.set(materialType);
    }

    public double getStock() {
        return stock.get();
    }

    public DoubleProperty stockProperty() {
        return stock;
    }

    public void setStock(double stock) {
        this.stock.set(stock);
    }

    public String getStockTime() {
        return stockTime.get();
    }

    public StringProperty stockTimeProperty() {
        return stockTime;
    }

    public void setStockTime(String stockTime) {
        this.stockTime.set(stockTime);
    }

    @Override
    public String toString() {
        return "Material{" +
                "materialId=" + materialId +
                ", materialName=" + materialName +
                ", materialPrice=" + materialPrice +
                ", stuffNumber=" + stuffNumber +
                ", materialQualityTime=" + materialQualityTime +
                ", materialType=" + materialType +
                ", stock=" + stock +
                ", stockTime=" + stockTime +
                '}';
    }
}
