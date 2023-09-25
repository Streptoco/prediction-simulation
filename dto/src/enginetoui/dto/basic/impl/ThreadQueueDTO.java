package enginetoui.dto.basic.impl;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class ThreadQueueDTO {


    public IntegerProperty numOfRunning;
    public IntegerProperty numOfWaiting;
    public IntegerProperty numOfDone;

    public ThreadQueueDTO() {
        this.numOfDone = new SimpleIntegerProperty();
        this.numOfRunning = new SimpleIntegerProperty();
        this.numOfWaiting = new SimpleIntegerProperty();
    }

    public int getNumOfRunning() {
        return numOfRunning.get();
    }

    public IntegerProperty numOfRunningProperty() {
        return numOfRunning;
    }

    public void setNumOfRunning(int numOfRunning) {
        this.numOfRunning.set(numOfRunning);
    }

}
