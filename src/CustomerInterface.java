import java.io.File;
import java.util.Scanner;

public class CustomerInterface extends Interface {

    public CustomerInterface(Scanner inputReader, User[] userDataArray, Car[] carDataArray, Customer user, File userDataCsv, File carDataCsv) {
        super(inputReader, userDataArray, carDataArray, user, userDataCsv, carDataCsv);
    }

    @Override
    public void supermenu() {
        String menu =
            """
                ===============================================
                CUSTOMER MENU

                [1] ---------------------- Manage Profile
                [2] ---------------------- Browse Cars
                [3] ---------------------- Purchase Car
                [4] ---------------------- View Invoice
                [5] ---------------------- Return Car
                [6] ---------------------- Log Out
            """;

        while (true) { 
            switch(optionHandler(menu, 6)){
                case 1:
                    manageProfile();
                    break;
                case 2:
                    browseCars();
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    System.out.println("Thank you for choosing Miner Car Dealership!");
                    return;
            }
        }
    }

    public void manageProfile(){
        String menu = 
            """
                MANAGE PROFILE
                [1] ---------------------- View Profile
                [2] ---------------------- Change Username
                [3] ---------------------- Change Password
                [4] ---------------------- Add Money
                [5] ---------------------- Back
            """;
        while (true) { 
            switch (optionHandler(menu, 5)) {
                case 1:
                    ((Customer) this.user).displayProfile();
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:  
                    return;
            }
        }

    }

    public void browseCars(){
        String menu = 
            """
            BROWSE CARS

            [1] ---------------------- Display Cars
            [2] ---------------------- Filter Cars
            """;

        switch(optionHandler(menu, 2)){
            case 1:
                displayCars();
                break;
            case 2:
                filterCars();
                break;
        }
    }

    public void filterCars(){
        String menu = 
        """
        Choose search criteria: 

            [1] ---------------------- ID
            [2] ---------------------- Car Type
            [3] ---------------------- Condition
            [4] ---------------------- Price
        """;
        String criterion;


        switch (optionHandler(menu, 4)) {
            case 1:      
                int idCriterion;
                try {
                    System.out.print("Enter ID: ");
                    criterion = inputReader.next();
                    idCriterion = Integer.parseInt(criterion);

                } catch (NumberFormatException e){
                    System.out.println("Mismatched input. Returning...");
                    break;
                }

                Car result = carById(idCriterion);

                if (result != null) {
                    result.displayCar();
                } else {
                    System.out.println("Car not found.");
                }

                break;
            case 2:
                submenuCarByType();
                break;
            case 3:
                submenuCarByCondition();
                break;
            case 4: 
                int priceCriterion;
                boolean found = false;

                try {
                    System.out.print("Enter price: ");
                    criterion = inputReader.next();
                    
                    priceCriterion = Integer.parseInt(criterion);
                } catch (NumberFormatException e){
                    System.out.println("Mismatched input. Returning...");
                    return;
                }

                for (int x = 0; x < carDataArray.length; x++){
                    if (Math.abs(carDataArray[x].price-priceCriterion)<=5000) {
                        carDataArray[x].displayCar();
                        found = true;
                    }                           
                }

                if (!found) {
                    System.out.println("Car not found.");
                }
                break;
        }
    }

    public void submenuCarByType(){
        String menu = 
        """
            Choose search criteria: 
                            
            [1] ---------------------- Hatchback
            [2] ---------------------- Pickup
            [3] ---------------------- Sedan
            [4] ---------------------- SUV
        """;
        String typeIn = "Placeholder";

        switch (optionHandler(menu, 4)) {
            case 1 -> typeIn = "Hatchback";
            case 2 -> typeIn = "Pickup";
            case 3 -> typeIn = "Sedan";
            case 4 -> typeIn = "SUV";   
        }
        if (!printCarByType(typeIn)){
            System.out.println("Car not found.");
        }
    }

    public void submenuCarByCondition(){
        String menu = 
        """
            Choose search criteria: 
                            
            [1] ---------------------- New
            [2] ---------------------- Used
        """;
        String conditionIn = "Placeholder";

        switch (optionHandler(menu, 2)) {
            case 1 -> conditionIn = "New";
            case 2 -> conditionIn = "Used";
        }
        if (!printCarByCondition(conditionIn)){
            System.out.println("Car not found.");
        }

    }
    
    public Car carById(int idIn){
        for (int x = 0; x < carDataArray.length; x++){
            if (carDataArray[x].id==idIn) {
                return carDataArray[x];
            }                           
        }
        return null;
    }

    public boolean printCarByType(String carTypeIn){
        boolean found = false;
        for (int x = 0; x < this.carDataArray.length; x++){
            if (this.carDataArray[x].getCarType().equals(carTypeIn)) {
                carDataArray[x].displayCar();
            }                           
        }
        return found;
    }

    public boolean printCarByCondition(String conditionIn){
        boolean found = false;
        for (int x = 0; x < carDataArray.length; x++){
            if (carDataArray[x].condition.equals(conditionIn)) {
                carDataArray[x].displayCar();
                found = true;
            }                           
        }
        return found;
    }

    public boolean printCarByPrice(double priceIn){
        boolean found = false;
        return found;
    }

}
