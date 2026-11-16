import java.util.Scanner;

public class Admin extends User{
    public Admin(int id, String firstName, String lastName, String username, String password){
        super(id, firstName, lastName, username, password);
    }

    public void menu(Scanner inputReader){
        String option;
        while (true){
            System.out.print("\nOption: ");
            option = inputReader.nextLine();
        }
        
    }



    // displayUsers(), searchUsers(string username/name), searchUsers(int id), updateUser(int id), deleteuser(which?), updateCar(int id), deleteCar(int id)
}
