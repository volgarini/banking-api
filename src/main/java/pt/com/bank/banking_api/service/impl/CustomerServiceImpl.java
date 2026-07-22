package pt.com.bank.banking_api.service.impl;

import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import pt.com.bank.banking_api.dto.request.CreateCustomerRequest;
import pt.com.bank.banking_api.dto.request.UpdateCustomerRequest;
import pt.com.bank.banking_api.dto.response.CustomerResponse;
import pt.com.bank.banking_api.dto.response.PageResponse;
import pt.com.bank.banking_api.entity.Customer;
import pt.com.bank.banking_api.entity.DocumentType;
import pt.com.bank.banking_api.exception.CustomerNotFoundException;
import pt.com.bank.banking_api.exception.DocumentAlreadyExistsException;
import pt.com.bank.banking_api.exception.DocumentTypeNotFoundException;
import pt.com.bank.banking_api.exception.EmailAlreadyExistsException;
import pt.com.bank.banking_api.exception.PhoneNumberAlreadyExistsException;
import pt.com.bank.banking_api.mapper.CustomerMapper;
import pt.com.bank.banking_api.repository.CustomerRepository;
import pt.com.bank.banking_api.repository.DocumentTypeRepository;
import pt.com.bank.banking_api.service.CustomerService;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final DocumentTypeRepository documentTypeRepository;
    private final CustomerMapper customerMapper;

    @Override
    @Transactional
    public CustomerResponse create(CreateCustomerRequest request) {

        validateCustomer(request);

        DocumentType documentType = documentTypeRepository.findById(request.documentTypeId())
                .orElseThrow(() -> new DocumentTypeNotFoundException(request.documentTypeId()));

        Customer customer = customerMapper.toEntity(request);
        customer.setDocumentType(documentType);

        Customer savedCustomer = customerRepository.save(customer);

        return customerMapper.toResponse(savedCustomer);
    }

    @Override
    public PageResponse<CustomerResponse> findAll(Pageable pageable) {
        return PageResponse.from(customerRepository.findAll(pageable)
                .map(customerMapper::toResponse));
    }

    @Override
    public CustomerResponse findById(UUID id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));

        return customerMapper.toResponse(customer);
    }

    @Override
    @Transactional
    public CustomerResponse update(UUID id, UpdateCustomerRequest request) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));

        validateUpdate(customer, request);

        DocumentType documentType = documentTypeRepository.findById(request.documentTypeId())
                .orElseThrow(() -> new DocumentTypeNotFoundException(request.documentTypeId()));

        customerMapper.updateEntity(request, customer);
        customer.setDocumentType(documentType);

        Customer updatedCustomer = customerRepository.save(customer);

        return customerMapper.toResponse(updatedCustomer);
    }

    @Override
    @Transactional
    public void delete(UUID id) {

        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));

        customerRepository.delete(customer);
    }

    private void validateCustomer(CreateCustomerRequest request) {

        if (customerRepository.existsByEmail(request.email())) {
            throw new EmailAlreadyExistsException(request.email());
        }

        if (customerRepository.existsByPhoneNumber(request.phoneNumber())) {
            throw new PhoneNumberAlreadyExistsException(request.phoneNumber());
        }

        if (customerRepository.existsByDocumentNumber(request.documentNumber())) {
            throw new DocumentAlreadyExistsException(request.documentNumber());
        }
    }

    private void validateUpdate(Customer customer,
                                UpdateCustomerRequest request) {

        customerRepository.findByEmail(request.email())
                .filter(c -> !c.getId().equals(customer.getId()))
                .ifPresent(c -> {
                    throw new EmailAlreadyExistsException(request.email());
                });
                
        customerRepository.findByPhoneNumber(request.phoneNumber())
                .filter(c -> !c.getId().equals(customer.getId()))
                .ifPresent(c -> {
                    throw new PhoneNumberAlreadyExistsException(request.phoneNumber());
                });

        customerRepository.findByDocumentNumber(request.documentNumber())
                .filter(c -> !c.getId().equals(customer.getId()))
                .ifPresent(c -> {
                    throw new DocumentAlreadyExistsException(request.documentNumber());
                });
    }
}
