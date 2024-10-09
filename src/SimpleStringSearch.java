import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class SimpleStringSearch {

    // Perform simple string search (case-insensitive) on the log file content
    public void searchLog(File logFile, String searchTerm) {
        System.out.println("\nSearching for: " + searchTerm + " (Simple String Search)");

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
        } catch (IOException e) {
            System.out.println("Error reading log file: " + logFile.getName());
            e.printStackTrace();
        }
    }
}
