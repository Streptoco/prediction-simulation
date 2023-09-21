package engine.property.api;

import engine.action.expression.ReturnType;

public abstract class AbstractProperty implements PropertyInterface {
    protected String name;
    protected Double rangeFrom;
    protected Double rangeTo;
    protected boolean isRandomlyGenerated;
    protected ReturnType propertyType;
    protected int lastChangedTick;
    protected int countOfChanges;
    protected int sumOfConsistency;

    public AbstractProperty(String name, double rangeFrom, double rangeTo, boolean isRandomlyGenerated, ReturnType propertyType) {
        this.name = name;
        this.rangeFrom = rangeFrom;
        this.rangeTo = rangeTo;
        this.isRandomlyGenerated = isRandomlyGenerated;
        this.propertyType = propertyType;
        this.lastChangedTick = 0;
        this.countOfChanges = 0;
        this.sumOfConsistency = 0;
    }

    public String getName() { return name; }

    public double getFrom() { return rangeFrom; }

    public double getTo() { return rangeTo; }

    public boolean getRandomStatus() { return isRandomlyGenerated; }

    public ReturnType getPropertyType() { return propertyType; }

    public abstract Object getValue();

    @Override
    public double getAverageTimeOfChange() {
        if(countOfChanges != 0) {
            return (double) sumOfConsistency / countOfChanges;
        } else {
            return 0;
        }
    }

    @Override
    public int timeSinceLastChange(int currentTick) {
        return currentTick - this.lastChangedTick;
    }

}
