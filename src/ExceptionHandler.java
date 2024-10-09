import java.io.*;

public class ExceptionHandler {

    // Example of handling exceptions in file operations
    public void readFile(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName + ".log"))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: Log file not found.");
        } catch (IOException e) {
            System.out.println("An error occurred while reading the log file.");
        }
    }
}
