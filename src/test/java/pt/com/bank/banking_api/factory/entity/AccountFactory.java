package pt.com.bank.banking_api.factory.entity;

import pt.com.bank.banking_api.entity.Account;
import pt.com.bank.banking_api.entity.Customer;
import pt.com.bank.banking_api.enums.AccountStatus;
import pt.com.bank.banking_api.enums.AccountType;
import pt.com.bank.banking_api.factory.constants.AccountTestConstants;
import pt.com.bank.banking_api.factory.constants.TestConstants;

public final class AccountFactory {
    private AccountFactory() {

    }

    public static Account create(Customer customer) {
        return Account.builder()
                .id(AccountTestConstants.ACCOUNT_ID)
                .customer(customer)
                .accountNumber(AccountTestConstants.ACCOUNT_NUMBER)
                .iban(AccountTestConstants.IBAN)
                .accountType(AccountTestConstants.DEFAULT_ACCOUNT_TYPE)
                .status(AccountTestConstants.DEFAULT_ACCOUNT_STATUS)
                .balance(AccountTestConstants.INITIAL_BALANCE)
                .createdAt(TestConstants.CREATED_AT)
                .updatedAt(TestConstants.UPDATED_AT)
                .build();
    }

    public static Account checking(Customer customer) {
        return create(customer);
    }

    public static Account savings(Customer customer) {
        Account account = create(customer);
        account.setAccountType(AccountType.SAVINGS);
        return account;
    }

    public static Account blocked(Customer customer) {
        Account account = create(customer);
        account.setStatus(AccountStatus.BLOCKED);
        return account;
    }

    public static Account closed(Customer customer) {
        Account account = create(customer);
        account.setStatus(AccountStatus.CLOSED);
        return account;
    }
}
