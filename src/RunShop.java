import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class RunShop {

    /** Main 
This essentially establishes the arrays for users and cars.
If both arrays return null, then the file isn't found.
**/
static File logFile = new File("log.txt");
static Log log = new Log(logFile);
static Car[] invoiceCars = new Car[100];
static int[] invoiceCustomerIDs = new int[100];
static double[] invoicePrices = new double[100];
static int invoiceCount = 0;

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
                    userDataArray = login(inputReader,userDataArray, carDataArray);
                    break;
                case "1":
                    userDataArray = register(inputReader, userDataArray);
                    break;
                case "2":
                    saveUsers(userDataArray);
                    saveCars(carDataArray);
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

/**
* Counts the number of lines in a file.
* @param file the file to count.
* @return number of lines, or -1 if the files is not found.
*/
   
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

/** 
Takes in the username from the userDataArray, sort of to verify a customer's identity.
If it turns out to be the exact username, it comes out as true.
But if it doesn't it returns as false.
**/

    public static boolean HasDupeUsername(String usernameIn, User[] userDataArray){
        for (int x = 0; x < userDataArray.length; x++){
            if (userDataArray[x].username.equals(usernameIn)){
                return true;
            }
        }
        return false;
    }

    /**
        Loads all users from user_data.csv into an array.
        Skips the header row and ignores malformed lines.
        @return array of User objects, or null if the file is missing.
    **/


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

  
/** Loads all cars from car_data.csv into an array.
Skips the header row and ignores malformed lines. 
@return array of Car objects, or null if the file is missing.
**/


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
   Car[] carDataArray = new Car[dataLines + 100];
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

    /** Prompts for the username and password and logs the user in.
    Directs the user to the customer or admin menu based on their type.
    @param inputReader the scanner for user input.
    @param userDataArray the array of users.
    @param carDataArray the array of cars.
    */
  
public static User[] login(Scanner inputReader, User[] userDataArray, Car[] carDataArray) {
    System.out.print("Username: ");
    String username = inputReader.nextLine();
    System.out.print("Password: ");
    String password = inputReader.nextLine();

    User found = null;
    for (User u : userDataArray) {
        if (u != null && u.getUsername().equals(username) && u.checkPassword(password)) {
            found = u;
            break;
        }
    }

    if (found == null) {
        System.out.println("Invalid username or password.");
        return userDataArray;
    }

    if (found instanceof Customer) {
        log.writeToLog(username+" logged in.");
        customerAction(inputReader, userDataArray, carDataArray, (Customer) found);
    } else if (found instanceof Admin) {
        log.writeToLog("Admin "+username+" logged in.");
        userDataArray = adminAction(inputReader, userDataArray, carDataArray, (Admin) found);
    }

    return userDataArray;
}
    
/** Registers a new customer and appends them to the users CSV.
@param inputReader the scanner for user input.
@param userDataArray the current array of users.
@return the updated user array after reloading.
*/
    
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

                    


                    // create customer object and write to userData


                    try (FileWriter myWriter = new FileWriter("user_data.csv", true)) {
                        myWriter.write((userDataArray.length+1)+","+firstNameIn+","+lastNameIn+","+moneyAvailableIn+","+0+","+membershipIn+","+usernameIn+","+passwordIn+",Customer\n");
                        System.out.println("New user "+usernameIn+" created!");
                        log.writeToLog("New user "+usernameIn+" registered.");
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

    /** Displays the admin menu and handles the admin actions.
    @param inputReader the scanner for user input.
    @param userDataArray the array of users.
    @param carDataArray the array of cars.
    @param adminname the logged-in admin.
    */

    public static User[] adminAction(Scanner inputReader, User[] userDataArray, Car[] carDataArray, Admin adminname){
       
        
        while (true){

           System.out.println(
                    """
                    ADMIN MENU  
                                    
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
                    """);
            System.out.print("Option: ");
            String option = inputReader.nextLine();

            switch (option) {
                case "0":
                    for (User u : userDataArray) {
                        if (u != null) System.out.println(u);
                    }
                    break;

                case "1": // search users
                    System.out.print("Enter search criteria: ");
                    String criterion = inputReader.next();
                    boolean found = false;
                    try{
                        int idCriterion = Integer.parseInt(criterion.trim());
                        for (int x = 0; x < userDataArray.length; x++){
                            if (userDataArray[x].id==idCriterion) {
                                System.out.println(userDataArray[x]);
                                found = true;
                                break;
                            }                           
                        }
                    }catch(NumberFormatException e){
                        for (int x = 0; x < userDataArray.length; x++){
                            if (userDataArray[x].username.contains(criterion) || userDataArray[x].firstName.contains(criterion) || userDataArray[x].lastName.contains(criterion)) {
                                System.out.println(userDataArray[x]);
                                found = true;
                            }                           
                        }

                    }
                    if (!found){
                        System.out.println("User not found!");
                    }
                    break;

                case "2": // add user
                userDataArray = addUser(inputReader, userDataArray);
                break;

                case "3": // update user
                updateUser(inputReader, userDataArray);
                saveUsers(userDataArray);
                break;

                case "4": // delete user
                break;
                case "5": // display cars
                    for (Car c : carDataArray) {
                        if (c != null) c.displayCar();
                    }
                    break;
                case "6": // add car
                    addCar(inputReader, carDataArray);
                    break;
                case "7": // update car
                    break;
                case "8": // delete car
                    deleteCar(inputReader, carDataArray);
                    break;
                case "9": // get revenue
                    System.out.println("Not implemented.");
                    break;
                case "A":
                    System.out.println("Thank you for choosing Miner Car Dealership!");
                    return userDataArray;
                default:
                    System.out.println("Input not recognized. Please try again!");
            }
            System.out.println("=======================================");
        }


    }

public static User[] addUser(Scanner inputReader, User[] userDataArray) {

    System.out.println("\n[][][] ADD NEW USER [][][]");
    System.out.println("[0] Customer");
    System.out.println("[1] Admin");
    System.out.print("Choose user type: ");

    String option = inputReader.nextLine();

    if(!option.equals("0") && !option.equals("1")){
        System.out.println("Invalid user type.");
        return userDataArray;
    }

    System.out.print("Enter first name: ");
    String firstName = inputReader.nextLine();

    System.out.print("Enter last name: ");
    String lastName = inputReader.nextLine();

    System.out.print("Enter username: ");
    String username = inputReader.nextLine();

    boolean usernameTaken = true;

    while(usernameTaken){
        usernameTaken = false;

        for(User u : userDataArray){
            if (u != null && u.getUsername().equalsIgnoreCase(username)){
                usernameTaken = true;
                break;
            }
        }

        if(usernameTaken){
            System.out.println("Username already exists.");
            System.out.print("Enter another username: ");
            username = inputReader.nextLine();
        }
    }

    System.out.print("Enter password: ");
    String password = inputReader.nextLine();

    int newID = 100;

    for(User u : userDataArray){
        if(u != null && u.getId() >= newID){
            newID = u.getId() + 1;
        }
    }

    User newUser;

    if(option.equals("0")){

        System.out.print("Enter money available: $");
        double money = Double.parseDouble(inputReader.nextLine());

        System.out.print("MinerCars Membership (TRUE/FALSE): ");
        boolean membership = inputReader.nextLine().equalsIgnoreCase("TRUE");

        newUser = new Customer(newID, firstName, lastName, username, password, money, 0, membership);
    }else{
        newUser = new Admin(newID, firstName, lastName, username, password);
    }

    User[] updatedArray = new User[userDataArray.length + 1];

    for (int x = 0; x < userDataArray.length; x++) {
        updatedArray[x] = userDataArray[x];
    }

    updatedArray[updatedArray.length - 1] = newUser;

    System.out.println("New user created successfully!");
    System.out.println("User ID: " + newID);

    return updatedArray;
}

public static void updateUser(Scanner inputReader, User[] userDataArray){
    System.out.print("Enter the User ID you want to update: ");
    int id = Integer.parseInt(inputReader.nextLine());
    User found = null;

    for(User u : userDataArray){
        if (u != null && u.getId() == id){
            found = u;
            break;
        }
    }

    if(found == null){
        System.out.println("User not found!");
        return;
    }

    System.out.println("User found: " + found);
    System.out.println("[1] Update First Name");
    System.out.println("[2] Update Last Name");
    System.out.println("[3] Update Username");
    System.out.println("[4] Update Password");
    System.out.println("[0] Back");
    System.out.print("Choose an option: ");

    String choice = inputReader.nextLine();

    switch (choice){
        case "1":
            System.out.print("Enter new first name: ");
            found.setFirstName(inputReader.nextLine());
            break;

        case "2":
            System.out.print("Enter new last name: ");
            found.setLastName(inputReader.nextLine());
            break;

        case "3":
            System.out.print("Enter new username: ");
            String username = inputReader.nextLine();

            boolean duplicate = false;

            for(User u : userDataArray){
                if (u != null && u != found &&
                    u.getUsername().equals(username)){
                    duplicate = true;
                    break;
                }
            }

            if (duplicate){
                System.out.println("Username already exists!");
            }else{
                found.setUsername(username);
                System.out.println("Username updated!");
            }
            break;

        case "4":
            System.out.print("Enter new password: ");
            found.setPassword(inputReader.nextLine());
            break;

        case "0":
            return;

        default:
            System.out.println("Invalid option.");
    }
}

public static void addCar(Scanner inputReader, Car[] carDataArray){
    int id = 1;
    for (Car c : carDataArray){
        if (c != null && c.getId() >= id){
            id = c.getId() + 1;
        }
    }

    System.out.println("\n[][][] Add Car [][][]");
    System.out.println("[1] Sedan");
    System.out.println("[2] SUV");
    System.out.println("[3] Hatchback");
    System.out.println("[4] Pickup");
    System.out.print("Choose car type: ");

    String choice = inputReader.nextLine();
    String type = "";

    switch (choice) {
        case "1":
            type = "Sedan";
            break;
        case "2":
            type = "SUV";
            break;
        case "3":
            type = "Hatchback";
            break;
        case "4":
            type = "Pickup";
            break;
        default:
            System.out.println("Invalid car type.");
            return;
    }

    try{
        System.out.print("Enter model: ");
        String model = inputReader.nextLine();

        System.out.print("Enter condition (New/Used): ");
        String condition = inputReader.nextLine();

        System.out.print("Enter color: ");
        String color = inputReader.nextLine();

        System.out.print("Enter capacity: ");
        int capacity = Integer.parseInt(inputReader.nextLine());

        System.out.print("Enter mileage: ");
        int mileage = Integer.parseInt(inputReader.nextLine());

        System.out.print("Enter fuel type: ");
        String fuelType = inputReader.nextLine();

        System.out.print("Enter transmission: ");
        String transmission = inputReader.nextLine();

        System.out.print("Enter VIN: ");
        String vin = inputReader.nextLine();

        System.out.print("Enter price: ");
        double price = Double.parseDouble(inputReader.nextLine());

        System.out.print("Enter number of cars available: ");
        int carsAvailable = Integer.parseInt(inputReader.nextLine());

        if (capacity <= 0 || mileage < 0 || price < 0 || carsAvailable < 0){
            System.out.println("Invalid number entered.");
            return;
        }

        Car newCar = null;

        if (type.equals("Sedan")){
            newCar = new Sedan(id, model, condition, color, capacity,
                    mileage, fuelType, transmission, vin, price, carsAvailable);
        } else if (type.equals("SUV")){
            newCar = new SUV(id, model, condition, color, capacity,
                    mileage, fuelType, transmission, vin, price, carsAvailable);
        } else if (type.equals("Hatchback")){
            newCar = new Hatchback(id, model, condition, color, capacity,
                    mileage, fuelType, transmission, vin, price, carsAvailable);
        } else if (type.equals("Pickup")){
            newCar = new Pickup(id, model, condition, color, capacity,
                    mileage, fuelType, transmission, vin, price, carsAvailable);
        }

        int emptySpot = -1;
        for(int x = 0; x < carDataArray.length; x++){
            if(carDataArray[x] == null){
                emptySpot = x;
                break;
            }
        }

        if(emptySpot == -1){
            System.out.println("No room for another car.");
            return;
        }

        carDataArray[emptySpot] = newCar;

        System.out.println("Car added successfully!");
        newCar.displayCar();

        log.writeToLog("Admin added car ID " + id);

    } catch (NumberFormatException e) {
        System.out.println("Please enter valid numbers.");
    }
}

/** Displays the customer menu and it handles the customer's actions.
@param inputReader the scanner for user input.
@param userDataArray the array of users.
@param carDataArray the array of cars.
@param customer the logged-in customer.
*/
    
    public static void customerAction(Scanner inputReader, User[] userDataArray, Car[] carDataArray, Customer customer){
       
while (true) {
         System.out.println(
                """
                CUSTOMER MENU

                [0] ---------------------- Manage Profile
                [1] ---------------------- Browse Cars
                [2] ---------------------- Purchase Car
                [3] ---------------------- View Invoice
                [4] ---------------------- Return Car
                [5] ---------------------- Log Out
                """);
    System.out.println("Option: ");
    String option = inputReader.nextLine();

    switch (option) {
        case "0":
            manageProfile(inputReader, userDataArray, customer);
            break;
        case "1":  // browse (display/filter) cars
            browseCars(inputReader, carDataArray);
            break;
        case "2": // purchase car
            purchaseCar(inputReader, customer, carDataArray, userDataArray);
            break;
        case "3": //view invoice
            viewInvoice(customer);
            break;
        case "4": // return car
            returnCar(inputReader, customer, carDataArray);
            break;
        case "5":
            System.out.println("Thank you for choosing Miner Car Dealership!");
            return;
        default:
            System.out.println("Input not recognized. Please try again!");
    }
}

    }

public static void browseCars(Scanner inputReader, Car[] carDataArray){
    String option;
    boolean found = false;
    System.out.println(
                """
                BROWSE CARS

                [0] ---------------------- Display Cars
                [1] ---------------------- Filter Cars
                """);
            System.out.println("Option: ");
            option = inputReader.nextLine();
            if (option.equals("0")){
                for (Car c: carDataArray) {
                    if (c != null) c.displayCar();
                }
                return;
            }else if (option.equals("1")){ 

                System.out.print("""
                    Choose search criteria: 

                    [0] ---------------------- ID
                    [1] ---------------------- Car Type
                    [2] ---------------------- Condition
                    [3] ---------------------- Price
                """);
                System.out.println("Option: ");
                option = inputReader.nextLine();
                
                switch (option){
                    case "0":
                        try {
                        System.out.print("Enter ID: ");
                        String criterion = inputReader.next();
                        int idCriterion = Integer.parseInt(criterion);
                        for (int x = 0; x < carDataArray.length; x++){
                            if (carDataArray[x].id==idCriterion) {
                                carDataArray[x].displayCar();
                                return;
                            }                           
                        }
                        } catch (NumberFormatException e){
                            System.out.println("Mismatched input. Returning...");
                            return;
                        }
                        break;
                    case "1":
                        System.out.print("""
                            Choose search criteria: 
                            
                            [0] ---------------------- Hatchback
                            [1] ---------------------- Pickup
                            [2] ---------------------- Sedan
                            [3] ---------------------- SUV
                        """);
                        System.out.println("Option: ");
                        option = inputReader.nextLine();
                        switch (option){
                            case "0":
                                for (int x = 0; x < carDataArray.length; x++){
                                    if (carDataArray[x].getCarType().equals("Hatchback")) {
                                        carDataArray[x].displayCar();
                                        found = true;
                                    }                           
                                }
                                break;
                            case "1":
                                for (int x = 0; x < carDataArray.length; x++){
                                    if (carDataArray[x].getCarType().equals("Pickup")) {
                                        carDataArray[x].displayCar();
                                        found = true;
                                    }                           
                                }
                                break;
                            case "2":
                                for (int x = 0; x < carDataArray.length; x++){
                                    if (carDataArray[x].getCarType().equals("Sedan")) {
                                        carDataArray[x].displayCar();
                                        found = true;
                                    }                           
                                }
                                break;
                            case "3":
                                for (int x = 0; x < carDataArray.length; x++){
                                    if (carDataArray[x].getCarType().equals("SUV")) {
                                        carDataArray[x].displayCar();
                                        found = true;
                                    }                           
                                }
                                break;
                            default:
                                System.out.println("Input not recognized. Returning...");
                                return;
                        }
                        break;
                    case "2":
                        System.out.print("""
                            Choose search criteria: 

                            [0] ---------------------- New
                            [1] ---------------------- Used
                        """);
                        System.out.println("Option: ");
                        option = inputReader.nextLine();
                        switch (option){
                            case "0":
                                for (int x = 0; x < carDataArray.length; x++){
                                    if (carDataArray[x].condition.equals("New")) {
                                        carDataArray[x].displayCar();
                                        found = true;
                                    }                           
                                }
                                break;
                            case "1":
                                for (int x = 0; x < carDataArray.length; x++){
                                    if (carDataArray[x].condition.equals("Used")) {
                                        carDataArray[x].displayCar();
                                        found = true;
                                    }                           
                                }
                                break;
                            default:
                                System.out.println("Input not recognized. Returning...");
                                return;
                        }
                        break;
                    case "3":
                        try {
                        System.out.print("Enter price: ");
                        String criterion = inputReader.next();
                        int priceCriterion = Integer.parseInt(criterion);
                        for (int x = 0; x < carDataArray.length; x++){
                            if (Math.abs(carDataArray[x].price-priceCriterion)<=5000) {
                                carDataArray[x].displayCar();
                                found = true;
                            }                           
                        }
                        break;
                        } catch (NumberFormatException e){
                            System.out.println("Mismatched input. Returning...");
                            return;
                        }

                    default:
                        System.out.println("Input not recognized. Returning...");
                        return;
                }
                
                if (!found){
                    System.out.println("freka");
                }
            }else{
                System.out.println("Input not recognized. Returning...");
            }
}

public static void deleteCar(Scanner inputReader, Car[] carDataArray){
    System.out.print("Enter the Car ID you want to delete: ");
    String criterion = inputReader.next();
    try{
        int idCriterion = Integer.parseInt(criterion);
        for (int x = 0; x < carDataArray.length; x++){
            if (carDataArray[x].id==idCriterion) {
                carDataArray[x]=null;
                saveCars(carDataArray);
                System.out.println("Car deleted.");
                return;
            }                           
        }
        System.out.println("Car not found.");
        return;
    } catch (NumberFormatException e){
        System.out.println("Input type mismatch. Please enter a number.");
        return;
    }
}

public static void purchaseCar(Scanner inputReader, Customer customer, Car[] carDataArray, User[] userDataArray){
    System.out.print("Enter the Car ID you want to purchase: ");
    int carID = inputReader.nextInt();
    inputReader.nextLine();

    boolean found = false;

    for(int x = 0; x < carDataArray.length; x++){
        if(carDataArray[x] != null && carDataArray[x].getId() == carID){
            found = true;
            Car car = carDataArray[x];

            if(car.getCarsAvailable() <= 0){
                System.out.println("This car is not available.");
            }
            else if(customer.getMoneyAvailable() < car.getPrice()){
                System.out.println("You do not have enough money to purchase this car.");
            }
            else{
                customer.setMoneyAvailable(customer.getMoneyAvailable() - car.getPrice());
                car.setCarsAvailable(car.getCarsAvailable() - 1);
                customer.setCarsPurchased(customer.getCarsPurchased() + 1);

                invoiceCars[invoiceCount] = car;
                invoiceCustomerIDs[invoiceCount] = customer.getId();
                invoicePrices[invoiceCount] = car.getPrice();
                invoiceCount++;

                System.out.println("Car purchased successfully!");
                car.displayCar();
                System.out.println("Remaining Money: $" + customer.getMoneyAvailable());

                saveCars(carDataArray);
                saveUsers(userDataArray);
            }

            break;
        }
    }

    if (!found) {
        System.out.println("Car ID not found.");
    }
}


public static void viewInvoice(Customer customer) {

    boolean found = false;
    double total = 0;

    System.out.println("\n[][][] PURCHASE INVOICE [][][]");
    System.out.println("Customer: " + customer.getFirstName() + " " + customer.getLastName());
    System.out.println("Customer ID: " + customer.getId());

    for (int x = 0; x < invoiceCount; x++) {

        if (invoiceCustomerIDs[x] == customer.getId()) {

            found = true;

            System.out.println("\nCar ID: " + invoiceCars[x].getId());
            System.out.println("Car Type: " + invoiceCars[x].getCarType());
            System.out.println("Model: " + invoiceCars[x].getModel());
            System.out.println("Price: $" + invoicePrices[x]);

            total = total + invoicePrices[x];
        }
    }

    if (found) {
        System.out.println("\nTotal: $" + total);
    }
    else {
        System.out.println("No purchases found.");
    }
}


public static void returnCar(Scanner inputReader, Customer customer, Car[] carDataArray){

    System.out.print("Enter the Car ID you want to return: ");
    int carID = inputReader.nextInt();
    inputReader.nextLine();

    boolean found = false;
    

    for(int x = 0; x < invoiceCount; x++){
        if(invoiceCustomerIDs[x] == customer.getId() && invoiceCars[x].getId() == carID){
            found = true;
            for(int y = 0; y < carDataArray.length; y++){
                if(carDataArray[y] != null && carDataArray[y].getId() == carID){
                    carDataArray[y].setCarsAvailable(carDataArray[y].getCarsAvailable() + 1);
                    break;
                }
            }
            double refund = invoicePrices[x];
            customer.setMoneyAvailable(customer.getMoneyAvailable() + refund);
            customer.setCarsPurchased(customer.getCarsPurchased() - 1);
            for(int y = x; y < invoiceCount - 1; y++){
                invoiceCars[y] = invoiceCars[y + 1];
                invoiceCustomerIDs[y] = invoiceCustomerIDs[y + 1];
                invoicePrices[y] = invoicePrices[y + 1];
            }

            invoiceCount--;

            System.out.println("Car returned successfully!");
            System.out.println("Refund: $" + refund);
            break;
        }
    }

    if (!found) {
        System.out.println("You have not purchased a car with that ID.");
    }
}



    public static User findByUsername(String username, User[] userDataArray){
        for (int x = 0; x < userDataArray.length; x++){
                if (userDataArray[x].username.equals(username)) {
                return userDataArray[x];
            }                           
        }
        return null;
    }

      /** 
This saves the users data. which overwrites user_data.csv
If it isn't true, then it overwrites.
**/

public static void saveUsers(User[] userDataArray) {
    try (FileWriter writer = new FileWriter("user_data.csv")) {
        writer.write("ID,First Name,Last Name,Money Available,Cars Purchased,MinerCars Membership,Username,Password,User Type\n");
//header
        for (User u : userDataArray) {
            if (u == null) continue;

            if (u instanceof Customer) {
                Customer c = (Customer) u;
                writer.write(c.getId() + "," +
                             c.getFirstName() + "," +
                             c.getLastName() + "," +
                             c.getMoneyAvailable() + "," +
                             c.getCarsPurchased() + "," +
                             (c.hasMembership() ? "TRUE" : "FALSE") + "," +
                             c.getUsername() + "," +
                             c.getPassword() + "," +
                             "Customer\n");
            } else { // Admin
                writer.write(u.getId() + "," +
                             u.getFirstName() + "," +
                             u.getLastName() + "," +
                             ",,," +
                             u.getUsername() + "," +
                             u.getPassword() + "," +
                             "Admin\n");
            }
        }
    } catch (IOException e) {
        System.out.println("Failed to save users: " + e.getMessage());
    }
}

  /**
This saves the cars data. which overwrites car_data.csv
If it isn't true, then it overwrites.
*/

public static void saveCars(Car[] carDataArray){
    try (FileWriter writer = new FileWriter("car_data.csv")) {
        // header
    writer.write("ID,Car Type,Model,Condition,Color,Capacity,Mileage,Fuel Type,Transmission,VIN,Price,Cars Available\n");
        for (Car c : carDataArray) {
           if (c == null) continue;
            writer.write(c.getId() + "," +
            c.getCarType() + "," +
            c.getModel() + "," +
            c.getCondition() + "," +
            c.getColor() + "," +
            c.getCapacity() + "," +
            c.getMileage() + "," +
            c.getFuelType() + "," +
            c.getTransmission() + "," +
            c.getVIN() + "," +
            c.getPrice() + "," +
            c.getCarsAvailable() + "\n");
        }
    } catch (IOException e) {
        System.out.println("Failed to save cars: " + e.getMessage());
    }
}




public static void manageProfile(Scanner inputReader, User[] userDataArray, Customer customer){
    String option;
    do{
        System.out.println("""
                MANAGE PROFILE
                [0] ---------------------- View Profile
                [1] ---------------------- Change Username
                [2] ---------------------- Change Password
                [3] ---------------------- Add Money
                [4] ---------------------- Back
                """);

        System.out.print("Option: ");
        option = inputReader.nextLine();

        switch (option){
            case "0":
                System.out.println(customer);
                break;

            case "1":
                System.out.print("Enter new username: ");
                String newUsername = inputReader.nextLine();

                boolean usernameTaken = false;

                for(User user : userDataArray){
                    if (user != null && user != customer && user.getUsername().equalsIgnoreCase(newUsername)){
                        usernameTaken = true;
                        break;
                    }
                }

                if(newUsername.isBlank()){
                    System.out.println("Username cannot be empty.");
                }else if(usernameTaken){
                    System.out.println("Username is already taken.");
                }else{
                    customer.setUsername(newUsername);
                    saveUsers(userDataArray);
                    System.out.println("Username updated!");
                    
                }
                break;

            case "2":
                System.out.print("Enter new password: ");
                String newPassword = inputReader.nextLine();

                if(newPassword.isBlank()){
                    System.out.println("Password cannot be empty.");
                }else{
                    customer.setPassword(newPassword);
                    saveUsers(userDataArray);
                    System.out.println("Password updated!");
                }
                break;

            case "3":
                System.out.print("How much money would you like to add? $");

                try{
                    double amount = Double.parseDouble(inputReader.nextLine());

                    if(amount > 0){
                        customer.setMoneyAvailable(customer.getMoneyAvailable() + amount);
                        saveUsers(userDataArray);
                        System.out.println("Money added successfully!");
                    }else{
                        System.out.println("Enter an amount greater than zero.");
                    }
                }catch(NumberFormatException e){
                    System.out.println("Please enter a valid amount.");
                }
                break;

            case "4":
                System.out.println("Returning to main menu");
                break;

            default:
                System.out.println("Invalid option. Try again.");
        }

    }while (!option.equals("4"));
}


}

