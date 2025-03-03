package gui;

import keycards.KeyCard;
import keycards.ManagerCard;
import keycards.CardDatabase;
import logs.LogManager;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * UserGUI: อินเทอร์เฟซสำหรับผู้ใช้ที่ใช้ KeyCard ในการเข้าถึงห้องต่างๆ
 *
 * 🟢 User Story ที่เกี่ยวข้อง:
 * - "ในฐานะผู้ใช้งาน ฉันต้องการเลือกการ์ดและตรวจสอบสิทธิ์ของฉันเพื่อเข้าใช้งานห้อง"
 * - "ในฐานะผู้ใช้งาน ฉันต้องการเห็นข้อมูล KeyCard ที่ฉันเลือก"
 * - "ในฐานะผู้ดูแลระบบ ฉันต้องการให้ Manager Card ต้องใส่รหัสก่อนเข้า Manager Panel"
 * - "ในฐานะผู้ใช้งาน ฉันต้องการให้ระบบมี Cooldown สำหรับการเข้าห้อง"
 *
 * 🟢 Features:
 * - แสดงรายการห้องที่สามารถเข้าได้
 * - แสดงข้อมูล KeyCard และเลือกใช้งาน KeyCard ได้
 * - ตรวจสอบสิทธิ์การเข้าห้อง
 * - มีระบบ Cooldown 5 วินาทีต่อการเข้าห้อง
 */
public class UserGUI extends JFrame {
    private JPanel roomPanel, cardPanelContainer;
    private JScrollPane cardPanel;
    private KeyCard selectedCard;
    private JButton lastEnteredRoom = null;
    private String[] rooms = {"101", "102", "103", "201", "202", "203", "301", "302", "303"};
    private JButton[] roomButtons = new JButton[rooms.length];
    private boolean isCooldown = false; // Cooldown flag

    /**
     * Constructor: สร้าง UI ของ User Panel
     */
    public UserGUI() {
        setTitle("Building Access System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1000, 650);
        setLayout(new GridLayout(1, 2, 5, 5)); // ใช้ GridLayout เพื่อให้จัดองค์ประกอบได้สมดุล
        getContentPane().setBackground(new Color(45, 45, 45)); // พื้นหลังสีเทาเข้ม

        // **Room Selection Panel**
        roomPanel = new JPanel(new GridLayout(3, 3, 8, 8)); // ใช้ GridLayout ให้ช่องว่างเท่ากัน
        roomPanel.setBorder(BorderFactory.createTitledBorder(null, "Room Selection", 0, 0, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        roomPanel.setBackground(new Color(55, 55, 55));

        // สร้างปุ่มห้อง
        for (int i = 0; i < rooms.length; i++) {
            roomButtons[i] = new JButton(rooms[i]);
            roomButtons[i].setFont(new Font("Arial", Font.BOLD, 18));
            roomButtons[i].setBackground(new Color(255, 140, 140)); // สีแดงอ่อน (เข้าไม่ได้)
            roomButtons[i].setOpaque(true);
            roomButtons[i].setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
            roomButtons[i].setPreferredSize(new Dimension(120, 120));
            roomButtons[i].addActionListener(e -> enterRoom((JButton) e.getSource()));
            roomPanel.add(roomButtons[i]);
        }
        add(roomPanel);

        // **Card Information Panel**
        cardPanelContainer = new JPanel(new BorderLayout());
        cardPanelContainer.setBorder(BorderFactory.createTitledBorder(null, "Card Information", 0, 0, new Font("Arial", Font.BOLD, 14), Color.WHITE));
        cardPanelContainer.setBackground(new Color(55, 55, 55));
        cardPanel = createCardInfoPanel();
        cardPanelContainer.add(cardPanel, BorderLayout.CENTER);
        add(cardPanelContainer);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * สร้าง Panel ที่แสดงข้อมูลของ KeyCard ที่มีอยู่
     */
    private JScrollPane createCardInfoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(55, 55, 55));

        for (KeyCard card : CardDatabase.getCards()) {
            JButton cardButton = new JButton("<html><b>" + card.getName() + "</b><br>ID: " + card.getCardId() + "</html>");
            cardButton.setPreferredSize(new Dimension(250, 70));
            cardButton.setFont(new Font("Arial", Font.BOLD, 14));

            if (card instanceof ManagerCard) {
                cardButton.setBackground(new Color(100, 149, 237)); // ฟ้าอ่อนสำหรับ Manager
            } else {
                cardButton.setBackground(new Color(255, 180, 120)); // ส้มอ่อนสำหรับ Customer
            }

            cardButton.addActionListener(e -> {
                if (card instanceof ManagerCard) {
                    promptManagerPassword(card);
                } else {
                    selectedCard = card;
                    updateRoomColors();
                    refreshCardPanel();
                }
            });

            if (selectedCard == card) {
                cardButton.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 4)); // Highlight สีน้ำเงิน
            } else {
                cardButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            }

            panel.add(cardButton);
            panel.add(Box.createVerticalStrut(5));
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(280, 400));
        return scrollPane;
    }

    /**
     * อัปเดตการแสดงผลของ Panel KeyCard
     */
    public void refreshCardPanel() {
        cardPanelContainer.remove(cardPanel);
        cardPanel = createCardInfoPanel();
        cardPanelContainer.add(cardPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    /**
     * อัปเดตสีของปุ่มห้องตามสิทธิ์ของ KeyCard ที่เลือก
     */
    private void updateRoomColors() {
        for (JButton roomButton : roomButtons) {
            if (selectedCard.canAccessRoom(roomButton.getText())) {
                roomButton.setBackground(new Color(180, 238, 180)); // สีเขียวอ่อน (เข้าได้)
            } else {
                roomButton.setBackground(new Color(255, 140, 140)); // สีแดงอ่อน (เข้าไม่ได้)
            }
        }
    }

    /**
     * ตรวจสอบสิทธิ์การเข้าห้องและใช้ Cooldown
     */
    private void enterRoom(JButton roomButton) {
        if (isCooldown) {
            JOptionPane.showMessageDialog(this, "You must wait for the cooldown to finish!", "Cooldown Active", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (selectedCard != null && selectedCard.canAccessRoom(roomButton.getText())) {
            if (lastEnteredRoom != null) {
                lastEnteredRoom.setBackground(new Color(180, 238, 180)); // กลับเป็นสีเขียวอ่อน
            }

            roomButton.setBackground(new Color(255, 223, 150)); // สีเหลืองอ่อน (กำลังเข้าใช้งาน)
            lastEnteredRoom = roomButton;
            LogManager.saveLog(selectedCard.getDisplayName() + " entered " + roomButton.getText());

            // เริ่ม Cooldown 5 วินาที
            isCooldown = true;
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    isCooldown = false;
                }
            }, 5000);
        }
    }

    /**
     * ตรวจสอบรหัสผ่านของ Manager ก่อนเปิด Manager Panel
     */
    private void promptManagerPassword(KeyCard card) {
        String password = JOptionPane.showInputDialog(this, "Enter Manager Password:", "Authentication", JOptionPane.PLAIN_MESSAGE);
        if ("12345".equals(password)) {
            selectedCard = card;
            updateRoomColors();
            refreshCardPanel();
            new ManagerGUI(this);
        } else {
            JOptionPane.showMessageDialog(this, "Incorrect password!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Main method เพื่อรัน UserGUI
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(UserGUI::new);
    }
}
