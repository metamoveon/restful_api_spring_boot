package example.cashcard;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CashCardJsonTest {

    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    void cashCardSerializationTest() throws Exception {
        CashCard card = new CashCard(99L, 123.45, "owner");

        String json = mapper.writeValueAsString(card);

        String expectedJson = "{\"id\":99,\"amount\":123.45,\"owner\":\"owner\"}";

        // เปรียบเทียบแบบ JSON tree จะไม่สนใจ whitespace
        assertEquals(mapper.readTree(expectedJson), mapper.readTree(json));
    }

    @Test
    void cashCardListSerializationTest() throws Exception {
        List<CashCard> cards = List.of(
                new CashCard(99L, 123.45, "owner"),
                new CashCard(100L, 1.0, "owner"),
                new CashCard(101L, 150.0, "owner")
        );

        String json = mapper.writeValueAsString(cards);

        String expectedJson = "[" +
                "{\"id\":99,\"amount\":123.45,\"owner\":\"owner\"}," +
                "{\"id\":100,\"amount\":1.0,\"owner\":\"owner\"}," +
                "{\"id\":101,\"amount\":150.0,\"owner\":\"owner\"}" +
                "]";

        // เปรียบเทียบแบบ JSON tree ทั้ง list
        assertEquals(mapper.readTree(expectedJson), mapper.readTree(json));
    }
}
