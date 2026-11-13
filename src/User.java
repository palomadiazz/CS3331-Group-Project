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

    public void setUsername(String usernameIn){
        this.username = usernameIn;
        // where to include dupe checking?
    }

    public void setPassword(String passwordIn){
        this.username = passwordIn;
    }

    public boolean checkPassword(String password){
        return this.password.equals(password);
    }

    
}
