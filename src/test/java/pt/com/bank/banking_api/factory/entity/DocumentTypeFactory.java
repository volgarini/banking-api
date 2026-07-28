package pt.com.bank.banking_api.factory.entity;

import pt.com.bank.banking_api.entity.DocumentType;
import pt.com.bank.banking_api.factory.constants.DocumentTypeTestConstants;

public final class DocumentTypeFactory {

    private DocumentTypeFactory() {
    }

    public static DocumentType create() {
        return DocumentType.builder()
                .id(DocumentTypeTestConstants.DOCUMENT_TYPE_ID)
                .code(DocumentTypeTestConstants.DEFAULT_DOCUMENT)
                .description(DocumentTypeTestConstants.DEFAULT_DESCRIPTION)
                .build();
    }

    public static DocumentType withCode(String document) {
        DocumentType documentType = create();
        documentType.setCode(document);
        return documentType;
    }

    public static DocumentType passport(String document) {
        DocumentType documentType = create();
        documentType.setCode(DocumentTypeTestConstants.PASSPORT);
        return documentType;
    }

    public static DocumentType residencePermit(String document) {
        DocumentType documentType = create();
        documentType.setCode(DocumentTypeTestConstants.RESIDENCE_PERMIT);
        return documentType;
    }
}
