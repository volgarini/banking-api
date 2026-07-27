package pt.com.bank.banking_api.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import pt.com.bank.banking_api.entity.Account;
import pt.com.bank.banking_api.enums.AccountStatus;
import pt.com.bank.banking_api.enums.AccountType;

public interface AccountRepository extends JpaRepository<Account, UUID> {

    boolean existsByAccountNumber(String accountNumber);

    boolean existsByIban(String iban);

    List<Account> findByCustomerId(UUID customerId);

    List<Account> findByStatus(AccountStatus status);

    List<Account> findByAccountType(AccountType accountType);

    Optional<Account> findByAccountNumber(String accountNumber);

    Optional<Account> findByIban(String iban);

    boolean existsByCustomerIdAndAccountType(UUID customerId, AccountType accountType);
}
