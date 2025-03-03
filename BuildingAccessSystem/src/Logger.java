import java.io.FileWriter;
import java.io.IOException;

public class Logger {
    private static final String FILE_NAME = "logs.txt";

    public static void log(String message) {
        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {
            writer.write(message + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
