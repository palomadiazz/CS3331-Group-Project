import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner; 

public class RunShop {

    static File userDataCsv = new File("user_data.csv");
    static File carDataCsv = new File("car_data.csv");

    public static void main(String[] args) {

        Scanner inputReader = new Scanner(System.in);

        System.out.println("Welcome to Miner Cars Dealership!");
        // when not in login or register menu, return here so the user may pick from one

        //DEBUG
        register(inputReader);
        inputReader.close();
    }

    
    public static int linesInFile(File file){
        try{
            Scanner fileReader = new Scanner(file);
            int count = 0;

            while (fileReader.hasNextLine()) { 
                count++;
                fileReader.nextLine();
            }

            fileReader.close();
            return count;
        } catch (FileNotFoundException e) {
            return -1;
        }
    }

    public static User[] loadUsers(File file) {
        User userDataArray[] = new User[linesInFile(userDataCsv)];
        return userDataArray;
    }

    public static Car[] loadCars() {
        Car carDataArray[] = new Car[linesInFile(carDataCsv)];
        return carDataArray; 
    }   

    public static Scanner login(Scanner inputReader){
        // prompts user for a username and password
        // include back option to return to main
        return inputReader;
    }
    public static Scanner register(Scanner inputReader){
        // prompts user for all required data (fname, lname, budget, membership) to create a new user object.
        // the rubric is confusing on what to do after a successful registration, so likely just return back out into main
        // include back option to return to main

        System.out.println("Welcome, new user!");

        try{

            // id and username must be generated.

            System.out.print("\nEnter your first name: ");
            String firstNameIn = inputReader.nextLine();

            System.out.print("\nEnter your last name: ");
            String lastNameIn = inputReader.nextLine();

            System.out.print("\nWhat's your budget? ");
            Double moneyAvailableIn = inputReader.nextDouble();

            System.out.print("\nDo you have a membership with us? (Y/N) ");
            String membershipIn = inputReader.nextLine();
            


            // create customer object and write to userData

        }catch (InputMismatchException e){
            System.out.println("Bad datatype. Please try again!");
        }
        
        return inputReader;

    }
}