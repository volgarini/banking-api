package pt.com.bank.banking_api.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import pt.com.bank.banking_api.entity.Customer;
import pt.com.bank.banking_api.factory.entity.CustomerFactory;
import pt.com.bank.banking_api.factory.entity.DocumentTypeFactory;

@SpringBootTest
@Testcontainers(disabledWithoutDocker = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class CustomerRepositoryIT {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void existsByEmail_shouldReturnTrue() {

        Customer customer = CustomerFactory.create(DocumentTypeFactory.create());
        
        customerRepository.save(customer);

        assertTrue(customerRepository.existsByEmail(customer.getEmail()));
    }

    @Test
    void existsByEmail_shouldReturnFalse() {

        assertFalse(customerRepository.existsByEmail("notfound@email.com"));
    }

    @Test
    void existsByPhoneNumber_shouldReturnTrue() {

        Customer customer = CustomerFactory.create(DocumentTypeFactory.create());

        customerRepository.save(customer);

        assertTrue(customerRepository.existsByPhoneNumber(customer.getPhoneNumber()));
    }

    @Test
    void existsByPhoneNumber_shouldReturnFalse() {

        assertFalse(customerRepository.existsByPhoneNumber("999999999"));
    }

    @Test
    void existsByDocumentNumber_shouldReturnTrue() {

        Customer customer = CustomerFactory.create(DocumentTypeFactory.create());

        customerRepository.save(customer);

        assertTrue(customerRepository.existsByDocumentNumber(customer.getDocumentNumber()));
    }

    @Test
    void existsByDocumentNumber_shouldReturnFalse() {

        assertFalse(customerRepository.existsByDocumentNumber("000000000"));
    }

    @Test
    void findByEmail_shouldReturnCustomer() {

        Customer customer = CustomerFactory.create(DocumentTypeFactory.create());

        customerRepository.save(customer);

        Optional<Customer> result =
                customerRepository.findByEmail(customer.getEmail());

        assertTrue(result.isPresent());
        assertEquals(customer.getEmail(), result.get().getEmail());
    }

    @Test
    void findByEmail_shouldReturnEmpty() {

        assertTrue(customerRepository.findByEmail("unknown@email.com").isEmpty());
    }

    @Test
    void findByPhoneNumber_shouldReturnCustomer() {

        Customer customer = CustomerFactory.create(DocumentTypeFactory.create());

        customerRepository.save(customer);

        Optional<Customer> result =
                customerRepository.findByPhoneNumber(customer.getPhoneNumber());

        assertTrue(result.isPresent());
        assertEquals(customer.getPhoneNumber(), result.get().getPhoneNumber());
    }

    @Test
    void findByPhoneNumber_shouldReturnEmpty() {

        assertTrue(customerRepository.findByPhoneNumber("999999999").isEmpty());
    }

    @Test
    void findByDocumentNumber_shouldReturnCustomer() {

        Customer customer = CustomerFactory.create(DocumentTypeFactory.create());

        customerRepository.save(customer);

        Optional<Customer> result =
                customerRepository.findByDocumentNumber(customer.getDocumentNumber());

        assertTrue(result.isPresent());
        assertEquals(customer.getDocumentNumber(), result.get().getDocumentNumber());
    }

    @Test
    void findByDocumentNumber_shouldReturnEmpty() {

        assertTrue(customerRepository.findByDocumentNumber("000000000").isEmpty());
    }
}
