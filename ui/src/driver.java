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
        int i = 1;
        List<EntityDTO> entities = engine.GetAllEntities();
        for(EntityDTO entity : entities) {
            System.out.println(i++ + ". " + "Name: " + entity.name + "\nPopulation: " + entity.population + "Properties:\n");
            for(PropertyDTO property : entity.propertyList) {
                System.out.println("\tProperty Name: " + property.name + "\tProperty type: " + property.type + "\n");
                if(!(property.type.equals(ReturnType.STRING) || property.type.equals(ReturnType.BOOLEAN))) {
                    System.out.println("\tFrom: " + property.from + "\tTo: " + property.to + "\n");
                }
                System.out.println("\tIs randomly generated: " + property.isRandomlyGenerated + "\n");
            }
        }
        i = 1;
        List<RuleDTO> rules = engine.GetAllRules();
        for(RuleDTO rule : rules) {
            System.out.println(i + ". " + "Name: " + rule.name + "\nActivated every " + rule.tick + "ticks\n" + "In probability under " + rule.probability + "\n" +
                    "Number of actions: " + rule.numOfActions + "\n");
            int j = 1;
            System.out.println("Actions of the rule:\n");
            for(ActionDTO action : rule.actionNames) {
                System.out.println("\t" + j++ + ". " + action.type + "\n\t" + action.property);
            }
        }
        System.out.println("The simulation will run" + engine.GetSimulationTotalTicks() + "ticks or " + engine.GetSimulationTotalTime() + "seconds");

    }


    private static void Run() {
        Engine engine = new Engine();
        WelcomePlayer();
        System.out.println("Please choose an option:\n1. Load a new XML File.\n2. Show simulation details\n" +
                "3. Run the simulation\n4. Show details of past simulation\n5. Exit :)");
        Scanner scanner = new Scanner(System.in);
        int userChoice = scanner.nextInt();
        do {
            switch (userChoice){
                case 1:
                    LoadXMLFile(engine);
                    break;
                case 2:
                    ShowSimulationDetails(engine);
                    break;

            }
        } while(userChoice != 5);



    }
}
