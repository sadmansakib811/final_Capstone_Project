import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileReaderUtil {

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
}
