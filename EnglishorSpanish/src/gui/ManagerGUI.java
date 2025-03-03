package gui;

import keycards.KeyCard;
import keycards.CardDatabase;
import logs.LogManager;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

/**
 * ManagerGUI: อินเทอร์เฟซสำหรับจัดการ KeyCards และ Logs ของระบบ
 *
 * 🟢 User Story ที่เกี่ยวข้อง:
 * - "ในฐานะผู้ดูแลระบบ ฉันต้องการสามารถเพิ่ม, แก้ไข และลบ KeyCards ได้"
 * - "ในฐานะผู้ดูแลระบบ ฉันต้องการบันทึก Logs ทุกการเปลี่ยนแปลงที่เกิดขึ้น"
 *
 * 🟢 Features:
 * - เพิ่ม/ลบ/แก้ไข KeyCards
 * - บันทึกและแสดง Logs การทำงาน
 * - จัดการ Permissions ของ KeyCards
 */
public class ManagerGUI extends JFrame {
    private JButton addCardButton, removeCardButton, editCardButton;
    private JComboBox<String> cardDropdown;
    private JTextArea logArea;
    private UserGUI userGUI;

    /**
     * Constructor: สร้าง UI ของ Manager Panel
     * @param userGUI ใช้สำหรับอัปเดตข้อมูลของ User GUI
     */
    public ManagerGUI(UserGUI userGUI) {
        this.userGUI = userGUI;
        setTitle("Manager Panel");
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(new Color(45, 45, 45)); // พื้นหลังเป็นสีเทาเข้ม

        // ** Control Panel **
        JPanel controlPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        controlPanel.setBackground(new Color(55, 55, 55));

        // ปุ่มสำหรับเพิ่ม/ลบ/แก้ไขบัตร
        addCardButton = createStyledButton("Add Card", new Color(100, 149, 237)); // ฟ้าอ่อน
        removeCardButton = createStyledButton("Remove Card", new Color(255, 140, 140)); // แดงอ่อน
        editCardButton = createStyledButton("Edit Card", new Color(255, 180, 120)); // ส้มอ่อน

        controlPanel.add(addCardButton);
        controlPanel.add(editCardButton);
        controlPanel.add(removeCardButton);

        // กำหนด ActionListener ให้ปุ่ม
        addCardButton.addActionListener(e -> showAddCardDialog());
        removeCardButton.addActionListener(e -> removeCard());
        editCardButton.addActionListener(e -> showEditCardDialog());

        // ** Logs Panel **
        logArea = new JTextArea();
        logArea.setEditable(false);
        logArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        logArea.setBackground(new Color(55, 55, 55)); // พื้นหลังเทาเข้ม
        logArea.setForeground(Color.WHITE); // ข้อความสีขาว
        JScrollPane logScroll = new JScrollPane(logArea);
        logScroll.setBorder(BorderFactory.createTitledBorder(null, "Access Logs", 0, 0, new Font("Arial", Font.BOLD, 14), Color.WHITE));

        add(controlPanel, BorderLayout.NORTH);
        add(logScroll, BorderLayout.CENTER);
        updateDropdown();
        setVisible(true);
    }

