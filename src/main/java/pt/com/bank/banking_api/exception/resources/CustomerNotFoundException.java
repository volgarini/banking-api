package pt.com.bank.banking_api.exception.resources;

import java.util.UUID;

public class CustomerNotFoundException extends ResourceNotFoundException {
    public CustomerNotFoundException(UUID id) {
        super("Customer", id);
    }
    
}
