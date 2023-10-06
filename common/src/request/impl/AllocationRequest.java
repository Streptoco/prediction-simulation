package request.impl;

public class AllocationRequest {
    private int requestID;
    private final String simulationName;
    private final int numOfRuns;
    private String status;
    private int secondsToRun;
    private int ticksToRun;

    public AllocationRequest(String simulationName, int numOfRuns, int amountTick, int amountTime) {
        this.requestID = -1;
        this.simulationName = simulationName;
        this.numOfRuns = numOfRuns;
        this.status = "loading";
        this.ticksToRun = amountTick;
        this.secondsToRun = amountTime;

    }

    public void setRequestID(int requestID) {
        if (this.requestID == -1) {
            this.requestID = requestID;
        }
    }
}
