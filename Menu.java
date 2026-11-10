import java.util.List;

/**
 * Represents an interactive console menu containing a list of selectable options.
 * 
 * @author Jean Jose Rendon Sifontes, Katia Villalva
 * @version 1.0
 * @since November 10, 2026
 */
public class Menu {
    private List<String> options;
    private boolean isDisplaying;

    /**
     * Constructs a Menu with a given list of options.
     * 
     * @param options list of string descriptions for menu choices
     */
    public Menu(List<String> options) {
        this.options = options;
        this.isDisplaying = false;
    }

    /** Sets the list of options for the menu. */
    public void setOptions(List<String> options) {
        this.options = options;
    }

    /** Sets the display state flag. */
    public void setDisplaying(boolean value) {
        this.isDisplaying = value;
    }

    /** Returns the list of options. */
    public List<String> getOptions() {
        return this.options;
    }

    /** Checks if the menu is currently active/displayed. */
    public boolean isDisplaying() {
        return this.isDisplaying;
    }

    /** Prints the menu options formatted in the console. */
    public void displayOptions() {
        this.isDisplaying = true;
        System.out.println("\n========================================");
        System.out.println("            MINER CARS MENU             ");
        System.out.println("========================================");
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
        System.out.println("========================================");
        System.out.print("Please select an option (or type 'EXIT' to quit): ");
    }
}
