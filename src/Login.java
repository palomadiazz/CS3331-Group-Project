import java.util.Scanner;

public class Login {
    private Scanner scanner;
    private User[] userDataArray;
    private Car[] carDataArray;

    public Login(Scanner scanner, User[] userDataArray, Car[] carDataArray){
        this.scanner = scanner;
        this.userDataArray = userDataArray;
        this.carDataArray = carDataArray;
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
