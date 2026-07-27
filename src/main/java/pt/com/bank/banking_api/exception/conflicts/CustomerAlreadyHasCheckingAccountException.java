package pt.com.bank.banking_api.exception.conflicts;

import java.util.UUID;

public class CustomerAlreadyHasCheckingAccountException extends ConflictException{
    public CustomerAlreadyHasCheckingAccountException(UUID customerId){
        super("Customer with id: " + customerId + " already has checking account");
    }
}
