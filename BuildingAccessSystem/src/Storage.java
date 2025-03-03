import java.io.*;
import java.util.*;

public class Storage {
    private static final String FILE_NAME = "data.properties";

    public static void saveData(Map<String, Set<String>> keycardAccess) {
        Properties properties = new Properties();
        for (Map.Entry<String, Set<String>> entry : keycardAccess.entrySet()) {
            properties.setProperty(entry.getKey(), String.join(",", entry.getValue()));
        }
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            properties.store(writer, "Keycard Access Data");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, Set<String>> loadData() {
        Map<String, Set<String>> keycardAccess = new HashMap<>();
        Properties properties = new Properties();
        File file = new File(FILE_NAME);
        if (!file.exists()) return keycardAccess;
        try (FileReader reader = new FileReader(FILE_NAME)) {
            properties.load(reader);
            for (String key : properties.stringPropertyNames()) {
                Set<String> rooms = new HashSet<>(Arrays.asList(properties.getProperty(key).split(",")));
                keycardAccess.put(key, rooms);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return keycardAccess;
    }
}
