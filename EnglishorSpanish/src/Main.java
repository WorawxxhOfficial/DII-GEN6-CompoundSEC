import permissions.*;
import keycards.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        // Create GUI Frame
        JFrame frame = new JFrame("KeyCard Access System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));

        // Input Fields
        JTextField cardIdField = new JTextField();
        JTextField ownerField = new JTextField();
        JComboBox<String> accessTypeBox = new JComboBox<>(new String[]{"Building", "Room"});
        JButton grantButton = new JButton("Grant Permission");
        JButton revokeButton = new JButton("Revoke Permission");
        JButton activateButton = new JButton("Activate KeyCard");

        // Result Area
        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);

        // Add Components to Panel
        panel.add(new JLabel("Card ID:"));
        panel.add(cardIdField);
        panel.add(new JLabel("Owner Name:"));
        panel.add(ownerField);
        panel.add(new JLabel("Access Type:"));
        panel.add(accessTypeBox);
        panel.add(grantButton);
        panel.add(revokeButton);
        panel.add(activateButton);

        // Add Panel and Result Area to Frame
        frame.add(panel, BorderLayout.CENTER);
        frame.add(new JScrollPane(resultArea), BorderLayout.SOUTH);

        // Permissions and KeyCard Management
        Permission buildingAccess = new BuildingAccess();

        // Event Handlers
        grantButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cardId = cardIdField.getText();
                String owner = ownerField.getText();
                String accessType = (String) accessTypeBox.getSelectedItem();

                if ("Building".equals(accessType)) {
                    buildingAccess.grantPermission(cardId, "Main Building");
                    resultArea.append("Granted building access for " + owner + " (" + cardId + ")\n");
                } else {
                    resultArea.append("Room access not implemented yet.\n");
                }
            }
        });

        revokeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cardId = cardIdField.getText();
                String accessType = (String) accessTypeBox.getSelectedItem();

                if ("Building".equals(accessType)) {
                    buildingAccess.revokePermission(cardId, "Main Building");
                    resultArea.append("Revoked building access for card " + cardId + "\n");
                } else {
                    resultArea.append("Room access not implemented yet.\n");
                }
            }
        });

        activateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cardId = cardIdField.getText();
                String owner = ownerField.getText();
                KeyCard keyCard = new EmployeeKeyCard(cardId, owner, buildingAccess);

                keyCard.activate();
                resultArea.append("Activated KeyCard for " + owner + " (" + cardId + ")\n");
            }
        });

        // Show Frame
        frame.setVisible(true);
    }
}
