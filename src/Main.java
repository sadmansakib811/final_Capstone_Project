import java.io.File;
import java.util.Scanner;

public class Main {
    private static final String FILENAME_PREFIX = "FH_DORTMUND_energy_management_date_";

    public static void main(String[] args) {
        LogManager logManager = new LogManager();
        LogSearch logSearch = new LogSearch();
        Scanner scanner = new Scanner(System.in);

        // Step 1: Generate log files for 5 days
        System.out.println("Generating 5 days of log files...");
        logManager.generateLogFiles();

        // Step 2: Show available log files
        File[] logFiles = showAvailableLogFiles();
        if (logFiles == null || logFiles.length == 0) {
            System.out.println("No log files found.");
            return;
        }

        // Step 3: Let the user select which log file to open (by date)
        System.out.print("Enter the number of the log file you want to open: ");
        int fileNumber = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        if (fileNumber < 1 || fileNumber > logFiles.length) {
            System.out.println("Invalid choice. Exiting.");
            return;
        }

        // Step 4: Show the selected log file content
        File selectedFile = logFiles[fileNumber - 1];
        logSearch.showLogFileContent(selectedFile);

        // Step 5: Perform the search (ask user for search type and term)
        logSearch.performSearch(selectedFile, scanner);

        scanner.close();
    }

    // Method to show all available log files and return them as an array
    private static File[] showAvailableLogFiles() {
        File dir = new File(".");  // Current directory
        System.out.println("Looking for log files in directory: " + dir.getAbsolutePath());

        // Correctly filter files starting with the right prefix and ending with .log
        File[] logFiles = dir.listFiles((d, name) -> name.startsWith("FH_DORTMUND_energy_management_date_") && name.endsWith(".log"));

        if (logFiles != null && logFiles.length > 0) {
            System.out.println("Available log files:");
            for (int i = 0; i < logFiles.length; i++) {
                System.out.println((i + 1) + ". " + logFiles[i].getName());
            }
        } else {
            System.out.println("No log files found.");
        }

        return logFiles;
    }
}
