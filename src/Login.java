import java.io.File;
import java.util.Scanner;

public class Login {
    private Scanner scanner;
    private User[] userDataArray;
    private Car[] carDataArray;
    private File userDataCsv;
    private File carDataCsv;

    public Login(Scanner scanner, User[] userDataArray, Car[] carDataArray, File userDataCsv, File carDataCsv){
        this.scanner = scanner;
        this.userDataArray = userDataArray;
        this.carDataArray = carDataArray;
        this.userDataCsv = userDataCsv;
        this.carDataCsv = carDataCsv;
    }

    public int optionHandler(String menu, int max){
        String option;
        int parsedOption;

        while (true){
            System.out.println(menu);

            System.out.print("Option: ");
            option = this.scanner.next();

            try {
                parsedOption = Integer.parseInt(option);
                if (parsedOption > 0 && parsedOption <= max) {
                    return parsedOption;
                }
            } catch (NumberFormatException e) {
            } finally {
                System.out.println("Input not recognized. Please try again!");
            }
        }
    }
    
}
