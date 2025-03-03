import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;

public class UserGUI extends JFrame {
    private static final String[] ROOMS = { "101", "102", "103", "201", "202", "203", "301", "302", "303", "GYM" };
    private JTextField keycardField;
    private Map<String, JButton> roomButtons = new HashMap<>();
    private String currentKeycard = "";
    private String currentRoom = ""; // ห้องที่เข้าอยู่

    public static String[] getRooms() {
        return ROOMS;
    }

    public UserGUI() {
        setTitle("Building Access System");
        setSize(500, 400);
        setLayout(new GridLayout(4, 3));

        keycardField = new JTextField();
        keycardField.setFont(new Font("Arial", Font.PLAIN, 20));
        keycardField.setHorizontalAlignment(JTextField.CENTER);
        add(keycardField);

        JButton enterButton = new JButton("Enter Keycard");
        enterButton.addActionListener(e -> checkKeycard());
        add(enterButton);

        for (String room : ROOMS) {
            JButton btn = new JButton(room);
            btn.setEnabled(false);
            btn.setBackground(Color.RED);
            btn.addActionListener(e -> accessRoom(room));
            roomButtons.put(room, btn);
            add(btn);
        }

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void checkKeycard() {
        String keycard = keycardField.getText().trim();
        if (keycard.isEmpty()) return;

        currentKeycard = keycard;
        Map<String, Set<String>> keycardAccess = Storage.loadData();
        boolean isManager = keycard.equals("manager101");

        for (String room : ROOMS) {
            JButton btn = roomButtons.get(room);
            if (isManager || (keycardAccess.containsKey(keycard) && keycardAccess.get(keycard).contains(room))) {
                btn.setBackground(Color.GREEN);
                btn.setEnabled(true);
            } else {
                btn.setBackground(Color.RED);
                btn.setEnabled(false);
            }
        }

        if (isManager) {
            String password = JOptionPane.showInputDialog("Enter Manager Password:");
            if ("12345".equals(password)) {
                new ManagerGUI();
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect Password!");
            }
        }
    }

    private void accessRoom(String room) {
        if (roomButtons.get(room).getBackground() == Color.GREEN) {
            // เปลี่ยนห้องเดิมเป็นสีเขียวถ้ามี
            if (!currentRoom.isEmpty()) {
                roomButtons.get(currentRoom).setBackground(Color.GREEN);
                logAccess(currentRoom, false); // บันทึกการออกห้อง
            }

            roomButtons.get(room).setBackground(Color.YELLOW);
            currentRoom = room; // อัปเดตห้องปัจจุบัน
            JOptionPane.showMessageDialog(this, "Access granted to Room " + room);

            // บันทึกการเข้า
            logAccess(room, true);
        } else {
            JOptionPane.showMessageDialog(this, "Access Denied!");
        }
    }

    private void logAccess(String room, boolean isEntering) {
        String action = isEntering ? "entered" : "exited";
        String logMessage = "User " + action + " Room " + room + " at " + new java.util.Date() + "\n";
        System.out.println(logMessage); // แสดง log ใน console

        // บันทึก log ลงไฟล์
        try (FileWriter writer = new FileWriter("access_log.txt", true)) {
            writer.write(logMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

