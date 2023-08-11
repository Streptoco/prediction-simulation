package engine;

/*
* World contains a main loop that ticks one at a time, and also has a list of rules.
* The list of rules, we'll go by them and for every iteration in the loop we'll see if it can be invoked.
* */

//TODO: in the loop, when it ends, return why it ended.

import engine.actions.expression.ReturnType;
import engine.entity.impl.EntityDefinition;
import engine.properties.api.AbstractProperty;
import engine.properties.impl.BooleanProperty;
import engine.properties.impl.DecimalProperty;
import engine.properties.impl.IntProperty;
import engine.properties.impl.StringProperty;

import java.util.ArrayList;
import java.util.Random;

public class World {
    private int tickCounter;
    private ArrayList<EntityDefinition> entities;
    private ArrayList<Rule> rules;
    private static ArrayList<AbstractProperty> environmentProperties;
    //Constructors

    public World(int tickCounter, ArrayList<EntityDefinition> entities, ArrayList<Rule> rules) {
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

    public static ReturnType propertyTypeGetter (String propertyName) {
        for (AbstractProperty property : environmentProperties) {
            if (property.getName().equals(propertyName)) {
                return property.getPropertyType();
            }
            else {
                // TODO: handle error: no such property
            }
        }
        return null;
    }

    public static Object environmentGetter(String propertyName) {
        for (AbstractProperty property : environmentProperties) {
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
            else {
                // TODO: Handle expection : no such property
            }
        }
        return null;
    }

    public static int randomGetter(int randomNumberUpperBound) {
        Random random = new Random();
        return random.nextInt(randomNumberUpperBound);
    }
}
