/**
 * Abstract base class representing a generic vehicle in the Miner Cars Dealership system.
 * Serves as the superclass for specific car types such as Sedan, SUV, Hatchback, and Pickup.
 * 
 * @author Jean Jose Rendon Sifontes, Katia Villalva
 * @version 1.0
 * @since November 10, 2026
 */
public abstract class Car {
    private final int id;
    private final String carType;
    private final String model;
    private String condition;
    private double price;
    private String color;
    private final int capacity;
    private int mileage;
    private final String transmission;
    private final String fuelType;
    private final String VIN;
    private int carsAvailable; // Number of cars available in the inventory

    /**
     * Constructs a new Car with all its specific attributes.
     * 
     * @param id            Unique identifier for the car
     * @param carType       Type of the car (e.g., Sedan, SUV)
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
    protected Car(int id, String carType, String model, String condition, double price, 
                  String color, int capacity, int mileage, String transmission, 
                  String fuelType, String VIN, int carsAvailable) {
        this.id = id;
        this.carType = carType;
        this.model = model;
        this.condition = condition;
        this.price = price;
        this.color = color;
        this.capacity = capacity;
        this.mileage = mileage;
        this.transmission = transmission;
        this.fuelType = fuelType;
        this.VIN = VIN;
        this.carsAvailable = carsAvailable;
    }

    // ==================== Setters ====================

    /**
     * Updates the condition of the car.
     * @param condition New condition to set
     */
    public void setCondition(String condition) {
        this.condition = condition;
    }

    /**
     * Updates the price of the car.
     * @param price New price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Updates the color of the car.
     * @param color New color to set
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Updates the mileage of the car.
     * @param mileage New mileage value
     */
    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    /**
     * Updates the number of available cars in stock.
     * @param carsAvailable New available count
     */
    public void setCarsAvailable(int carsAvailable) {
        this.carsAvailable = carsAvailable;
    }

    // ==================== Getters ====================

    /** @return The unique car ID */
    public int getId() { return this.id; }

    /** @return The type of the car */
    public String getCarType() { return this.carType; }

    /** @return The model of the car */
    public String getModel() { return this.model; }

    /** @return The condition of the car */
    public String getCondition() { return this.condition; }

    /** @return The price of the car */
    public double getPrice() { return this.price; }

    /** @return The color of the car */
    public String getColor() { return this.color; }

    /** @return The passenger capacity */
    public int getCapacity() { return this.capacity; }

    /** @return The car mileage */
    public int getMileage() { return this.mileage; }

    /** @return The transmission type */
    public String getTransmission() { return this.transmission; }

    /** @return The fuel type */
    public String getFuelType() { return this.fuelType; }

    /** @return The Vehicle Identification Number (VIN) */
    public String getVIN() { return this.VIN; }

    /** @return The number of cars available */
    public int getCarsAvailable() { return this.carsAvailable; }

    // ==================== Display Methods ====================

    /**
     * Prints all generic attributes and values of the car to the console in a formatted layout.
     */
    public void displayInformation() {
        System.out.println("-------------------------------------------");
        System.out.println("Car ID: " + this.id);
        System.out.println("Car Type: " + this.carType);
        System.out.println("Model: " + this.model);
        System.out.println("Condition: " + this.condition);
        System.out.println("Price: $" + this.price);
        System.out.println("Color: " + this.color);
        System.out.println("Capacity: " + this.capacity + " passengers");
        System.out.println("Mileage: " + this.mileage + " miles");
        System.out.println("Transmission: " + this.transmission);
        System.out.println("Fuel Type: " + this.fuelType);
        System.out.println("VIN: " + this.VIN);
        System.out.println("Cars Available: " + this.carsAvailable);
        System.out.println("-------------------------------------------");
    }

    /**
     * Abstract method implemented by subclasses to print car-type-specific details 
     * (e.g., for Sedan, SUV, Hatchback, Pickup).
     */
    public abstract void displayCarSpecs();
}
