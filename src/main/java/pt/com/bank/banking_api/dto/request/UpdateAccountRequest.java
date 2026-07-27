package pt.com.bank.banking_api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import pt.com.bank.banking_api.enums.AccountStatus;

public record UpdateAccountRequest(
        @Schema(
                description = "Account status", 
                example = "ACTIVE") 
        @NotNull(message = "Account status is required") 
        AccountStatus status

) {
}
