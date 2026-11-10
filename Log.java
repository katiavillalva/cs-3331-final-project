import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Handles activity logging for the Miner Cars Dealership system, 
 * writing timestamped actions to a persistent log file.
 * 
 * @author Jean Jose Rendon Sifontes, Katia Villalva
 * @version 1.0
 * @since November 10, 2026
 */
public class Log {
    private String logFilePath;

    /**
     * Constructs a Log manager with a specified file path.
     * 
     * @path logFilePath the path to the log text file
     */
    public Log(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    /**
     * Writes a timestamped log entry recording a user's action.
     * 
     * @param username the username of the person performing the action
     * @param action   description of the action performed
     */
    public void writeLog(String username, String action) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss MM/dd/yy");
        LocalDateTime now = LocalDateTime.now();
        String timestamp = dtf.format(now);
        String logEntry = timestamp + " - " + username + " " + action;

        try (PrintWriter writer = new PrintWriter(new FileWriter(logFilePath, true))) {
            writer.println(logEntry);
        } catch (IOException e) {
            System.out.println("Error writing to log file: " + e.getMessage());
        }
    }
}
