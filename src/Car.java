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

/** 
Details a car with the given specifications:
The car's ID,
The name of the model the car is,
What the car's condition is,
What color the car is,
The seating capacity for the car,
The current mileage the car has,
What type of fuel the car takes,
The car's transmission type,
The car's identification number (VIN),
The price of the car,
And the number of units or cars in stock.
*/
    
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

    public String getFuelType(){
        return fuelType;
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

public String getColor() {
    return color;
}

    public int getCapacity() {
        return capacity;
    }

    public int getMileage() {
        return mileage;
    }

    public String getTransmission() {
        return transmission;
    }

    public String getVIN() {
        return vin;
    }
    
    
    public abstract String getCarType();

/** 
Records a timestamped message to the log files, which pushes the message into the log.
*/
    
    public void displayCar() {
        System.out.println("ID: " + id);
        System.out.println("VIN:" + vin);
        System.out.println("Type: " + getCarType());
        System.out.println("Model: " + model);
        System.out.println("Condition: " + condition);
        System.out.println("Color: " + color);
        System.out.println("Capacity: " + capacity);
        System.out.println("Mileage: " + mileage);
        System.out.println("Fuel: " + fuelType);
        System.out.println("Transmission: " + transmission);
        System.out.println("Price: $" + price);
        System.out.println("Available: " + carsAvailable + "\n ");
    }
}
