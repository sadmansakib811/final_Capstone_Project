package energymanagement;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException; 
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;

public class FileReaderUtil {

    // Show the content of the selected log file
    public void showLogFileContent(File logFile) throws IOException {
        System.out.println("\nShowing content of " + logFile.getName() + ":");

        // Resource management (HA2 - Part c)
        try (BufferedReader br = new BufferedReader(new FileReader(logFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
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

    // Method to show file metadata
    public void showMetadata(File logFile) throws IOException {
        try {
            BasicFileAttributes attrs = Files.readAttributes(logFile.toPath(), BasicFileAttributes.class);
            System.out.println("Metadata for " + logFile.getName() + ":");
            System.out.println("Creation Date: " + attrs.creationTime());
            System.out.println("Last Modified: " + attrs.lastModifiedTime());
            System.out.println("File Size: " + logFile.length() + " bytes");
        } catch (IOException e) {
            System.out.println("Error reading metadata for " + logFile.getName());
            throw e;
        }
    }
}
