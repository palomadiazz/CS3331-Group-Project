public class User extends Person{

    protected int id;
    protected String username;
    protected String password;

    public User(int id, String firstName, String lastName, String username, String password){
        super(firstName, lastName);

        this.id = id;
        this.username = username;
        this.password = password;
    }

    public int getId(){
        return id;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.username = password;
    }

    public boolean checkPassword(String password){
        return this.password.equals(password);
    }

    
}
