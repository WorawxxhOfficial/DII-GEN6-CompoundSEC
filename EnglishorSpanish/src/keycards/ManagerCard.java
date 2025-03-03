package keycards;

import java.io.Serializable;

/**
 * ManagerCard: ใช้แทนบัตรของผู้ดูแลระบบ (Manager)
 *
 * 🟢 User Story ที่เกี่ยวข้อง:
 * - "ในฐานะผู้ดูแลระบบ ฉันต้องการให้มีบัตรพิเศษที่สามารถเข้าทุกห้องได้"
 * - "ในฐานะผู้ดูแลระบบ ฉันต้องการให้บัตร Manager เป็นบัตรถาวร และไม่สามารถลบออกจากระบบได้"
 * - "ในฐานะผู้ใช้ ฉันต้องการให้การ์ด Manager สามารถเปิด Manager GUI ได้โดยต้องใส่รหัสผ่านก่อน"
 *
 * 🟢 Features:
 * - Manager Card มีหมายเลขบัตรที่ **ตายตัว** (`manager101`)
 * - สามารถเข้าถึง **ทุกห้องในอาคาร** ได้
 * - ไม่สามารถถูกลบออกจากระบบได้
 * - ใช้เปิด Manager GUI ได้ แต่ต้องใส่รหัสผ่านก่อน
 */
public class ManagerCard extends KeyCard implements Serializable {
    private static final String MANAGER_ID = "manager101"; // รหัสตายตัวของ Manager Card
    private static final String MANAGER_NAME = "Manager";  // ชื่อที่ใช้แสดงในระบบ

    /**
     * Constructor สำหรับสร้าง ManagerCard
     * - กำหนดชื่อบัตรเป็น "Manager"
     * - กำหนดหมายเลขบัตรเป็น "manager101"
     * - ให้สิทธิ์เข้าถึงทุกห้องในระบบ
     */
    public ManagerCard() {
        super(MANAGER_NAME);
        super.cardId = MANAGER_ID; // ใช้หมายเลขบัตรคงที่
        grantAllPermissions(); // ให้สิทธิ์เข้าทุกห้อง
    }

    /**
     * ให้สิทธิ์เข้าทุกห้องในอาคาร (ทุกหมายเลขห้อง)
     */
    private void grantAllPermissions() {
        String[] allRooms = {"101", "102", "103", "201", "202", "203", "301", "302", "303"};
        for (String room : allRooms) {
            setPermission(room, true);
        }
    }

    /**
     * แสดงชื่อบัตรในรูปแบบ "Manager \nID: manager101"
     * @return ข้อความที่ใช้แสดงใน UI
     */
    @Override
    public String getDisplayName() {
        return MANAGER_NAME + " \nID: " + MANAGER_ID;
    }
}
