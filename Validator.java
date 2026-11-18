import java.util.List;

/** Class with methods to check if a user input is valid format and throw exceptions with descriptive error messages. */
public class Validator {

    // ==================== Constructor ====================
    /** No-arg private constructor */
    private Validator() { }

    // ==================== Static validation methods ====================

    /** Returns the input if it is a valid ID format
     * @param input the String to validate
     * @param name the attribute name 
     * @return the trimmed input if it is a positive integer
     * @throws IllegalArgumentException if the input is not a positive integer
     */
    public static int validateWholeNumber(String input, String name) {
        int n = validateNonnegativeInt(input, name);
        if (n == 0) {
            throw new IllegalArgumentException("Invalid. " + name + " must be positive.");
        }
        return n;
    }

    /** Returns the input if it is a nonnegative integer
     * @param input the String to validate
     * @param name the attribute name 
     * @return the car ID if the input was a positive integer
     * @throws IllegalArgumentException if the input is not a nonnegative integer
     */
    public static int validateNonnegativeInt(String input, String name) {
        try {
            input = validateNonempty(input);
            int n = Integer.parseInt(input);
            if (n < 0) throw new IllegalArgumentException("Invalid. " + name + " cannot be negative.");
            return n;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid. " + name + " must be a whole number.");
        }
    }


    /** Checks that the input is not null or blank
     * @param input the String to validate
     * @return the trimmed input if it is nonempty
     * @throws IllegalArgumentException if the input is null, empty, or only whitespace
     */
    public static String validateNonempty(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Invalid. Input cannot be blank.");
        }
        return input.trim();
    }

    /** Returns the lowercase input if it is one of the allowed options in the list 
     * @param input the String to validate
     * @param options the allowed lowercase values
     * @param name the attribute name used in the error message
     * @return the lowercase input if it is an allowed option
     * @throws IllegalArgumentException if the input is not one of the options
     */
    public static String validateOption(String input, List<String> options, String name) {
        input = validateString(input).toLowerCase();
        if (!options.contains(input)) {
            throw new IllegalArgumentException("Invalid " + name + ". Must be one of: " + options);
        }
    return input;
    }

    /** Returns the input if it is a valid car type
     * @param input the String to validate
     * @return the input if it is a valid car type
     * @throws IllegalArgumentException if the input not a valid car subclass
     */
    public static String validateCarType(String input) {
        return validateOption(input, CarFactory.CAR_TYPES, "car type");
    }

    /** Returns the input if it is a valid user type
     * @param input the String to validate
     * @return the input if it is a valid user type ("admin, "customer")
     * @throws IllegalArgumentException if the input not a valid user type 
     */
     public static String validateUserType(String input) {
        return validateOption(input, UserFactory.USER_TYPES, "user type");
    }

     /** Returns the input parsed as a double if it is a valid price
     * @param input the String to validate
     * @return the input as a double if it is a valid price (numeric and nonnegative)
     * @throws IllegalArgumentException if the input is invalid 
     */
    public static double validatePrice(String input) {
        try {
            input = validateNonempty(input);
            double price = Double.parseDouble(input);
            if (price < 0 || Double.isNaN(price) || Double.isInfinite(price)) {
                throw new IllegalArgumentException("Invalid. Amount must be a nonnegative number.");
            }
            return price;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid. Amount must be a nonnegative number.");
        }
    }

    /** Returns the input if it is a valid car condition
     * @param input the String to validate
     * @return the car condition from the valid input ("new, "used")
     * @throws IllegalArgumentException if the input not a valid car condition
     */
    public static String validateCondition(String input) {
        return validateOption(input, CarFactory.CAR_CONDITION_TYPES, "car condition");
    }

    /** Returns the trimmed input if it is nonempty and does not contain commas 
     * @param input the String to validate
     * @return the trimmed String if it is valid 
     * @throws IllegalArgumentException if the String is invalid
     */
    public static String validateString(String input) {
        input = validateNonempty(input);
        if (!input.contains(",")) {
            return input;
        } else {
            throw new IllegalArgumentException("Input cannot contain commas.");
        }
    }


}