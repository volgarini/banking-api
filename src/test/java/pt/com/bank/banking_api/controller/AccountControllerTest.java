package pt.com.bank.banking_api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import pt.com.bank.banking_api.dto.request.CreateAccountRequest;
import pt.com.bank.banking_api.dto.request.UpdateAccountRequest;
import pt.com.bank.banking_api.dto.response.AccountResponse;
import pt.com.bank.banking_api.exception.resources.AccountNotFoundException;
import pt.com.bank.banking_api.exception.resources.CustomerNotFoundException;
import pt.com.bank.banking_api.factory.constants.AccountTestConstants;
import pt.com.bank.banking_api.factory.constants.CustomerTestConstants;
import pt.com.bank.banking_api.factory.entity.AccountFactory;
import pt.com.bank.banking_api.factory.entity.CustomerFactory;
import pt.com.bank.banking_api.factory.entity.DocumentTypeFactory;
import pt.com.bank.banking_api.factory.request.CreateAccountRequestFactory;
import pt.com.bank.banking_api.factory.request.UpdateAccountRequestFactory;
import pt.com.bank.banking_api.factory.response.AccountResponseFactory;
import pt.com.bank.banking_api.service.AccountService;

@WebMvcTest(AccountController.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AccountService accountService;

    @Test
    void create_shouldReturnCreated() throws Exception {
        // Arrange
        CreateAccountRequest request = CreateAccountRequestFactory.create();

        AccountResponse response = AccountResponseFactory.from(
                AccountFactory.checking(
                        CustomerFactory.create(DocumentTypeFactory.create())));

        when(accountService.create(any(CreateAccountRequest.class)))
                .thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/v1/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(response.id().toString()))
                .andExpect(jsonPath("$.accountNumber").value(response.accountNumber()))
                .andExpect(jsonPath("$.iban").value(response.iban()))
                .andExpect(jsonPath("$.customerId").value(response.customerId().toString()))
                .andExpect(jsonPath("$.customerName").value(response.customerName()))
                .andExpect(jsonPath("$.accountType").value(response.accountType().name()))
                .andExpect(jsonPath("$.status").value(response.status().name()));

        verify(accountService).create(any(CreateAccountRequest.class));
    }

    @Test
    void create_shouldReturnNotFound_whenCustomerDoesNotExist() throws Exception {

        CreateAccountRequest request = CreateAccountRequestFactory.create();

        when(accountService.create(any()))
                .thenThrow(new CustomerNotFoundException(
                        CustomerTestConstants.CUSTOMER_ID));

        mockMvc.perform(post("/api/v1/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());

        verify(accountService).create(any());
    }

    @Test
    void findById_shouldReturnOk() throws Exception {

        AccountResponse response = AccountResponseFactory.from(
                AccountFactory.checking(
                        CustomerFactory.create(DocumentTypeFactory.create())));

        when(accountService.findById(AccountTestConstants.ACCOUNT_ID))
                .thenReturn(response);

        mockMvc.perform(get("/api/v1/accounts/{id}",
                AccountTestConstants.ACCOUNT_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(response.id().toString()))
                .andExpect(jsonPath("$.accountNumber").value(response.accountNumber()));

        verify(accountService).findById(AccountTestConstants.ACCOUNT_ID);
    }

    @Test
    void findById_shouldReturnNotFound() throws Exception {

        when(accountService.findById(AccountTestConstants.ACCOUNT_ID))
                .thenThrow(new AccountNotFoundException(AccountTestConstants.ACCOUNT_ID));

        mockMvc.perform(get("/api/v1/accounts/{id}",
                AccountTestConstants.ACCOUNT_ID))
                .andExpect(status().isNotFound());

        verify(accountService).findById(AccountTestConstants.ACCOUNT_ID);
    }

    @Test
    void findAll_shouldReturnOk() throws Exception {

        List<AccountResponse> responses = List.of(
                AccountResponseFactory.from(
                        AccountFactory.checking(
                                CustomerFactory.create(DocumentTypeFactory.create()))));

        when(accountService.findAll())
                .thenReturn(responses);

        mockMvc.perform(get("/api/v1/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));

        verify(accountService).findAll();
    }

    @Test
    void update_shouldReturnOk() throws Exception {

        UpdateAccountRequest request = UpdateAccountRequestFactory.blocked();

        AccountResponse response = AccountResponseFactory.from(
                AccountFactory.blocked(
                        CustomerFactory.create(DocumentTypeFactory.create())));

        when(accountService.update(eq(AccountTestConstants.ACCOUNT_ID), any()))
                .thenReturn(response);

        mockMvc.perform(put("/api/v1/accounts/{id}",
                AccountTestConstants.ACCOUNT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("BLOCKED"));

        verify(accountService)
                .update(eq(AccountTestConstants.ACCOUNT_ID), any());
    }

    @Test
    void update_shouldReturnNotFound() throws Exception {

        UpdateAccountRequest request = UpdateAccountRequestFactory.blocked();

        when(accountService.update(eq(AccountTestConstants.ACCOUNT_ID), any()))
                .thenThrow(new AccountNotFoundException(AccountTestConstants.ACCOUNT_ID));

        mockMvc.perform(put("/api/v1/accounts/{id}",
                AccountTestConstants.ACCOUNT_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());

        verify(accountService)
                .update(eq(AccountTestConstants.ACCOUNT_ID), any());
    }

    @Test
    void delete_shouldReturnNoContent() throws Exception {

        doNothing().when(accountService)
                .delete(AccountTestConstants.ACCOUNT_ID);

        mockMvc.perform(delete("/api/v1/accounts/{id}",
                AccountTestConstants.ACCOUNT_ID))
                .andExpect(status().isNoContent());

        verify(accountService)
                .delete(AccountTestConstants.ACCOUNT_ID);
    }

    @Test
    void delete_shouldReturnNotFound() throws Exception {

        doThrow(new AccountNotFoundException(AccountTestConstants.ACCOUNT_ID))
                .when(accountService)
                .delete(AccountTestConstants.ACCOUNT_ID);

        mockMvc.perform(delete("/api/v1/accounts/{id}",
                AccountTestConstants.ACCOUNT_ID))
                .andExpect(status().isNotFound());

        verify(accountService)
                .delete(AccountTestConstants.ACCOUNT_ID);
    }
}