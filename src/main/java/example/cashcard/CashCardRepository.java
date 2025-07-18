package example.cashcard;                    // แพ็คเกจ example.cashcard
import org.springframework.data.repository.CrudRepository;  // นำเข้า CrudRepository
interface CashCardRepository extends CrudRepository {
    // อินเตอร์เฟส CashCardRepository สืบทอด CrudRepository
}