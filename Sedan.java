public class Sedan extends Car {
    \\ specs

    public Sedan(int id, String model, String condition, double price, String color, int capacity, int mileage, String transmission, String fuelType, String VIN, int carsAvailable) {
        super(id, "Sedan", model, condition, price, color, capacity, mileage, transmission, fuelType, VIN, carsAvailable);
    }

    @Override
    public void displayCarSpecs(){
    }
}