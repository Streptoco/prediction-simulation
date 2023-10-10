package enginetoui.dto.basic;

import request.api.RequestStatus;

public class RequestDTO {
    private final String simulationName;
    private int simulationID;
    public final int numOfRuns;
    public final int ticks;
    public final int seconds;
    private RequestStatus status;

    public RequestDTO(String simulationName, int numOfRuns, int ticks, int seconds) {
        this.simulationName = simulationName;
        this.simulationID = -1;
        this.numOfRuns = numOfRuns;
        this.ticks = ticks;
        this.seconds = seconds;
        this.status = RequestStatus.WAITING;
    }

    public String getSimulationName() {
        return simulationName;
    }

    public int getSimulationID() {
        return simulationID;
    }

    public void setSimulationID(int simulationID) {
        if (this.simulationID == -1) {
            this.simulationID = simulationID;
        }
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }
}
