package enginewrapper;

import engine.*;

import java.util.ArrayList;

public class EngineWrapper {
    public static void main(String[] args) {

        // initialize lists
        ArrayList<Entity> entities = new ArrayList<Entity>();
        ArrayList<Rule> rules = new ArrayList<Rule>();
        ArrayList<Property> properties = new ArrayList<Property>();

        // initialize entity
        Entity entity = new Entity("Gunslinger", 30);
        Property entityProperty1 = new IntProperty(21, "LifeLeft", 0,50, false);
        Property entityProperty2 = new DecimalProperty(3.14, "AimAmount", 10,60, false);
        properties.add(entityProperty1);
        properties.add(entityProperty2);
        entity.setProperties(properties);

        // initialize world
        Rule rule1 = new Rule("Aging", 1, 1);
        Rule rule2 = new Rule("PullingGun", 4, 0.75);
        Rule rule3 = new Rule("FindingAWoman", 6, 0.24);

        Expression expression = new Expression(entity, "11");

        Action action1 = new IncreaseAction(entity,entity.getPropertyByName("LifeLeft"),expression);
        //Action action2 = new DecreaseAction(entity,entity.getPropertyByName("AimAmount"), new Expression(entity, "23.12"));
        //Action action3 = new IncreaseAction(entity,);
        //Action action4 = new MultiplyAction(entity,);

        // add actions to rules
        rule1.addAction(action1);

        // add rules to lists
        rules.add(rule1);

        // add entities to lists
        entities.add(entity);

        World world = new World(300, entities, rules);
        Engine engine = new Engine();

        world.Run();
        System.out.println("finished building");
        //

    }
}
