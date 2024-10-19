package energymanagement;

import java.io.File;
import java.util.Scanner;
import java.io.IOException;

public class Main {
    private static final String FILENAME_PREFIX = "FH_DORTMUND_energy_management_date_";

    public static void main(String[] args) {
        LogManager logManager = new LogManager();
        LogSearch logSearch = new LogSearch();
        FileReaderUtil fileReaderUtil = new FileReaderUtil();  // For metadata
        ExceptionHandler exceptionHandler = new ExceptionHandler(); // ExceptionHandler instance
        Scanner scanner = new Scanner(System.in);

        try {
            // Step 1: Generate log files for 5 days
            System.out.println("Generating 5 days of log files...");
            logManager.generateLogFiles();

            exceptionHandler.handleMultipleExceptions();
            exceptionHandler.manageResource();
            exceptionHandler.chainException();
            
            
            // Step 2: Show available log files
            File[] logFiles = showAvailableLogFiles();
            if (logFiles == null || logFiles.length == 0) {
                System.out.println("No log files found.");
                return;
            }

            // Step 3: Let the user select a log file
            File selectedFile = selectLogFileByCriteria(scanner, logFiles);

            if (selectedFile != null) {
                System.out.println("Selected log file: " + selectedFile.getName());

                // Option to show metadata
                System.out.println("Do you want to view metadata for this file? (yes/no): ");
                String viewMetadata = scanner.nextLine();
                if (viewMetadata.equalsIgnoreCase("yes")) {
                    fileReaderUtil.showMetadata(selectedFile);  // Show metadata
                }

                // Show log file content
                logSearch.showLogFileContent(selectedFile);

                // Step 4: Perform the search
                logSearch.performSearch(selectedFile, scanner);
            }

            // --- Choice Section for Deleting or Moving Log Files ---
            System.out.println("\nDo you want to delete or move a log file?");
            System.out.println("1. Delete a log file");
            System.out.println("2. Move a log file");
            System.out.println("3. Exit");
            System.out.print("Enter your choice (1-3): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // Delete a log file
                    deleteLogFile(scanner, logFiles);
                    break;
                case 2:
                    // Move a log file
                    moveLogFile(scanner, logFiles);
                    break;
                case 3:
                    // Exit
                    System.out.println("Exiting the program.");
                    break;
                default:
                    System.out.println("Invalid choice. Exiting.");
                    break;
            }
            // --- End of Choice Section ---

        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close(); // Close the scanner resource
        }
    }

    // Method to show all available log files and return them as an array
    private static File[] showAvailableLogFiles() {
        File dir = new File("."); // Current directory
        System.out.println("Looking for log files in directory: " + dir.getAbsolutePath());

        File[] logFiles = dir.listFiles((d, name) -> name.startsWith(FILENAME_PREFIX) && name.endsWith(".log"));

        if (logFiles != null && logFiles.length > 0) {
            System.out.println("Available log files:");
            for (File logFile : logFiles) {
                System.out.println(logFile.getName());
            }
        } else {
            System.out.println("No log files found.");
        }

        return logFiles;
    }

    // Method to allow the user to select a log file by equipment name or date
    private static File selectLogFileByCriteria(Scanner scanner, File[] logFiles) {
        System.out.print("Enter the equipment name or date (YYYY-MM-DD) to open a log file: ");
        String searchCriteria = scanner.nextLine();

        for (File logFile : logFiles) {
            if (logFile.getName().contains(searchCriteria)) {
                return logFile;  // Return the log file that matches the criteria
            }
        }

        System.out.println("No log file found matching the criteria: " + searchCriteria);
        return null;
    }

    // Method to delete a log file
    private static void deleteLogFile(Scanner scanner, File[] logFiles) {
        System.out.println("\nEnter the name of the log file you want to delete:");
        String fileName = scanner.nextLine();

        
      
        
        File fileToDelete = null;
        for (File file : logFiles) {
            if (file.getName().equals(fileName)) {
                fileToDelete = file;
                break;
            }
        }

        if (fileToDelete != null) {
            if (fileToDelete.delete()) {
                System.out.println("Log file deleted: " + fileName);
            } else {
                System.out.println("Failed to delete log file: " + fileName);
            }
        } else {
            System.out.println("Log file not found: " + fileName);
        }
    }

    // Method to move a log file
    private static void moveLogFile(Scanner scanner, File[] logFiles) {
        System.out.println("\nEnter the name of the log file you want to move:");
        String fileName = scanner.nextLine();

        File fileToMove = null;
        for (File file : logFiles) {
            if (file.getName().equals(fileName)) {
                fileToMove = file;
                break;
            }
        }

        if (fileToMove != null) {
            System.out.print("Enter the destination directory path: ");
            String destinationDir = scanner.nextLine();
            File destDir = new File(destinationDir);

            if (!destDir.exists()) {
                System.out.println("Destination directory does not exist. Do you want to create it? (yes/no): ");
                String createDir = scanner.nextLine();
                if (createDir.equalsIgnoreCase("yes")) {
                    if (!destDir.mkdirs()) {
                        System.out.println("Failed to create destination directory.");
                        return;
                    }
                } else {
                    System.out.println("Operation cancelled.");
                    return;
                }
            }

            File destinationFile = new File(destDir, fileToMove.getName());
            if (fileToMove.renameTo(destinationFile)) {
                System.out.println("Log file moved to: " + destinationFile.getAbsolutePath());
            } else {
                System.out.println("Failed to move log file.");
            }
        } else {
            System.out.println("Log file not found: " + fileName);
        }
    }
}
