public abstract class Car{
    protected int id;
    protected String model;
    protected String condition;
    protected String color;
    protected int capacity;
    protected int mileage;
    protected String fuelType;
    protected String transmission;
    protected String vin;
    protected double price;
    protected int carsAvailable;

    public Car(int id, String model, String condition, String color, int capacity, int mileage, String fuelType, String transmission, String vin, double price, int carsAvailable){
        this.id = id;
        this.model = model;
        this.condition = condition;
        this.color = color;
        this.capacity = capacity;
        this.mileage = mileage;
        this.fuelType = fuelType;
        this.transmission = transmission;
        this.vin = vin;
        this.price = price;
        this.carsAvailable = carsAvailable;
    }

    public int getId(){
        return id;
    }

    public String getModel(){
        return model;
    }

    public String getCondition(){
        return condition;
    }

    public double getPrice(){
        return price;
    }

    public int getCarsAvailable(){
        return carsAvailable;
    }

    public void setCarsAvailable(int carsAvailable){
        this.carsAvailable = carsAvailable;
    }

    public abstract String getCarType();

    public void displayCar() {
        System.out.println("ID: " + id);
        System.out.println("Type: " + getCarType());
        System.out.println("Model: " + model);
        System.out.println("Condition: " + condition);
        System.out.println("Color: " + color);
        System.out.println("Capacity: " + capacity);
        System.out.println("Mileage: " + mileage);
        System.out.println("Fuel: " + fuelType);
        System.out.println("Transmission: " + transmission);
        System.out.println("Price: $" + price);
        System.out.println("Available: " + carsAvailable);
    }
}
