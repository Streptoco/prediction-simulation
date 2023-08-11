package enginewrapper;

import engine.*;
import engine.actions.expression.Expression;
import engine.actions.impl.IncreaseDecreaseAction;
import engine.entity.impl.EntityDefinition;
import engine.properties.impl.DecimalProperty;
import engine.properties.impl.IntProperty;

import java.util.ArrayList;

public class EngineWrapper {
    public static void main(String[] args) {

        // initialize lists
        ArrayList<EntityDefinition> entities = new ArrayList<EntityDefinition>();
        ArrayList<Rule> rules = new ArrayList<Rule>();
        ArrayList<Property> properties = new ArrayList<Property>();

        // initialize entity
        EntityDefinition entityDefinition = new EntityDefinition("Gunslinger", 30);
        Property entityProperty1 = new IntProperty(21, "LifeLeft", 0,50, false);
        Property entityProperty2 = new DecimalProperty(3.14, "AimAmount", 10,60, false);
        properties.add(entityProperty1);
        properties.add(entityProperty2);
        entityDefinition.setProperties(properties);

        // initialize world
        Rule rule1 = new Rule("Aging", 1, 1);
        Rule rule2 = new Rule("PullingGun", 4, 0.75);
        Rule rule3 = new Rule("FindingAWoman", 6, 0.24);

        Expression expression1 = new Expression(entityDefinition, "11"); // free expression
        Expression expression2 = new Expression(entityDefinition, "LifeLeft"); // property expression
        Expression expression3 = new Expression(entityDefinition, "random(5)"); // environment function expression

        Action action1 = new IncreaseDecreaseAction(entityDefinition, entityDefinition.getPropertyByName("LifeLeft"),expression1);
        Action action2 = new IncreaseDecreaseAction(entityDefinition, entityDefinition.getPropertyByName("AimAmount"), new Expression(entityDefinition, "23.12"));
        //Action action3 = new IncreaseAction(entity,);
        //Action action4 = new MultiplyAction(entity,);

        // add actions to rules
        rule1.addAction(action1);
        rule1.addAction(action2);
        rule1.addAction(action3);

        // add rules to lists
        rules.add(rule1);

        // add entities to lists

        World world = new World(5, manager, rules);
        Engine engine = new Engine();

        world.Run();
        System.out.println("finished building!");
    }
}
