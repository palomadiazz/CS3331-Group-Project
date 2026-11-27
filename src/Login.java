import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Login {
    private Scanner inputReader;
    private User[] userDataArray;

    public Login(Scanner inputReader, User[] userDataArray){
        this.inputReader = inputReader;
        this.userDataArray = userDataArray;
    }
    
    public void supermenu(){
        String menu =
        """
                ===============================================
                MAIN MENU
                                   
                [1] --- Existing User? --- LOG IN
                [2] ------ New User? ----- REGISTER
                [3] ---------------------- EXIT
        """;

        while (true){
            switch (optionHandler(menu, 3)) {
                case 1:
                    actionLogin();              
                    break;
                case 2:
                    System.out.println("Welcome, new user!");
                    actionRegister();
                    break;
                case 3:
                    System.out.println("Thank you for using Miner Cars Dealership!");
                    return;
            }
        }
    };

    public User actionLogin(){
        String username;
        String password;
        User found = null;

        System.out.print("Username: ");
        username = inputReader.nextLine();
        System.out.print("Password: ");
        password = inputReader.nextLine();
        
        for (User u : userDataArray) {
            if (u != null && u.getUsername().equals(username) && u.checkPassword(password)) {
                found = u;
            }
        }

        return found;
    };

    public void actionRegister(){
        String firstNameIn;
        String lastNameIn;
        String usernameIn;
        String passwordIn;
        String moneyAvailableIn;
        String membershipIn;
        Double moneyAvailableDouble;
        boolean membershipBool;
        
        System.out.print("\nEnter your first name: ");
        firstNameIn = inputReader.nextLine();

        System.out.print("\nEnter your last name: ");
        lastNameIn = inputReader.nextLine();

        System.out.print("\nEnter your username: ");
        usernameIn = inputReader.nextLine();
            while (HasDupeUsername(usernameIn)){
                System.out.print("\nThis username is taken!");
                System.out.print("\nEnter your username: ");
                usernameIn = inputReader.nextLine();
        }

        System.out.print("\nEnter your password: ");
        passwordIn = inputReader.nextLine();

        System.out.print("\nWhat's your budget? ");
        moneyAvailableIn = inputReader.nextLine();
        try {
            moneyAvailableDouble = Double.valueOf(moneyAvailableIn);
        } catch (NumberFormatException e) {
            System.out.println("\nInput not recognized. Please try again!");
            return;
        }

        System.out.print("\nDo you have a membership with us? (TRUE/FALSE) ");
        membershipIn = inputReader.nextLine().toUpperCase();
        while (!(membershipIn.equals("TRUE") || membershipIn.equals("FALSE"))) {
            System.out.println("Please enter TRUE or FALSE!");
            System.out.print("\nDo you have a membership with us? (TRUE/FALSE) ");
            membershipIn = inputReader.nextLine().toUpperCase();
        }

        membershipBool = membershipIn.equals("TRUE");

        addUser(firstNameIn, lastNameIn, usernameIn, passwordIn, moneyAvailableDouble, membershipBool);

        System.out.println("New user "+usernameIn+" registered!");

    };

    public int optionHandler(String menu, int max){
        String option;
        int parsedOption;

        while (true){
            System.out.println(menu);

            System.out.print("Option: ");
            option = this.inputReader.nextLine().strip();

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
    
    public boolean HasDupeUsername(String usernameIn){
        for (User userDataArray1 : this.userDataArray) {
            if (userDataArray1.username.equals(usernameIn)) {
                return true;
            }
        }
        return false;
    }
    
    public void addUser(String firstName, String lastName, String username, String password, double moneyAvailable, boolean membership){
        User[] newArray = new User[userDataArray.length + 1];
        newArray[this.userDataArray.length] = new Customer(this.userDataArray.length+1, username, lastName, username, password, moneyAvailable, 0, membership);
        this.userDataArray = newArray;
        saveUsers();
    }
    
    public void saveUsers(){
        try (FileWriter writer = new FileWriter("user_data.csv")) {
            writer.write("ID,First Name,Last Name,Money Available,Cars Purchased,MinerCars Membership,Username,Password,User Type\n");
            for (User u : this.userDataArray) {
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
    
}
