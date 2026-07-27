package pt.com.bank.banking_api.factory.constants;

import java.util.UUID;

public final class CustomerTestConstants {
    private CustomerTestConstants() {
    }

    public static final UUID CUSTOMER_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    public static final String CUSTOMER_FULL_NAME = "Lucas Souza";

    public static final String CUSTOMER_EMAIL = "lucas@email.com";

    public static final String CUSTOMER_PHONE_NUMBER = "+351912345678";

    public static final String CUSTOMER_DOCUMENT_NUMBER = "123456789";
}
