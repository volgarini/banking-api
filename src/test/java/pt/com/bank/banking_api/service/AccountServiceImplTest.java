package pt.com.bank.banking_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pt.com.bank.banking_api.dto.request.CreateAccountRequest;
import pt.com.bank.banking_api.dto.request.UpdateAccountRequest;
import pt.com.bank.banking_api.dto.response.AccountResponse;
import pt.com.bank.banking_api.entity.Account;
import pt.com.bank.banking_api.entity.Customer;
import pt.com.bank.banking_api.entity.DocumentType;
import pt.com.bank.banking_api.enums.AccountType;
import pt.com.bank.banking_api.exception.resources.CustomerNotFoundException;
import pt.com.bank.banking_api.factory.constants.AccountTestConstants;
import pt.com.bank.banking_api.factory.constants.CustomerTestConstants;
import pt.com.bank.banking_api.factory.entity.AccountFactory;
import pt.com.bank.banking_api.factory.entity.CustomerFactory;
import pt.com.bank.banking_api.factory.entity.DocumentTypeFactory;
import pt.com.bank.banking_api.factory.request.CreateAccountRequestFactory;
import pt.com.bank.banking_api.factory.request.UpdateAccountRequestFactory;
import pt.com.bank.banking_api.factory.response.AccountResponseFactory;
import pt.com.bank.banking_api.mapper.AccountMapper;
import pt.com.bank.banking_api.repository.AccountRepository;
import pt.com.bank.banking_api.repository.CustomerRepository;
import pt.com.bank.banking_api.service.generator.AccountNumberGenerator;
import pt.com.bank.banking_api.service.generator.IbanGenerator;
import pt.com.bank.banking_api.service.impl.AccountServiceImpl;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AccountMapper accountMapper;

    @Mock
    private AccountNumberGenerator accountNumberGenerator;

    @Mock
    private IbanGenerator ibanGenerator;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void create_shouldCreateCheckingAccountSuccessfully() {
        // Arrange
        CreateAccountRequest request = CreateAccountRequestFactory.create();

        DocumentType documentType = DocumentTypeFactory.create();
        Customer customer = CustomerFactory.create(documentType);

        Account account = AccountFactory.checking(customer);
        Account savedAccount = AccountFactory.checking(customer);
        AccountResponse expectedResponse = AccountResponseFactory.from(savedAccount);

        when(customerRepository.findById(CustomerTestConstants.CUSTOMER_ID))
                .thenReturn(Optional.of(customer));

        when(accountRepository.existsByCustomerIdAndAccountType(
                CustomerTestConstants.CUSTOMER_ID,
                AccountType.CHECKING))
                .thenReturn(false);

        when(accountMapper.toEntity(request))
                .thenReturn(account);

        when(accountNumberGenerator.generate())
                .thenReturn(AccountTestConstants.ACCOUNT_NUMBER);

        when(accountRepository.existsByAccountNumber(AccountTestConstants.ACCOUNT_NUMBER))
                .thenReturn(false);

        when(ibanGenerator.generate())
                .thenReturn(AccountTestConstants.IBAN);

        when(accountRepository.existsByIban(AccountTestConstants.IBAN))
                .thenReturn(false);

        when(accountRepository.save(account))
                .thenReturn(savedAccount);

        when(accountMapper.toResponse(savedAccount))
                .thenReturn(expectedResponse);

        // Act
        AccountResponse response = accountService.create(request);

        // Assert
        assertNotNull(response);

        assertEquals(expectedResponse.id(), response.id());
        assertEquals(expectedResponse.accountNumber(), response.accountNumber());
        assertEquals(expectedResponse.iban(), response.iban());
        assertEquals(expectedResponse.customerId(), response.customerId());
        assertEquals(expectedResponse.customerName(), response.customerName());
        assertEquals(expectedResponse.accountType(), response.accountType());
        assertEquals(expectedResponse.status(), response.status());
        assertEquals(expectedResponse.balance(), response.balance());

        verify(customerRepository).findById(CustomerTestConstants.CUSTOMER_ID);

        verify(accountRepository)
                .existsByCustomerIdAndAccountType(
                        CustomerTestConstants.CUSTOMER_ID,
                        AccountType.CHECKING);

        verify(accountMapper).toEntity(request);

        verify(accountNumberGenerator).generate();
        verify(accountRepository)
                .existsByAccountNumber(AccountTestConstants.ACCOUNT_NUMBER);

        verify(ibanGenerator).generate();
        verify(accountRepository)
                .existsByIban(AccountTestConstants.IBAN);

        verify(accountRepository).save(account);
        verify(accountMapper).toResponse(savedAccount);

        verifyNoMoreInteractions(
                customerRepository,
                accountRepository,
                accountMapper,
                accountNumberGenerator,
                ibanGenerator);
    }

    @Test
    void create_shouldThrowCustomerNotFoundException_whenCustomerDoesNotExist() {
        // Arrange
        CreateAccountRequest request = CreateAccountRequestFactory.create();

        when(customerRepository.findById(CustomerTestConstants.CUSTOMER_ID))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                pt.com.bank.banking_api.exception.resources.CustomerNotFoundException.class,
                () -> accountService.create(request));

        verify(customerRepository)
                .findById(CustomerTestConstants.CUSTOMER_ID);

        verifyNoMoreInteractions(
                customerRepository,
                accountRepository,
                accountMapper,
                accountNumberGenerator,
                ibanGenerator);
    }

    @Test
    void create_shouldThrowCustomerAlreadyHasCheckingAccountException_whenCheckingAccountAlreadyExists() {
        // Arrange
        CreateAccountRequest request = CreateAccountRequestFactory.create();

        Customer customer = CustomerFactory.create(
                DocumentTypeFactory.create());

        when(customerRepository.findById(CustomerTestConstants.CUSTOMER_ID))
                .thenReturn(Optional.of(customer));

        when(accountRepository.existsByCustomerIdAndAccountType(
                CustomerTestConstants.CUSTOMER_ID,
                AccountType.CHECKING))
                .thenReturn(true);

        // Act & Assert
        assertThrows(
                pt.com.bank.banking_api.exception.conflicts.CustomerAlreadyHasCheckingAccountException.class,
                () -> accountService.create(request));

        verify(customerRepository)
                .findById(CustomerTestConstants.CUSTOMER_ID);

        verify(accountRepository)
                .existsByCustomerIdAndAccountType(
                        CustomerTestConstants.CUSTOMER_ID,
                        AccountType.CHECKING);

        verifyNoMoreInteractions(
                customerRepository,
                accountRepository,
                accountMapper,
                accountNumberGenerator,
                ibanGenerator);
    }

    @Test
    void findById_shouldReturnAccountSuccessfully() {
        // Arrange
        DocumentType documentType = DocumentTypeFactory.create();
        Customer customer = CustomerFactory.create(documentType);
        Account account = AccountFactory.checking(customer);
        AccountResponse expectedResponse = AccountResponseFactory.from(account);

        when(accountRepository.findById(AccountTestConstants.ACCOUNT_ID))
                .thenReturn(Optional.of(account));

        when(accountMapper.toResponse(account))
                .thenReturn(expectedResponse);

        // Act
        AccountResponse response = accountService.findById(AccountTestConstants.ACCOUNT_ID);

        // Assert
        assertNotNull(response);

        assertEquals(expectedResponse.id(), response.id());
        assertEquals(expectedResponse.accountNumber(), response.accountNumber());
        assertEquals(expectedResponse.iban(), response.iban());
        assertEquals(expectedResponse.customerId(), response.customerId());
        assertEquals(expectedResponse.customerName(), response.customerName());
        assertEquals(expectedResponse.accountType(), response.accountType());
        assertEquals(expectedResponse.status(), response.status());
        assertEquals(expectedResponse.balance(), response.balance());

        verify(accountRepository).findById(AccountTestConstants.ACCOUNT_ID);
        verify(accountMapper).toResponse(account);

        verifyNoMoreInteractions(
                accountRepository,
                accountMapper,
                customerRepository,
                accountNumberGenerator,
                ibanGenerator);
    }

    @Test
    void findById_shouldThrowAccountNotFoundException_whenAccountDoesNotExist() {
        // Arrange
        when(accountRepository.findById(AccountTestConstants.ACCOUNT_ID))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                pt.com.bank.banking_api.exception.resources.AccountNotFoundException.class,
                () -> accountService.findById(AccountTestConstants.ACCOUNT_ID));

        verify(accountRepository).findById(AccountTestConstants.ACCOUNT_ID);

        verifyNoMoreInteractions(
                accountRepository,
                accountMapper,
                customerRepository,
                accountNumberGenerator,
                ibanGenerator);
    }

    @Test
    void findAll_shouldReturnAllAccounts() {
        // Arrange
        DocumentType documentType = DocumentTypeFactory.create();

        Customer customer1 = CustomerFactory.create(documentType);
        Customer customer2 = CustomerFactory.withName(
                documentType,
                "Mary Jane");

        Account account1 = AccountFactory.checking(customer1);
        Account account2 = AccountFactory.savings(customer2);

        List<Account> accounts = List.of(account1, account2);

        AccountResponse response1 = AccountResponseFactory.from(account1);
        AccountResponse response2 = AccountResponseFactory.from(account2);

        when(accountRepository.findAll())
                .thenReturn(accounts);

        when(accountMapper.toResponse(account1))
                .thenReturn(response1);

        when(accountMapper.toResponse(account2))
                .thenReturn(response2);

        // Act
        List<AccountResponse> responses = accountService.findAll();

        // Assert
        assertNotNull(responses);
        assertEquals(2, responses.size());

        assertEquals(response1, responses.get(0));
        assertEquals(response2, responses.get(1));

        verify(accountRepository).findAll();
        verify(accountMapper).toResponse(account1);
        verify(accountMapper).toResponse(account2);

        verifyNoMoreInteractions(
                accountRepository,
                accountMapper,
                customerRepository,
                accountNumberGenerator,
                ibanGenerator);
    }

    @Test
    void delete_shouldDeleteAccountSuccessfully() {
        // Arrange
        DocumentType documentType = DocumentTypeFactory.create();
        Customer customer = CustomerFactory.create(documentType);
        Account account = AccountFactory.checking(customer);

        when(accountRepository.findById(AccountTestConstants.ACCOUNT_ID))
                .thenReturn(Optional.of(account));

        doNothing().when(accountRepository).delete(account);

        // Act
        accountService.delete(AccountTestConstants.ACCOUNT_ID);

        // Assert
        verify(accountRepository).findById(AccountTestConstants.ACCOUNT_ID);
        verify(accountRepository).delete(account);

        verifyNoMoreInteractions(
                accountRepository,
                accountMapper,
                customerRepository,
                accountNumberGenerator,
                ibanGenerator);
    }

    @Test
    void delete_shouldThrowAccountNotFoundException_whenAccountDoesNotExist() {
        // Arrange
        when(accountRepository.findById(AccountTestConstants.ACCOUNT_ID))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                pt.com.bank.banking_api.exception.resources.AccountNotFoundException.class,
                () -> accountService.delete(AccountTestConstants.ACCOUNT_ID));

        verify(accountRepository).findById(AccountTestConstants.ACCOUNT_ID);

        verify(accountRepository, never()).delete(any());

        verifyNoMoreInteractions(
                accountRepository,
                accountMapper,
                customerRepository,
                accountNumberGenerator,
                ibanGenerator);
    }

    @Test
    void update_shouldUpdateAccountSuccessfully() {
        // Arrange
        UpdateAccountRequest request = UpdateAccountRequestFactory.blocked();

        DocumentType documentType = DocumentTypeFactory.create();
        Customer customer = CustomerFactory.create(documentType);

        Account account = AccountFactory.checking(customer);

        Account updatedAccount = AccountFactory.blocked(customer);

        AccountResponse expectedResponse = AccountResponseFactory.from(updatedAccount);

        when(accountRepository.findById(AccountTestConstants.ACCOUNT_ID))
                .thenReturn(Optional.of(account));

        doAnswer(invocation -> {
            Account entity = invocation.getArgument(1);
            entity.setStatus(request.status());
            return null;
        }).when(accountMapper).updateEntity(eq(request), any(Account.class));

        when(accountRepository.save(account))
                .thenReturn(updatedAccount);

        when(accountMapper.toResponse(updatedAccount))
                .thenReturn(expectedResponse);

        // Act
        AccountResponse response = accountService.update(AccountTestConstants.ACCOUNT_ID, request);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse, response);

        verify(accountRepository).findById(AccountTestConstants.ACCOUNT_ID);
        verify(accountMapper).updateEntity(request, account);
        verify(accountRepository).save(account);
        verify(accountMapper).toResponse(updatedAccount);

        verifyNoMoreInteractions(
                accountRepository,
                accountMapper,
                customerRepository,
                accountNumberGenerator,
                ibanGenerator);
    }

    @Test
    void update_shouldThrowAccountNotFoundException_whenAccountDoesNotExist() {
        // Arrange
        UpdateAccountRequest request = UpdateAccountRequestFactory.blocked();

        when(accountRepository.findById(AccountTestConstants.ACCOUNT_ID))
                .thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(
                pt.com.bank.banking_api.exception.resources.AccountNotFoundException.class,
                () -> accountService.update(AccountTestConstants.ACCOUNT_ID, request));

        verify(accountRepository).findById(AccountTestConstants.ACCOUNT_ID);

        verify(accountRepository, never()).save(any());
        verify(accountMapper, never()).updateEntity(any(), any());
        verify(accountMapper, never()).toResponse(any());

        verifyNoMoreInteractions(
                accountRepository,
                accountMapper,
                customerRepository,
                accountNumberGenerator,
                ibanGenerator);
    }
}
