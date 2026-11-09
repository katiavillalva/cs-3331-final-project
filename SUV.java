public class SUV extends Car {
    \\ specs

    public SUV(int id, String model, String condition, double price, String color, int capacity, int mileage, String transmission, String fuelType, String VIN, int carsAvailable) {
        super(id, "SUV", model, condition, price, color, capacity, mileage, transmission, fuelType, VIN, carsAvailable);
    }

    @Override
    public void displayCarSpecs(){
    }
}