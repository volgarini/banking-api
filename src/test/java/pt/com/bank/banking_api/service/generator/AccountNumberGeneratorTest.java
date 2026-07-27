package pt.com.bank.banking_api.service.generator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;

@Component
public class AccountNumberGeneratorTest {
    private final AccountNumberGenerator generator =
        new AccountNumberGenerator(); 

    @Test
    void generate_shouldReturnAccountNumber() {

        // Act
        String accountNumber = generator.generate();

        // Assert
        assertNotNull(accountNumber);
        assertFalse(accountNumber.isBlank());
    }

    @Test
    void generate_shouldReturnAccountNumberWithTenDigits() {

        // Act
        String accountNumber = generator.generate();

        // Assert
        assertEquals(10, accountNumber.length());
    }

    @Test
    void generate_shouldReturnOnlyNumericCharacters() {

        // Act
        String accountNumber = generator.generate();

        // Assert
        assertTrue(accountNumber.matches("\\d{10}"));
    }

    @Test
    void generate_shouldGenerateUniqueAccountNumbers() {

        // Arrange
        Set<String> accountNumbers = new HashSet<>();

        // Act
        for (int i = 0; i < 1000; i++) {
            accountNumbers.add(generator.generate());
        }

        // Assert
        assertEquals(1000, accountNumbers.size());
    }

    @Test
    void generate_shouldNotContainSpaces() {

        // Act
        String accountNumber = generator.generate();

        // Assert
        assertEquals(accountNumber.trim(), accountNumber);
    }
}
