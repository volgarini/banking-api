package pt.com.bank.banking_api.exception.resources;
import java.util.UUID;

public class DocumentTypeNotFoundException extends ResourceNotFoundException {

    public DocumentTypeNotFoundException(UUID id) {
        super("Document type", id);
    }
}