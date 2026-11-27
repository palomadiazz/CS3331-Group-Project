import java.io.File;
import java.util.Scanner;

public class AdminInterface extends Interface {

    public AdminInterface(Scanner scanner, User[] userDataArray, Car[] carDataArray, Admin admin, File userDataCsv, File carDataCsv) {
        super(scanner, userDataArray, carDataArray, admin, userDataCsv, carDataCsv);
    }
    
    public void supermenu() {
    }
}
