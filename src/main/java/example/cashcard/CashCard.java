package example.cashcard;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import java.util.Objects;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Table(name = "cash_card")  // ชื่อตารางในฐานข้อมูล (ถ้าไม่กำหนด จะใช้ชื่อคลาสเป็นชื่อตาราง)
public class CashCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double amount;

    public CashCard() {
    }

    public CashCard(Long id, Double amount) {
        this.id = id;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CashCard)) return false;
        CashCard cashCard = (CashCard) o;
        return Objects.equals(id, cashCard.id) &&
                Objects.equals(amount, cashCard.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount);
    }

    @Override
    public String toString() {
        return "CashCard{" +
                "id=" + id +
                ", amount=" + amount +
                '}';
    }
}
