package pt.com.bank.banking_api.service.generator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

class IbanGeneratorTest {

    private final IbanGenerator generator = new IbanGenerator();

    @Test
    void generate_shouldReturnIban() {

        // Act
        String iban = generator.generate();

        // Assert
        assertNotNull(iban);
        assertFalse(iban.isBlank());
    }

    @Test
    void generate_shouldStartWithPT50() {

        // Act
        String iban = generator.generate();

        // Assert
        assertTrue(iban.startsWith("PT50"));
    }

    @Test
    void generate_shouldHaveTwentyFiveCharacters() {

        // Act
        String iban = generator.generate();

        // Assert
        assertEquals(25, iban.length());
    }

    @Test
    void generate_shouldContainOnlyValidCharacters() {

        // Act
        String iban = generator.generate();

        // Assert
        assertTrue(iban.matches("PT50\\d{21}"));
    }

    @Test
    void generate_shouldGenerateUniqueIbans() {

        // Arrange
        Set<String> ibans = new HashSet<>();

        // Act
        for (int i = 0; i < 1000; i++) {
            ibans.add(generator.generate());
        }

        // Assert
        assertEquals(1000, ibans.size());
    }

    @Test
    void generate_shouldNotContainSpaces() {

        // Act
        String iban = generator.generate();

        // Assert
        assertEquals(iban.trim(), iban);
        assertFalse(iban.contains(" "));
    }
}