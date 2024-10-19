package energymanagement;
import java.io.File;
import java.util.Scanner;
import java.io.IOException;

public class LogSearch {

    private FileReaderUtil fileReaderUtil = new FileReaderUtil();
    private SimpleStringSearch simpleStringSearch = new SimpleStringSearch();
    private RegexSearch regexSearch = new RegexSearch();

    // Ask the user what type of search they want to perform: string or regex
    public void performSearch(File logFile, Scanner scanner) throws IOException {
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
    public void showLogFileContent(File logFile) throws IOException {
        fileReaderUtil.showLogFileContent(logFile);  // Call FileReaderUtil to show file content
    }
}
