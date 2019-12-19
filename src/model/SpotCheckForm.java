package model;

import javafx.beans.property.*;

public class SpotCheckForm {
    private  IntegerProperty id =new SimpleIntegerProperty();
    private IntegerProperty planId =new SimpleIntegerProperty();
    private  StringProperty goodsName =new SimpleStringProperty();
    private DoubleProperty sumQuantity =new SimpleDoubleProperty();//总数
    private  StringProperty goodsUnit =new SimpleStringProperty();//单位
    private DoubleProperty checkQuantity=new SimpleDoubleProperty();//检查数量
    private DoubleProperty unqualifiedQuantity =new SimpleDoubleProperty();//不合格数量
    private DoubleProperty passRate =new SimpleDoubleProperty();//通过率
    private IntegerProperty isQualified = new SimpleIntegerProperty();//是否通过 0表示未通过 1表示通过、
    private  IntegerProperty stuffNumber= new SimpleIntegerProperty();
    private StringProperty date = new SimpleStringProperty();

    public String getGoodsName() {
        return goodsName.get();
    }

    public StringProperty goodsNameProperty() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName.set(goodsName);
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

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getPlanId() {
        return planId.get();
    }

    public IntegerProperty planIdProperty() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId.set(planId);
    }

    public double getSumQuantity() {
        return sumQuantity.get();
    }

    public DoubleProperty sumQuantityProperty() {
        return sumQuantity;
    }

    public void setSumQuantity(double sumQuantity) {
        this.sumQuantity.set(sumQuantity);
    }

    public double getCheckQuantity() {
        return checkQuantity.get();
    }

    public DoubleProperty checkQuantityProperty() {
        return checkQuantity;
    }

    public void setCheckQuantity(double checkQuantity) {
        this.checkQuantity.set(checkQuantity);
    }

    public double getUnqualifiedQuantity() {
        return unqualifiedQuantity.get();
    }

    public DoubleProperty unqualifiedQuantityProperty() {
        return unqualifiedQuantity;
    }

    public void setUnqualifiedQuantity(double unqualifiedQuantity) {
        this.unqualifiedQuantity.set(unqualifiedQuantity);
    }

    public double getPassRate() {
        return passRate.get();
    }

    public DoubleProperty passRateProperty() {
        return passRate;
    }

    public void setPassRate(double passRate) {
        this.passRate.set(passRate);
    }

    public int getIsQualified() {
        return isQualified.get();
    }

    public IntegerProperty isQualifiedProperty() {
        return isQualified;
    }

    public void setIsQualified(int isQualified) {
        this.isQualified.set(isQualified);
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

    public String getDate() {
        return date.get();
    }

    public StringProperty dateProperty() {
        return date;
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    @Override
    public String toString() {
        return "SpotCheckForm{" +
                "id=" + id +
                ", planId=" + planId +
                ", goodsName=" + goodsName +
                ", sumQuantity=" + sumQuantity +
                ", goodsUnit=" + goodsUnit +
                ", checkQuantity=" + checkQuantity +
                ", unqualifiedQuantity=" + unqualifiedQuantity +
                ", passRate=" + passRate +
                ", isQualified=" + isQualified +
                ", stuffNumber=" + stuffNumber +
                ", date=" + date +
                '}';
    }
}
