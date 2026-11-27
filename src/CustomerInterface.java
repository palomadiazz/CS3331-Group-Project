import java.io.File;
import java.util.Scanner;

public class CustomerInterface extends Interface {

    public CustomerInterface(Scanner scanner, User[] userDataArray, Car[] carDataArray, Customer customer, File userDataCsv, File carDataCsv) {
        super(scanner, userDataArray, carDataArray, customer, userDataCsv, carDataCsv);
    }

    public void supermenu() {
    }
    
    
}
