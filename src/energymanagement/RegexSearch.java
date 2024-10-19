package energymanagement;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException; 
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexSearch {

 
    public void searchLog(File logFile, String searchTerm) throws IOException {
        System.out.println("\nSearching for: " + searchTerm + " (Regular Expression Search)");

       
        try (BufferedReader br = new BufferedReader(new FileReader(logFile))) {
            String line;
            boolean found = false;

        
            Pattern pattern;
            try {
                pattern = Pattern.compile(searchTerm, Pattern.CASE_INSENSITIVE); 
            } catch (Exception e) {
                System.out.println("Invalid regular expression. Exiting search.");
                return;
            }

            
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
        
        catch (FileNotFoundException e) {
            System.out.println("The file " + logFile.getName() + " was not found.");
            throw e; 
        } catch (IOException e) {
            System.out.println("An I/O error occurred while reading " + logFile.getName() + ".");
            throw e; 
        }
    }
}
