package pt.com.bank.banking_api.factory;

import java.util.UUID;

import pt.com.bank.banking_api.dto.request.CreateCustomerRequest;
import pt.com.bank.banking_api.dto.request.UpdateCustomerRequest;
import pt.com.bank.banking_api.entity.Customer;
import pt.com.bank.banking_api.entity.DocumentType;

public final class CustomerTestDataFactory {

    private CustomerTestDataFactory() {
    }

    public static CreateCustomerRequest createRequest(UUID documentTypeId) {

        return new CreateCustomerRequest(
                "Lucas Souza",
                "lucas@email.com",
                "+351912345678",
                documentTypeId,
                "123456789"
        );
    }

    public static UpdateCustomerRequest updateRequest(UUID documentTypeId) {

        return new UpdateCustomerRequest(
                "Lucas Souza Updated",
                "lucas@email.com",
                "+351912345678",
                documentTypeId,
                "123456789"
        );
    }

    public static Customer customer(DocumentType documentType) {

        Customer customer = new Customer();

        customer.setId(UUID.randomUUID());
        customer.setFullName("Lucas Souza");
        customer.setEmail("lucas@email.com");
        customer.setPhoneNumber("+351912345678");
        customer.setDocumentNumber("123456789");
        customer.setDocumentType(documentType);

        return customer;
    }

}