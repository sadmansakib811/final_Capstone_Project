package energymanagement;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException; // Import for FileNotFoundException

public class SimpleStringSearch {

    // Perform simple string search (case-insensitive) on the log file content
//	This indicates that the method may throw an IOException during its execution.
    public void searchLog(File logFile, String searchTerm) throws IOException {
        System.out.println("\nSearching for: " + searchTerm + " (Simple String Search)");

        // Try-with-resources for resource management (HA2 - Part c)
        try (BufferedReader br = new BufferedReader(new FileReader(logFile))) {
            String line;
            boolean found = false;

            // Case-insensitive string search
            while ((line = br.readLine()) != null) {
                if (line.toLowerCase().contains(searchTerm.toLowerCase())) {
                    System.out.println("Match found: " + line);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No matches found for: " + searchTerm);
            }
        }
        // Handling Multiple Exceptions (HA2 - Part a)
        catch (FileNotFoundException e) {
            System.out.println("The file " + logFile.getName() + " was not found.");
            throw e; // Re-throwing Exception (HA2 - Part b)
        }
        catch (IOException e) {
            System.out.println("An I/O error occurred while reading " + logFile.getName() + ".");
            throw e; // Re-throwing Exception (HA2 - Part b) reading from a corrupted file
        }
    }
}
