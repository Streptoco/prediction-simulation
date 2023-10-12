package enginetoui.dto.basic;

import request.api.RequestStatus;

public class RequestDTO {
    private final String simulationName;
    public final int numOfRuns;
    public final int ticksToRun;
    public final int secondsToRun;
    public final String username;
    private RequestStatus status;

    public RequestDTO(String simulationName, int numOfRuns, int ticks, int seconds, String username) {
        this.simulationName = simulationName;
        this.numOfRuns = numOfRuns;
        this.ticksToRun = ticks;
        this.secondsToRun = seconds;
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

    public int getNumOfRuns() {
        return numOfRuns;
    }

    public int getTicksToRun() {
        return ticksToRun;
    }

    public int getSecondsToRun() {
        return secondsToRun;
    }

    public String getUsername() {
        return username;
    }
}
