package example.cashcard;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

interface CashCardRepository extends CrudRepository<CashCard, Long>, PagingAndSortingRepository<CashCard, Long>{
        // เปลี่ยนจาก CashCard เป็น Optional<CashCard> เพื่อจัดการกรณีที่ไม่พบข้อมูล
        CashCard findByIdAndOwner(Long id, String owner);
        Page<CashCard> findByOwner(String owner, Pageable pageable);
        boolean existsByIdAndOwner(Long id, String owner);
}