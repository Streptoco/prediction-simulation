import engine.action.api.ActionInterface;
import engine.action.expression.ReturnType;
import engine.exception.XMLException;
import engine.general.object.Engine;
import enginetoui.dto.basic.ActionDTO;
import enginetoui.dto.basic.EntityDTO;
import enginetoui.dto.basic.PropertyDTO;
import enginetoui.dto.basic.RuleDTO;
import exceptions.FileDoesNotExistException;

import java.io.File;
import java.util.List;
import java.util.Scanner;

public class driver {
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
            } catch (XMLException e) {
                System.out.println(e.getMessage());
            }
        } while (!isWorldAccepted);
    }

    private static void ShowSimulationDetails(Engine engine) {
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
                if(!action.property.equals("")) {
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
       return 0;
    }

    public static void SetEnvVariables(Engine engine){
        List<PropertyDTO> envVariables = engine.GetAllEnvProperties();
        System.out.println("Please choose the environment variable you wish to set, insert 0 to continue\n");
        int i = 1;
        Scanner scanner = new Scanner(System.in);
        int userChoice;
        String newValue, propertyName;
        do {
            for(PropertyDTO currentVar : envVariables) {
                System.out.println(i + ". " + currentVar.getName());
            }
            i++;
            userChoice = scanner.nextInt();
            propertyName = envVariables.get(userChoice - 1).name;
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
        } while(userChoice != 0);

    }


    private static void Run() {
        Engine engine = new Engine();
        WelcomePlayer();
        System.out.println("Please choose an option:\n1. Load a new XML File.\n2. Show simulation details\n" +
                "3. Run the simulation\n4. Show details of past simulation\n5. Exit :)");
        Scanner scanner = new Scanner(System.in);
        int userChoice = scanner.nextInt();
        do {
            switch (userChoice) {
                case 1:
                    LoadXMLFile(engine);
                    break;
                case 2:
                    ShowSimulationDetails(engine);
                    break;
                case 3:
                    RunTheWorld(engine);
                    break;
                default:
                    System.out.println("Wrong input! Try again\n");
                    break;

            }
            System.out.println("Please choose an option:\n1. Load a new XML File.\n2. Show simulation details\n" +
                    "3. Run the simulation\n4. Show details of past simulation\n5. Exit :)");
            userChoice = scanner.nextInt();
        } while (userChoice != 5);


    }
}
