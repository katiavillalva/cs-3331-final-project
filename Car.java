/**
 * Represents a generic vehicle in the dealership system.
 * Serves as the superclass for specific car types.
 * 
 * @author Jean Jose Rendon Sifontes, Katia Villalva
 * @version 2.0
 * @since November 17, 2026
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
    public static final String HEADER = buildHeader();

    /**
     * Constructs a new Car with all its specific attributes.
     * 
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

    // ==================== toString method ====================

     /**
     * Builds the column labels for the car table, lined up with toString().
     * @return the labels followed by a divider line
     */
    private static String buildHeader() {
        String labels = String.format("%-5s %-12s %-18s %-10s %-13s %-10s %-5s %-10s %-12s %-10s %-18s %-5s",
            "ID", "Type", "Model", "Condition", "Price", "Color", "Seats", "Mileage",
            "Transmission", "Fuel", "VIN", "Stock");
        return labels + "\n" + "-".repeat(labels.length());
    }

    /** Overridden toString method 
     * @return String containing all profile information except the password
     */
    @Override
    public String toString() {
        return String.format("%-5d %-12s %-18s %-10s $%-12.2f %-10s %-5d %-10d %-12s %-10s %-18s %-5d",
            this.id, this.carType, this.model, this.condition, this.price, this.color,
            this.capacity, this.mileage, this.transmission, this.fuelType, this.VIN, this.carsAvailable);
    }

    /**
     * Abstract method implemented by subclasses to print car-type-specific details 
     * (e.g., for Sedan, SUV, Hatchback, Pickup).
     */
    public abstract void displayCarSpecs();
}
