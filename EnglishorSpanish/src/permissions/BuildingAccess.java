package permissions;

/**
 * BuildingAccess: จัดการสิทธิ์ในการเข้าถึงอาคาร
 *
 * 🟢 User Story ที่เกี่ยวข้อง:
 * - "ในฐานะผู้ดูแลระบบ ฉันต้องการให้ KeyCard สามารถมีสิทธิ์เข้าอาคารหลักก่อนเข้าห้องได้"
 * - "ในฐานะผู้ดูแลระบบ ฉันต้องการให้สามารถเพิ่มหรือยกเลิกสิทธิ์การเข้าอาคารของ KeyCard ได้"
 *
 * 🟢 Features:
 * - รองรับการให้สิทธิ์และยกเลิกสิทธิ์เข้าถึงอาคาร
 * - ใช้ร่วมกับ Interface `Permission` เพื่อรองรับหลักการ **SOLID (Interface Segregation)**
 * - สามารถเพิ่มระบบตรวจสอบสิทธิ์เพิ่มเติมในอนาคต
 */
public class BuildingAccess implements Permission {

    /**
     * ให้สิทธิ์ KeyCard ในการเข้าถึงอาคาร
     * @param cardId หมายเลขบัตรที่ต้องการให้สิทธิ์
     * @param area พื้นที่อาคารที่ได้รับสิทธิ์เข้า
     */
    @Override
    public void grantPermission(String cardId, String area) {
        System.out.println("✅ Building access granted for " + area + " (Card ID: " + cardId + ")");
    }

    /**
     * ยกเลิกสิทธิ์การเข้าถึงอาคารของ KeyCard
     * @param cardId หมายเลขบัตรที่ต้องการยกเลิกสิทธิ์
     * @param area พื้นที่อาคารที่ถูกเพิกถอนสิทธิ์
     */
    @Override
    public void revokePermission(String cardId, String area) {
        System.out.println("❌ Building access revoked for " + area + " (Card ID: " + cardId + ")");
    }
}
