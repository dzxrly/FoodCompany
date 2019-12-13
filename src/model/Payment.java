package model;

import javafx.beans.property.*;

//支出表
public class Payment {
    private IntegerProperty paymentId = new SimpleIntegerProperty();
    private IntegerProperty stuffNumber = new SimpleIntegerProperty();
    private DoubleProperty paymentPrice = new SimpleDoubleProperty();
    private StringProperty payTime = new SimpleStringProperty();
    private IntegerProperty paymentType = new SimpleIntegerProperty();//0表示现货订单收入，1表示预定订单收入
    private StringProperty paymentItems = new SimpleStringProperty();//具体收入款项

    public int getPaymentId() {
        return paymentId.get();
    }

    public IntegerProperty paymentIdProperty() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId.set(paymentId);
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

    public double getPaymentPrice() {
        return paymentPrice.get();
    }

    public DoubleProperty paymentPriceProperty() {
        return paymentPrice;
    }

    public void setPaymentPrice(double paymentPrice) {
        this.paymentPrice.set(paymentPrice);
    }

    public String getPayTime() {
        return payTime.get();
    }

    public StringProperty payTimeProperty() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime.set(payTime);
    }

    public int getPaymentType() {
        return paymentType.get();
    }

    public IntegerProperty paymentTypeProperty() {
        return paymentType;
    }

    public void setPaymentType(int paymentType) {
        this.paymentType.set(paymentType);
    }

    public String getPaymentItems() {
        return paymentItems.get();
    }

    public StringProperty paymentItemsProperty() {
        return paymentItems;
    }

    public void setPaymentItems(String paymentItems) {
        this.paymentItems.set(paymentItems);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", stuffNumber=" + stuffNumber +
                ", paymentPrice=" + paymentPrice +
                ", payTime=" + payTime +
                ", paymentType=" + paymentType +
                ", paymentItems=" + paymentItems +
                '}';
    }
}
