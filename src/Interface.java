import java.io.File;
import java.util.Scanner;

public abstract class Interface {
    protected Scanner scanner;
    protected User[] userDataArray;
    protected Car[] carDataArray;
    protected User user;
    private File userDataCsv;
    private File carDataCsv;

    public Interface(Scanner scanner, User[] userDataArray, Car[] carDataArray, User user, File userDataCsv, File carDataCsv){
        this.scanner = scanner;
        this.userDataArray = userDataArray;
        this.carDataArray = carDataArray;
        this.user = user;
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
