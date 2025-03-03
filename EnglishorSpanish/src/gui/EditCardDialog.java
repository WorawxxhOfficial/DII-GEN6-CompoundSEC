//package gui;
//
//import keycards.KeyCard;
//import keycards.CardDatabase;
//
//import javax.swing.*;
//import java.awt.*;
//
//public class EditCardDialog extends JDialog {
//    private JTextField nameField;
//    private KeyCard card;
//
//    public EditCardDialog(KeyCard card) {
//        this.card = card;
//        setTitle("Edit Card");
//        setSize(350, 200);
//        setLayout(new GridLayout(3, 1, 10, 10));
//        getContentPane().setBackground(new Color(45, 45, 45));
//
//        nameField = new JTextField(card.getName());
//        JButton saveBtn = new JButton("Save");
//
//        saveBtn.addActionListener(e -> {
//            card.setName(nameField.getText());
//            CardDatabase.saveCards();
//            dispose();
//        });
//
//        add(new JLabel("Edit Name:", SwingConstants.CENTER));
//        add(nameField);
//        add(saveBtn);
//
//        setLocationRelativeTo(null);
//        setVisible(true);
//    }
//}
