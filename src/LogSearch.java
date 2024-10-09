import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Scanner;

public class LogSearch {

    // Show the content of the selected log file
    public void showLogFileContent(File logFile) {
        System.out.println("\nShowing content of " + logFile.getName() + ":");
        try (BufferedReader br = new BufferedReader(new FileReader(logFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading log file: " + logFile.getName());
            e.printStackTrace();
        }
    }

    // Search log files based on user's choice: string search or regex search
    public void searchLog(File logFile, String searchTerm, boolean isRegexSearch) {
        System.out.println("\nSearching for: " + searchTerm);

        try (BufferedReader br = new BufferedReader(new FileReader(logFile))) {
            String line;
            boolean found = false;

            if (isRegexSearch) {
                // User selected regex search, try to compile the search term as a regex
                Pattern pattern;
                try {
                    pattern = Pattern.compile(searchTerm, Pattern.CASE_INSENSITIVE);  // Case-insensitive regex
                } catch (Exception e) {
                    System.out.println("Invalid regular expression. Exiting search.");
                    return;  // Exit if the regex is invalid
                }

                // Search the log file using the regular expression
                while ((line = br.readLine()) != null) {
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        System.out.println("Match found: " + line);
                        found = true;
                    }
                }
            } else {
                // Perform a simple case-insensitive string search
                while ((line = br.readLine()) != null) {
                    if (line.toLowerCase().contains(searchTerm.toLowerCase())) {
                        System.out.println("Match found: " + line);
                        found = true;
                    }
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

    // Ask the user what type of search they want to perform: string or regex
    public void performSearch(File logFile, Scanner scanner) {
        System.out.println("\nSelect the type of search:");
        System.out.println("1. Simple String Search");
        System.out.println("2. Regular Expression Search");

        int choice;
        try {
            choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline
        } catch (Exception e) {
            System.out.println("Invalid choice. Exiting.");
            return;
        }

        // Show examples if the user selects regex search
        if (choice == 2) {
            System.out.println("\nExamples of regular expressions:");
            System.out.println("\\d+ kWh  -> Find energy values (numbers followed by 'kWh')");
            System.out.println("(?i)Lighting  -> Find case-insensitive 'Lighting'");
            System.out.println("(?i)(Heating|H-Bahn)  -> Find 'Heating' or 'H-Bahn'");
            System.out.println("\\d{4}-\\d{2}-\\d{2}  -> Find dates in format 'YYYY-MM-DD'");
        }

        // Ask for the search term or regex (manual input)
        System.out.print("\nEnter the search term or regular expression: ");
        String searchTerm = scanner.nextLine();

        // Call the appropriate search based on user choice
        if (choice == 1) {
            searchLog(logFile, searchTerm, false);  // Simple string search
        } else if (choice == 2) {
            searchLog(logFile, searchTerm, true);   // Regex search
        } else {
            System.out.println("Invalid choice. Exiting.");
        }
    }
}
import java.io.File;
import java.util.Scanner;

public class LogSearch {

    private FileReaderUtil fileReaderUtil = new FileReaderUtil();
    private SimpleStringSearch simpleStringSearch = new SimpleStringSearch();
    private RegexSearch regexSearch = new RegexSearch();

    // Ask the user what type of search they want to perform: string or regex
    public void performSearch(File logFile, Scanner scanner) {
        System.out.println("\nSelect the type of search:");
        System.out.println("1. Simple String Search");
        System.out.println("2. Regular Expression Search");

        int choice;
        try {
            choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline
        } catch (Exception e) {
            System.out.println("Invalid choice. Exiting.");
            return;
        }

        // Show examples if the user selects regex search
        if (choice == 2) {
            System.out.println("\nExamples of regular expressions:");
            System.out.println("\\d+ kWh  -> Find energy values (numbers followed by 'kWh')");
            System.out.println("(?i)Lighting  -> Find case-insensitive 'Lighting'");
            System.out.println("(?i)(Heating|H-Bahn)  -> Find 'Heating' or 'H-Bahn'");
            System.out.println("\\d{4}-\\d{2}-\\d{2}  -> Find dates in format 'YYYY-MM-DD'");
        }

        // Ask for the search term or regex (manual input)
        System.out.print("\nEnter the search term or regular expression: ");
        String searchTerm = scanner.nextLine();

        // Call the appropriate search based on user choice
        if (choice == 1) {
            simpleStringSearch.searchLog(logFile, searchTerm);  // Call simple string search
        } else if (choice == 2) {
            regexSearch.searchLog(logFile, searchTerm);   // Call regex search
        } else {
            System.out.println("Invalid choice. Exiting.");
        }
    }

    // Method to show log file content (delegates to FileReaderUtil)
    public void showLogFileContent(File logFile) {
        fileReaderUtil.showLogFileContent(logFile);  // Call FileReaderUtil to show file content
    }
}
