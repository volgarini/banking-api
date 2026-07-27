package pt.com.bank.banking_api.factory.constants;

import java.time.LocalDateTime;

public final class TestConstants {

    private TestConstants() {
    }

    public static final LocalDateTime CREATED_AT =
            LocalDateTime.of(2026, 1, 1, 10, 0);
    public static final LocalDateTime UPDATED_AT =
            LocalDateTime.of(2026, 2, 1, 10, 0);
}