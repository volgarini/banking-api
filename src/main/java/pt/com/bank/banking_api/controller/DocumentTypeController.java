package pt.com.bank.banking_api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import pt.com.bank.banking_api.dto.response.DocumentTypeResponse;
import pt.com.bank.banking_api.service.DocumentTypeService;

@RestController
@RequestMapping("/api/v1/document-types")
@RequiredArgsConstructor
@Tag(
        name = "Document Types",
        description = "Operations related to document types"
)
public class DocumentTypeController {

    private final DocumentTypeService service;

    @Operation(summary = "Find all document types")
    @ApiResponse(responseCode = "200", description = "Document types found")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<DocumentTypeResponse> findAll() {

        return service.findAll();
    }

    @Operation(summary = "Find document type by id")
    @ApiResponse(responseCode = "200", description = "Document type found")
    @ApiResponse(responseCode = "404", description = "Document type not found")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DocumentTypeResponse findById(
            @PathVariable UUID id) {

        return service.findById(id);
    }

}