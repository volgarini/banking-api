package pt.com.bank.banking_api.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import pt.com.bank.banking_api.dto.response.CustomerResponse;
import pt.com.bank.banking_api.dto.response.PageResponse;
import pt.com.bank.banking_api.exception.CustomerNotFoundException;
import pt.com.bank.banking_api.exception.EmailAlreadyExistsException;
import pt.com.bank.banking_api.service.CustomerService;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    CustomerService service;

    @Test
    void shouldReturnCustomers() throws Exception {

        when(service.findAll(any()))
                .thenReturn(new PageResponse<>(java.util.List.of(), 1, 10, 0, 0, true));

        mvc.perform(get("/api/v1/customers?page=1&size=10"))
                .andExpect(status().isOk())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .jsonPath("$.page").value(1))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .jsonPath("$.size").value(10));
    }

    @Test
    void shouldLimitPageSizeAndIgnoreUnsupportedSortFields() throws Exception {

        when(service.findAll(any()))
                .thenReturn(new PageResponse<>(java.util.List.of(), 0, 100, 0, 0, true));

        mvc.perform(get("/api/v1/customers?size=500&sort=documentNumber,desc"))
                .andExpect(status().isOk());

        var pageableCaptor = org.mockito.ArgumentCaptor.forClass(org.springframework.data.domain.Pageable.class);
        verify(service).findAll(pageableCaptor.capture());

        assertThat(pageableCaptor.getValue().getPageSize()).isEqualTo(100);
        assertThat(pageableCaptor.getValue().getSort().getOrderFor("fullName").isAscending()).isTrue();
    }

    @Test
    void shouldCreateCustomer() throws Exception {

        UUID documentTypeId = UUID.randomUUID();
        when(service.create(any()))
                .thenReturn(new CustomerResponse(
                        UUID.randomUUID(), "John Doe", "john@example.com", "+351912345678",
                        documentTypeId, "Citizen Card", "123456789", null, null));

        mvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerRequest(documentTypeId)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldRejectInvalidCustomerPayload() throws Exception {

        mvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .jsonPath("$.validationErrors.fullName").exists());
    }

    @Test
    void shouldReturnConflictWhenCreatingDuplicatedCustomer() throws Exception {

        UUID documentTypeId = UUID.randomUUID();
        when(service.create(any()))
                .thenThrow(new EmailAlreadyExistsException("john@example.com"));

        mvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerRequest(documentTypeId)))
                .andExpect(status().isConflict());
    }

    @Test
    void shouldReturnGenericConflictForDatabaseIntegrityViolation() throws Exception {

        UUID documentTypeId = UUID.randomUUID();
        when(service.create(any()))
                .thenThrow(new DataIntegrityViolationException("database constraint"));

        mvc.perform(post("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerRequest(documentTypeId)))
                .andExpect(status().isConflict())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers
                        .jsonPath("$.message").value("The request conflicts with existing data."));
    }

    @Test
    void shouldReturnCustomerById() throws Exception {

        UUID id = UUID.randomUUID();

        CustomerResponse response = mock(CustomerResponse.class);

        when(service.findById(id))
                .thenReturn(response);

        mvc.perform(get("/api/v1/customers/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteCustomer() throws Exception {

        UUID id = UUID.randomUUID();

        mvc.perform(delete("/api/v1/customers/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldUpdateCustomer() throws Exception {

        UUID id = UUID.randomUUID();
        UUID documentTypeId = UUID.randomUUID();
        when(service.update(any(), any()))
                .thenReturn(new CustomerResponse(
                        id, "John Doe", "john@example.com", "+351912345678",
                        documentTypeId, "Citizen Card", "123456789", null, null));

        mvc.perform(put("/api/v1/customers/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerRequest(documentTypeId)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnNotFoundWhenCustomerDoesNotExist() throws Exception {

        UUID id = UUID.randomUUID();
        when(service.findById(id)).thenThrow(new CustomerNotFoundException(id));

        mvc.perform(get("/api/v1/customers/{id}", id))
                .andExpect(status().isNotFound());
    }

    private String customerRequest(UUID documentTypeId) {
        return """
                {
                  "fullName": "John Doe",
                  "email": "john@example.com",
                  "phoneNumber": "+351912345678",
                  "documentTypeId": "%s",
                  "documentNumber": "123456789"
                }
                """.formatted(documentTypeId);
    }

}
