package pt.com.bank.banking_api.exception.resources;

import java.util.UUID;

public abstract class ResourceNotFoundException extends RuntimeException {

    protected ResourceNotFoundException(String resource, UUID id) {
        super(resource + " not found with id: " + id);
    }

}
