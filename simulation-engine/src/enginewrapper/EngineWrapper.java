package enginewrapper;

import engine.*;
import engine.actions.api.ActionInterface;
import engine.actions.expression.Expression;
import engine.actions.impl.calculation.CalculationAction;
import engine.actions.impl.condition.impl.ConditionAction;
import engine.actions.impl.increasedecrease.IncreaseDecreaseAction;
import engine.entity.impl.EntityDefinition;
import engine.entity.impl.EntityInstance;
import engine.entity.impl.EntityInstanceManager;
import engine.properties.api.PropertyInterface;
import engine.properties.impl.DecimalProperty;
import engine.properties.impl.IntProperty;
import java.util.ArrayList;
import java.util.List;

public class EngineWrapper {
    public static void main(String[] args) {

        // initialize lists
        ArrayList<Rule> rules = new ArrayList<Rule>();

        // create entity
        EntityInstanceManager manager = new EntityInstanceManager();
        EntityDefinition entityDefinition = new EntityDefinition("Gunslinger", 30);
        manager.create(entityDefinition); // Arthur
        manager.create(entityDefinition); // Dutch
        manager.create(entityDefinition); // Micah
        List<EntityInstance> entities = manager.getInstances();

        // create properties
        PropertyInterface entityProperty1 = new IntProperty(21, "LifeLeft", 0,50, false);
        PropertyInterface entityProperty2 = new DecimalProperty(32.45, "AimAmount", 10,60, false);

        // create env properties
        PropertyInterface envProperty1 = new IntProperty(15,"miss-target-chances", 0,100,false);

        // World
        World world = new World(5, manager, rules);
        world.getEnvironment().setProperty(envProperty1);

        // insert properties to specific entity
        entities.get(0).addProperty(entityProperty1);
        entities.get(0).addProperty(entityProperty2);
        entities.get(1).addProperty(entityProperty1);
        entities.get(1).addProperty(entityProperty2);
        entities.get(2).addProperty(entityProperty1);
        entities.get(2).addProperty(entityProperty2);

        // initialize world
        Rule rule1 = new Rule("Aging", 1, 1);
        Rule rule2 = new Rule("PullingGun", 4, 0.75);
        Rule rule3 = new Rule("FindingAWoman", 6, 0.24);

        Expression expression1 = new Expression(entities.get(0), "11"); // free expression
        Expression expression2 = new Expression(entities.get(0), "LifeLeft"); // property expression
        Expression expression3 = new Expression(entities.get(0), "0"); // environment function expression
        Expression expression4 = new Expression(entities.get(0), "environment(miss-target-chances)"); // environment function expression
        Expression expression5 = new Expression(entities.get(0), "32.45"); // free expression



        ActionInterface action1 = new IncreaseDecreaseAction(entityDefinition, "LifeLeft", expression4, "increase");
        ActionInterface action2 = new IncreaseDecreaseAction(entityDefinition, "LifeLeft", expression3, "INCreaSE");
        ActionInterface action3 = new IncreaseDecreaseAction(entityDefinition, "AimAmount", new Expression(entities.get(0), "11.25"), "decrease");
        ActionInterface action4 = new CalculationAction(entityDefinition, "LifeLeft", "divide", expression1, expression3);
        ActionInterface action5 = new ConditionAction(entityDefinition, "AimAmount", "=", expression5, action1, action3);
        //Action action3 = new IncreaseAction(entity,);
        //Action action4 = new MultiplyAction(entity,);

        // add actions to rules
        rule1.addAction(action5);

        // add rules to lists
        rules.add(rule1);




        Engine engine = new Engine();

        world.Run();
        System.out.println("finished building!");
    }
}
