package permissions;

/**
 * RoomAccess: จัดการสิทธิ์ในการเข้าห้องสำหรับ KeyCard
 *
 * 🟢 User Story ที่เกี่ยวข้อง:
 * - "ในฐานะผู้ดูแลระบบ ฉันต้องการให้สามารถกำหนดสิทธิ์ KeyCard ให้เข้าถึงห้องที่ต้องการได้"
 * - "ในฐานะผู้ใช้งาน ฉันต้องการให้ระบบสามารถตรวจสอบสิทธิ์ของ KeyCard ก่อนเข้าใช้งานห้อง"
 *
 * 🟢 Features:
 * - รองรับการให้สิทธิ์และเพิกถอนสิทธิ์ของ KeyCard ในระดับ "ห้อง"
 * - ใช้ร่วมกับ Interface `Permission` ตามหลัก **SOLID (Interface Segregation Principle)**
 * - สามารถขยายเพื่อเชื่อมต่อกับฐานข้อมูลหรือระบบเช็คอินได้ในอนาคต
 */
public class RoomAccess implements Permission {

    /**
     * ให้สิทธิ์ KeyCard ในการเข้าถึงห้องที่กำหนด
     * @param cardId หมายเลขบัตรที่ได้รับสิทธิ์
     * @param area หมายเลขห้องที่ได้รับสิทธิ์เข้า
     */
    @Override
    public void grantPermission(String cardId, String area) {
        System.out.println("✅ Room access granted for " + area + " (Card ID: " + cardId + ")");
    }

    /**
     * ยกเลิกสิทธิ์ของ KeyCard ในห้องที่กำหนด
     * @param cardId หมายเลขบัตรที่ถูกเพิกถอนสิทธิ์
     * @param area หมายเลขห้องที่ถูกเพิกถอนสิทธิ์เข้า
     */
    @Override
    public void revokePermission(String cardId, String area) {
        System.out.println("❌ Room access revoked for " + area + " (Card ID: " + cardId + ")");
    }
}
