import java.io.File;
import java.util.Scanner;

public class AdminInterface extends Interface {

    public AdminInterface(Scanner inputReader, User[] userDataArray, Car[] carDataArray, Admin admin, File userDataCsv, File carDataCsv) {
        super(inputReader, userDataArray, carDataArray, admin, userDataCsv, carDataCsv);
    }
    
    @Override
    public void supermenu() {
        String menu =
        """
            ===============================================
            ADMIN MENU  
                                    
            [1]  ---------------------- Display Users
            [2]  ---------------------- Search Users
            [3]  ---------------------- Add User
            [4]  ---------------------- Update User
            [5]  ---------------------- Delete User
            [6]  ---------------------- Display Cars
            [7]  ---------------------- Add Car
            [8]  ---------------------- Update Car
            [9]  ---------------------- Delete Car
            [10] ---------------------- Get Revenue
            [11] ---------------------- Log out
        """;

        while (true) { 
            switch (optionHandler(menu, 11)) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    displayCars();
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    break;
                case 10:
                    break;
                case 11:
                    System.out.println("Thank you for choosing Miner Car Dealership!");
                    return;
            }
        }
    }
}
