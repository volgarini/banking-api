package pt.com.bank.banking_api.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pt.com.bank.banking_api.dto.request.CreateAccountRequest;
import pt.com.bank.banking_api.dto.request.UpdateAccountRequest;
import pt.com.bank.banking_api.dto.response.AccountResponse;
import pt.com.bank.banking_api.entity.Account;
import pt.com.bank.banking_api.entity.Customer;
import pt.com.bank.banking_api.enums.AccountStatus;
import pt.com.bank.banking_api.enums.AccountType;
import pt.com.bank.banking_api.exception.conflicts.CustomerAlreadyHasCheckingAccountException;
import pt.com.bank.banking_api.exception.resources.AccountNotFoundException;
import pt.com.bank.banking_api.exception.resources.CustomerNotFoundException;
import pt.com.bank.banking_api.mapper.AccountMapper;
import pt.com.bank.banking_api.repository.AccountRepository;
import pt.com.bank.banking_api.repository.CustomerRepository;
import pt.com.bank.banking_api.service.AccountService;
import pt.com.bank.banking_api.service.generator.AccountNumberGenerator;
import pt.com.bank.banking_api.service.generator.IbanGenerator;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final AccountMapper accountMapper;
    private final AccountNumberGenerator accountNumberGenerator;
    private final IbanGenerator ibanGenerator;

    @Override
    public AccountResponse create(CreateAccountRequest request) {

        Customer customer = customerRepository.findById(request.customerId())
                .orElseThrow(() -> new CustomerNotFoundException(request.customerId()));

        validateCheckingAccount(customer.getId(), request.accountType());

        Account account = accountMapper.toEntity(request);

        account.setCustomer(customer);
        account.setAccountNumber(generateAccountNumber());
        account.setIban(generateIban());
        account.setStatus(AccountStatus.ACTIVE);
        account.setBalance(BigDecimal.ZERO);

        return accountMapper.toResponse(accountRepository.save(account));
    }

    @Override
    public AccountResponse findById(UUID id) {
        return accountMapper.toResponse(findAccount(id));
    }

    @Override
    public List<AccountResponse> findAll() {
        return accountRepository.findAll()
                .stream()
                .map(accountMapper::toResponse)
                .toList();
    }

    @Override
    public AccountResponse update(UUID id, UpdateAccountRequest request) {

        Account account = findAccount(id);

        accountMapper.updateEntity(request, account);

        return accountMapper.toResponse(accountRepository.save(account));
    }

    @Override
    public void delete(UUID id) {
        accountRepository.delete(findAccount(id));
    }

    private Account findAccount(UUID id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
    }

    private void validateCheckingAccount(UUID customerId, AccountType accountType) {

        if (accountType == AccountType.CHECKING
                && accountRepository.existsByCustomerIdAndAccountType(
                        customerId,
                        AccountType.CHECKING)) {

            throw new CustomerAlreadyHasCheckingAccountException(customerId);
        }
    }

    private String generateAccountNumber() {

        String accountNumber;

        do {
            accountNumber = accountNumberGenerator.generate();
        } while (accountRepository.existsByAccountNumber(accountNumber));

        return accountNumber;
    }

    private String generateIban() {

        String iban;

        do {
            iban = ibanGenerator.generate();
        } while (accountRepository.existsByIban(iban));

        return iban;
    }
}
