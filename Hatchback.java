public class Hatchback extends Car {
    \\ specs

    public Hatchback(int id, String model, String condition, double price, String color, int capacity, int mileage, String transmission, String fuelType, String VIN, int carsAvailable) {
        super(id, "Hatchback", model, condition, price, color, capacity, mileage, transmission, fuelType, VIN, carsAvailable);
    }

    @Override
    public void displayCarSpecs(){
    }
}