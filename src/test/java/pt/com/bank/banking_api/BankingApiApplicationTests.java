package pt.com.bank.banking_api;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import pt.com.bank.banking_api.dto.request.CreateCustomerRequest;
import pt.com.bank.banking_api.dto.request.UpdateCustomerRequest;
import pt.com.bank.banking_api.dto.response.CustomerResponse;
import pt.com.bank.banking_api.dto.response.PageResponse;
import pt.com.bank.banking_api.entity.DocumentType;
import pt.com.bank.banking_api.repository.CustomerRepository;
import pt.com.bank.banking_api.repository.DocumentTypeRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Testcontainers
class BankingApiApplicationTests {

	@Container
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17");

	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgres::getJdbcUrl);
		registry.add("spring.datasource.username", postgres::getUsername);
		registry.add("spring.datasource.password", postgres::getPassword);
	}

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private DocumentTypeRepository documentTypeRepository;

	private DocumentType documentType;

	@BeforeEach
	void setUp() {
		customerRepository.deleteAll();
		documentType = documentTypeRepository.findAll().stream()
				.findFirst()
				.orElseThrow();
	}

	@Test
	void shouldCreateFindAndListCustomers() {
		CreateCustomerRequest request = createRequest();

		ResponseEntity<CustomerResponse> createResponse = restTemplate.postForEntity(
				"/api/v1/customers", request, CustomerResponse.class);

		assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		assertThat(createResponse.getBody()).isNotNull();
		assertThat(createResponse.getBody().email()).isEqualTo(request.email());

		UUID customerId = createResponse.getBody().id();
		ResponseEntity<CustomerResponse> findResponse = restTemplate.getForEntity(
				"/api/v1/customers/{id}", CustomerResponse.class, customerId);

		assertThat(findResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(findResponse.getBody().id()).isEqualTo(customerId);

		ResponseEntity<PageResponse<CustomerResponse>> listResponse = restTemplate.exchange(
				"/api/v1/customers?page=0&size=10", HttpMethod.GET, null,
				new ParameterizedTypeReference<>() {});

		assertThat(listResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(listResponse.getBody().totalElements()).isEqualTo(1);
		assertThat(listResponse.getBody().content()).extracting(CustomerResponse::id)
				.containsExactly(customerId);
	}

	@Test
	void shouldUpdateAndDeleteCustomer() {
		CustomerResponse customer = createCustomer();
		UpdateCustomerRequest updateRequest = new UpdateCustomerRequest(
				"Updated Customer", "updated@example.com", "+351911111111",
			documentType.getId(), "UPDATED-123");

		ResponseEntity<CustomerResponse> updateResponse = restTemplate.exchange(
				"/api/v1/customers/{id}", HttpMethod.PUT, new HttpEntity<>(updateRequest),
				CustomerResponse.class, customer.id());

		assertThat(updateResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(updateResponse.getBody().fullName()).isEqualTo(updateRequest.fullName());
		assertThat(updateResponse.getBody().email()).isEqualTo(updateRequest.email());

		ResponseEntity<Void> deleteResponse = restTemplate.exchange(
				"/api/v1/customers/{id}", HttpMethod.DELETE, null, Void.class, customer.id());

		assertThat(deleteResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

		ResponseEntity<String> findResponse = restTemplate.getForEntity(
				"/api/v1/customers/{id}", String.class, customer.id());
		assertThat(findResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
	}

	@Test
	void shouldRejectDuplicateEmail() {
		CreateCustomerRequest request = createRequest();
		restTemplate.postForEntity("/api/v1/customers", request, CustomerResponse.class);

		CreateCustomerRequest duplicateEmailRequest = new CreateCustomerRequest(
				"Another Customer", request.email(), "+351922222222", documentType.getId(), "DIFFERENT-123");
		ResponseEntity<String> response = restTemplate.postForEntity(
				"/api/v1/customers", duplicateEmailRequest, String.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
	}

	private CustomerResponse createCustomer() {
		ResponseEntity<CustomerResponse> response = restTemplate.postForEntity(
				"/api/v1/customers", createRequest(), CustomerResponse.class);

		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
		return response.getBody();
	}

	private CreateCustomerRequest createRequest() {
		String suffix = UUID.randomUUID().toString().substring(0, 8);
		return new CreateCustomerRequest(
				"Integration Customer", "customer-" + suffix + "@example.com", "+351900000000",
			documentType.getId(), "DOC-" + suffix);
	}

}
