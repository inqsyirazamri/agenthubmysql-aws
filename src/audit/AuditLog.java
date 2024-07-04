package src.audit;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AuditLog {
    private static final String AUDIT_LOG_FILE = "audit_log.txt";
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public static void logEvent(String username, String event) {
        String timestamp = dtf.format(LocalDateTime.now());
        String logEntry = timestamp + " - " + username + " - " + event;

        try (FileWriter writer = new FileWriter(AUDIT_LOG_FILE, true)) {
            writer.write(logEntry + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
