package pt.com.bank.banking_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import pt.com.bank.banking_api.dto.request.CreateCustomerRequest;
import pt.com.bank.banking_api.dto.request.UpdateCustomerRequest;
import pt.com.bank.banking_api.dto.response.CustomerResponse;
import pt.com.bank.banking_api.dto.response.PageResponse;
import pt.com.bank.banking_api.entity.Customer;
import pt.com.bank.banking_api.entity.DocumentType;
import pt.com.bank.banking_api.exception.conflicts.DocumentAlreadyExistsException;
import pt.com.bank.banking_api.exception.conflicts.EmailAlreadyExistsException;
import pt.com.bank.banking_api.exception.conflicts.PhoneNumberAlreadyExistsException;
import pt.com.bank.banking_api.exception.resources.CustomerNotFoundException;
import pt.com.bank.banking_api.exception.resources.DocumentTypeNotFoundException;
import pt.com.bank.banking_api.factory.constants.CustomerTestConstants;
import pt.com.bank.banking_api.factory.entity.CustomerFactory;
import pt.com.bank.banking_api.factory.entity.DocumentTypeFactory;
import pt.com.bank.banking_api.factory.request.CreateCustomerRequestFactory;
import pt.com.bank.banking_api.factory.request.UpdateCustomerRequestFactory;
import pt.com.bank.banking_api.factory.response.CustomerResponseFactory;
import pt.com.bank.banking_api.mapper.CustomerMapper;
import pt.com.bank.banking_api.repository.CustomerRepository;
import pt.com.bank.banking_api.repository.DocumentTypeRepository;
import pt.com.bank.banking_api.service.impl.CustomerServiceImpl;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

        @Mock
        private CustomerRepository customerRepository;

        @Mock
        private DocumentTypeRepository documentTypeRepository;

        @Mock
        private CustomerMapper customerMapper;

        @InjectMocks
        private CustomerServiceImpl customerService;

        @Test
        void create_shouldCreateCustomerSuccessfully() {

                CreateCustomerRequest request = CreateCustomerRequestFactory.create();

                Customer customer = CustomerFactory.create(DocumentTypeFactory.create());

                CustomerResponse response = CustomerResponseFactory.create();

                DocumentType documentType = DocumentTypeFactory.create();

                when(customerRepository.existsByEmail(request.email()))
                                .thenReturn(false);

                when(customerRepository.existsByPhoneNumber(request.phoneNumber()))
                                .thenReturn(false);

                when(customerRepository.existsByDocumentNumber(request.documentNumber()))
                                .thenReturn(false);

                when(documentTypeRepository.findById(request.documentTypeId()))
                                .thenReturn(Optional.of(documentType));

                when(customerMapper.toEntity(request))
                                .thenReturn(customer);

                when(customerRepository.save(customer))
                                .thenReturn(customer);

                when(customerMapper.toResponse(customer))
                                .thenReturn(response);

                CustomerResponse result = customerService.create(request);

                assertNotNull(result);
                assertEquals(response, result);

                verify(customerRepository).save(customer);
        }

        @Test
        void create_shouldThrowDocumentTypeNotFoundException() {

                // Arrange
                CreateCustomerRequest request = CreateCustomerRequestFactory.create();

                when(customerRepository.existsByEmail(request.email()))
                                .thenReturn(false);

                when(customerRepository.existsByPhoneNumber(request.phoneNumber()))
                                .thenReturn(false);

                when(customerRepository.existsByDocumentNumber(request.documentNumber()))
                                .thenReturn(false);

                when(documentTypeRepository.findById(request.documentTypeId()))
                                .thenReturn(Optional.empty());

                // Act + Assert
                assertThrows(
                                DocumentTypeNotFoundException.class,
                                () -> customerService.create(request));

                verify(customerRepository).existsByEmail(request.email());
                verify(customerRepository).existsByPhoneNumber(request.phoneNumber());
                verify(customerRepository).existsByDocumentNumber(request.documentNumber());

                verify(documentTypeRepository).findById(request.documentTypeId());

                verify(customerRepository, never()).save(any(Customer.class));
                verify(customerMapper, never()).toEntity(any(CreateCustomerRequest.class));
                verify(customerMapper, never()).toResponse(any(Customer.class));
        }

        @Test
        void create_shouldThrowPhoneNumberAlreadyExistsException() {

                // Arrange
                CreateCustomerRequest request = CreateCustomerRequestFactory.create();

                when(customerRepository.existsByEmail(request.email()))
                                .thenReturn(false);

                when(customerRepository.existsByPhoneNumber(request.phoneNumber()))
                                .thenReturn(true);

                // Act + Assert
                assertThrows(
                                PhoneNumberAlreadyExistsException.class,
                                () -> customerService.create(request));

                verify(customerRepository).existsByEmail(request.email());
                verify(customerRepository).existsByPhoneNumber(request.phoneNumber());

                verify(customerRepository, never())
                                .existsByDocumentNumber(anyString());

                verifyNoInteractions(documentTypeRepository);

                verify(customerRepository, never())
                                .save(any(Customer.class));

                verifyNoInteractions(customerMapper);
        }

        @Test
        void create_shouldThrowEmailAlreadyExistsException() {
                // Arrange
                CreateCustomerRequest request = CreateCustomerRequestFactory.create();

                when(customerRepository.existsByEmail(request.email()))
                                .thenReturn(true);

                // Act + Assert
                assertThrows(
                                EmailAlreadyExistsException.class,
                                () -> customerService.create(request));

                verify(customerRepository).existsByEmail(request.email());

                verify(customerRepository, never())
                                .existsByDocumentNumber(anyString());

                verifyNoInteractions(documentTypeRepository);

                verify(customerRepository, never())
                                .save(any(Customer.class));

                verifyNoInteractions(customerMapper);
        }

        @Test
        void create_shouldThrowDocumentAlreadyExistsException() {

                // Arrange
                CreateCustomerRequest request = CreateCustomerRequestFactory.create();

                when(customerRepository.existsByEmail(request.email()))
                                .thenReturn(false);

                when(customerRepository.existsByPhoneNumber(request.phoneNumber()))
                                .thenReturn(false);

                when(customerRepository.existsByDocumentNumber(request.documentNumber()))
                                .thenReturn(true);

                // Act + Assert
                assertThrows(
                                DocumentAlreadyExistsException.class,
                                () -> customerService.create(request));

                verify(customerRepository).existsByEmail(request.email());
                verify(customerRepository).existsByPhoneNumber(request.phoneNumber());
                verify(customerRepository).existsByDocumentNumber(request.documentNumber());

                verifyNoInteractions(documentTypeRepository);

                verify(customerRepository, never())
                                .save(any(Customer.class));

                verifyNoInteractions(customerMapper);
        }

        @Test
        void findAll_shouldReturnPage() {

                // Arrange
                Pageable pageable = PageRequest.of(0, 10);

                Customer customer = CustomerFactory.create(DocumentTypeFactory.create());
                CustomerResponse response = CustomerResponseFactory.create();

                PageImpl<Customer> customerPage = new PageImpl<>(List.of(customer), pageable, 1);

                when(customerRepository.findAll(pageable))
                                .thenReturn(customerPage);

                when(customerMapper.toResponse(customer))
                                .thenReturn(response);

                // Act
                PageResponse<CustomerResponse> result = customerService.findAll(pageable);

                // Assert
                assertNotNull(result);
                assertEquals(1, result.content().size());
                assertEquals(response, result.content().get(0));
                assertEquals(0, result.page());
                assertEquals(10, result.size());
                assertEquals(1L, result.totalElements());
                assertEquals(1, result.totalPages());
                assertTrue(result.last());

                verify(customerRepository).findAll(pageable);
                verify(customerMapper).toResponse(customer);
        }

        @Test
        void findById_shouldReturnCustomerWhenIdExists() {
                // Arrange
                UUID customerId = UUID.randomUUID();
                Customer customer = CustomerFactory.create(DocumentTypeFactory.create());
                customer.setId(customerId);

                CustomerResponse response = CustomerResponseFactory.create();

                when(customerRepository.findById(customerId))
                                .thenReturn(Optional.of(customer));

                when(customerMapper.toResponse(customer))
                                .thenReturn(response);

                // Act
                CustomerResponse result = customerService.findById(customerId);

                // Assert
                assertNotNull(result);
                assertEquals(response, result);

                verify(customerRepository).findById(customerId);
                verify(customerMapper).toResponse(customer);
        }

        @Test
        void findById_shouldThrowCustomerNotFoundExceptionWhenIdDoesNotExist() {
                // Arrange
                UUID customerId = UUID.randomUUID();

                when(customerRepository.findById(customerId))
                                .thenReturn(Optional.empty());

                // Act & Assert
                assertThrows(
                                CustomerNotFoundException.class,
                                () -> customerService.findById(customerId));

                verify(customerRepository).findById(customerId);
                verifyNoInteractions(customerMapper);
        }

        @Test
        void update_shouldUpdateCustomerSuccessfully() {

                // Arrange
                UUID customerId = CustomerTestConstants.CUSTOMER_ID;

                DocumentType documentType = DocumentTypeFactory.create();

                UpdateCustomerRequest request = UpdateCustomerRequestFactory.create();

                Customer customer = CustomerFactory.create(documentType);

                CustomerResponse response = CustomerResponseFactory.create();

                when(customerRepository.findById(customerId))
                                .thenReturn(Optional.of(customer));

                when(customerRepository.findByEmail(request.email()))
                                .thenReturn(Optional.empty());

                when(customerRepository.findByPhoneNumber(request.phoneNumber()))
                                .thenReturn(Optional.empty());

                when(customerRepository.findByDocumentNumber(request.documentNumber()))
                                .thenReturn(Optional.empty());

                when(documentTypeRepository.findById(request.documentTypeId()))
                                .thenReturn(Optional.of(documentType));

                when(customerRepository.save(customer))
                                .thenReturn(customer);

                when(customerMapper.toResponse(customer))
                                .thenReturn(response);

                // Act
                CustomerResponse result = customerService.update(customerId, request);

                // Assert
                assertNotNull(result);
                assertEquals(response, result);

                verify(customerRepository).findById(customerId);
                verify(customerRepository).findByEmail(request.email());
                verify(customerRepository).findByPhoneNumber(request.phoneNumber());
                verify(customerRepository).findByDocumentNumber(request.documentNumber());

                verify(documentTypeRepository).findById(request.documentTypeId());

                verify(customerMapper).updateEntity(request, customer);

                verify(customerRepository).save(customer);

                verify(customerMapper).toResponse(customer);
        }

        @Test
        void update_shouldThrowCustomerNotFoundException() {

                // Arrange
                UUID customerId = CustomerTestConstants.CUSTOMER_ID;

                UpdateCustomerRequest request = UpdateCustomerRequestFactory.create();

                when(customerRepository.findById(customerId))
                                .thenReturn(Optional.empty());

                // Act + Assert
                assertThrows(CustomerNotFoundException.class,
                                () -> customerService.update(customerId, request));

                verify(customerRepository).findById(customerId);

                verifyNoInteractions(documentTypeRepository);
                verify(customerRepository, never()).save(any());
                verifyNoInteractions(customerMapper);
        }

        @Test
        void update_shouldThrowEmailAlreadyExistsException() {

                // Arrange
                UUID customerId = CustomerTestConstants.CUSTOMER_ID;

                UpdateCustomerRequest request = UpdateCustomerRequestFactory.create();

                Customer customer = CustomerFactory.create(DocumentTypeFactory.create());

                Customer anotherCustomer = CustomerFactory.create(DocumentTypeFactory.create());

                anotherCustomer.setId(UUID.randomUUID());

                when(customerRepository.findById(customerId))
                                .thenReturn(Optional.of(customer));

                when(customerRepository.findByEmail(request.email()))
                                .thenReturn(Optional.of(anotherCustomer));

                // Act + Assert
                assertThrows(
                                EmailAlreadyExistsException.class,
                                () -> customerService.update(customerId, request));

                verify(customerRepository).findById(customerId);
                verify(customerRepository).findByEmail(request.email());

                verify(customerRepository, never())
                                .findByPhoneNumber(anyString());

                verify(customerRepository, never())
                                .findByDocumentNumber(anyString());

                verifyNoInteractions(documentTypeRepository);

                verify(customerRepository, never())
                                .save(any(Customer.class));

                verify(customerMapper, never())
                                .updateEntity(any(), any());

                verify(customerMapper, never())
                                .toResponse(any());
        }

        @Test
        void update_shouldThrowPhoneNumberAlreadyExistsException() {

                // Arrange
                UUID customerId = CustomerTestConstants.CUSTOMER_ID;

                UpdateCustomerRequest request = UpdateCustomerRequestFactory.create();

                Customer customer = CustomerFactory.create(DocumentTypeFactory.create());

                Customer anotherCustomer = CustomerFactory.create(DocumentTypeFactory.create());

                anotherCustomer.setId(UUID.randomUUID());

                when(customerRepository.findById(customerId))
                                .thenReturn(Optional.of(customer));

                when(customerRepository.findByEmail(request.email()))
                                .thenReturn(Optional.empty());

                when(customerRepository.findByPhoneNumber(request.phoneNumber()))
                                .thenReturn(Optional.of(anotherCustomer));

                // Act + Assert
                assertThrows(
                                PhoneNumberAlreadyExistsException.class,
                                () -> customerService.update(customerId, request));

                verify(customerRepository).findById(customerId);
                verify(customerRepository).findByEmail(request.email());
                verify(customerRepository).findByPhoneNumber(request.phoneNumber());

                verify(customerRepository, never())
                                .findByDocumentNumber(anyString());

                verifyNoInteractions(documentTypeRepository);

                verify(customerRepository, never())
                                .save(any(Customer.class));

                verify(customerMapper, never())
                                .updateEntity(any(), any());

                verify(customerMapper, never())
                                .toResponse(any());
        }

        @Test
        void update_shouldThrowDocumentAlreadyExistsException() {

                // Arrange
                UUID customerId = CustomerTestConstants.CUSTOMER_ID;

                UpdateCustomerRequest request = UpdateCustomerRequestFactory.create();

                Customer customer = CustomerFactory.create(DocumentTypeFactory.create());

                Customer anotherCustomer = CustomerFactory.create(DocumentTypeFactory.create());

                anotherCustomer.setId(UUID.randomUUID());

                when(customerRepository.findById(customerId))
                                .thenReturn(Optional.of(customer));

                when(customerRepository.findByEmail(request.email()))
                                .thenReturn(Optional.empty());

                when(customerRepository.findByPhoneNumber(request.phoneNumber()))
                                .thenReturn(Optional.empty());

                when(customerRepository.findByDocumentNumber(request.documentNumber()))
                                .thenReturn(Optional.of(anotherCustomer));

                // Act + Assert
                assertThrows(
                                DocumentAlreadyExistsException.class,
                                () -> customerService.update(customerId, request));

                verify(customerRepository).findById(customerId);
                verify(customerRepository).findByEmail(request.email());
                verify(customerRepository).findByPhoneNumber(request.phoneNumber());
                verify(customerRepository).findByDocumentNumber(request.documentNumber());

                verifyNoInteractions(documentTypeRepository);

                verify(customerRepository, never())
                                .save(any(Customer.class));

                verify(customerMapper, never())
                                .updateEntity(any(), any());

                verify(customerMapper, never())
                                .toResponse(any());
        }

        @Test
        void delete_shouldDeleteCustomerSuccessfully() {

                // Arrange
                UUID customerId = CustomerTestConstants.CUSTOMER_ID;

                Customer customer = CustomerFactory.create(DocumentTypeFactory.create());

                when(customerRepository.findById(customerId))
                                .thenReturn(Optional.of(customer));

                // Act
                customerService.delete(customerId);

                // Assert
                verify(customerRepository).findById(customerId);
                verify(customerRepository).delete(customer);

                verifyNoInteractions(documentTypeRepository);
                verifyNoInteractions(customerMapper);
        }

        @Test
        void delete_shouldThrowCustomerNotFoundException() {

                // Arrange
                UUID customerId = CustomerTestConstants.CUSTOMER_ID;

                when(customerRepository.findById(customerId))
                                .thenReturn(Optional.empty());

                // Act + Assert
                assertThrows(
                                CustomerNotFoundException.class,
                                () -> customerService.delete(customerId));

                verify(customerRepository).findById(customerId);

                verify(customerRepository, never())
                                .delete(any(Customer.class));

                verifyNoInteractions(documentTypeRepository);
                verifyNoInteractions(customerMapper);
        }
}
