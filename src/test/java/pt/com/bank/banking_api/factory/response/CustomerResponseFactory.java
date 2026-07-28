package pt.com.bank.banking_api.factory.response;

import pt.com.bank.banking_api.dto.response.CustomerResponse;
import pt.com.bank.banking_api.entity.Customer;
import pt.com.bank.banking_api.factory.constants.CustomerTestConstants;
import pt.com.bank.banking_api.factory.constants.DocumentTypeTestConstants;
import pt.com.bank.banking_api.factory.constants.TestConstants;

public final class CustomerResponseFactory {

    private CustomerResponseFactory() {
    }

    public static CustomerResponse create() {
        return new CustomerResponse(
                CustomerTestConstants.CUSTOMER_ID,
                CustomerTestConstants.CUSTOMER_FULL_NAME,
                CustomerTestConstants.CUSTOMER_EMAIL,
                CustomerTestConstants.CUSTOMER_PHONE_NUMBER,
                DocumentTypeTestConstants.DOCUMENT_TYPE_ID,
                DocumentTypeTestConstants.DEFAULT_DOCUMENT,
                DocumentTypeTestConstants.DEFAULT_DESCRIPTION,
                TestConstants.CREATED_AT,
                TestConstants.UPDATED_AT);
    }

    public static CustomerResponse from(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                customer.getFullName(),
                customer.getEmail(),
                customer.getPhoneNumber(),
                customer.getDocumentType().getId(),
                customer.getDocumentType().getCode(),
                customer.getDocumentNumber(),
                TestConstants.CREATED_AT,
                TestConstants.UPDATED_AT);
    }
}