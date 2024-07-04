package src.backup;

import java.io.*;
import java.sql.*;

public class DatabaseBackup {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/agenthub";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "test123";
    private static final String MYSQLDUMP_PATH = "C:\\Program Files\\MySQL\\MySQL Server 8.0\\bin\\mysqldump.exe"; 

    public static void backupDatabase(String backupFilePath) throws IOException, SQLException, InterruptedException {
        String executeCmd = MYSQLDUMP_PATH + " --no-tablespaces -u " + DB_USER + " -p" + DB_PASSWORD + " agenthub -r " + backupFilePath;
        Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
        int processComplete = runtimeProcess.waitFor();

        StringBuilder errorOutput = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(runtimeProcess.getErrorStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                errorOutput.append(line).append(System.lineSeparator());
                System.err.println(line);
            }
        }

        StringBuilder standardOutput = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(runtimeProcess.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                standardOutput.append(line).append(System.lineSeparator());
                System.out.println(line);
            }
        }

        if (processComplete == 0) {
            System.out.println("Backup completed successfully.");
        } else {
            System.err.println("Backup failed.");
            System.err.println("Error Output: " + errorOutput.toString());
            System.err.println("Standard Output: " + standardOutput.toString());
        }
    }

    public static void restoreDatabase(String restoreFilePath) throws IOException, SQLException, InterruptedException {
        String[] executeCmd = new String[]{"mysql", "-u", DB_USER, "-p" + DB_PASSWORD, "agenthub", "-e", " source " + restoreFilePath};
        Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
        int processComplete = runtimeProcess.waitFor();

        // Capture error stream
        StringBuilder errorOutput = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(runtimeProcess.getErrorStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                errorOutput.append(line).append(System.lineSeparator());
                System.err.println(line);
            }
        }

        // Capture standard output stream (if any)
        StringBuilder standardOutput = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(runtimeProcess.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                standardOutput.append(line).append(System.lineSeparator());
                System.out.println(line);
            }
        }

        if (processComplete == 0) {
            System.out.println("Restore completed successfully.");
        } else {
            System.err.println("Restore failed.");
            System.err.println("Error Output: " + errorOutput.toString());
            System.err.println("Standard Output: " + standardOutput.toString());
        }
    }
}
