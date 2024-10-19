package energymanagement;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException; // Import for FileNotFoundException
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexSearch {

    // Perform regular expression search on the log file content
    public void searchLog(File logFile, String searchTerm) throws IOException {
        System.out.println("\nSearching for: " + searchTerm + " (Regular Expression Search)");

        // Try-with-resources for resource management (HA2 - Part c)
        try (BufferedReader br = new BufferedReader(new FileReader(logFile))) {
            String line;
            boolean found = false;

            // Compile the regular expression
            Pattern pattern;
            try {
                pattern = Pattern.compile(searchTerm, Pattern.CASE_INSENSITIVE);  // Case-insensitive regex
            } catch (Exception e) {
                System.out.println("Invalid regular expression. Exiting search.");
                return;
            }

            // Search the log file using the regular expression
            while ((line = br.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
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
        } catch (IOException e) {
            System.out.println("An I/O error occurred while reading " + logFile.getName() + ".");
            throw e; // Re-throwing Exception (HA2 - Part b)
        }
    }
}
