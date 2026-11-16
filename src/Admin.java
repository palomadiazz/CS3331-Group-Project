public class Admin extends User{
    public Admin(int id, String firstName, String lastName, String username, String password){
        super(id, firstName, lastName, username, password);
    }

    public String toString(){
        return "ID: "+this.id+"\nUsername: "+this.username+"\nName: "+this.firstName+" "+this.lastName+"\n ";
    }

    // displayUsers(), searchUsers(string username/name), searchUsers(int id), updateUser(int id), deleteuser(which?), updateCar(int id), deleteCar(int id)
}
