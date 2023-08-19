import exceptions.FileDoesNotExistException;

import java.io.File;
import java.util.Scanner;

public class driver {
    public static void main(String[] args) {
        Run();
    }

    private static void WelcomePlayer() {
        System.out.println("Hello and welcome to the predictions simulator!\nPlease enter a valid XML file path.");
        Scanner scanner = new Scanner(System.in);
        String filePath = scanner.nextLine();
    }
    private static void Run() {
        WelcomePlayer();

    }
}
