package enginetoui.dto.basic;

import request.api.RequestStatus;

public class RequestDTO {
    private final String simulationName;
    public final int numOfRuns;
    public final int ticks;
    public final int seconds;
    private RequestStatus status;

    public RequestDTO(String simulationName, int numOfRuns, int ticks, int seconds) {
        this.simulationName = simulationName;
        this.numOfRuns = numOfRuns;
        this.ticks = ticks;
        this.seconds = seconds;
        this.status = RequestStatus.WAITING;
    }

    public String getSimulationName() {
        return simulationName;
    }


    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }
}
