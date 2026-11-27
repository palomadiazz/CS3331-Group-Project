import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public abstract class Interface {
    protected Scanner inputReader;
    protected User[] userDataArray;
    protected Car[] carDataArray;
    protected User user;
    protected File userDataCsv;
    protected File carDataCsv;

    public Interface(Scanner inputReader, User[] userDataArray, Car[] carDataArray, User user, File userDataCsv, File carDataCsv){
        this.inputReader = inputReader;
        this.userDataArray = userDataArray;
        this.carDataArray = carDataArray;
        this.user = user;
        this.userDataCsv = userDataCsv;
        this.carDataCsv = carDataCsv;
    }

    public abstract void supermenu();

    public int optionHandler(String menu, int max){
        String option;
        int parsedOption;

        while (true){
            System.out.println(menu);

            System.out.print("\nOption: ");
            option = this.inputReader.next();

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
    
    public void displayCars(){
        for (Car c: carDataArray) {
            if (c != null) c.displayCar();
        }
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

    public void saveCars(){
        try (FileWriter writer = new FileWriter("car_data.csv")) {
            // header
        writer.write("ID,Car Type,Model,Condition,Color,Capacity,Mileage,Fuel Type,Transmission,VIN,Price,Cars Available\n");
            for (Car c : this.carDataArray) {
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

    public boolean hasDupeUsername(String usernameIn){
        for (User userDataArray1 : this.userDataArray) {
            if (userDataArray1.username.equals(usernameIn)) {
                return true;
            }
        }
        return false;
    }
}
