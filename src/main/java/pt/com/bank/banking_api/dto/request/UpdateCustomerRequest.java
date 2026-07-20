package pt.com.bank.banking_api.dto.request;

import jakarta.validation.constraints.*;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateCustomerRequest(

         @Schema(
                description = "Customer full name",
                example = "John Doe"
        )
        @NotBlank(message = "Full name is required")
        @Size(max = 150)
        String fullName,
        
        @Schema(
                description = "Customer email",
                example = "john.doe@email.com"
        )
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email")
        @Size(max = 150)
        String email,

         @Schema(
                description = "Customer phone number",
                example = "123-456-7890"
        )
        @NotBlank(message = "Phone is required")
        @Size(max = 20)
        String phoneNumber,

         @Schema(
                description = "Customer document type ID",
                example = "123e4567-e89b-12d3-a456-426614174000"
        )
        @NotNull(message = "Document type is required")
        UUID documentTypeId,

        @Schema(
                description = "Customer document number",
                example = "123456789"
        )
        @NotBlank(message = "Document number is required")
        @Size(max = 40)
        String documentNumber
) {
}
