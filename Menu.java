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
    private boolean isRunning = false; 
    private String name;
    private int width = 40;

    /**
     * Constructs a Menu with a given list of options.
     * 
     * @param options list of string descriptions for menu choices
     * @param name the title of the menu
     */
    public Menu(List<String> options, String nameIn) {
        this.options = options;
        this.name = nameIn;
    }

    /** Sets the list of options for the menu. */
    public void setOptions(List<String> options) {
        this.options = options;
    }

    /** Sets the display state flag. */
    public void setName(String nameIn) {
        this.name = nameIn;
    }

    /** Returns the list of options. */
    public List<String> getOptions() {
        return this.options;
    }

    /** Returns the running state of the menu. 
   * @return true if the menu is currently running, false otherwise. */
    public boolean getIsRunning() {
        return this.isRunning;
    }

    /** Returns the title of the menu. 
     * @return the title of the menu. */
    public String getName() {
        return this.name;
    }


    /** Prints the menu options formatted in the console. */
    public void displayOptions() {
        this.isRunning = true;
        String divider = "=".repeat(this.width);
        String header = " ".repeat((int) (this.width - this.name.length()) / 2) + this.name;
        
        System.out.println(divider);
        System.out.println(header);
        System.out.println(divider);
        for (int i = 0; i < options.size(); i++) {
            System.out.println((i + 1) + ". " + options.get(i));
        }
        System.out.println(divider);
        System.out.print("Please select an option (1-" + options.size() + "): ");
    }

}
