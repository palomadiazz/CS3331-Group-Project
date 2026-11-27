import java.io.File;
import java.util.Scanner;

public class AdminInterface extends Interface {

    public AdminInterface(Scanner inputReader, User[] userDataArray, Car[] carDataArray, Admin admin, File userDataCsv, File carDataCsv) {
        super(inputReader, userDataArray, carDataArray, admin, userDataCsv, carDataCsv);
    }
    
    @Override
    public void supermenu() {
        while (true) { 
            return;
        }
    }
}
