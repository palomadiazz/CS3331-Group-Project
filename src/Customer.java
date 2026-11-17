public class Customer extends User{
    private double moneyAvailable;
    private boolean membership;
    private int carsPurchased;

    public Customer(int id, String firstName, String lastName, String username, String password, double moneyAvailable, int carsPurchased, boolean membership){
        super(id, firstName, lastName, username, password);

        this.moneyAvailable = moneyAvailable;
        this.membership = membership;
        this.carsPurchased = carsPurchased;
    }

    public double getMoneyAvailable(){
        return moneyAvailable;
    
    }

    public int getCarsPurchased(){
        return carsPurchased;
    }

    public boolean hasMembership(){
        return membership;
    }

    public void setMoneyAvailable(double moneyAvailable){
        this.moneyAvailable = moneyAvailable;
    }

    public void setCarsPurchased(int carsPurchased) {
        this.carsPurchased = carsPurchased;
    }

    public String toString(){
        return "ID: "+this.id+"\nUsername: "+this.username+"\nName: "+this.firstName+" "+this.lastName+"\nMoney Available: "+this.moneyAvailable+"\nCars Purchased: "+this.carsPurchased+"\nMembership Status: "+this.membership+"\n ";
    }

    public void displayProfile(){
        System.out.println("[][][] CUSTOMER PROFILE [][][]");
        System.out.println("ID: " + id);
        System.out.println("Name: " + firstName + " " + lastName);
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        System.out.println("Money Available: $" + moneyAvailable);
        System.out.println("Cars Purchased: " + carsPurchased);
        System.out.println("Membership: " + membership);
        System.out.println("User Type: Customer");
    }
}
