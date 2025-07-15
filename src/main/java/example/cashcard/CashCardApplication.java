package example.cashcard; // ควรอยู่ในแพ็คเกจหลักของแอปพลิเคชัน

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // Annotation สำคัญที่บอกว่านี่คือคลาสหลักของ Spring Boot Application
public class CashCardApplication {

    public static void main(String[] args) {
        // เมธอดหลักที่ใช้สำหรับรัน Spring Boot Application
        SpringApplication.run(CashCardApplication.class, args);
    }

}