import java.util.Scanner;

public abstract class Interface {
    protected Scanner scanner;
    protected User[] userDataArray;
    protected Car[] carDataArray;
    protected User user;

    public Interface(Scanner scanner, User[] userDataArray, Car[] carDataArray, User user){
        this.scanner = scanner;
        this.userDataArray = userDataArray;
        this.carDataArray = carDataArray;
        this.user = user;
    }
    
}
