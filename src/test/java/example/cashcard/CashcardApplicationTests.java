package example.cashcard;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CashCardApplicationTests {
	@Autowired
	TestRestTemplate restTemplate;

	private TestRestTemplate withAuth() {
		return restTemplate.withBasicAuth("sarah1", "abc123");
	}

	@Test
	@DirtiesContext
	void shouldReturnACashCardWhenDataIsSaved() {
		CashCard cashCard = new CashCard(null, 500.00, "sarah1");

		ResponseEntity<Void> postResponse = withAuth().postForEntity("/cashcards", cashCard, Void.class);
		assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		// ดึง URI ที่ระบบสร้างให้
		URI location = postResponse.getHeaders().getLocation();

		// ใช้ location ที่ Spring คืนมา เพื่อ GET ตรวจสอบ
		ResponseEntity<CashCard> getResponse = withAuth().getForEntity(location, CashCard.class);
		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(getResponse.getBody().getAmount()).isEqualTo(500.00);
		assertThat(getResponse.getBody().getOwner()).isEqualTo("sarah1");
	}


	@Test
	void shouldNotReturnACashCardWithAnUnknownId() {
		ResponseEntity<String> response = withAuth().getForEntity("/cashcards/1000", String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
		assertThat(response.getBody()).isBlank();
	}


	@Test
	@DirtiesContext
	void shouldCreateANewCashCard() {
		// สร้าง restTemplate ที่ใส่ username/password
		TestRestTemplate authenticatedRestTemplate = withAuth().withBasicAuth("sarah1", "abc123");

		CashCard newCashCard = new CashCard(null, 250.00, "sarah1");
		ResponseEntity<Void> createResponse = authenticatedRestTemplate.postForEntity("/cashcards", newCashCard, Void.class);
		assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		URI locationOfNewCashCard = createResponse.getHeaders().getLocation();
		ResponseEntity<String> getResponse = authenticatedRestTemplate.getForEntity(locationOfNewCashCard, String.class);
		assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(getResponse.getBody());
		Number id = documentContext.read("$.id");
		Double amount = documentContext.read("$.amount");

		assertThat(id).isNotNull();
		assertThat(amount).isEqualTo(250.00);
	}


	@Test
	void shouldReturnAllCashCardsWhenListIsRequested() {
		ResponseEntity<String> response = withAuth().getForEntity("/cashcards", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		int cashCardCount = documentContext.read("$.length()");
		assertThat(cashCardCount).isEqualTo(3);

		JSONArray ids = documentContext.read("$..id");
		assertThat(ids).containsExactlyInAnyOrder(99, 100, 101);

		JSONArray amounts = documentContext.read("$..amount");
		assertThat(amounts).containsExactlyInAnyOrder(123.45, 1.00, 150.00);
	}

	@Test
	void shouldReturnAPageOfCashCards() {
		ResponseEntity<String> response = withAuth().getForEntity("/cashcards?page=0&size=1", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		JSONArray page = documentContext.read("$[*]");
		assertThat(page.size()).isEqualTo(1);
	}

	@Test
	@DirtiesContext
	void shouldReturnASortedPageOfCashCards() {
		// เตรียมข้อมูลก่อน (ไม่งั้นฐานข้อมูลจะว่าง)
		withAuth().postForEntity("/cashcards", new CashCard(null, 150.00, "sarah1"), Void.class);
		withAuth().postForEntity("/cashcards", new CashCard(null, 123.45, "sarah1"), Void.class);
		withAuth().postForEntity("/cashcards", new CashCard(null, 1.00, "sarah1"), Void.class);

		// เรียกข้อมูลแบบ sort ตาม amount จากมากไปน้อย
		ResponseEntity<String> response = withAuth().getForEntity("/cashcards?page=0&size=1&sort=amount,desc", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		JSONArray read = documentContext.read("$[*]");
		assertThat(read.size()).isEqualTo(1);

		double amount = documentContext.read("$[0].amount");
		assertThat(amount).isEqualTo(150.00);
	}


	@Test
	@DirtiesContext
	void shouldReturnASortedPageOfCashCardsWithNoParametersAndUseDefaultValues() {
		// เตรียมข้อมูลใหม่ 3 รายการ
		withAuth().postForEntity("/cashcards", new CashCard(null, 123.45, "sarah1"), Void.class);
		withAuth().postForEntity("/cashcards", new CashCard(null, 1.00, "sarah1"), Void.class);
		withAuth().postForEntity("/cashcards", new CashCard(null, 150.00, "sarah1"), Void.class);

		// ทดสอบเรียกดูข้อมูล
		ResponseEntity<String> response = withAuth().getForEntity("/cashcards", String.class);
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

		DocumentContext documentContext = JsonPath.parse(response.getBody());
		JSONArray page = documentContext.read("$[*]");
		assertThat(page.size()).isEqualTo(3); // ✅ ตอนนี้จะผ่าน

		JSONArray amounts = documentContext.read("$..amount");
		assertThat(amounts).containsExactly(1.00, 123.45, 150.00); // ✅ เรียงตาม default (amount ascending)
	}


}
