package pt.com.bank.banking_api.factory.request;

import java.util.UUID;

import pt.com.bank.banking_api.dto.request.CreateCustomerRequest;
import pt.com.bank.banking_api.factory.constants.CustomerTestConstants;
import pt.com.bank.banking_api.factory.constants.DocumentTypeTestConstants;

public final class CreateCustomerRequestFactory {

    private CreateCustomerRequestFactory() {
    }

    public static CreateCustomerRequest create() {
        return new CreateCustomerRequest(
                CustomerTestConstants.CUSTOMER_FULL_NAME,
                CustomerTestConstants.CUSTOMER_EMAIL,
                CustomerTestConstants.CUSTOMER_PHONE_NUMBER,
                DocumentTypeTestConstants.DOCUMENT_TYPE_ID,
                CustomerTestConstants.CUSTOMER_DOCUMENT_NUMBER
        );
    }

    public static CreateCustomerRequest withDocumentTypeId(UUID documentTypeId) {
        return new CreateCustomerRequest(
                CustomerTestConstants.CUSTOMER_FULL_NAME,
                CustomerTestConstants.CUSTOMER_EMAIL,
                CustomerTestConstants.CUSTOMER_PHONE_NUMBER,
                documentTypeId,
                CustomerTestConstants.CUSTOMER_DOCUMENT_NUMBER
        );
    }

    public static CreateCustomerRequest withEmail(String email) {
        return new CreateCustomerRequest(
                CustomerTestConstants.CUSTOMER_FULL_NAME,
                email,
                CustomerTestConstants.CUSTOMER_PHONE_NUMBER,
                DocumentTypeTestConstants.DOCUMENT_TYPE_ID,
                CustomerTestConstants.CUSTOMER_DOCUMENT_NUMBER
        );
    }

    public static CreateCustomerRequest withDocumentNumber(String documentNumber) {
        return new CreateCustomerRequest(
                CustomerTestConstants.CUSTOMER_FULL_NAME,
                CustomerTestConstants.CUSTOMER_EMAIL,
                CustomerTestConstants.CUSTOMER_PHONE_NUMBER,
                DocumentTypeTestConstants.DOCUMENT_TYPE_ID,
                documentNumber
        );
    }

    public static CreateCustomerRequest withFullName(String fullName) {
        return new CreateCustomerRequest(
                fullName,
                CustomerTestConstants.CUSTOMER_EMAIL,
                CustomerTestConstants.CUSTOMER_PHONE_NUMBER,
                DocumentTypeTestConstants.DOCUMENT_TYPE_ID,
                CustomerTestConstants.CUSTOMER_DOCUMENT_NUMBER
        );
    }
}
