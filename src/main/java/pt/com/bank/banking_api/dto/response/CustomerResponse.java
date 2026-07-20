package pt.com.bank.banking_api.dto.response;

import java.time.LocalDateTime;
import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

public record CustomerResponse(
        
        @Schema(
                description = "Customer id",
                example = "123e4567-e89b-12d3-a456-426614174000"
        )
        UUID id,

        @Schema(
                description = "Customer full name",
                example = "John Doe"
        )
        String fullName,

        @Schema(
                description = "Customer email",
                example = "john.doe@email.com"
        )
        String email,

         @Schema(
                description = "Customer phone number",
                example = "123-456-7890"
        )
        String phoneNumber,

        @Schema(
                description = "Customer document type ID",
                example = "123e4567-e89b-12d3-a456-426614174000"
        )
        UUID documentTypeId,

        @Schema(
                description = "Customer document type",
                example = "Passport"
        )
        String documentType,

        @Schema(
                description = "Customer document number",
                example = "123456789"
        )
        String documentNumber,

        @Schema(
                description = "Customer creation date",
                example = "2023-01-01T00:00:00Z"
        )
        LocalDateTime createdAt,

        @Schema(
                description = "Customer last update date",
                example = "2023-01-01T00:00:00Z"
        )
        LocalDateTime updatedAt
) {
}