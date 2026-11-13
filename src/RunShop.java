import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner; 

public class RunShop {

    static File userData = new File("user_data.csv");
    static File carData = new File("car_data.csv");

    public static void main(String[] args) {

        System.out.println("Welcome to Miner Cars Dealership!");
        // when not in login or register menu, return here so the user may pick from one

        //DEBUG
        register();
    }


    public static void login(){
        // prompts user for a username and password
        // include back option to return to main

    }
    public static void register(){
        // prompts user for all required data (fname, lname, budget, membership) to create a new user object.
        // the rubric is confusing on what to do after a successful registration, so likely just return back out into main
        // include back option to return to main

        Scanner inputReader = new Scanner(System.in);
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
            // convert string to bool

            // create customer object and write to userData

        }catch (InputMismatchException e){
            System.out.println("Bad datatype. Please try again!");
        }
        
        inputReader.close();

    }
}