    /**
     * สร้างปุ่มที่มีสีและสไตล์ที่กำหนด
     */
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        return button;
    }

    /**
     * อัปเดตรายการบัตรใน Dropdown
     */
    private void updateDropdown() {
        if (cardDropdown == null) {
            cardDropdown = new JComboBox<>();
            add(cardDropdown, BorderLayout.SOUTH);
        }
        cardDropdown.removeAllItems();
        for (KeyCard card : CardDatabase.getCards()) {
            cardDropdown.addItem(card.getDisplayName());
        }
    }

    /**
     * แสดงหน้าต่างสำหรับเพิ่ม KeyCard ใหม่
     */
    private void showAddCardDialog() {
        JTextField nameField = new JTextField();
        JCheckBox[] permissionCheckboxes = new JCheckBox[9];
        String[] rooms = {"101", "102", "103", "201", "202", "203", "301", "302", "303"};

        JPanel panel = new JPanel(new GridLayout(11, 1));
        panel.setBackground(new Color(55, 55, 55));

        JLabel nameLabel = new JLabel("Enter Customer Name:");
        nameLabel.setForeground(Color.WHITE);
        panel.add(nameLabel);
        panel.add(nameField);

        JLabel permLabel = new JLabel("Permissions:");
        permLabel.setForeground(Color.WHITE);
        panel.add(permLabel);

        // สร้าง Checkbox สำหรับเลือกห้องที่ KeyCard สามารถเข้าได้
        for (int i = 0; i < rooms.length; i++) {
            permissionCheckboxes[i] = new JCheckBox("Access to Room " + rooms[i]);
            permissionCheckboxes[i].setForeground(Color.WHITE);
            permissionCheckboxes[i].setBackground(new Color(55, 55, 55));
            panel.add(permissionCheckboxes[i]);
        }

        int result = JOptionPane.showConfirmDialog(this, panel, "Add New Card", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            if (!name.isEmpty()) {
                KeyCard newCard = new KeyCard(name);
                for (int i = 0; i < rooms.length; i++) {
                    newCard.setPermission(rooms[i], permissionCheckboxes[i].isSelected());
                }
                CardDatabase.addCard(newCard);
                updateDropdown();
                userGUI.refreshCardPanel();
                log("Added Card: " + newCard.getDisplayName());
            }
        }
    }

    /**
     * แสดงหน้าต่างแก้ไขข้อมูล KeyCard
     */
    private void showEditCardDialog() {
        String selectedCardName = (String) cardDropdown.getSelectedItem();
        KeyCard selectedCard = CardDatabase.getCardByName(selectedCardName);

        if (selectedCard != null) {
            JTextField nameField = new JTextField(selectedCard.getName());
            JCheckBox[] permissionCheckboxes = new JCheckBox[9];
            String[] rooms = {"101", "102", "103", "201", "202", "203", "301", "302", "303"};

            JPanel panel = new JPanel(new GridLayout(11, 1));
            panel.setBackground(new Color(55, 55, 55));

            JLabel nameLabel = new JLabel("Edit Name:");
            nameLabel.setForeground(Color.WHITE);
            panel.add(nameLabel);
            panel.add(nameField);

            JLabel permLabel = new JLabel("Permissions:");
            permLabel.setForeground(Color.WHITE);
            panel.add(permLabel);

            for (int i = 0; i < rooms.length; i++) {
                permissionCheckboxes[i] = new JCheckBox("Access to Room " + rooms[i], selectedCard.canAccessRoom(rooms[i]));
                permissionCheckboxes[i].setForeground(Color.WHITE);
                permissionCheckboxes[i].setBackground(new Color(55, 55, 55));
                panel.add(permissionCheckboxes[i]);
            }

            int result = JOptionPane.showConfirmDialog(this, panel, "Edit Card", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                selectedCard.setName(nameField.getText().trim());
                for (int i = 0; i < rooms.length; i++) {
                    selectedCard.setPermission(rooms[i], permissionCheckboxes[i].isSelected());
                }
                updateDropdown();
                userGUI.refreshCardPanel();
                log("Edited Card: " + selectedCard.getDisplayName());
            }
        }
    }

    /**
     * ลบบัตรที่เลือกออกจากระบบ
     */
    private void removeCard() {
        String selectedCard = (String) cardDropdown.getSelectedItem();
        if (selectedCard != null && !selectedCard.contains("Manager")) {
            CardDatabase.removeCard(selectedCard);
            updateDropdown();
            userGUI.refreshCardPanel();
            log("Removed Card: " + selectedCard);
        } else {
            JOptionPane.showMessageDialog(this, "Manager Card cannot be removed!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * บันทึกข้อความลง Logs
     */
    private void log(String message) {
        logArea.append("[" + LocalDateTime.now() + "] " + message + "\n");
        LogManager.saveLog(message);
    }
}
