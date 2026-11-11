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
    private boolean isDisplaying = false;
    private String header; 
    private final String divider = "=".repeat(50); // Divider line for formatting the options

    /**
     * Constructs a Menu with a given list of options.
     * 
     * @param options list of string descriptions for menu choices
     * @param header the header text for the menu
     */
    public Menu(List<String> options, String header) {
        this.options = options;
        this.header = padHeader(header);
    }

    /** Sets the list of options for the menu. */
    public void setOptions(List<String> options) {
        this.options = options;
    }

    /** Sets the display state flag. */
    public void setDisplaying(boolean value) {
        this.isDisplaying = value;
    }
    /** Sets the upper case header text for the menu. 
     * @param header the header text to set
    */
    public void setHeader(String header) {
        this.header = padHeader(header);
    }

    /** Pads the header text to center it within the divider. */
    private String padHeader(String header) {
        int totalHeaderLength = divider.length();
        int padding = (int) Math.max(0, (totalHeaderLength - header.length()) / 2);
        return " ".repeat(padding) + header;
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
        System.out.println("\n" + divider);
        System.out.println(this.header);
        System.out.println(divider);
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
        System.out.println(divider);
        System.out.print("Please enter an option in the console (1-" + options.size() + "): ");
    }
}
