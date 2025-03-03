package permissions;

/**
 * Permission: Interface สำหรับการจัดการสิทธิ์ของ KeyCard
 *
 * 🟢 User Story ที่เกี่ยวข้อง:
 * - "ในฐานะผู้ดูแลระบบ ฉันต้องการให้สามารถกำหนดสิทธิ์การเข้าถึงได้ ทั้งในระดับอาคารและห้อง"
 * - "ในฐานะผู้ดูแลระบบ ฉันต้องการให้มีโครงสร้างโค้ดที่สามารถขยายได้ในอนาคต"
 *
 * 🟢 Features:
 * - ใช้เป็น Interface สำหรับกำหนดสิทธิ์ให้กับ KeyCard
 * - รองรับการให้และเพิกถอนสิทธิ์
 * - ใช้ร่วมกับ `BuildingAccess` และ `RoomAccess` ได้
 * - **ออกแบบตามหลัก SOLID (Interface Segregation Principle)**
 */
public interface Permission {

    /**
     * ให้สิทธิ์ KeyCard ในการเข้าถึงพื้นที่ที่กำหนด
     * @param cardId หมายเลขบัตรที่ได้รับสิทธิ์
     * @param area พื้นที่ที่ได้รับสิทธิ์เข้า
     */
    void grantPermission(String cardId, String area);

    /**
     * ยกเลิกสิทธิ์ของ KeyCard ในพื้นที่ที่กำหนด
     * @param cardId หมายเลขบัตรที่ถูกเพิกถอนสิทธิ์
     * @param area พื้นที่ที่ถูกเพิกถอนสิทธิ์เข้า
     */
    void revokePermission(String cardId, String area);
}
