package pt.com.bank.banking_api.service;

import java.util.List;
import java.util.UUID;

import pt.com.bank.banking_api.dto.request.CreateCustomerRequest;
import pt.com.bank.banking_api.dto.request.UpdateCustomerRequest;
import pt.com.bank.banking_api.dto.response.CustomerResponse;

public interface CustomerService {

    CustomerResponse create(CreateCustomerRequest request);

    List<CustomerResponse> findAll();

    CustomerResponse findById(UUID id);

    CustomerResponse update(UUID id, UpdateCustomerRequest request);

    void delete(UUID id);
}