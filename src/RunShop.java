import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class RunShop {

    /* Main 
This essentially establishes the arrays for users and cars.
If both arrays return null, then the file isn't found.
*/


    public static void main(String[] args) {
        User[] userDataArray = loadUsers();
        Car[] carDataArray = loadCars();
        if (userDataArray==null || carDataArray==null){
            System.out.println("Files not found.");
            return;
        }
        Scanner inputReader = new Scanner(System.in);
        String option;

        /* 
        DEBUG
        for (int x = 0; x < userDataArray.length; x++){
            System.out.println(userDataArray[x]);
        }
        */


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

/* 
Takes in the username from the userDataArray, sort of to verify a customer's identity.
If it turns out to be the exact username, it comes out as true.
But if it doesn't it returns as false.
*/

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
   if (!userDataCsv.exists()) {
    return null;
   }


   // First pass: count data lines only
   int dataLines = 0;
   try (Scanner counter = new Scanner(userDataCsv)){
    if (counter.hasNextLine()) counter.nextLine(); // skips header
    while (counter.hasNextLine()){
      counter.nextLine();
      dataLines++;
    }
   } catch (FileNotFoundException e) {
    return null;
   }
   User[] userDataArray = new User[dataLines];
   int count = 0;


  try (Scanner fileReader = new Scanner(userDataCsv)) {
    if (fileReader.hasNextLine()) fileReader.nextLine(); // skip header


    while (fileReader.hasNextLine() && count < dataLines) {
      String line = fileReader.nextLine().trim();
      if (line.isEmpty()) continue;


      String[] parts = line.split(",");
      if (parts.length < 9) continue; // malformed line


      try{
        int idIn = Integer.parseInt(parts[0].trim());
        String firstNameIn = parts[1].trim();
        String lastNameIn = parts[2].trim();
        double moneyAvailableIn = 0;
        if (!parts[3].trim().isEmpty()){
            moneyAvailableIn = Double.parseDouble(parts[3].trim());
        }
        int carsPurchasedIn = 0;
        if (!parts[4].trim().isEmpty()){
            carsPurchasedIn = Integer.parseInt(parts[4].trim());
        }
        boolean membershipIn = false;
        if (!parts[5].trim().isEmpty()){
            membershipIn = parts[5].trim().equalsIgnoreCase("TRUE");
        }
        String usernameIn = parts[6].trim();
        String passwordIn = parts[7].trim();
        String userType = parts[8].trim();
        if (userType.equalsIgnoreCase("Customer")) {
          userDataArray[count] = new Customer(idIn, firstNameIn, lastNameIn, usernameIn, passwordIn, moneyAvailableIn, carsPurchasedIn, membershipIn);
        } else {
          userDataArray[count] = new Admin(idIn, firstNameIn, lastNameIn, usernameIn, passwordIn);
        }
        count++;
      } catch (NumberFormatException e) {
        System.out.println("Skipping bad user line: " + line);
      }
    }
  } catch (FileNotFoundException e) {
    return null;
  }


  return userDataArray;
    }


    public static Car[] loadCars() {
        File carDataCsv = new File("car_data.csv");
        if (!carDataCsv.exists()) {
    return null;
   }


   // First pass: count data lines only
   int dataLines = 0;
   try (Scanner counter = new Scanner(carDataCsv)){
    if (counter.hasNextLine()) counter.nextLine(); // skips header
    while (counter.hasNextLine()){
      counter.nextLine();
      dataLines++;
    }
   } catch (FileNotFoundException e) {
    return null;
   }
   Car[] carDataArray = new Car[dataLines];
   int count = 0;


  try (Scanner fileReader = new Scanner(carDataCsv)) {
    if (fileReader.hasNextLine()) fileReader.nextLine(); // skip header


    while (fileReader.hasNextLine() && count < dataLines) {
      String line = fileReader.nextLine().trim();
      if (line.isEmpty()) continue;


      String[] parts = line.split(",");
      if (parts.length < 12) continue; // malformed line


      try{
        int idIn = Integer.parseInt(parts[0].trim());
        String carType = parts[1].trim();
        String modelIn = parts[2].trim();
        String conditionIn = parts[3].trim();
        String colorIn = parts[4].trim();
        int capacityIn = Integer.parseInt(parts[5].trim());
        int mileageIn = Integer.parseInt(parts[6].trim());
        String fuelTypeIn = parts[7].trim();
        String transmissionIn = parts[8].trim();
        String vinIn = parts[9].trim();
        double priceIn = Double.parseDouble(parts[10].trim());
        int carsAvailableIn = Integer.parseInt(parts[11].trim());


      switch (carType) {
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
      } catch (NumberFormatException e) {
        System.out.println("Skipping bad car line: " + line);
      }
    }
  } catch (FileNotFoundException e) {
    return null;
  }


  return carDataArray;
    }  

    public static User findByUsername(String username){
        return null;
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
                    inputReader.nextLine();
                    String membershipIn = inputReader.nextLine().toUpperCase();
                    while (!(membershipIn.equals("TRUE") || membershipIn.equals("FALSE"))) {
                        System.out.println("Please enter TRUE or FALSE!");
                        System.out.print("\nDo you have a membership with us? (TRUE/FALSE) ");
                        membershipIn = inputReader.nextLine().toUpperCase();
                    }

                    System.out.println("New user "+usernameIn+" created!");


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
    

    public static void adminAction(Scanner inputReader, User[] userDataArray, Car[] carDataArray, Admin adminname){
        String option;
        
        while (true){

            System.out.println(
                    """
                    MAIN MENU
                                    
                    [0] ---------------------- Display Users
                    [1] ---------------------- Search Users
                    [2] ---------------------- Add User
                    [3] ---------------------- Update User
                    [4] ---------------------- Delete User
                    [5] ---------------------- Display Cars
                    [6] ---------------------- Add Car
                    [7] ---------------------- Update Car
                    [8] ---------------------- Delete Car
                    [9] ---------------------- Get Revenue
                    [A] ---------------------- Log out
                    """
                );


            System.out.print("\nOption: ");
            option = inputReader.nextLine();
            switch (option) {
                case "0": // display users
                    for (int x = 0; x < userDataArray.length; x++){
                        System.out.println(userDataArray[x]);
                    }
                    break;
                case "1": // search users
                    break;
                case "2": // add user
                    break;
                case "3": // update user
                    break;
                case "4": // delete user
                    break;
                case "5": // display cars
                    break;
                case "6": // add car
                    break;
                case "7": // update car
                    break;
                case "8": // delete car 
                    break;
                case "9": // get revenue
                    System.out.println("Not implemented.");
                    break;
                case "A": // log out
                    System.out.println("Thank you for choosing Miner Car Dealership!");
                    return;
                default:
                    System.out.println("Input not recognized. Please try again!");
                    break;
                }
            System.out.println("=======================================");
        }


    }

    public static void customerAction(Scanner inputReader, User[] userDataArray, Car[] carDataArray, Customer customer){
        String option;

        System.out.println(
                """
                MAIN MENU
                                   
                [0] ---------------------- Manage Profile (view + update/change user/pass/money)
                [1] ---------------------- Browse Cars (display+filter)
                [2] ---------------------- Purchase Car
                [3] ---------------------- View Invoice
                [4] ---------------------- Return Car
                [5] ---------------------- Log Out
                """
            );


        System.out.print("\nOption: ");
        option = inputReader.nextLine();
        switch (option) {
            case "0": // manage profile INTO view profile update profile INTO change username change password update money
                break;
            case "1": // browse cars INTO display cars filter cars
                break;
            case "2": // purchase car
                break;
            case "3": // view invoice 
                System.out.println("Not implemented.");
                break;
            case "4": // return car
                System.out.println("Not implemented.");
                break;
            case "5": // log out
                System.out.println("Thank you for choosing Miner Car Dealership!");
                return;
            default:
                System.out.println("Input not recognized. Please try again!");
                break;
            }
        System.out.println("=======================================");
    }
}

