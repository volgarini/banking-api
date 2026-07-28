package pt.com.bank.banking_api.mapper;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import pt.com.bank.banking_api.dto.request.CreateCustomerRequest;
import pt.com.bank.banking_api.dto.request.UpdateCustomerRequest;
import pt.com.bank.banking_api.dto.response.CustomerResponse;
import pt.com.bank.banking_api.entity.Customer;
import pt.com.bank.banking_api.factory.entity.CustomerFactory;
import pt.com.bank.banking_api.factory.entity.DocumentTypeFactory;
import pt.com.bank.banking_api.factory.request.CreateCustomerRequestFactory;
import pt.com.bank.banking_api.factory.request.UpdateCustomerRequestFactory;

class CustomerMapperTest {

    private final CustomerMapper mapper = Mappers.getMapper(CustomerMapper.class);

    @Test
    void toEntity_shouldMapCreateRequestToCustomer() {

        // Arrange
        CreateCustomerRequest request = CreateCustomerRequestFactory.create();

        // Act
        Customer customer = mapper.toEntity(request);

        // Assert
        assertNotNull(customer);

        assertNull(customer.getId());
        assertNull(customer.getDocumentType());
        assertNull(customer.getCreatedAt());
        assertNull(customer.getUpdatedAt());

        assertEquals(request.fullName(), customer.getFullName());
        assertEquals(request.email(), customer.getEmail());
        assertEquals(request.phoneNumber(), customer.getPhoneNumber());
        assertEquals(request.documentNumber(), customer.getDocumentNumber());
    }

    @Test
    void updateEntity_shouldUpdateCustomerFields() {

        // Arrange
        UpdateCustomerRequest request = UpdateCustomerRequestFactory.create();

        Customer customer = CustomerFactory.create(DocumentTypeFactory.create());

        // Act
        mapper.updateEntity(request, customer);

        // Assert
        assertEquals(request.fullName(), customer.getFullName());
        assertEquals(request.email(), customer.getEmail());
        assertEquals(request.phoneNumber(), customer.getPhoneNumber());
        assertEquals(request.documentNumber(), customer.getDocumentNumber());

        assertNotNull(customer.getId());
        assertNotNull(customer.getDocumentType());
    }

    @Test
    void toResponse_shouldMapCustomerToResponse() {

        // Arrange
        Customer customer = CustomerFactory.create(DocumentTypeFactory.create());

        customer.setDocumentType(DocumentTypeFactory.create());

        // Act
        CustomerResponse response = mapper.toResponse(customer);

        // Assert
        assertNotNull(response);

        assertEquals(customer.getId(), response.id());
        assertEquals(customer.getFullName(), response.fullName());
        assertEquals(customer.getEmail(), response.email());
        assertEquals(customer.getPhoneNumber(), response.phoneNumber());

        assertEquals(
                customer.getDocumentType().getId(),
                response.documentTypeId());

        assertEquals(
                customer.getDocumentType().getDescription(),
                response.documentType());

        assertEquals(
                customer.getDocumentNumber(),
                response.documentNumber());

        assertEquals(customer.getCreatedAt(), response.createdAt());
        assertEquals(customer.getUpdatedAt(), response.updatedAt());
    }
}