package pt.com.bank.banking_api.exception;

public class DocumentAlreadyExistsException extends RuntimeException {

    public DocumentAlreadyExistsException(String documentNumber) {
        super("Document already exists: " + documentNumber);
    }
}
