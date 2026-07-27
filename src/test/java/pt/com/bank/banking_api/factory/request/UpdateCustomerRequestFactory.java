package pt.com.bank.banking_api.factory.request;

import java.util.UUID;

import pt.com.bank.banking_api.dto.request.UpdateCustomerRequest;
import pt.com.bank.banking_api.factory.constants.CustomerTestConstants;
import pt.com.bank.banking_api.factory.constants.DocumentTypeTestConstants;

public final class UpdateCustomerRequestFactory {

    private UpdateCustomerRequestFactory() {
    }

    public static UpdateCustomerRequest create() {
        return new UpdateCustomerRequest(
                CustomerTestConstants.CUSTOMER_FULL_NAME,
                CustomerTestConstants.CUSTOMER_EMAIL,
                CustomerTestConstants.CUSTOMER_PHONE_NUMBER,
                DocumentTypeTestConstants.DOCUMENT_TYPE_ID,
                CustomerTestConstants.CUSTOMER_DOCUMENT_NUMBER
        );
    }

    public static UpdateCustomerRequest withDocumentTypeId(UUID documentTypeId) {
        return new UpdateCustomerRequest(
                CustomerTestConstants.CUSTOMER_FULL_NAME,
                CustomerTestConstants.CUSTOMER_EMAIL,
                CustomerTestConstants.CUSTOMER_PHONE_NUMBER,
                documentTypeId,
                CustomerTestConstants.CUSTOMER_DOCUMENT_NUMBER
        );
    }

    public static UpdateCustomerRequest withEmail(String email) {
        return new UpdateCustomerRequest(
                CustomerTestConstants.CUSTOMER_FULL_NAME,
                email,
                CustomerTestConstants.CUSTOMER_PHONE_NUMBER,
                DocumentTypeTestConstants.DOCUMENT_TYPE_ID,
                CustomerTestConstants.CUSTOMER_DOCUMENT_NUMBER
        );
    }

    public static UpdateCustomerRequest withDocumentNumber(String documentNumber) {
        return new UpdateCustomerRequest(
                CustomerTestConstants.CUSTOMER_FULL_NAME,
                CustomerTestConstants.CUSTOMER_EMAIL,
                CustomerTestConstants.CUSTOMER_PHONE_NUMBER,
                DocumentTypeTestConstants.DOCUMENT_TYPE_ID,
                documentNumber
        );
    }

    public static UpdateCustomerRequest withFullName(String fullName) {
        return new UpdateCustomerRequest(
                fullName,
                CustomerTestConstants.CUSTOMER_EMAIL,
                CustomerTestConstants.CUSTOMER_PHONE_NUMBER,
                DocumentTypeTestConstants.DOCUMENT_TYPE_ID,
                CustomerTestConstants.CUSTOMER_DOCUMENT_NUMBER
        );
    }

    public static UpdateCustomerRequest withPhoneNumber(String phoneNumber) {
        return new UpdateCustomerRequest(
                CustomerTestConstants.CUSTOMER_FULL_NAME,
                CustomerTestConstants.CUSTOMER_EMAIL,
                phoneNumber,
                DocumentTypeTestConstants.DOCUMENT_TYPE_ID,
                CustomerTestConstants.CUSTOMER_DOCUMENT_NUMBER
        );
    }
}