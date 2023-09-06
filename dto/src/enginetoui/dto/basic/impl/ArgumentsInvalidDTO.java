package enginetoui.dto.basic.impl;

public class ArgumentsInvalidDTO {
    private String argumentName;

    public ArgumentsInvalidDTO(String name) {
        this.argumentName = name;
    }
    public String getName() {return  argumentName;}

    public String setName(String name) { return this.argumentName = name;}
}
