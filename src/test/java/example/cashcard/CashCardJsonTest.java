package example.cashcard;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import java.io.IOException;
import static org.assertj.core.api.Assertions.assertThat;

// @JsonTest ใช้สำหรับบอก Spring Boot ว่านี่คือคลาสสำหรับทดสอบเกี่ยวกับการแปลงข้อมูล JSON
// โดยจะทำการตั้งค่าที่จำเป็นให้โดยอัตโนมัติ เช่น ObjectMapper และ JacksonTester
@JsonTest
class CashCardJsonTest {

    // @Autowired คือการบอกให้ Spring Boot ฉีด (Inject) dependency ที่จัดการโดย Spring Container เข้ามาให้โดยอัตโนมัติ
    // ในที่นี้คือการขอ JacksonTester ที่ถูกสร้างและตั้งค่าไว้สำหรับทดสอบคลาส CashCard
    @Autowired
    private JacksonTester<CashCard> json;

    // @Test คือ Annotation จาก JUnit ที่ระบุว่าเมธอดนี้เป็น Test Case ที่ต้องถูกรัน
    @Test
    void cashCardSerializationTest() throws IOException {
        // สร้าง Object ของ CashCard เพื่อใช้ในการทดสอบ
        CashCard cashCard = new CashCard(99L, 123.45);

        // --- การทดสอบที่ 1: เปรียบเทียบผลลัพธ์ JSON กับไฟล์ที่คาดหวัง ---
        // json.write(cashCard) -> แปลง cashCard object ให้เป็น JSON string
        // isStrictlyEqualToJson("expected.json") -> เปรียบเทียบ JSON string ที่ได้กับเนื้อหาในไฟล์ expected.json
        // ไฟล์นี้จะถูกเก็บไว้ที่ src/test/resources/example/cashcard/expected.json
        assertThat(json.write(cashCard)).isStrictlyEqualToJson("expected.json");

        // --- การทดสอบที่ 2: ตรวจสอบว่ามี key 'id' และมีค่าที่ถูกต้อง ---
        // hasJsonPathNumberValue("@.id") -> ตรวจสอบว่าใน JSON document มี key 'id' และค่าของมันเป็นตัวเลข
        assertThat(json.write(cashCard)).hasJsonPathNumberValue("@.id");
        // extractingJsonPathNumberValue("@.id") -> ดึงค่าตัวเลขจาก key 'id' ออกมา
        // isEqualTo(99) -> ตรวจสอบว่าค่าที่ดึงออกมาเท่ากับ 99
        assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.id").isEqualTo(99);

        // --- การทดสอบที่ 3: ตรวจสอบว่ามี key 'amount' และมีค่าที่ถูกต้อง ---
        // hasJsonPathNumberValue("@.amount") -> ตรวจสอบว่าใน JSON document มี key 'amount' และค่าของมันเป็นตัวเลข
        assertThat(json.write(cashCard)).hasJsonPathNumberValue("@.amount");
        // extractingJsonPathNumberValue("@.amount") -> ดึงค่าตัวเลขจาก key 'amount' ออกมา
        // isEqualTo(123.45) -> ตรวจสอบว่าค่าที่ดึงออกมาเท่ากับ 123.45
        assertThat(json.write(cashCard)).extractingJsonPathNumberValue("@.amount").isEqualTo(123.45);
    }

    @Test // @Test: Annotation จาก JUnit ที่ระบุว่าเมธอดนี้เป็น Test Case ที่ต้องถูกรัน
    void cashCardDeserializationTest() throws IOException { // เมธอดทดสอบการ Deserialization (แปลง JSON String กลับเป็น Object Java)
        // throws IOException: แจ้งว่าเมธอดนี้อาจเกิดข้อผิดพลาดที่เกี่ยวข้องกับการอ่าน/เขียน (เช่น การอ่าน JSON String)
        String expected = """   
            {                   
                "id":99,        
                "amount":123.45 
                
            }
            """;

        // 1. ตรวจสอบการ Deserialization ของ Object ทั้งหมด
        // json.parseObject(expected): ทำการแปลง JSON String 'expected' ให้เป็น CashCard Object
        // .id(): ดึงค่า id จาก CashCard Object ที่ได้จากการแปลง
        // .isEqualTo(new CashCard(99L,123.45)): ยืนยันว่า CashCard Object ที่ได้มี id และ amount ตรงกับ CashCard ที่สร้างขึ้นใหม่
        //                                       (นี่เป็นการยืนยันว่า Object ทั้งก้อนถูกแปลงอย่างถูกต้อง)
        assertThat(json.parse(expected)).isEqualTo(new CashCard(99L,123.45));

        // 2. ตรวจสอบค่า 'id' ที่ได้จากการ Deserialization
        // json.parseObject(expected): ทำการแปลง JSON String ให้เป็น CashCard Object
        // .id(): ดึงค่า id จาก CashCard Object ที่ได้
        // .isEqualTo(99): ยืนยันว่าค่า id ที่ได้นั้นเท่ากับ 99 (ซึ่งตรงกับค่าใน JSON String)
        assertThat(json.parseObject(expected).id()).isEqualTo(99);

        // 3. ตรวจสอบค่า 'amount' ที่ได้จากการ Deserialization
        // json.parseObject(expected): ทำการแปลง JSON String ให้เป็น CashCard Object
        // .amount(): ดึงค่า amount จาก CashCard Object ที่ได้
        // .isEqualTo(123.45): ยืนยันว่าค่า amount ที่ได้นั้นเท่ากับ 123.45 (ซึ่งตรงกับค่าใน JSON String)
        assertThat(json.parseObject(expected).amount()).isEqualTo(123.45);
    }
}