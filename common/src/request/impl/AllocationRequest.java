package request.impl;

import request.api.RequestStatus;

public class AllocationRequest implements Comparable<AllocationRequest> {
    private int requestID;
    private final String simulationName;
    private final int numOfRuns;
    private RequestStatus status;
    private int secondsToRun;
    private int ticksToRun;

    public AllocationRequest(String simulationName, int numOfRuns, int amountTick, int amountTime) {
        this.requestID = -1;
        this.simulationName = simulationName;
        this.numOfRuns = numOfRuns;
        this.status = RequestStatus.WAITING;
        this.ticksToRun = amountTick;
        this.secondsToRun = amountTime;

    }

    public void setRequestID(int requestID) {
        if (this.requestID == -1) {
            this.requestID = requestID;
        }
    }

    @Override
    public int compareTo(AllocationRequest o) {
        return this.requestID - o.requestID;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder()
                .append("Simulation Name: " + simulationName)
                .append("\nRequest ID: " + requestID)
                .append("\nNumber of runs: " + numOfRuns);
        if(secondsToRun == Integer.MAX_VALUE && ticksToRun == Integer.MAX_VALUE) {
            result.append("\nTermination: By User");
        } else if (secondsToRun == Integer.MAX_VALUE) {
            result.append("\nTermination: By ").append(ticksToRun).append(" Ticks");
        } else if (ticksToRun == Integer.MAX_VALUE) {
            result.append("\nTermination: By ").append(secondsToRun).append(" Seconds");
        } else {
            result.append("\nTermination: By ").append(ticksToRun).append(" Ticks and ").append(secondsToRun).append(" Seconds");
        }
        return String.valueOf(result);

    }
}
