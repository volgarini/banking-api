package pt.com.bank.banking_api.factory.response;

import pt.com.bank.banking_api.dto.response.AccountResponse;
import pt.com.bank.banking_api.entity.Account;
import pt.com.bank.banking_api.factory.constants.TestConstants;

public final class AccountResponseFactory {

    private AccountResponseFactory() {
    }

    public static AccountResponse from(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getAccountNumber(),
                account.getIban(),
                account.getCustomer().getId(),
                account.getCustomer().getFullName(),
                account.getAccountType(),
                account.getStatus(),
                account.getBalance(),
                TestConstants.CREATED_AT,
                TestConstants.UPDATED_AT);
    }
}
