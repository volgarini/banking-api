package pt.com.bank.banking_api.service;

import java.util.List;
import java.util.UUID;

import pt.com.bank.banking_api.dto.request.CreateAccountRequest;
import pt.com.bank.banking_api.dto.request.UpdateAccountRequest;
import pt.com.bank.banking_api.dto.response.AccountResponse;

public interface AccountService {

    AccountResponse create(CreateAccountRequest request);

    AccountResponse findById(UUID id);

    List<AccountResponse> findAll();

    AccountResponse update(UUID id, UpdateAccountRequest request);

    void delete(UUID id);

}