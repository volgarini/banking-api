package pt.com.bank.banking_api.factory.request;

import java.util.UUID;

import pt.com.bank.banking_api.dto.request.CreateAccountRequest;
import pt.com.bank.banking_api.enums.AccountType;
import pt.com.bank.banking_api.factory.constants.CustomerTestConstants;

public final class CreateAccountRequestFactory {

    private CreateAccountRequestFactory() {
    }

    public static CreateAccountRequest create() {
        return new CreateAccountRequest(
                CustomerTestConstants.CUSTOMER_ID,
                AccountType.CHECKING
        );
    }

    public static CreateAccountRequest withCustomerId(UUID customerId) {
        return new CreateAccountRequest(
                customerId,
                AccountType.CHECKING
        );
    }

    public static CreateAccountRequest withAccountType(AccountType accountType) {
        return new CreateAccountRequest(
                CustomerTestConstants.CUSTOMER_ID,
                accountType
        );
    }
}
