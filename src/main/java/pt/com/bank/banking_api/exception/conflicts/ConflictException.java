package pt.com.bank.banking_api.exception.conflicts;

public abstract class ConflictException extends RuntimeException{
    protected ConflictException (String message) {
        super(message);
    }
}