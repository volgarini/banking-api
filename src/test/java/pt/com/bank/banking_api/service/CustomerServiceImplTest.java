package pt.com.bank.banking_api.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pt.com.bank.banking_api.dto.request.CreateCustomerRequest;
import pt.com.bank.banking_api.dto.request.UpdateCustomerRequest;
import pt.com.bank.banking_api.dto.response.CustomerResponse;
import pt.com.bank.banking_api.dto.response.PageResponse;
import pt.com.bank.banking_api.entity.Customer;
import pt.com.bank.banking_api.entity.DocumentType;
import pt.com.bank.banking_api.exception.CustomerNotFoundException;
import pt.com.bank.banking_api.exception.DocumentAlreadyExistsException;
import pt.com.bank.banking_api.exception.EmailAlreadyExistsException;
import pt.com.bank.banking_api.exception.PhoneNumberAlreadyExistsException;
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

        private final CustomerMapper mapper = Mappers.getMapper(CustomerMapper.class);

        @InjectMocks
        private CustomerServiceImpl service;

        private DocumentType documentType;
        private Customer customer;
        private CreateCustomerRequest createRequest;

        @BeforeEach
        void setUp() {

                documentType = DocumentType.builder()
                                .id(UUID.randomUUID())
                                .code("CC")
                                .description("Citizen Card")
                                .build();

                customer = Customer.builder()
                                .id(UUID.randomUUID())
                                .fullName("Lucas Souza")
                                .email("lucas@email.com")
                                .phoneNumber("+351912345678")
                                .documentNumber("123456789")
                                .documentType(documentType)
                                .build();

                createRequest = new CreateCustomerRequest(
                                "Lucas Souza",
                                "lucas@email.com",
                                "+351912345678",
                                documentType.getId(),
                                "123456789");

                service = new CustomerServiceImpl(
                                customerRepository,
                                documentTypeRepository,
                                mapper);
        }

        @Test
        void shouldCreateCustomer() {

                when(customerRepository.existsByEmail(any()))
                                .thenReturn(false);

                when(customerRepository.existsByPhoneNumber(any()))
                                .thenReturn(false);

                when(customerRepository.existsByDocumentNumber(any()))
                                .thenReturn(false);

                when(documentTypeRepository.findById(documentType.getId()))
                                .thenReturn(Optional.of(documentType));

                when(customerRepository.save(any(Customer.class)))
                                .thenReturn(customer);

                CustomerResponse response = service.create(createRequest);

                assertThat(response).isNotNull();
                assertThat(response.fullName()).isEqualTo(customer.getFullName());
                assertThat(response.email()).isEqualTo(customer.getEmail());

                verify(customerRepository).save(any(Customer.class));
        }

        @Test
        void shouldThrowEmailAlreadyExists() {

                when(customerRepository.existsByEmail(createRequest.email()))
                                .thenReturn(true);

                assertThatThrownBy(() -> service.create(createRequest))
                                .isInstanceOf(pt.com.bank.banking_api.exception.EmailAlreadyExistsException.class);

                verify(customerRepository, never()).save(any());
        }

        @Test
        void shouldThrowPhoneAlreadyExists() {

                when(customerRepository.existsByEmail(any()))
                                .thenReturn(false);

                when(customerRepository.existsByPhoneNumber(any()))
                                .thenReturn(true);

                assertThatThrownBy(() -> service.create(createRequest))
                                .isInstanceOf(PhoneNumberAlreadyExistsException.class);

                verify(customerRepository, never()).save(any());
        }

        @Test
        void shouldThrowDocumentAlreadyExists() {

                when(customerRepository.existsByEmail(any()))
                                .thenReturn(false);

                when(customerRepository.existsByPhoneNumber(any()))
                                .thenReturn(false);

                when(customerRepository.existsByDocumentNumber(any()))
                                .thenReturn(true);

                assertThatThrownBy(() -> service.create(createRequest))
                                .isInstanceOf(DocumentAlreadyExistsException.class);

                verify(customerRepository, never()).save(any());
        }

        @Test
        void shouldFindAllCustomers() {

                var pageable = PageRequest.of(0, 20);
                when(customerRepository.findAll(pageable))
                                .thenReturn(new PageImpl<>(java.util.List.of(customer), pageable, 1));

                PageResponse<CustomerResponse> result = service.findAll(pageable);

                assertThat(result.content()).hasSize(1);
                assertThat(result.content().getFirst().email())
                                .isEqualTo(customer.getEmail());

                verify(customerRepository).findAll(pageable);
        }

        @Test
        void shouldFindCustomerById() {

                when(customerRepository.findById(customer.getId()))
                                .thenReturn(Optional.of(customer));

                CustomerResponse response = service.findById(customer.getId());

                assertThat(response.id())
                                .isEqualTo(customer.getId());

                assertThat(response.email())
                                .isEqualTo(customer.getEmail());

                verify(customerRepository)
                                .findById(customer.getId());
        }

        @Test
        void shouldThrowCustomerNotFound() {

                UUID id = UUID.randomUUID();

                when(customerRepository.findById(id))
                                .thenReturn(Optional.empty());

                assertThatThrownBy(() -> service.findById(id))
                                .isInstanceOf(pt.com.bank.banking_api.exception.CustomerNotFoundException.class);

                verify(customerRepository).findById(id);
        }

        @Test
        void shouldDeleteCustomer() {

                when(customerRepository.findById(customer.getId()))
                                .thenReturn(Optional.of(customer));

                service.delete(customer.getId());

                verify(customerRepository).delete(customer);
        }

        @Test
        void shouldThrowCustomerNotFoundWhenDeleting() {

                UUID id = UUID.randomUUID();

                when(customerRepository.findById(id))
                                .thenReturn(Optional.empty());

                assertThatThrownBy(() -> service.delete(id))
                                .isInstanceOf(pt.com.bank.banking_api.exception.CustomerNotFoundException.class);

                verify(customerRepository).findById(id);
                verify(customerRepository, never()).delete(any());
        }

        @Test
        void shouldThrowDocumentTypeNotFound() {

                when(customerRepository.existsByEmail(any()))
                                .thenReturn(false);

                when(customerRepository.existsByPhoneNumber(any()))
                                .thenReturn(false);

                when(customerRepository.existsByDocumentNumber(any()))
                                .thenReturn(false);

                when(documentTypeRepository.findById(documentType.getId()))
                                .thenReturn(Optional.empty());

                assertThatThrownBy(() -> service.create(createRequest))
                                .isInstanceOf(pt.com.bank.banking_api.exception.DocumentTypeNotFoundException.class);

                verify(customerRepository, never()).save(any());
        }

        @Test
        void shouldUpdateCustomer() {

                UpdateCustomerRequest request = new UpdateCustomerRequest(
                                "Lucas Souza Updated",
                                "lucas@email.com",
                                "+351912345678",
                                documentType.getId(),
                                "123456789");

                when(customerRepository.findById(customer.getId()))
                                .thenReturn(Optional.of(customer));

                when(customerRepository.findByEmail(request.email()))
                                .thenReturn(Optional.of(customer));

                when(customerRepository.findByPhoneNumber(request.phoneNumber()))
                                .thenReturn(Optional.of(customer));

                when(customerRepository.findByDocumentNumber(request.documentNumber()))
                                .thenReturn(Optional.of(customer));

                when(documentTypeRepository.findById(documentType.getId()))
                                .thenReturn(Optional.of(documentType));

                when(customerRepository.save(any(Customer.class)))
                                .thenReturn(customer);

                CustomerResponse response = service.update(customer.getId(), request);

                assertThat(response).isNotNull();

                verify(customerRepository).save(any(Customer.class));
        }

        @Test
        void shouldThrowCustomerNotFoundWhenUpdating() {

                UUID id = UUID.randomUUID();

                UpdateCustomerRequest request = new UpdateCustomerRequest(
                                "Lucas Souza",
                                "lucas@email.com",
                                "+351912345678",
                                documentType.getId(),
                                "123456789");

                when(customerRepository.findById(id))
                                .thenReturn(Optional.empty());

                assertThatThrownBy(() -> service.update(id, request))
                                .isInstanceOf(CustomerNotFoundException.class);

                verify(customerRepository, never()).save(any());
        }

        @Test
        void shouldThrowDocumentTypeNotFoundWhenUpdating() {

                UUID nonexistentDocumentTypeId = UUID.randomUUID();
                UpdateCustomerRequest request = new UpdateCustomerRequest(
                                "Lucas Souza",
                                "lucas@email.com",
                                "+351912345678",
                                nonexistentDocumentTypeId,
                                "123456789");

                when(customerRepository.findById(customer.getId()))
                                .thenReturn(Optional.of(customer));

                when(customerRepository.findByEmail(request.email()))
                                .thenReturn(Optional.of(customer));

                when(customerRepository.findByPhoneNumber(request.phoneNumber()))
                                .thenReturn(Optional.of(customer));

                when(customerRepository.findByDocumentNumber(request.documentNumber()))
                                .thenReturn(Optional.of(customer));

                when(documentTypeRepository.findById(nonexistentDocumentTypeId))
                                .thenReturn(Optional.empty());

                assertThatThrownBy(() -> service.update(customer.getId(), request))
                                .isInstanceOf(pt.com.bank.banking_api.exception.DocumentTypeNotFoundException.class);

                verify(customerRepository, never()).save(any());
        }

        @Test
        void shouldThrowDuplicatedEmailOnUpdate() {

                UpdateCustomerRequest request = new UpdateCustomerRequest(
                                "Lucas Souza",
                                "novo@email.com",
                                "+351912345678",
                                documentType.getId(),
                                "123456789");

                Customer anotherCustomer = Customer.builder()
                                .id(UUID.randomUUID())
                                .email("novo@email.com")
                                .build();

                when(customerRepository.findById(customer.getId()))
                                .thenReturn(Optional.of(customer));

                when(customerRepository.findByEmail(request.email()))
                                .thenReturn(Optional.of(anotherCustomer));

                assertThatThrownBy(() -> service.update(customer.getId(), request))
                                .isInstanceOf(EmailAlreadyExistsException.class);

                verify(customerRepository, never()).save(any());
        }

        @Test
        void shouldThrowDuplicatedPhoneOnUpdate() {

                UpdateCustomerRequest request = new UpdateCustomerRequest(
                                "Lucas Souza",
                                "lucas@email.com",
                                "+351999999999",
                                documentType.getId(),
                                "123456789");

                Customer anotherCustomer = Customer.builder()
                                .id(UUID.randomUUID())
                                .phoneNumber("+351999999999")
                                .build();

                when(customerRepository.findById(customer.getId()))
                                .thenReturn(Optional.of(customer));

                when(customerRepository.findByEmail(request.email()))
                                .thenReturn(Optional.of(customer));

                when(customerRepository.findByPhoneNumber(request.phoneNumber()))
                                .thenReturn(Optional.of(anotherCustomer));

                assertThatThrownBy(() -> service.update(customer.getId(), request))
                                .isInstanceOf(PhoneNumberAlreadyExistsException.class);

                verify(customerRepository, never()).save(any());
        }

        @Test
        void shouldThrowDuplicatedDocumentOnUpdate() {

                UpdateCustomerRequest request = new UpdateCustomerRequest(
                                "Lucas Souza",
                                "lucas@email.com",
                                "+351912345678",
                                documentType.getId(),
                                "999999999");

                Customer anotherCustomer = Customer.builder()
                                .id(UUID.randomUUID())
                                .documentNumber("999999999")
                                .build();

                when(customerRepository.findById(customer.getId()))
                                .thenReturn(Optional.of(customer));

                when(customerRepository.findByEmail(request.email()))
                                .thenReturn(Optional.of(customer));

                when(customerRepository.findByPhoneNumber(request.phoneNumber()))
                                .thenReturn(Optional.of(customer));

                when(customerRepository.findByDocumentNumber(request.documentNumber()))
                                .thenReturn(Optional.of(anotherCustomer));

                assertThatThrownBy(() -> service.update(customer.getId(), request))
                                .isInstanceOf(DocumentAlreadyExistsException.class);

                verify(customerRepository, never()).save(any());
        }

}
