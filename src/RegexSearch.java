import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexSearch {

    // Perform regular expression search on the log file content
    public void searchLog(File logFile, String searchTerm) {
        System.out.println("\nSearching for: " + searchTerm + " (Regular Expression Search)");

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
        } catch (IOException e) {
            System.out.println("Error reading log file: " + logFile.getName());
            e.printStackTrace();
        }
    }
}
