package logs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * LogManager: ใช้สำหรับบันทึกข้อมูล Log ของระบบ
 *
 * 🟢 User Story ที่เกี่ยวข้อง:
 * - "ในฐานะผู้ดูแลระบบ ฉันต้องการให้ทุกการกระทำเกี่ยวกับ KeyCard ถูกบันทึกไว้"
 * - "ในฐานะผู้ดูแลระบบ ฉันต้องการให้มี Log การเข้า-ออกห้องของผู้ใช้"
 * - "ในฐานะผู้ดูแลระบบ ฉันต้องการให้ Log ถูกจัดเก็บเป็นไฟล์แยกตามวัน เพื่อให้ค้นหาข้อมูลย้อนหลังได้ง่าย"
 *
 * 🟢 Features:
 * - **สร้างโฟลเดอร์ `logs/` อัตโนมัติ** ถ้ายังไม่มี
 * - **บันทึก Log ลงไฟล์** โดยใช้ชื่อไฟล์เป็น `log_YYYY-MM-DD.txt`
 * - **บันทึกเวลาที่เกิดเหตุการณ์ (Timestamp)** ไว้ในแต่ละบรรทัดของ Log
 */
public class LogManager {
    private static final String LOG_FOLDER = "logs"; // โฟลเดอร์เก็บ Log

    // สร้างโฟลเดอร์ Log ถ้ายังไม่มี
    static {
        ensureLogFolderExists();
    }

    /**
     * ตรวจสอบว่ามีโฟลเดอร์ `logs/` แล้วหรือไม่ ถ้าไม่มีให้สร้างขึ้นมา
     */
    private static void ensureLogFolderExists() {
        File folder = new File(LOG_FOLDER);
        if (!folder.exists()) {
            folder.mkdirs(); // สร้างโฟลเดอร์ logs/
        }
    }

    /**
     * บันทึกข้อความลงในไฟล์ Log ของวันนั้นๆ
     * @param message ข้อความที่ต้องการบันทึก
     */
    public static void saveLog(String message) {
        ensureLogFolderExists(); // ตรวจสอบโฟลเดอร์ก่อน
        String filename = LOG_FOLDER + "/log_" + LocalDate.now() + ".txt"; // ตั้งชื่อไฟล์เป็น log_YYYY-MM-DD.txt

        try (FileWriter writer = new FileWriter(filename, true)) { // เปิดไฟล์ในโหมด append
            writer.write("[" + LocalDateTime.now() + "] " + message + "\n"); // บันทึก Timestamp + ข้อความ
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
