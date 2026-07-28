package pt.com.bank.banking_api.factory.response;

import pt.com.bank.banking_api.dto.response.DocumentTypeResponse;
import pt.com.bank.banking_api.entity.DocumentType;

public final class DocumentTypeResponseFactory {

    private DocumentTypeResponseFactory() {
        // Utility class
    }

    public static DocumentTypeResponse from(DocumentType documentType) {
        return new DocumentTypeResponse(
                documentType.getId(),
                documentType.getCode(),
                documentType.getDescription()
        );
    }

}
