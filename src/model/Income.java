package model;

import javafx.beans.property.*;

//收入表
public class Income {
    private IntegerProperty incomeId = new SimpleIntegerProperty();
    private IntegerProperty stuffNumber = new SimpleIntegerProperty();
    private DoubleProperty incomePrice = new SimpleDoubleProperty();
    private StringProperty incomeTime = new SimpleStringProperty();
    private IntegerProperty incomeType = new SimpleIntegerProperty();//0表示付预付款 1表示付尾款 2表示付全款
    private StringProperty incomeItem = new SimpleStringProperty();//具体收入款项
    private IntegerProperty orderId = new SimpleIntegerProperty();
    private StringProperty payCardNumber =new SimpleStringProperty();

    public int getIncomeId() {
        return incomeId.get();
    }

    public IntegerProperty incomeIdProperty() {
        return incomeId;
    }

    public void setIncomeId(int incomeId) {
        this.incomeId.set(incomeId);
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

    public double getIncomePrice() {
        return incomePrice.get();
    }

    public DoubleProperty incomePriceProperty() {
        return incomePrice;
    }

    public void setIncomePrice(double incomePrice) {
        this.incomePrice.set(incomePrice);
    }

    public String getIncomeTime() {
        return incomeTime.get();
    }

    public StringProperty incomeTimeProperty() {
        return incomeTime;
    }

    public void setIncomeTime(String incomeTime) {
        this.incomeTime.set(incomeTime);
    }

    public int getIncomeType() {
        return incomeType.get();
    }

    public IntegerProperty incomeTypeProperty() {
        return incomeType;
    }

    public void setIncomeType(int incomeType) {
        this.incomeType.set(incomeType);
    }

    public String getIncomeItem() {
        return incomeItem.get();
    }

    public StringProperty incomeItemProperty() {
        return incomeItem;
    }

    public void setIncomeItem(String incomeItem) {
        this.incomeItem.set(incomeItem);
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

    public String getPayCardNumber() {
        return payCardNumber.get();
    }

    public StringProperty payCardNumberProperty() {
        return payCardNumber;
    }

    public void setPayCardNumber(String payCardNumber) {
        this.payCardNumber.set(payCardNumber);
    }

    @Override
    public String toString() {
        return "Income{" +
                "incomeId=" + incomeId +
                ", stuffNumber=" + stuffNumber +
                ", incomePrice=" + incomePrice +
                ", incomeTime=" + incomeTime +
                ", incomeType=" + incomeType +
                ", incomeItem=" + incomeItem +
                ", orderId=" + orderId +
                ", payCardNumber=" + payCardNumber +
                '}';
    }
}
