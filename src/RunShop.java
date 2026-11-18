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
                    login(inputReader,userDataArray, carDataArray);
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

    /** Prompts for the username and password and logs the user in.
    Directs the user to the customer or admin menu based on their type.
    @param inputReader the scanner for user input.
    @param userDataArray the array of users.
    @param carDataArray the array of cars.
    */
  
public static void login(Scanner inputReader, User[] userDataArray, Car[] carDataArray) {
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
        return;
    }

    if (found instanceof Customer) {
        log.writeToLog(username+" logged in.");
        customerAction(inputReader, userDataArray, carDataArray, (Customer) found);
    } else if (found instanceof Admin) {
        log.writeToLog("Admin "+username+" logged in.");
        adminAction(inputReader, userDataArray, carDataArray, (Admin) found);
    }
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

                    System.out.println("New user "+usernameIn+" created!");


                    // create customer object and write to userData


                    try (FileWriter myWriter = new FileWriter("user_data.csv", true)) {
                        myWriter.write((userDataArray.length+1)+","+firstNameIn+","+lastNameIn+","+moneyAvailableIn+","+0+","+membershipIn+","+usernameIn+","+passwordIn+",Customer\n");
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

    public static void adminAction(Scanner inputReader, User[] userDataArray, Car[] carDataArray, Admin adminname){
       
        
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
                case "2": // add user
                break;
                case "3": // update user
                break;
                case "4": // delete user
                break;
                case "5": // display cars
                    for (Car c : carDataArray) {
                        if (c != null) c.displayCar();
                    }
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
                case "A":
                    System.out.println("Thank you for choosing Miner Car Dealership!");
                    return;
                default:
                    System.out.println("Input not recognized. Please try again!");
            }
            System.out.println("=======================================");
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
        System.out.println(
                """
                BROWSE CARS

                [0] ---------------------- Display Car
                [1] ---------------------- Filter Cars
                """);
            System.out.println("Option: ");
            option = inputReader.nextLine();
            if (option.equals("0")){
                for (Car c: carDataArray) {
                    if (c != null) c.displayCar();
                }
                break;
            }else if (option.equals("1")){ //placeholder, fix shortly
                
                boolean found = false;
                System.out.print("""
                    Choose search criteria: 
                    [0] ---------------------- ID
                    [1] ---------------------- Car Type
                    [2] ---------------------- Condition
                    [3] ---------------------- Price
                """);
                System.out.println("Option: ");
                option = inputReader.nextLine();
                System.out.print("Enter search criteria: ");
                String criterion = inputReader.next();
                switch (option){
                    case "0":
                        try {
                        int idCriterion = Integer.parseInt(criterion);
                        for (int x = 0; x < carDataArray.length; x++){
                            if (carDataArray[x].id==idCriterion) {
                                System.out.println(carDataArray[x]);
                                found = true;
                                break;
                            }                           
                        }
                        } catch (NumberFormatException e){
                            System.out.println("Mismatched input. Returning...");
                        } finally {
                        break;
                        }
                        
                    case "1":
                        break;
                    case "2":
                        break;
                    case "3":
                        break;
                    default:
                        System.out.println("Input not recognized. Returning...");
                        break;
                }
                if (!found){
                        System.out.println("Car not found!");
                }
                
            }else{
                System.out.println("Input not recognized. Returning...");
                break;
            }
        case "2": // purchase car
            purchaseCar(inputReader, customer, carDataArray);
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


public static void purchaseCar(Scanner inputReader, Customer customer, Car[] carDataArray){
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

  /* 
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
                    System.out.println("Password updated!");
                }
                break;

            case "3":
                System.out.print("How much money would you like to add? $");

                try{
                    double amount = Double.parseDouble(inputReader.nextLine());

                    if(amount > 0){
                        customer.setMoneyAvailable(customer.getMoneyAvailable() + amount);
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

