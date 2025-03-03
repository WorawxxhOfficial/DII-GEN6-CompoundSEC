import java.util.*;

public class Building {
    private static Map<String, Set<String>> keycardAccess = Storage.loadData();

    public static void addPermission(String keycardId, String room) {
        keycardAccess.computeIfAbsent(keycardId, k -> new HashSet<>()).add(room);
        Storage.saveData(keycardAccess);
    }

    public static void removePermission(String keycardId, String room) {
        if (keycardAccess.containsKey(keycardId)) {
            keycardAccess.get(keycardId).remove(room);
            Storage.saveData(keycardAccess);
        }
    }

    public static boolean hasAccess(String keycardId, String room) {
        return keycardAccess.getOrDefault(keycardId, new HashSet<>()).contains(room);
    }

    public static Set<String> getAccessibleRooms(String keycardId) {
        return keycardAccess.getOrDefault(keycardId, new HashSet<>());
    }

    public static Set<String> getAllKeycards() {
        return keycardAccess.keySet();
    }
}
