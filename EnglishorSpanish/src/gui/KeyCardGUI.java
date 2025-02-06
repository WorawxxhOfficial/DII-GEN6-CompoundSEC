package gui;

import permissions.*;
import keycards.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KeyCardGUI extends JFrame {
    private JTextArea logArea; // สำหรับแสดง log
    private JTextField cardIdField, ownerField, targetField;

    public KeyCardGUI() {
        // ตั้งค่าหลัก
        setTitle("KeyCard Management System");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ส่วนบนสำหรับฟอร์มกรอกข้อมูล
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        cardIdField = new JTextField();
        ownerField = new JTextField();
        targetField = new JTextField();

        inputPanel.add(new JLabel("Card ID:"));
        inputPanel.add(cardIdField);
        inputPanel.add(new JLabel("Owner:"));
        inputPanel.add(ownerField);
        inputPanel.add(new JLabel("Target (Building/Room):"));
        inputPanel.add(targetField);

        // ปุ่มสำหรับการกระทำ
        JButton grantButton = new JButton("Grant Permission");
        JButton revokeButton = new JButton("Revoke Permission");
        inputPanel.add(grantButton);
        inputPanel.add(revokeButton);

        add(inputPanel, BorderLayout.NORTH);

        // พื้นที่สำหรับแสดง log
        logArea = new JTextArea();
        logArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(logArea);
        add(scrollPane, BorderLayout.CENTER);

        // ปุ่ม Grant Permission
        grantButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cardId = cardIdField.getText();
                String owner = ownerField.getText();
                String target = targetField.getText();

                if (cardId.isEmpty() || owner.isEmpty() || target.isEmpty()) {
                    log("Error: All fields must be filled!");
                    return;
                }

                // สร้าง KeyCard และ Grant Permission
                Permission buildingPermission = new BuildingAccess();
                KeyCard employeeCard = new EmployeeKeyCard(cardId, owner, buildingPermission);
                employeeCard.activate();
                employeeCard.use();
                log("Permission granted: Card " + cardId + " for " + target);
            }
        });

        // ปุ่ม Revoke Permission
        revokeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cardId = cardIdField.getText();
                String target = targetField.getText();

                if (cardId.isEmpty() || target.isEmpty()) {
                    log("Error: Card ID and Target must be filled!");
                    return;
                }

                // Revoke Permission
                Permission buildingPermission = new BuildingAccess();
                buildingPermission.revokePermission(cardId, target);
                log("Permission revoked: Card " + cardId + " cannot access " + target);
            }
        });
    }

    private void log(String message) {
        logArea.append(message + "\n");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new KeyCardGUI().setVisible(true);
        });
    }
}
