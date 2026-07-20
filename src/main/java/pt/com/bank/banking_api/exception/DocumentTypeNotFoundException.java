package pt.com.bank.banking_api.exception;
import java.util.UUID;

public class DocumentTypeNotFoundException extends RuntimeException {

    public DocumentTypeNotFoundException(UUID id) {
        super("Document type not found with id: " + id);
    }
}