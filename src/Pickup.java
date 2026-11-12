public class Pickup extends Car{
    public Pickup(int id, String model, String condition, String color, int capacity, int mileage, String fuelType, String transmission, String vin, double price, int carsAvailable){
        super(id, model, condition, color, capacity, mileage, fuelType, transmission, vin, price, carsAvailable);
    }

    @Override
    public String getCarType(){
        return "Pickup";
    }
}
