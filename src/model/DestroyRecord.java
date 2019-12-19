package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

//销毁表
public class DestroyRecord {
    private IntegerProperty recordId = new SimpleIntegerProperty();
    private StringProperty goodsName = new SimpleStringProperty();
    private IntegerProperty stuffNumber = new SimpleIntegerProperty();
    private StringProperty destroyTime = new SimpleStringProperty();
    private StringProperty destroyNote = new SimpleStringProperty();

    public int getRecordId() {
        return recordId.get();
    }

    public IntegerProperty recordIdProperty() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId.set(recordId);
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

    public int getStuffNumber() {
        return stuffNumber.get();
    }

    public IntegerProperty stuffNumberProperty() {
        return stuffNumber;
    }

    public void setStuffNumber(int stuffNumber) {
        this.stuffNumber.set(stuffNumber);
    }

    public String getDestroyTime() {
        return destroyTime.get();
    }

    public StringProperty destroyTimeProperty() {
        return destroyTime;
    }

    public void setDestroyTime(String destroyTime) {
        this.destroyTime.set(destroyTime);
    }

    public String getDestroyNote() {
        return destroyNote.get();
    }

    public StringProperty destroyNoteProperty() {
        return destroyNote;
    }

    public void setDestroyNote(String destroyNote) {
        this.destroyNote.set(destroyNote);
    }

    @Override
    public String toString() {
        return "DestroyRecord{" +
                "recordId=" + recordId +
                ", goodsName=" + goodsName +
                ", stuffNumber=" + stuffNumber +
                ", destroyTime=" + destroyTime +
                ", destroyNote=" + destroyNote +
                '}';
    }
}
