package energymanagement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

public class ExceptionHandler {

    private static final String FILENAME_PREFIX = "FH_DORTMUND_energy_management_date_";

    // a. Handling Multiple Exceptions
    public void handleMultipleExceptions() {
        for (int i = 0; i < 5; i++) {
            LocalDate date = LocalDate.now().minusDays(i);
            String filePath = FILENAME_PREFIX + date.toString() + ".log";  // Generated log file names
            Path path = Paths.get(filePath);

            try {
                if (!Files.exists(path)) {
                    System.out.println("File does not exist: " + filePath);
                    continue;  // Skip this file if it doesn't exist
                }

                // Perform file operations
                byte[] data = Files.readAllBytes(path);
                System.out.println("File read successfully: " + filePath);

            } catch (IOException e) {
                System.out.println("I/O error occurred: " + e.getMessage());
                // b. Re-throwing the exception
                throw new RuntimeException("Error while processing the file: " + filePath, e);
            }
        }
    }

    // c. Resource Management using try-with-resources
    public void manageResource() {
        for (int i = 0; i < 5; i++) {
            LocalDate date = LocalDate.now().minusDays(i);
            String fileName = FILENAME_PREFIX + date.toString() + ".log";
            Path path = Paths.get(fileName);

            if (!Files.exists(path)) {
                System.out.println("File does not exist: " + fileName);
                continue;
            }

            try (var br = Files.newBufferedReader(path)) {
                String line;
                System.out.println("\nReading file: " + fileName);
                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
            } catch (IOException e) {
                System.out.println("Resource handling error: " + e.getMessage());
            }
        }
    }

    // d. Chaining Exceptions
    public void chainException() {
        for (int i = 0; i < 5; i++) {
            LocalDate date = LocalDate.now().minusDays(i);
            String filePath = FILENAME_PREFIX + date.toString() + ".log";
            Path path = Paths.get(filePath);

            if (!Files.exists(path)) {
                System.out.println("File does not exist: " + filePath);
                continue;
            }

            try {
                Files.readAllBytes(path);
            } catch (IOException e) {
                throw new RuntimeException("Chained Exception: Failed to read file: " + filePath, e);
            }
        }
    }
}
