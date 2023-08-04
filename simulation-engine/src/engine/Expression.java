package engine;

//TODO: figure out how to segment the functions needed to be implemented: environment, and random.
//TODO: error handling.
//TODO: 1. if expression is a name of a function (env,random) then do them. 2. if not, search all property names. 3. else, free expression.

enum Type {
    FUNCTION,
    PROPERTY,
    FREE
}

public class Expression {
    String name;
    Type type;
    Entity entity;
    Property propertyMatch;

    public Expression (Entity entity, String name) {
        this.name = name;
        this.entity = entity;
        evaluateExpression();
    }

    public void evaluateExpression() {
        propertyMatch = entity.getPropertyByName(name);

        if (name.equals("environment") || name.equals("random")) {
            // TODO: segment to functions.
            type = Type.FUNCTION;
        }
        else if (propertyMatch != null) {
            type = Type.PROPERTY;
        }
        else {
            type = Type.FREE;
        }
    }
}
