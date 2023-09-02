package enginetoui.dto.basic.impl;


public class TerminationDTO {
    public final int ticks;
    public final long howManySecondToRun;
    public boolean isUserInteractive;

    public TerminationDTO(int ticks, long howManySecondToRun, boolean isUserInteractive) {
        this.ticks = ticks;
        this.howManySecondToRun = howManySecondToRun;
        this.isUserInteractive = isUserInteractive;
    }
}
