import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;


public class RunShop {

    public static void main(String[] args) {
        User[] userDataArray = loadUsers();
        Car[] carDataArray = loadCars();
        if (userDataArray==null || carDataArray==null){
            System.out.println("Files not found.");
            return;
        }
        Scanner inputReader = new Scanner(System.in);
        String option;

        System.out.println("Welcome to Miner Cars Dealership!");

        OUTER:
        while (true) {
            System.out.println(
                """
                MAIN MENU
                                    
                [0] --- Existing User? --- LOG IN
                [1] ------ New User? ----- REGISTER
                [2] ---------------------- EXIT
                """
            );

            System.out.print("\nOption: ");
            option = inputReader.nextLine();
            switch (option) {
                case "0":
                    login(inputReader,userDataArray);
                    break;
                case "1":
                    userDataArray = register(inputReader, userDataArray);
                    break;
                case "2":
                    System.out.println("Thank you for using Miner Cars Dealership!");
                    inputReader.close();
                    break OUTER;
                default:
                    System.out.println("Input not recognized. Please try again!");
                    break;
            }
            System.out.println("=======================================");
        }
        
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

    public static boolean HasDupeUsername(String usernameIn, User[] userDataArray){
        for (int x = 0; x < userDataArray.length; x++){
            if (userDataArray[x].username.equals(usernameIn)){
                return true;
            }
        }
        return false;
    }

    public static User[] loadUsers() {
        File userDataCsv = new File("user_data.csv");
        try {
            User userDataArray[] = new User[linesInFile(userDataCsv)];
        
        

        int idIn;
        String firstNameIn;
        String lastNameIn;
        double moneyAvailableIn;
        int carsPurchasedIn;
        String membershipString;
        boolean membershipIn;
        String usernameIn;
        String passwordIn;
        String userType;

        try {
            int count = 0;
            Scanner fileReader = new Scanner(userDataCsv);
            fileReader.useDelimiter(",");

            while (fileReader.hasNextLine()){
                if (count==0){
                    fileReader.nextLine(); //discard header
                }
                
                
                idIn = fileReader.nextInt();
                firstNameIn = fileReader.next();
                lastNameIn = fileReader.next();
                moneyAvailableIn = fileReader.nextDouble();
                carsPurchasedIn = fileReader.nextInt();
                membershipString = fileReader.next();
                usernameIn = fileReader.next();
                passwordIn = fileReader.next();
                fileReader.useDelimiter("\n");

                userType = fileReader.next();
                userType=userType.substring(1,userType.length());
                userType=userType.trim();

                membershipIn = membershipString.equals("TRUE");

                System.out.println(idIn+firstNameIn+lastNameIn+usernameIn+passwordIn+moneyAvailableIn+carsPurchasedIn+membershipIn);
                fileReader.useDelimiter(",");

                if (userType.equals("Customer")){
                    userDataArray[count] = new Customer(idIn, firstNameIn, lastNameIn, usernameIn, passwordIn, moneyAvailableIn, carsPurchasedIn, membershipIn);
                } else {
                    userDataArray[count] = new Admin(idIn, firstNameIn, lastNameIn, usernameIn, passwordIn);
                }
                count++;
            }
            fileReader.close();

            //debug
            for (int x = 0; x < userDataArray.length; x++){
                System.out.println(userDataArray[x].firstName);
            }

        } catch (FileNotFoundException e) {
        }
        
        return userDataArray;
        } catch (NegativeArraySizeException e) {
            return null;
        }
    }

    public static Car[] loadCars() {
        File carDataCsv = new File("car_data.csv");
        try{
        Car carDataArray[] = new Car[linesInFile(carDataCsv)];


        int idIn;
        String carType;
        String modelIn;
        String conditionIn;
        String colorIn;
        int capacityIn;
        int mileageIn;
        String fuelTypeIn;
        String transmissionIn;
        String vinIn;
        double priceIn;
        int carsAvailableIn;

        try {
            int count = 0;
            Scanner fileReader = new Scanner(carDataCsv);
            fileReader.useDelimiter(",");

            while (fileReader.hasNextLine()){
                if (count==0){
                    fileReader.nextLine(); //discard header
                }
                idIn = fileReader.nextInt();
                carType = fileReader.next();
                modelIn = fileReader.next();
                conditionIn = fileReader.next();
                colorIn = fileReader.next();
                capacityIn = fileReader.nextInt();
                mileageIn = fileReader.nextInt();
                fuelTypeIn = fileReader.next();
                transmissionIn = fileReader.next();
                vinIn = fileReader.next();
                priceIn = fileReader.nextDouble();
                carsAvailableIn = fileReader.nextInt();

                switch (carType){
                    case "Sedan":
                        carDataArray[count] = new Sedan(idIn, modelIn, conditionIn, colorIn, capacityIn, mileageIn, fuelTypeIn, transmissionIn, vinIn, priceIn, carsAvailableIn);
                        break;
                    case "SUV":
                        carDataArray[count] = new SUV(idIn, modelIn, conditionIn, colorIn, capacityIn, mileageIn, fuelTypeIn, transmissionIn, vinIn, priceIn, carsAvailableIn);
                        break;
                    case "Pickup":
                        carDataArray[count] = new Pickup(idIn, modelIn, conditionIn, colorIn, capacityIn, mileageIn, fuelTypeIn, transmissionIn, vinIn, priceIn, carsAvailableIn);
                        break;
                    case "Hatchback":
                        carDataArray[count] = new Hatchback(idIn, modelIn, conditionIn, colorIn, capacityIn, mileageIn, fuelTypeIn, transmissionIn, vinIn, priceIn, carsAvailableIn);
                        break;
                    default:
                        carDataArray[count] = new Sedan(idIn, modelIn, conditionIn, colorIn, capacityIn, mileageIn, fuelTypeIn, transmissionIn, vinIn, priceIn, carsAvailableIn);
                        break;
                }
                count++;
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
        }
        return carDataArray; 
        } catch (NegativeArraySizeException e) {
            return null;
        }
    }   

    public static Scanner login(Scanner inputReader, User[] userDataArray){
        // prompts user for a username and password
        // include back option to return to main
        return inputReader;
    }
    public static User[] register(Scanner inputReader, User[] userDataArray){
        // prompts user for all required data (fname, lname, budget, membership) to create a new user object.
        // the rubric is confusing on what to do after a successful registration, so likely just return back out into main
        // include back option to return to main

        String option;

        System.out.println("Welcome, new user!");
        System.out.println("[0] ---------------------- REGISTER\n[1] ---------------------- CANCEL");
        System.out.print("\nOption: ");
        option = inputReader.nextLine();

        switch (option) {
            case "0":
                try{

                    // id and username must be generated.
                    // iterate through list of users and see if there are any matching names? throw exception if so

                    System.out.print("\nEnter your first name: ");
                    String firstNameIn = inputReader.nextLine();

                    System.out.print("\nEnter your last name: ");
                    String lastNameIn = inputReader.nextLine();

                    System.out.print("\nEnter your username: ");
                    String usernameIn = inputReader.nextLine();
                    while (HasDupeUsername(usernameIn, userDataArray)){
                        System.out.print("\nThis username is taken!");
                        System.out.print("\nEnter your username: ");
                        usernameIn = inputReader.nextLine();
                    }

                    System.out.print("\nEnter your password: ");
                    String passwordIn = inputReader.nextLine();

                    System.out.print("\nWhat's your budget? ");
                    Double moneyAvailableIn = inputReader.nextDouble();

                    System.out.print("\nDo you have a membership with us? (TRUE/FALSE) ");
                    String membershipIn = inputReader.nextLine().toUpperCase();
                    while (!(membershipIn.equals("TRUE") || membershipIn.equals("FALSE"))) {
                        System.out.println("Please enter TRUE or FALSE!");
                        System.out.print("\nDo you have a membership with us? (TRUE/FALSE) ");
                        membershipIn = inputReader.nextLine().toUpperCase();
                    }

                    // create customer object and write to userData

                    try (FileWriter myWriter = new FileWriter("user_data.csv", true)) {
                        myWriter.write(userDataArray.length+","+firstNameIn+","+lastNameIn+","+moneyAvailableIn+","+0+","+membershipIn+","+usernameIn+","+passwordIn+",Customer\n");
                    } catch (IOException e) {
                        System.out.println("An error occurred.");
                    }

                }catch (InputMismatchException e){
                    System.out.println("\nBad datatype. Please try again!");
                }
                break;
            case "1":
                System.out.println("\nReturning to main menu...");
                break;
            default:
                System.out.println("\nInput not recognized. Please try again!");
                break;
            }
            
        userDataArray = loadUsers();
        return userDataArray;

    }
}