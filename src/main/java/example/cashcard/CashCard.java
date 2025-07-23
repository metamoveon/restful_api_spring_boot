package example.cashcard;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public record CashCard(@Id Long id, Double amount) {
}
