package pt.com.bank.banking_api.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import pt.com.bank.banking_api.enums.AccountStatus;
import pt.com.bank.banking_api.enums.AccountType;

public record AccountResponse(
        @Schema(
                description = "Account id",
                example = "123e4567-e89b-12d3-a456-426614174000"
        )
        UUID id,

        @Schema(
                description = "Account number",
                example = "1001"
        )
        String accountNumber,

        @Schema(
                description = "International Bank Account Number",
                example = "PT50001234567890123456789"
        )
        String iban,

        @Schema(
                description = "Customer id",
                example = "123e4567-e89b-12d3-a456-426614174001"
        )
        UUID customerId,

        @Schema(
                description = "Customer name",
                example = "João Silva"
        )
        String customerName,

        @Schema(
                description = "Account type",
                example = "CHECKING"
        )
        AccountType accountType,

        @Schema(
                description = "Account status",
                example = "ACTIVE"
        )
        AccountStatus status,

        @Schema(
                description = "Account balance",
                example = "1500.50"
        )
        BigDecimal balance,

        @Schema(
                description = "Account creation date",
                example = "2024-01-15T10:30:00"
        )
        LocalDateTime createdAt,

        @Schema(
                description = "Account last update date",
                example = "2024-07-24T15:45:30"
        )
        LocalDateTime updatedAt

) {
}
