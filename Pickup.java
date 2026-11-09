public class Pickup extends Car {
    \\ specs

    public Pickup(int id, String model, String condition, double price, String color, int capacity, int mileage, String transmission, String fuelType, String VIN, int carsAvailable) {
        super(id, "Pickup", model, condition, price, color, capacity, mileage, transmission, fuelType, VIN, carsAvailable);
    }

    @Override
    public void displayCarSpecs(){
    }
}