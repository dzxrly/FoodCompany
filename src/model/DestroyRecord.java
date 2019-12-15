package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

//销毁表
public class DestroyRecord {
    private IntegerProperty recordId = new SimpleIntegerProperty();
    private IntegerProperty destroyNumber = new SimpleIntegerProperty();
    private StringProperty destroyReason = new SimpleStringProperty();
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

    public int getDestroyNumber() {
        return destroyNumber.get();
    }

    public IntegerProperty destroyNumberProperty() {
        return destroyNumber;
    }

    public void setDestroyNumber(int destroyNumber) {
        this.destroyNumber.set(destroyNumber);
    }

    public String getDestroyReason() {
        return destroyReason.get();
    }

    public StringProperty destroyReasonProperty() {
        return destroyReason;
    }

    public void setDestroyReason(String destroyReason) {
        this.destroyReason.set(destroyReason);
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
                ", destroyNumber=" + destroyNumber +
                ", destroyReason=" + destroyReason +
                ", stuffNumber=" + stuffNumber +
                ", destroyTime=" + destroyTime +
                ", destroyNote=" + destroyNote +
                '}';
    }
}
