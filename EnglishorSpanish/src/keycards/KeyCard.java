package keycards;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * KeyCard: ใช้สำหรับแทนบัตรที่ใช้เข้าอาคารและห้องต่างๆ
 *
 * 🟢 User Story ที่เกี่ยวข้อง:
 * - "ในฐานะผู้ดูแลระบบ ฉันต้องการให้ KeyCard แต่ละใบมีหมายเลขที่ไม่ซ้ำกัน"
 * - "ในฐานะผู้ใช้งาน ฉันต้องการให้ KeyCard สามารถกำหนดสิทธิ์เข้าใช้งานห้องได้"
 * - "ในฐานะผู้ดูแลระบบ ฉันต้องการให้หมายเลข KeyCard มีการเข้ารหัสด้วยเวลาออกบัตร"
 *
 * 🟢 Features:
 * - มีหมายเลขบัตร (Card ID) ที่ไม่ซ้ำกัน
 * - ใช้ **Time-Based Encryption** ในการสร้างหมายเลขบัตร
 * - สามารถกำหนดสิทธิ์การเข้าห้อง (Permissions) ได้แบบ Dynamic
 */
public class KeyCard implements Serializable {
    protected String name;  // ชื่อเจ้าของบัตร
    protected String cardId;  // หมายเลขบัตร (มีการเข้ารหัสเวลาออกบัตร)
    private Map<String, Boolean> permissions = new HashMap<>(); // สิทธิ์เข้าห้อง

    /**
     * Constructor สำหรับสร้าง KeyCard ใหม่ (กำหนดชื่อ และสร้างหมายเลขบัตรอัตโนมัติ)
     * @param name ชื่อของเจ้าของบัตร
     */
    public KeyCard(String name) {
        this.name = name;
        this.cardId = generateCardId();
    }

    /**
     * Constructor สำหรับสร้าง KeyCard โดยใช้หมายเลขบัตรที่กำหนดเอง
     * (ใช้กรณีโหลดบัตรจากไฟล์ หรือสร้างบัตรด้วย ID ที่มีอยู่แล้ว)
     * @param name ชื่อของเจ้าของบัตร
     * @param cardId หมายเลขบัตรที่กำหนดเอง
     */
    public KeyCard(String name, String cardId) {
        this.name = name;
        this.cardId = cardId;
    }

    /**
     * ใช้สร้างหมายเลขบัตรแบบสุ่ม + Time-Based Encryption (รูปแบบ XXXXXXXXMMDDHHMM)
     * @return หมายเลขบัตรที่ถูกเข้ารหัสเวลา
     */
    private String generateCardId() {
        String baseId = String.valueOf((int) (Math.random() * 10000000)); // สุ่มเลข 7 หลัก
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMddHHmm")); // ดึงวันที่-เวลา
        return baseId + timestamp;
    }

    /**
     * Getter สำหรับดึงหมายเลขบัตร
     * @return หมายเลขบัตรที่ไม่ซ้ำกัน
     */
    public String getCardId() {
        return cardId;
    }

    /**
     * แสดงชื่อเจ้าของบัตร + หมายเลขบัตรในรูปแบบที่อ่านง่าย
     * @return ชื่อเจ้าของบัตรและหมายเลขบัตร
     */
    public String getDisplayName() {
        return name + " - ID: " + cardId;
    }

    /**
     * ตรวจสอบว่าบัตรสามารถเข้าห้องที่กำหนดได้หรือไม่
     * @param room หมายเลขห้องที่ต้องการตรวจสอบ
     * @return true ถ้าบัตรสามารถเข้าห้องได้, false ถ้าไม่ได้รับอนุญาต
     */
    public boolean canAccessRoom(String room) {
        return permissions.getOrDefault(room, false);
    }

    /**
     * กำหนดสิทธิ์การเข้าห้องให้กับบัตร
     * @param room หมายเลขห้องที่ต้องการกำหนดสิทธิ์
     * @param canAccess true ถ้าสามารถเข้าได้, false ถ้าไม่สามารถเข้าได้
     */
    public void setPermission(String room, boolean canAccess) {
        permissions.put(room, canAccess);
    }

    /**
     * Getter สำหรับดึงชื่อเจ้าของบัตร
     * @return ชื่อของเจ้าของบัตร
     */
    public String getName() {
        return name;
    }

    /**
     * Setter สำหรับเปลี่ยนชื่อเจ้าของบัตร
     * @param newName ชื่อใหม่ของเจ้าของบัตร
     */
    public void setName(String newName) {
        this.name = newName;
    }
}
