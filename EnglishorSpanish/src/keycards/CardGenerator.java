package keycards;

import java.time.LocalTime;
import java.util.Random;

/**
 * CardGenerator: ใช้สำหรับสร้างหมายเลขบัตร (Card ID) แบบสุ่ม
 *
 * 🟢 User Story ที่เกี่ยวข้อง:
 * - "ในฐานะผู้ดูแลระบบ ฉันต้องการให้ระบบสามารถสร้างหมายเลขบัตรได้แบบสุ่มและไม่ซ้ำกัน"
 * - "ในฐานะผู้ดูแลระบบ ฉันต้องการให้หมายเลขบัตรมีการเข้ารหัสเวลา เพื่อให้สามารถระบุช่วงเวลาที่ออกบัตรได้"
 *
 * 🟢 Features:
 * - สร้างหมายเลขบัตรแบบสุ่มที่ไม่ซ้ำกัน
 * - ใช้ **Time-Based Encryption** โดยเพิ่มเวลาปัจจุบันเข้าไปในหมายเลขบัตร
 */
public class CardGenerator {

    /**
     * สร้างหมายเลขบัตร (Card ID) โดยรวมเลขสุ่ม 7 หลักกับข้อมูลเวลาแบบ Time-Based Encryption
     * @return หมายเลขบัตรในรูปแบบ "XXXXXXXTTTT" (เลขสุ่ม 7 หลัก + เวลาปัจจุบัน 4 หลัก)
     */
    public static String generateCardID() {
        Random rand = new Random();

        // สุ่มเลข 7 หลัก (เริ่มจาก 1000000 เพื่อให้มี 7 หลักเสมอ)
        int cardNumber = 1000000 + rand.nextInt(9000000);

        // ดึงเวลาปัจจุบัน (ชั่วโมงและนาที) และแปลงเป็นสตริง เช่น "1045" หมายถึง 10:45 AM
        String timeSuffix = LocalTime.now().toString().replace(":", "").substring(0, 4);

        // รวมเลขสุ่มและเวลาเข้าด้วยกัน
        return cardNumber + timeSuffix;
    }
}
