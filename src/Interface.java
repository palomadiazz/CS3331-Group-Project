import java.util.Scanner;

public class Interface {
    private Scanner scanner;
    private User[] userDataArray;
    private Car[] carDataArray;

    public Interface(Scanner scanner, User[] userDataArray, Car[] carDataArray){
        this.scanner = scanner;
        this.userDataArray = userDataArray;
        this.carDataArray = carDataArray;
    }
    
}
