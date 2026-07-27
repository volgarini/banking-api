package pt.com.bank.banking_api.mapper;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import pt.com.bank.banking_api.dto.request.CreateAccountRequest;
import pt.com.bank.banking_api.dto.request.UpdateAccountRequest;
import pt.com.bank.banking_api.dto.response.AccountResponse;
import pt.com.bank.banking_api.entity.Account;
import pt.com.bank.banking_api.entity.Customer;
import pt.com.bank.banking_api.entity.DocumentType;
import pt.com.bank.banking_api.enums.AccountStatus;
import pt.com.bank.banking_api.enums.AccountType;
import pt.com.bank.banking_api.factory.entity.AccountFactory;
import pt.com.bank.banking_api.factory.entity.CustomerFactory;
import pt.com.bank.banking_api.factory.entity.DocumentTypeFactory;
import pt.com.bank.banking_api.factory.request.CreateAccountRequestFactory;
import pt.com.bank.banking_api.factory.request.UpdateAccountRequestFactory;

class AccountMapperTest {

    private final AccountMapper mapper = Mappers.getMapper(AccountMapper.class);

    @Test
    void toEntity_shouldMapCreateAccountRequestToAccount() {

        // Arrange
        CreateAccountRequest request = CreateAccountRequestFactory.create();

        // Act
        Account account = mapper.toEntity(request);

        // Assert
        assertNotNull(account);

        assertNull(account.getId());

        assertEquals(request.accountType(), account.getAccountType());

        assertNull(account.getCustomer());

        assertNull(account.getAccountNumber());

        assertNull(account.getIban());

        assertEquals(account.getBalance(), BigDecimal.ZERO);

        assertNull(account.getStatus());

        assertNull(account.getCreatedAt());

        assertNull(account.getUpdatedAt());
    }

    @Test
    void toResponse_shouldMapAccountToResponse() {

        // Arrange
        DocumentType documentType = DocumentTypeFactory.create();

        Customer customer = CustomerFactory.create(documentType);

        Account account = AccountFactory.checking(customer);

        // Act
        AccountResponse response = mapper.toResponse(account);

        // Assert
        assertNotNull(response);

        assertEquals(account.getId(), response.id());

        assertEquals(account.getAccountNumber(), response.accountNumber());

        assertEquals(account.getIban(), response.iban());

        assertEquals(customer.getId(), response.customerId());

        assertEquals(customer.getFullName(), response.customerName());

        assertEquals(account.getAccountType(), response.accountType());

        assertEquals(account.getStatus(), response.status());

        assertEquals(account.getBalance(), response.balance());

        assertEquals(account.getCreatedAt(), response.createdAt());

        assertEquals(account.getUpdatedAt(), response.updatedAt());
    }

    @Test
    void updateEntity_shouldUpdateOnlyStatus() {

        // Arrange
        DocumentType documentType = DocumentTypeFactory.create();

        Customer customer = CustomerFactory.create(documentType);

        Account account = AccountFactory.checking(customer);

        UpdateAccountRequest request = UpdateAccountRequestFactory.blocked();

        UUID id = account.getId();
        String accountNumber = account.getAccountNumber();
        String iban = account.getIban();
        BigDecimal balance = account.getBalance();
        AccountType accountType = account.getAccountType();
        Customer originalCustomer = account.getCustomer();

        // Act
        mapper.updateEntity(request, account);

        // Assert
        assertEquals(AccountStatus.BLOCKED, account.getStatus());

        assertEquals(id, account.getId());

        assertEquals(accountNumber, account.getAccountNumber());

        assertEquals(iban, account.getIban());

        assertEquals(balance, account.getBalance());

        assertEquals(accountType, account.getAccountType());

        assertEquals(originalCustomer, account.getCustomer());
    }
}