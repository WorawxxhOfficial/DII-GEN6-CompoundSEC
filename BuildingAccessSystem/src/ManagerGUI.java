import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ManagerGUI extends JFrame {
    private JComboBox<String> keycardDropdown;
    private JButton addKeycardButton, revokeKeycardButton, savePermissionsButton;
    private JPanel checkboxPanel;
    private Map<String, Set<String>> keycardAccess;
    private Map<String, JCheckBox> roomCheckboxes = new HashMap<>();

    public ManagerGUI() {
        setTitle("Manager Panel");
        setSize(400, 400);
        setLayout(new FlowLayout());

        keycardAccess = Storage.loadData();

        keycardDropdown = new JComboBox<>(keycardAccess.keySet().toArray(new String[0]));
        keycardDropdown.addActionListener(e -> loadPermissions());
        add(keycardDropdown);

        addKeycardButton = new JButton("Add Keycard");
        addKeycardButton.addActionListener(e -> addKeycard());
        add(addKeycardButton);

        revokeKeycardButton = new JButton("Revoke Keycard");
        revokeKeycardButton.addActionListener(e -> revokeKeycard());
        add(revokeKeycardButton);

        checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new GridLayout(5, 2));
        for (String room : UserGUI.getRooms()) {
            JCheckBox checkBox = new JCheckBox(room);
            roomCheckboxes.put(room, checkBox);
            checkboxPanel.add(checkBox);
        }
        add(checkboxPanel);

        savePermissionsButton = new JButton("Save Permissions");
        savePermissionsButton.addActionListener(e -> savePermissions());
        add(savePermissionsButton);

        loadPermissions();
        setVisible(true);
    }

    private void addKeycard() {
        String newKeycard = JOptionPane.showInputDialog("Enter new Keycard ID:");
        if (newKeycard != null && !newKeycard.trim().isEmpty()) {
            if (!keycardAccess.containsKey(newKeycard)) {
                keycardAccess.put(newKeycard, new HashSet<>());
                Storage.saveData(keycardAccess);
                keycardDropdown.addItem(newKeycard);
                JOptionPane.showMessageDialog(this, "Keycard added successfully!");
            } else {
                JOptionPane.showMessageDialog(this, "Keycard already exists!");
            }
        }
    }

    private void revokeKeycard() {
        String selectedKeycard = (String) keycardDropdown.getSelectedItem();
        if (selectedKeycard != null) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure?", "Confirm Revoke", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                keycardAccess.remove(selectedKeycard);
                Storage.saveData(keycardAccess);
                keycardDropdown.removeItem(selectedKeycard);
                JOptionPane.showMessageDialog(this, "Keycard revoked!");
            }
        }
    }

    private void loadPermissions() {
        String selectedKeycard = (String) keycardDropdown.getSelectedItem();
        if (selectedKeycard != null) {
            Set<String> allowedRooms = keycardAccess.getOrDefault(selectedKeycard, new HashSet<>());
            for (String room : UserGUI.getRooms()) {
                roomCheckboxes.get(room).setSelected(allowedRooms.contains(room));
            }
        }
    }

    private void savePermissions() {
        String selectedKeycard = (String) keycardDropdown.getSelectedItem();
        if (selectedKeycard != null) {
            Set<String> newPermissions = new HashSet<>();
            for (String room : UserGUI.getRooms()) {
                if (roomCheckboxes.get(room).isSelected()) {
                    newPermissions.add(room);
                }
            }
            keycardAccess.put(selectedKeycard, newPermissions);
            Storage.saveData(keycardAccess);
            JOptionPane.showMessageDialog(this, "Permissions updated!");
        }
    }
}
