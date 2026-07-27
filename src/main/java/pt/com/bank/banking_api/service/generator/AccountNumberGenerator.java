package pt.com.bank.banking_api.service.generator;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class AccountNumberGenerator {

    public String generate() {
        long number = Math.abs(UUID.randomUUID().getMostSignificantBits());

        return String.format("%010d", number % 10_000_000_000L);
    }
}
