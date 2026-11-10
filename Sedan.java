/**
 * Represents a Sedan vehicle in the Miner Cars Dealership system.
 * Extends the abstract class {@link Car} and provides specific implementations 
 * for sedan characteristics.
 * 
 * @author Jean Jose Rendon Sifontes, Katia Villalva
 * @version 1.0
 * @since November 10, 2026
 */
public class Sedan extends Car {

    /**
     * Constructs a new Sedan with the specified attributes.
     * Automatically sets the car type to "Sedan".
     * 
     * @param id            Unique identifier for the car
     * @param model         Model name of the car
     * @param condition     Condition of the car (e.g., New, Used)
     * @param price         Price of the car
     * @param color         Color of the car
     * @param capacity      Passenger seating capacity
     * @param mileage       Car mileage in miles
     * @param transmission  Transmission type (e.g., Automatic, Manual)
     * @param fuelType      Fuel type (e.g., Gasoline, Electric, Hybrid)
     * @param VIN           Vehicle Identification Number
     * @param carsAvailable Quantity available in inventory
     */
    public Sedan(int id, String model, String condition, double price, String color, 
                 int capacity, int mileage, String transmission, String fuelType, 
                 String VIN, int carsAvailable) {
        super(id, "Sedan", model, condition, price, color, capacity, mileage, transmission, fuelType, VIN, carsAvailable);
    }

    /**
     * Displays sedan-specific specifications such as body style and trunk configuration.
     */
    @Override
    public void displayCarSpecs() {
        System.out.println("--- Sedan Specific Specifications ---");
        System.out.println("Body Style: 4-Door Passenger Sedan");
        System.out.println("Trunk Capacity: Separate enclosed trunk compartment");
        System.out.println("Design: Aerodynamic comfort and fuel efficiency");
        System.out.println("-------------------------------------");
    }
}
