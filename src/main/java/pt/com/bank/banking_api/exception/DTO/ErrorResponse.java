package pt.com.bank.banking_api.exception.DTO;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

public record ErrorResponse(

        @Schema(
                description = "Error timestamp",
                example = "2023-01-01T00:00:00Z"
        )
        LocalDateTime timestamp,

        @Schema(
                description = "Error status",
                example = "400"
        )
        int status,

        @Schema(
                description = "Error code",
                example = "BAD_REQUEST"
        )
        String error,

        @Schema(
                description = "Error message",
                example = "Bad Request"
        )
        String message,

        @Schema(
                description = "Request path",
                example = "/api/customers"
        )
        String path

) {
}