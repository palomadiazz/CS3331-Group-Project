public class Admin extends User{
    public Admin(int id, String firstName, String lastName, String username, String password){
        super(id, firstName, lastName, username, password);
    }

    @Override
    public void menu(){
        
    }

    // displayUsers(), searchUsers(string username/name), searchUsers(int id), updateUser(int id), deleteuser(which?), updateCar(int id), deleteCar(int id)
}
