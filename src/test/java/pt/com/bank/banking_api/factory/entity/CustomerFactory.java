package pt.com.bank.banking_api.factory.entity;

import pt.com.bank.banking_api.factory.constants.CustomerTestConstants;
import pt.com.bank.banking_api.factory.constants.TestConstants;
import pt.com.bank.banking_api.entity.Customer;
import pt.com.bank.banking_api.entity.DocumentType;

public final class CustomerFactory {

    private CustomerFactory() {
    }

    public static Customer create(DocumentType documentType) {
        return Customer.builder()
                .id(CustomerTestConstants.CUSTOMER_ID)
                .fullName(CustomerTestConstants.CUSTOMER_FULL_NAME)
                .email(CustomerTestConstants.CUSTOMER_EMAIL)
                .phoneNumber(CustomerTestConstants.CUSTOMER_PHONE_NUMBER)
                .documentNumber(CustomerTestConstants.CUSTOMER_DOCUMENT_NUMBER)
                .documentType(documentType)
                .createdAt(TestConstants.CREATED_AT)
                .updatedAt(TestConstants.UPDATED_AT)
                .build();
    }

    public static Customer withName(DocumentType documentType, String fullName) {
        Customer customer = create(documentType);
        customer.setFullName(fullName);
        return customer;
    }

    public static Customer withEmail(DocumentType documentType, String email) {
        Customer customer = create(documentType);
        customer.setEmail(email);
        return customer;
    }

    public static Customer withDocumentNumber(DocumentType documentType, String documentNumber) {
        Customer customer = create(documentType);
        customer.setDocumentNumber(documentNumber);
        return customer;
    }

    public static Customer withDocumentType(DocumentType documentType) {
        Customer customer = create(documentType);
        return customer;
    }
}