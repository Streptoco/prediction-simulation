import engine.action.expression.ReturnType;
import engine.exception.XMLException;
import engine.general.object.Engine;
import engine.general.object.World;
import enginetoui.dto.basic.*;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class driver {
    private static boolean loadXML = false;

    private static boolean alreadyRun = false;

    public static void main(String[] args) {
        Run();
    }

    private static void WelcomePlayer() {
        System.out.println("Hello and welcome to the predictions simulator!");
    }

    private static void LoadXMLFile(Engine engine) {
        boolean isWorldAccepted = false;
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Please enter a valid XML file path.");
            String filePath = scanner.nextLine();
            try {
                engine.addSimulation(filePath);
                isWorldAccepted = true;
                loadXML = true;
            } catch (XMLException e) {
                System.out.println(e.getMessage());
            }
        } while (!isWorldAccepted);
    }

    private static void ShowSimulationDetails(Engine engine) {
        if (!loadXML) {
            System.out.println("No XML file loaded to the system, return to main menu\n");
            return;
        }
        int i = 1, j = 1;
        System.out.println("Entities:\n");
        List<EntityDTO> entities = engine.GetAllEntities();
        for (EntityDTO entity : entities) {
            System.out.println(i + ".  " + "Name: " + entity.name + "\n\tPopulation: " + entity.population + "\n\tProperties:");
            i++;
            for (PropertyDTO property : entity.propertyList) {
                System.out.println("\t\t" + j + ".  " + "Property Name: \"" + property.name + "\"" + "\tProperty type: \"" + property.type + "\"");
                if (!(property.type.equals(ReturnType.STRING) || property.type.equals(ReturnType.BOOLEAN))) {
                    System.out.println("\t\t\tFrom: " + property.from + "\n\t\t\tTo: " + property.to);
                }
                System.out.println("\t\t\tIs randomly generated: " + property.isRandomlyGenerated + "\n");
                j++;
            }
        }
        i = 1;
        System.out.println("Rules:\n");
        List<RuleDTO> rules = engine.GetAllRules();
        for (RuleDTO rule : rules) {
            System.out.println(i + ".  " + "Name: " + rule.name + "\n\tActivated every " + rule.tick + " ticks\n\t" + "In probability under " + rule.probability + "\n" +
                    "\tNumber of actions: " + rule.numOfActions);
            j = 1;
            System.out.println("\tActions of the rule:");
            for (ActionDTO action : rule.actionNames) {
                System.out.println("\t\t\t" + j + ".  " + "Action type: " + action.type);
                if (!action.property.equals("")) {
                    System.out.println("\t\t\t\tAction name: " + action.property);
                }
                j++;
            }
            i++;
        }
        System.out.println("\nThe simulation will run " + engine.GetSimulationTotalTicks() + " ticks or " + engine.GetSimulationTotalTime() + " seconds\n");

    }

    public static int RunTheWorld(Engine engine) {
        SetEnvVariables(engine);
        return engine.RunSimulation();
    }

    public static void SetEnvVariables(Engine engine) {
        List<PropertyDTO> envVariables = engine.GetAllEnvProperties();
        System.out.println("\nPlease choose the environment variable you wish to set, insert 0 to continue");
        int i = 1;
        int userChoice; //TODO: need to fix this menu
        Scanner scanner = new Scanner(System.in);
        String newValue, propertyName;
        do {
            for (PropertyDTO currentVar : envVariables) {
                System.out.println(i + ". " + currentVar.getName());
                i++;
            }
            System.out.println("Press 0 to continue");
            i = 1;
            userChoice = ValidChoice(envVariables.size(), 0);
            if (userChoice > 0) {
                propertyName = envVariables.get(userChoice - 1).name;
                System.out.println("Please insert the new value:");
                newValue = scanner.nextLine();
                switch (envVariables.get(userChoice - 1).type) {
                    case INT:
                        engine.SetVariable(propertyName, Integer.parseInt(newValue));
                        break;
                    case DECIMAL:
                        engine.SetVariable(propertyName, Double.parseDouble(newValue));
                        break;
                    case BOOLEAN:
                        engine.SetVariable(propertyName, Boolean.parseBoolean(newValue));
                    case STRING:
                        engine.SetVariable(propertyName, newValue);
                }
            }
        } while (userChoice != 0);

    }

    public static void ShowPastSimulations(Engine engine) {
        List<WorldDTO> allSimulations = engine.GetSimulations();
        int i = 1;
        System.out.println("All the past simulations:");
        for (WorldDTO simulations : allSimulations) {
            System.out.println(i + ".  " + "Simulation ID: " + simulations.simulationId + "\n\tSimulation Date: " + simulations.GetSimulationDateString());
            i++;
        }
        System.out.println("Please choose the desired simulation:");
        Scanner scanner = new Scanner(System.in);
        String userChoiceString;
        int userChoice = -1;
        boolean validChoice = false;
        do {
            userChoiceString = scanner.nextLine();
            try {
                userChoice = -1;
                userChoice = Integer.parseInt(userChoiceString);
            } catch (NumberFormatException e) {
                if (userChoice == -1) {
                    userChoice = -1;
                }
            }
            if (userChoice > 0 && userChoice <= allSimulations.size() + 1) {
                validChoice = true;
                break;
            } else {
                System.out.println("Wrong input, try again");
            }

        } while (!validChoice);

        WorldDTO relevantWorld = allSimulations.get(userChoice - 1);
        int simulationId = userChoice;
        System.out.println("Please choose the information you like to see\n1. Amount per entity\n2. Histogram of property");
        validChoice = false;
        do {
            userChoiceString = scanner.nextLine();
            try {
                userChoice = -1;
                userChoice = Integer.parseInt(userChoiceString);
            } catch (NumberFormatException e) {
                if (userChoice == -1) {
                    userChoice = -1;
                }
            }
            if (userChoice == 1 || userChoice == 2) {
                validChoice = true;
                break;
            } else {
                System.out.println("Wrong input, try again");
            }
        } while (!validChoice);

        if (userChoice == 1) {
            int j = 1;
            for (InstancesDTO instance : relevantWorld.instances) {
                System.out.println("\t" + j + ".  " + "Entity name: " + instance.entityName + "\n\t\tAmount at the beginning: " + instance.numberOfInstances
                        + "\n\t\t" + "Amount at the end: " + instance.numberOfRemainInstances + "\n");
            }
        } else {
            System.out.println("Choose the relevant entity:");
            int j = 1;
            for (InstancesDTO instance : relevantWorld.instances) {
                System.out.println("\t" + j + ".  " + "Entity name: " + instance.entityName);
            }
            userChoice = ValidChoice(relevantWorld.instances.size(), 1) - 1;
            int userChoiceName = userChoice;
            j = 1;
            System.out.println("Choose the desired property");
            for(String propertyName : relevantWorld.instances.get(userChoice).propertyNames) {
                System.out.println("\t" + j + ". "+ "Property name: " + propertyName);
                j++;
            }
            userChoice = ValidChoice(relevantWorld.instances.get(userChoice).propertyNames.size(), 1) - 1;
            int userChoiceProperty = userChoice;
            String propertyName = relevantWorld.instances.get(userChoiceName).propertyNames.get(userChoiceProperty);

            Map<String, Integer> histogram = relevantWorld.GetHistogram(relevantWorld.instances.get(userChoiceName).entityName, propertyName, simulationId, engine);
            for(Map.Entry<String, Integer> entry : histogram.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                System.out.println("Value: " + key + ", Amount: " + value );
            }


        }

    }

    private static void Run() {
        Engine engine = new Engine();
        int simulationId;
        WelcomePlayer();
        System.out.println("Please choose an option:\n1. Load a new XML File.\n2. Show simulation details\n" +
                "3. Run the simulation\n4. Show details of past simulation\n5. Exit :)");
        Scanner scanner = new Scanner(System.in);
        int userChoice = -1;
        do {
            userChoice = ValidChoice(5, 1);

            switch (userChoice) {
                case 1:
                    LoadXMLFile(engine);
                    alreadyRun = false;
                    break;
                case 2:
                    ShowSimulationDetails(engine);
                    break;
                case 3:
                    if (!loadXML) {
                        System.out.println("No XML file loaded to the system, return to main menu\n");
                    } else if(alreadyRun) {
                        System.out.println("This xml file was already used, please load a new xml");
                    }
                    else {
                        simulationId = RunTheWorld(engine);
                        alreadyRun = true;
                        System.out.println("Simulation ran successfully, run id: " + simulationId);
                    }
                    break;
                case 4:
                    ShowPastSimulations(engine);
                    break;
                case 5:
                    break;
                default:
                    System.out.println("Wrong input! Try again\n");
                    break;

            }
            if (userChoice != 5) {
                System.out.println("Please choose an option:\n1. Load a new XML File.\n2. Show simulation details\n" +
                        "3. Run the simulation\n4. Show details of past simulation\n5. Exit :)");
            }

        } while (userChoice != 5);

    }

    public static int ValidChoice(int upLimit, int downLimit) {
        Scanner scanner = new Scanner(System.in);
        String userChoiceString;
        int userChoice = 1;
        boolean validChoice = false;
        do {
            userChoiceString = scanner.nextLine();
            try {
                userChoice = -1;
                userChoice = Integer.parseInt(userChoiceString);
            } catch (NumberFormatException e) {
                if (userChoice == -1) {
                    userChoice = -1;
                }
            }
            if (userChoice != -1) {
                if (userChoice >= downLimit || userChoice < upLimit) {
                    validChoice = true;
                    return userChoice;
                } else {
                    System.out.println("Wrong input, try again");
                }
            } else {
                System.out.println("Wrong input, try again");
            }
        } while (!validChoice);
        return 0;

    }

}
