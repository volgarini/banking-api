package pt.com.bank.banking_api.dto.response;

import java.util.UUID;

import io.swagger.v3.oas.annotations.media.Schema;

public record DocumentTypeResponse(

        @Schema(
                description = "Document type id",
                example = "123e4567-e89b-12d3-a456-426614174000"
        )
        UUID id,

        @Schema(
                description = "Document type code",
                example = "CPF"
        )
        String code,

        @Schema(
                description = "Document type description",
                example = "Brazilian CPF"
        )
        String description

) {
}