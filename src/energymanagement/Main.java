package energymanagement;
import java.util.Properties;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.SwingUtilities;
import java.io.FileInputStream;

public class Main {
    private static final String FILENAME_PREFIX = "FH_DORTMUND_energy_management_date_";
    private static final String PROPERTIES_FILE_PATH = "initial_settings.properties";

    public static void main(String[] args) {
        EnergySimulator energySimulator = new EnergySimulator(); // Create EnergySimulator instance
        LogManager logManager = new LogManager(energySimulator); // Pass energySimulator to LogManager
        LogSearch logSearch = new LogSearch();
        FileReaderUtil fileReaderUtil = new FileReaderUtil();  // For metadata
        ExceptionHandler exceptionHandler = new ExceptionHandler(); // ExceptionHandler instance
        Scanner scanner = new Scanner(System.in);
      
        //**********************battery inital setting from properties file******************************
        int batteryCapacity = readBatteryCapacity();
        Battery battery = new Battery(batteryCapacity);
        //**********************end******************************



        // Start the multithreaded simulation
        energySimulator.startSimulation(battery);

        // Start the UI
        SwingUtilities.invokeLater(() -> {
            EnergyManagementUI ui = new EnergyManagementUI(battery, logManager, energySimulator);
            ui.setVisible(true); // Make the UI visible
        });

        // Proceed with console functionalities
        try {
            // Generate log files
            System.out.println("Generating 5 days of log files...");
            logManager.generateLogFiles();

            // Handle exceptions
            exceptionHandler.handleMultipleExceptions();
            exceptionHandler.manageResource();
            exceptionHandler.chainException();

            // Show available log files
            File[] logFiles = showAvailableLogFiles();
            if (logFiles == null || logFiles.length == 0) {
                System.out.println("No log files found.");
                return;
            }

            // Select a log file
            File selectedFile = selectLogFileByCriteria(scanner, logFiles);

            if (selectedFile != null) {
                System.out.println("Selected log file: " + selectedFile.getName());

                System.out.print("Do you want to view metadata for this file? (yes/no): ");
                String viewMetadata = scanner.nextLine();
                if (viewMetadata.equalsIgnoreCase("yes")) {
                    fileReaderUtil.showMetadata(selectedFile);
                }

                logSearch.showLogFileContent(selectedFile);

                logSearch.performSearch(selectedFile, scanner);
            }

            // Options to delete or move log files
            System.out.println("\nDo you want to delete or move a log file?");
            System.out.println("1. Delete a log file");
            System.out.println("2. Move a log file");
            System.out.println("3. Exit");
            System.out.print("Enter your choice (1-3): ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    deleteLogFile(scanner, logFiles);
                    break;
                case 2:
                    moveLogFile(scanner, logFiles);
                    break;
                case 3:
                    System.out.println("Exiting the program.");
                    break;
                default:
                    System.out.println("Invalid choice. Exiting.");
                    break;
            }

        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }


    private static int readBatteryCapacity() {
        Properties properties = new Properties();

        int batteryCapacity = 6000; // Default value in case the property is not found

        try (FileInputStream input = new FileInputStream(PROPERTIES_FILE_PATH)) {
            properties.load(input);
            String capacityStr = properties.getProperty("batteryCapacity");
            try {
                batteryCapacity = Integer.parseInt(capacityStr);
            } catch(Exception e) {
                System.out.println("batteryCapacity property not found. Using default value: " + batteryCapacity);
            }
        } catch (IOException e) {
            System.out.println("Error reading properties file: " + e.getMessage());
            System.out.println("Using default battery capacity: " + batteryCapacity);
        } catch (NumberFormatException e) {
            System.out.println("Invalid battery capacity value in properties file. Using default value: " + batteryCapacity);
        }
        return batteryCapacity;
    }

    
    

    private static File[] showAvailableLogFiles() {
        File dir = new File(".");
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

    private static File selectLogFileByCriteria(Scanner scanner, File[] logFiles) {
        System.out.print("Enter the equipment name or date (YYYY-MM-DD) to open a log file: ");
        String searchCriteria = scanner.nextLine();

        for (File logFile : logFiles) {
            if (logFile.getName().contains(searchCriteria)) {
                return logFile;
            }
        }

        System.out.println("No log file found matching the criteria: " + searchCriteria);
        return null;
    }

    private static void deleteLogFile(Scanner scanner, File[] logFiles) {
        System.out.print("\nEnter the name of the log file you want to delete: ");
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

    private static void moveLogFile(Scanner scanner, File[] logFiles) {
        System.out.print("\nEnter the name of the log file you want to move: ");
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
                System.out.print("Destination directory does not exist. Do you want to create it? (yes/no): ");
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
