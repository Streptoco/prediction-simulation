import engine.general.object.Engine;
import exceptions.FileDoesNotExistException;
import uitoengine.filetransfer.FileTransferDTO;

import java.io.File;
import java.util.Scanner;

public class driver {
    public static void main(String[] args) {
        Run();
    }

    private static void WelcomePlayer() {
        System.out.println("Hello and welcome to the predictions simulator!\nPlease enter a valid XML file path.");
        Engine engine = new Engine();
        Scanner scanner = new Scanner(System.in);
        String filePath = scanner.nextLine();
        //TODO: check engine.setXmlFilePath()
        
    }
    private static void Run() {
        WelcomePlayer();

    }
}
