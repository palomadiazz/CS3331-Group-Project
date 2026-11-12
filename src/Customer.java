public class Customer extends User{
    private double moneyAvailable;
    private boolean membership;
    private String carsPurchased;

    public Customer(int id, String firstName, String lastName, String username, String password, double moneyAvailable, String carsPurchased, boolean membership){
        super(id, firstName, lastName, username, password);

        this.moneyAvailable = moneyAvailable;
        this.membership = membership;
        this.carsPurchased = carsPurchased;
    }

    public double getMoneyAvailable(){
        return moneyAvailable;
    }

    public String getCarsPurchased(){
        return carsPurchased;
    }

    public boolean hasMembership(){
        return membership;
    }

    public void setMoneyAvailable(double moneyAvailable){
        this.moneyAvailable = moneyAvailable;
    }

    public void setCarsPurchased(String carsPurchased) {
        this.carsPurchased = carsPurchased;
    }
}
