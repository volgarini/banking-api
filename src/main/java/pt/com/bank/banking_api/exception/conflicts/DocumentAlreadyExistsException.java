package pt.com.bank.banking_api.exception.conflicts;

public class DocumentAlreadyExistsException extends ConflictException {

    public DocumentAlreadyExistsException(String documentNumber) {
        super("Document already exists: " + documentNumber);
    }
}
