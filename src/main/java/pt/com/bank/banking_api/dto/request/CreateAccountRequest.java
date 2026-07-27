package pt.com.bank.banking_api.dto.request;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import pt.com.bank.banking_api.enums.AccountType;

public record CreateAccountRequest(
        @Schema(
                description = "Customer document type ID", 
                example = "123e4567-e89b-12d3-a456-426614174000") 
        @NotNull(message = "Customer is required") 
        UUID customerId,

        @Schema(
                description = "Account Type", 
                example = "CHECKING")
        @NotNull(message = "Account type is required") 
        AccountType accountType) {

}
