package pt.com.bank.banking_api.service;

import java.util.UUID;

import org.springframework.data.domain.Pageable;

import pt.com.bank.banking_api.dto.request.CreateCustomerRequest;
import pt.com.bank.banking_api.dto.request.UpdateCustomerRequest;
import pt.com.bank.banking_api.dto.response.CustomerResponse;
import pt.com.bank.banking_api.dto.response.PageResponse;

public interface CustomerService {

    CustomerResponse create(CreateCustomerRequest request);

    PageResponse<CustomerResponse> findAll(Pageable pageable);

    CustomerResponse findById(UUID id);

    CustomerResponse update(UUID id, UpdateCustomerRequest request);

    void delete(UUID id);
}
