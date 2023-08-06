package engine;

/*
* World contains a main loop that ticks one at a time, and also has a list of rules.
* The list of rules, we'll go by them and for every iteration in the loop we'll see if it can be invoked.
* */

//TODO: in the loop, when it ends, return why it ended.

import engine.properties.*;

import java.util.ArrayList;
import java.util.Random;

public class World {
    private int tickCounter;
    private ArrayList<Entity> entities;
    private ArrayList<Rule> rules;
    private static ArrayList<Property> environmentProperties;
    //Constructors

    public World(int tickCounter, ArrayList<Entity> entities, ArrayList<Rule> rules) {
        this.tickCounter = tickCounter;
        this.entities = entities;
        this.rules = rules;
    }

    public void Run() {
        for (int i = 0; i < tickCounter; i++) {
            for (Rule rule : rules) {
                rule.invokeAction();
            }
        }
    }

    public static Object environmentGetter(String propertyName) {
        for (Property property : environmentProperties) {
            if (property.getName().equals(propertyName)) {
                if (property.getClass().equals(IntProperty.class)) {
                    IntProperty convertProperty = (IntProperty) property;
                    return convertProperty.getValue();
                }
                else if (property.getClass().equals(DecimalProperty.class)) {
                    DecimalProperty convertProperty = (DecimalProperty) property;
                    return convertProperty.getValue();
                }
                else if (property.getClass().equals(BooleanProperty.class)) {
                    BooleanProperty convertProperty = (BooleanProperty) property;
                    return convertProperty.getValue();
                }
                else if (property.getClass().equals(StringProperty.class)) {
                    StringProperty convertProperty = (StringProperty) property;
                    return convertProperty.getValue();
                }
            }
        }
        return null;
    }

    public static int randomGetter(int randomNumberUpperBound) {
        Random random = new Random();
        return random.nextInt(randomNumberUpperBound);
    }
}
