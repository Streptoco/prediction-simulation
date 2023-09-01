package enginetoui.dto.basic.impl;

import enginetoui.dto.basic.api.DTO;

public class TerminationDTO implements DTO {
    public final int ticks;
    public final long howManySecondToRun;
    public boolean isUserInteractive;

    public TerminationDTO(int ticks, long howManySecondToRun, boolean isUserInteractive) {
        this.ticks = ticks;
        this.howManySecondToRun = howManySecondToRun;
        this.isUserInteractive = isUserInteractive;
    }

    @Override
    public void transferData() {

    }

    @Override
    public String getName() {
        return "Termination";
    }
}
