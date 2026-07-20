package pt.com.bank.banking_api.exception;

public class PhoneNumberAlreadyExistsException extends RuntimeException {

    public PhoneNumberAlreadyExistsException(String phoneNumber) {
        super("Phone number already exists: " + phoneNumber);
    }
}
