package example.cashcard;

import org.springframework.data.annotation.Id;  //เรียกใช้ แพคเกจ annotation ในหมวดของ Id

// ประกาศ Java Record ชื่อ CashCard
// Record เป็นคุณสมบัติใหม่ใน Java ที่ช่วยให้เราสร้างคลาสสำหรับเก็บข้อมูลได้กระชับและอ่านง่ายขึ้น
// โดยหลักแล้วจะใช้กับคลาสที่ออกแบบมาเพื่อเก็บข้อมูลและไม่ต้องการให้มีการเปลี่ยนแปลงข้อมูล (immutable data carriers)
// เมื่อเราประกาศ record แบบนี้ คอมไพเลอร์ของ Java จะสร้างสิ่งเหล่านี้ให้โดยอัตโนมัติ:
// 1. Constructor: ตัวสร้างที่รับค่า 'id' และ 'amount'
// 2. Accessor Methods: เมธอด 'id()' และ 'amount()' (คล้ายกับ Getter) เพื่อเข้าถึงค่า
// 3. equals() และ hashCode(): เมธอดสำหรับเปรียบเทียบความเท่าเทียมกันของวัตถุ CashCard
// 4. toString(): เมธอดสำหรับสร้างข้อความแสดงผลของวัตถุ CashCard ที่อ่านง่าย
record CashCard(@Id  Long id, Double amount){

    // ใน record เราไม่จำเป็นต้องเขียน constructor, getter, equals(), hashCode(), หรือ toString()
    // ด้วยตัวเองเลย เพราะคอมไพเลอร์จะจัดการสร้างให้โดยอัตโนมัติจาก components ที่เราประกาศไว้ด้านบน (id, amount)

    // อย่างไรก็ตาม หากเราต้องการเพิ่ม validation (การตรวจสอบข้อมูล)
    // หรือ custom logic บางอย่างที่ต้องการให้ทำงานเมื่อ record ถูกสร้างขึ้น
    // เราสามารถเพิ่ม 'compact constructor' ได้ดังนี้:
    /*
    public CashCard {
        // ตัวอย่าง: ตรวจสอบว่า 'amount' (ยอดเงิน) จะต้องไม่ติดลบ
        if (amount < 0) {
            throw new IllegalArgumentException("Amount cannot be negative"); // ถ้าติดลบ ให้โยน Exception
        }
    }
    */

    // นอกจากนี้ เรายังสามารถเพิ่ม methods อื่นๆ ที่เกี่ยวข้องกับการทำงานของ CashCard ได้ด้วย
    // เช่น methods สำหรับการคำนวณ หรือการตรวจสอบสถานะเพิ่มเติมจากข้อมูลที่มีอยู่
    /*
    public boolean isValidAmount() {
        return this.amount > 0; // ตัวอย่าง: เมธอดที่ตรวจสอบว่ายอดเงินเป็นบวกหรือไม่
    }
    */
}