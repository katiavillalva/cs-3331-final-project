/**
 * Represents a Pickup truck in the Miner Cars Dealership system.
 * Extends the abstract class {@link Car} and provides specific implementations 
 * for pickup truck characteristics.
 * 
 * @author Jean Jose Rendon Sifontes, Katia Villalva
 * @version 1.0
 * @since November 10, 2026
 */
public class Pickup extends Car {

    /**
     * Constructs a new Pickup with the specified attributes.
     * Automatically sets the car type to "Pickup".
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
    public Pickup(int id, String model, String condition, double price, String color, 
                  int capacity, int mileage, String transmission, String fuelType, 
                  String VIN, int carsAvailable) {
        super(id, "Pickup", model, condition, price, color, capacity, mileage, transmission, fuelType, VIN, carsAvailable);
    }

    /**
     * Displays pickup-specific specifications such as bed capacity and towing capabilities.
     */
    @Override
    public void displayCarSpecs() {
        System.out.println("--- Pickup Specific Specifications ---");
        System.out.println("Body Style: Extended / Crew Cab Pickup");
        System.out.println("Bed Capacity: Heavy-duty open cargo bed");
        System.out.println("Towing & Hauling: High towing capacity with 4WD support");
        System.out.println("--------------------------------------");
    }
}
