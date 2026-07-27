package pt.com.bank.banking_api.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import pt.com.bank.banking_api.entity.Account;
import pt.com.bank.banking_api.entity.Customer;
import pt.com.bank.banking_api.entity.DocumentType;
import pt.com.bank.banking_api.enums.AccountStatus;
import pt.com.bank.banking_api.enums.AccountType;
import pt.com.bank.banking_api.factory.entity.AccountFactory;
import pt.com.bank.banking_api.factory.entity.CustomerFactory;
import pt.com.bank.banking_api.factory.entity.DocumentTypeFactory;

@SpringBootTest
@Testcontainers(disabledWithoutDocker = true)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
class AccountRepositoryIT {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    @Test
    void existsByAccountNumber_shouldReturnTrue() {

        DocumentType documentType = documentTypeRepository.save(DocumentTypeFactory.create());

        Customer customer = customerRepository.save(CustomerFactory.create(documentType));

        Account account = accountRepository.save(AccountFactory.checking(customer));

        boolean exists = accountRepository.existsByAccountNumber(account.getAccountNumber());

        assertTrue(exists);
    }

    @Test
    void existsByIban_shouldReturnTrue() {

        DocumentType documentType = documentTypeRepository.save(DocumentTypeFactory.create());

        Customer customer = customerRepository.save(CustomerFactory.create(documentType));

        Account account = accountRepository.save(AccountFactory.checking(customer));

        boolean exists = accountRepository.existsByIban(account.getIban());

        assertTrue(exists);
    }

    @Test
    void findByCustomerId_shouldReturnAccounts() {

        DocumentType documentType = documentTypeRepository.save(DocumentTypeFactory.create());

        Customer customer = customerRepository.save(CustomerFactory.create(documentType));

        accountRepository.save(AccountFactory.checking(customer));
        accountRepository.save(AccountFactory.savings(customer));

        List<Account> accounts = accountRepository.findByCustomerId(customer.getId());

        assertEquals(2, accounts.size());
    }

    @Test
    void findByStatus_shouldReturnBlockedAccounts() {

        DocumentType documentType = documentTypeRepository.save(DocumentTypeFactory.create());

        Customer customer = customerRepository.save(CustomerFactory.create(documentType));

        accountRepository.save(AccountFactory.blocked(customer));

        List<Account> accounts = accountRepository.findByStatus(AccountStatus.BLOCKED);

        assertEquals(1, accounts.size());
        assertEquals(AccountStatus.BLOCKED,
                accounts.getFirst().getStatus());
    }

    @Test
    void findByAccountNumber_shouldReturnAccount() {

        DocumentType documentType = documentTypeRepository.save(DocumentTypeFactory.create());

        Customer customer = customerRepository.save(CustomerFactory.create(documentType));

        Account account = accountRepository.save(AccountFactory.checking(customer));

        Optional<Account> result = accountRepository.findByAccountNumber(account.getAccountNumber());

        assertTrue(result.isPresent());
        assertEquals(account.getId(), result.get().getId());
    }

    @Test
    void findByIban_shouldReturnAccount() {

        DocumentType documentType = documentTypeRepository.save(DocumentTypeFactory.create());

        Customer customer = customerRepository.save(CustomerFactory.create(documentType));

        Account account = accountRepository.save(AccountFactory.checking(customer));

        Optional<Account> result = accountRepository.findByIban(account.getIban());

        assertTrue(result.isPresent());
        assertEquals(account.getId(), result.get().getId());
    }

    @Test
    void existsByCustomerIdAndAccountType_shouldReturnTrue() {

        DocumentType documentType = documentTypeRepository.save(DocumentTypeFactory.create());

        Customer customer = customerRepository.save(CustomerFactory.create(documentType));

        accountRepository.save(AccountFactory.checking(customer));

        boolean exists = accountRepository.existsByCustomerIdAndAccountType(
                customer.getId(),
                AccountType.CHECKING);

        assertTrue(exists);
    }
}
