package pt.com.bank.banking_api.factory.constants;

import java.math.BigDecimal;
import java.util.UUID;

import pt.com.bank.banking_api.enums.AccountStatus;
import pt.com.bank.banking_api.enums.AccountType;

public final class AccountTestConstants {
    private AccountTestConstants() {
    }

    public static final UUID ACCOUNT_ID = UUID.fromString("22222222-2222-2222-2222-222222222222");

    public static final String ACCOUNT_NUMBER = "1234567890";

    public static final String IBAN = "PT50000312345678901234567";

    public static final BigDecimal INITIAL_BALANCE = BigDecimal.ZERO;

    public static final AccountType DEFAULT_ACCOUNT_TYPE = AccountType.CHECKING;

    public static final AccountStatus DEFAULT_ACCOUNT_STATUS = AccountStatus.ACTIVE;
    
}
