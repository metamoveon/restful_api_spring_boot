package example.cashcard;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "cash_card")
public class CashCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    @JsonIgnore
    private String owner;  // ✅ เพิ่มฟิลด์ owner

    // Default constructor for JPA
    public CashCard() {
    }

    // Constructor แบบ 2 พารามิเตอร์
    public CashCard(Long id, Double amount) {
        this.id = id;
        this.amount = amount;
    }

    // ✅ Constructor แบบ 3 พารามิเตอร์
    public CashCard(Long id, Double amount, String owner) {
        this.id = id;
        this.amount = amount;
        this.owner = owner;
    }

    // Getters and Setters
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CashCard)) return false;
        CashCard cashCard = (CashCard) o;
        return Objects.equals(id, cashCard.id) &&
                Objects.equals(amount, cashCard.amount) &&
                Objects.equals(owner, cashCard.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, amount, owner);
    }

    // toString
    @Override
    public String toString() {
        return "CashCard{" +
                "id=" + id +
                ", amount=" + amount +
                ", owner='" + owner + '\'' +
                '}';
    }
}
