package pt.com.bank.banking_api.exception.resources;

import java.util.UUID;

public class AccountNotFoundException extends ResourceNotFoundException{
    
    public AccountNotFoundException(UUID id){
        super("Account",  id);
    }
    
}
