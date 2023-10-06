package enginetoui.dto.basic;

public class RequestDTO {
    public final String simulationName;
    public final int numOfRuns;
    public final int ticks;
    public final int seconds;
    public RequestDTO(String simulationName, int numOfRuns, int ticks, int seconds) {
        this.simulationName = simulationName;
        this.numOfRuns = numOfRuns;
        this.ticks = ticks;
        this.seconds = seconds;
    }
}
