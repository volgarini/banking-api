package pt.com.bank.banking_api.service.generator;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class IbanGenerator {

    private static final String COUNTRY_CODE = "PT";
    private static final String CHECK_DIGITS = "50";
    private static final String BANK_CODE = "0003";

    public String generate() {
        long number = Math.abs(UUID.randomUUID().getLeastSignificantBits());

        String accountIdentifier = String.format("%017d", number % 100_000_000_000_000_000L);

        return COUNTRY_CODE + CHECK_DIGITS + BANK_CODE + accountIdentifier;
    }
}