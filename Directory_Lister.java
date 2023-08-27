import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class Directory_Lister {
    public static void main(String[] args) {
        
        String directoryPath; // Create a variable to store path from user

        if (args.length == 0) { // When there's no argument
            System.out.println("No directory argument provided. Using current directory."); // Print out message
            directoryPath = "."; // Use current directory (which is .)
        } else { // Otherwise, use user's input
            directoryPath = args[0];
        }

        listDirectoryContents(directoryPath); 
    }

    // This function takes the path that was given by user
    public static void listDirectoryContents(String directoryPath) {
        // Convirt String variable directory path into a Path object
        Path dirPath = Paths.get(directoryPath);
        // If the file doesn't exist
        if (!Files.exists(dirPath)) { 
            System.out.println("Error: " + directoryPath + " does not exist!"); 
            return; // Indicate that this file doesn't exist
        }
        // If the file is not a directory
        if (!Files.isDirectory(dirPath)) {
            System.out.println("Error: " + directoryPath + " is not a directory!");
            return; // Indicate that this file is not a directory
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dirPath)) { // Create a stream (sequence of context within directory)
            for (Path entry : stream) { // This for loop goes through each file that are found
                String type; // Create a string variable to store the type
                if (Files.isDirectory(entry)) { 
                    type = "D"; // Store "D" into type if the file is a directory
                } else if (Files.isRegularFile(entry)) {
                    type = "F"; // Store "F" into type if the file is a regular file
                } else {
                    type = "O"; // Store "O" into type if the file is other type
                }
                long size = Files.size(entry); // Create a long variable to store the size of file
                String name = entry.getFileName().toString(); // Create a string variable to store the file name that was converted to string
        
                System.out.printf("[%s] %12d %s%n", type, size, name); // Print out all information in specific format depends on each type
            }
        } catch (IOException e) { // Try to catch exception if there's any
            System.out.println("Error: " + e.getMessage());
        }
    }
}
