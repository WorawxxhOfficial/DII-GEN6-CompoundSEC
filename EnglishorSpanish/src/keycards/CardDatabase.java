package keycards;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * CardDatabase: จัดการการเก็บข้อมูลของ KeyCards ทั้งหมด
 *
 * 🟢 User Story ที่เกี่ยวข้อง:
 * - "ในฐานะผู้ดูแลระบบ ฉันต้องการให้การ์ดที่ถูกเพิ่มไว้ยังคงอยู่ แม้ปิดโปรแกรม"
 * - "ในฐานะผู้ดูแลระบบ ฉันต้องการให้การ์ดของ Manager ต้องมีอยู่เสมอ และไม่สามารถลบได้"
 * - "ในฐานะผู้ใช้ ฉันต้องการให้ระบบสามารถดึงข้อมูลบัตรที่เคยถูกสร้างขึ้นมาใหม่เมื่อเปิดโปรแกรม"
 *
 * 🟢 Features:
 * - เก็บข้อมูล KeyCard ในไฟล์ `cards.dat`
 * - โหลด KeyCard กลับมาเมื่อเปิดโปรแกรมใหม่
 * - เพิ่ม/ลบ KeyCard
 * - Manager Card ต้องมีอยู่เสมอ และไม่สามารถถูกลบได้
 */
public class CardDatabase {
    private static List<KeyCard> cards = new ArrayList<>();
    private static final String FILE_NAME = "cards.dat";

    // โหลดข้อมูล KeyCards และตรวจสอบให้แน่ใจว่ามี Manager Card เสมอ
    static {
        loadCards();
        ensureManagerCardExists();
    }

    /**
     * ตรวจสอบว่ามี Manager Card หรือไม่ ถ้าไม่มีจะเพิ่มเข้าไป
     */
    private static void ensureManagerCardExists() {
        boolean hasManager = cards.stream().anyMatch(card -> card instanceof ManagerCard);
        if (!hasManager) {
            cards.add(new ManagerCard());
            saveCards();
        }
    }

    /**
     * เพิ่ม KeyCard ใหม่เข้าไปในระบบ และบันทึกลงไฟล์
     * @param card KeyCard ที่จะถูกเพิ่ม
     */
    public static void addCard(KeyCard card) {
        cards.add(card);
        saveCards();
    }

    /**
     * ลบ KeyCard ออกจากระบบ (ยกเว้น Manager Card)
     * @param displayName ชื่อของ KeyCard ที่จะถูกลบ
     */
    public static void removeCard(String displayName) {
        if (!displayName.contains("Manager")) { // ป้องกันการลบ Manager Card
            cards.removeIf(card -> card.getDisplayName().equals(displayName));
            saveCards();
        }
    }

    /**
     * ดึงรายการ KeyCard ทั้งหมด
     * @return รายการ KeyCard ที่ถูกเก็บอยู่ในฐานข้อมูล
     */
    public static List<KeyCard> getCards() {
        return cards;
    }

    /**
     * ค้นหา KeyCard โดยใช้ชื่อที่แสดง (Display Name)
     * @param name ชื่อของ KeyCard ที่ต้องการค้นหา
     * @return KeyCard ที่ตรงกับชื่อ หรือ null ถ้าไม่พบ
     */
    public static KeyCard getCardByName(String name) {
        return cards.stream().filter(card -> card.getDisplayName().equals(name)).findFirst().orElse(null);
    }

    /**
     * บันทึกข้อมูล KeyCards ทั้งหมดลงไฟล์ `cards.dat`
     */
    private static void saveCards() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(cards);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * โหลดข้อมูล KeyCards จากไฟล์ `cards.dat`
     */
    private static void loadCards() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return; // ถ้าไม่มีไฟล์ก็ไม่ต้องโหลด

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            cards = (List<KeyCard>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
