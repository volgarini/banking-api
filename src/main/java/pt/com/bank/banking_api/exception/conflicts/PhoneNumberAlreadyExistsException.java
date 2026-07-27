package pt.com.bank.banking_api.exception.conflicts;

public class PhoneNumberAlreadyExistsException extends ConflictException {

    public PhoneNumberAlreadyExistsException(String phoneNumber) {
        super("Phone number already exists: " + phoneNumber);
    }
}
