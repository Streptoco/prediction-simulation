package enginewrapper;

import engine.*;

import java.util.ArrayList;

public class EngineWrapper {
    public static void main() {
        // initialize lists
        ArrayList<Entity> entities = new ArrayList<Entity>();
        ArrayList<Rule> rules = new ArrayList<Rule>();
        ArrayList<Property> properties = new ArrayList<Property>();
        // initialize items
        Entity entity = new Entity("Gunslinger", 30);
        Property entityProperty1 = new IntProperty();
        Property entityProperty2 = new DecimalProperty();
        Rule rule1 = new Rule();
        Rule rule2 = new Rule();
        Rule rule3 = new Rule();
        Action action1 = new Action();
        Action action2 = new Action();
        Action action3 = new Action();
        Action action4 = new Action();
        Engine engine = new Engine();
        World world = new World(300, );
    }
}
