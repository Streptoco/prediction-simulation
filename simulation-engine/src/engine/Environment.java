package engine;

// for environmental variables...
//TODO: figure out how environment properties work and how I should store them
public class Environment {
    private static Map<String, PropertyInterface> envVariables = null;

    public Environment(){
        envVariables = new HashMap<>();
    }

    public static Object environmentGetter(String propertyName) {
        return envVariables.get(propertyName).getValue();
    }

    public static ReturnType propertyTypeGetter (String propertyName) {
        return envVariables.get(propertyName).getPropertyType();
    }

    public PropertyInterface getProperty(String name) {
        return envVariables.get(name);
    }
}
