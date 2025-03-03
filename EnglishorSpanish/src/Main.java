/**
 * Main: จุดเริ่มต้นของโปรแกรม (Entry Point)
 *
 * 🟢 User Story ที่เกี่ยวข้อง:
 * - "ในฐานะผู้ใช้งาน ฉันต้องการให้ User GUI เปิดขึ้นโดยอัตโนมัติเมื่อเริ่มโปรแกรม"
 * - "ในฐานะผู้ดูแลระบบ ฉันต้องการให้ Manager GUI สามารถเข้าถึงได้ผ่าน User GUI"
 *
 * 🟢 Features:
 * - เปิด **UserGUI** โดยอัตโนมัติเมื่อโปรแกรมเริ่มทำงาน
 * - ใช้ **SwingUtilities.invokeLater()** เพื่อให้ GUI ทำงานใน Event Dispatch Thread (EDT)
 */
public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(gui.UserGUI::new);
    }
}
