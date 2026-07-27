package pt.com.bank.banking_api.exception.conflicts;

public class EmailAlreadyExistsException extends ConflictException {

    public EmailAlreadyExistsException(String email) {
        super("Email already exists: " + email);
    }
}