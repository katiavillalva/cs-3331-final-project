import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Handles system logging operations with precise timestamp formatting.
 * 
 * @author Jean Jose Rendon Sifontes, Katia Villalva
 * @version 2.1
 * @since November 10, 2026
 */
public class Log {
    private final String logFileName;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss MM/dd/yy");

    public Log(String logFileName) {
        this.logFileName = logFileName;
    }


    /* Returns the name of the log file. */
    public String getLogFileName() {
        return logFileName;
    }

    /**
     * Writes a structured log entry to the log file.
     * 
     * @param username the user performing the action
     * @param action   description of the action
     */
    public void writeLog(String username, String action) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        String logEntry = timestamp + " - " + username + " " + action;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(logFileName, true))) {
            bw.write(logEntry);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to log file: " + e.getMessage());
        }
    }
}
