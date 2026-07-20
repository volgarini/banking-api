package pt.com.bank.banking_api.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import pt.com.bank.banking_api.dto.response.CustomerResponse;
import pt.com.bank.banking_api.service.CustomerService;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mvc;

    @MockBean
    CustomerService service;

    @Test
    void shouldReturnCustomers() throws Exception {

        when(service.findAll())
                .thenReturn(List.of());

        mvc.perform(get("/api/v1/customers"))
                .andExpect(status().isOk());
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

}