package enginetoui.dto.basic;

import request.api.RequestStatus;

public class RequestDTO {
    private final String simulationName;
    public final int numOfRuns;
    public final int ticks;
    public final int seconds;

    public final String username;
    private RequestStatus status;

    public RequestDTO(String simulationName, int numOfRuns, int ticks, int seconds, String username) {
        this.simulationName = simulationName;
        this.numOfRuns = numOfRuns;
        this.ticks = ticks;
        this.seconds = seconds;
        this.username = username;
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
