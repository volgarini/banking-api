package pt.com.bank.banking_api.factory;

import java.util.UUID;

import pt.com.bank.banking_api.entity.DocumentType;

public final class DocumentTypeTestDataFactory {

    private DocumentTypeTestDataFactory() {
    }

    public static DocumentType documentType() {

        DocumentType documentType = new DocumentType();

        documentType.setId(UUID.randomUUID());
        documentType.setCode("CC");
        documentType.setDescription("Citizen Card");

        return documentType;
    }

}
