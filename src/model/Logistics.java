package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

//物流表
public class Logistics {
    private IntegerProperty logisticsId = new SimpleIntegerProperty();
    private StringProperty logisticsNumber = new SimpleStringProperty();
    //物流类型0为普通陆运 1为海运 2为空运
    private IntegerProperty logisticsType = new SimpleIntegerProperty();
    private StringProperty companyName = new SimpleStringProperty();
    private StringProperty deliveryDate = new SimpleStringProperty();
    private StringProperty destination = new SimpleStringProperty();
    private IntegerProperty orderId = new SimpleIntegerProperty();

    public int getLogisticsId() {
        return logisticsId.get();
    }

    public IntegerProperty logisticsIdProperty() {
        return logisticsId;
    }

    public void setLogisticsId(int logisticsId) {
        this.logisticsId.set(logisticsId);
    }

    public String getLogisticsNumber() {
        return logisticsNumber.get();
    }

    public StringProperty logisticsNumberProperty() {
        return logisticsNumber;
    }

    public void setLogisticsNumber(String logisticsNumber) {
        this.logisticsNumber.set(logisticsNumber);
    }

    public int getLogisticsType() {
        return logisticsType.get();
    }

    public IntegerProperty logisticsTypeProperty() {
        return logisticsType;
    }

    public void setLogisticsType(int logisticsType) {
        this.logisticsType.set(logisticsType);
    }

    public String getCompanyName() {
        return companyName.get();
    }

    public StringProperty companyNameProperty() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName.set(companyName);
    }

    public String getDeliveryDate() {
        return deliveryDate.get();
    }

    public StringProperty deliveryDateProperty() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate.set(deliveryDate);
    }

    public String getDestination() {
        return destination.get();
    }

    public StringProperty destinationProperty() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination.set(destination);
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

    @Override
    public String toString() {
        return "Logistics{" +
                "logisticsId=" + logisticsId +
                ", logisticsNumber=" + logisticsNumber +
                ", logisticsType=" + logisticsType +
                ", companyName=" + companyName +
                ", deliveryDate=" + deliveryDate +
                ", destination=" + destination +
                ", orderId=" + orderId +
                '}';
    }
}
