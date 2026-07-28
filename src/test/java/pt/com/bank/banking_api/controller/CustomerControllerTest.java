package pt.com.bank.banking_api.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import pt.com.bank.banking_api.dto.request.CreateCustomerRequest;
import pt.com.bank.banking_api.dto.request.UpdateCustomerRequest;
import pt.com.bank.banking_api.dto.response.CustomerResponse;
import pt.com.bank.banking_api.dto.response.PageResponse;
import pt.com.bank.banking_api.exception.resources.CustomerNotFoundException;
import pt.com.bank.banking_api.factory.constants.CustomerTestConstants;
import pt.com.bank.banking_api.factory.request.CreateCustomerRequestFactory;
import pt.com.bank.banking_api.factory.request.UpdateCustomerRequestFactory;
import pt.com.bank.banking_api.factory.response.CustomerResponseFactory;
import pt.com.bank.banking_api.service.CustomerService;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @MockitoBean
        private CustomerService customerService;

        @Test
        void create_shouldReturnCreated() throws Exception {

                CreateCustomerRequest request = CreateCustomerRequestFactory.create();

                CustomerResponse response = CustomerResponseFactory.create();

                when(customerService.create(any(CreateCustomerRequest.class)))
                                .thenReturn(response);

                mockMvc.perform(post("/api/v1/customers")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.id")
                                                .value(CustomerTestConstants.CUSTOMER_ID.toString()))
                                .andExpect(jsonPath("$.email")
                                                .value(CustomerTestConstants.CUSTOMER_EMAIL));

                verify(customerService).create(any(CreateCustomerRequest.class));
        }

        @Test
        void findAll_shouldReturnOk() throws Exception {

                // Arrange
                CustomerResponse customer = CustomerResponseFactory.create();

                PageResponse<CustomerResponse> response = new PageResponse<>(
                                List.of(customer),
                                0,
                                10,
                                1L,
                                1,
                                true);

                when(customerService.findAll(any(Pageable.class)))
                                .thenReturn(response);

                // Act + Assert
                mockMvc.perform(get("/api/v1/customers")
                                .param("page", "0")
                                .param("size", "10"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.content").isArray())
                                .andExpect(jsonPath("$.content.length()").value(1))
                                .andExpect(jsonPath("$.content[0].id")
                                                .value(CustomerTestConstants.CUSTOMER_ID.toString()))
                                .andExpect(jsonPath("$.content[0].email")
                                                .value(CustomerTestConstants.CUSTOMER_EMAIL))
                                .andExpect(jsonPath("$.page").value(0))
                                .andExpect(jsonPath("$.size").value(10))
                                .andExpect(jsonPath("$.totalElements").value(1))
                                .andExpect(jsonPath("$.totalPages").value(1));

                verify(customerService)
                                .findAll(any(Pageable.class));
        }

        @Test
        void findById_shouldReturnOk() throws Exception {

                CustomerResponse response = CustomerResponseFactory.create();

                when(customerService.findById(CustomerTestConstants.CUSTOMER_ID))
                                .thenReturn(response);

                mockMvc.perform(get("/api/v1/customers/{id}",
                                CustomerTestConstants.CUSTOMER_ID))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id")
                                                .value(CustomerTestConstants.CUSTOMER_ID.toString()))
                                .andExpect(jsonPath("$.email")
                                                .value(CustomerTestConstants.CUSTOMER_EMAIL));

                verify(customerService)
                                .findById(CustomerTestConstants.CUSTOMER_ID);
        }

        @Test
        void findById_shouldReturnNotFound() throws Exception {

                when(customerService.findById(CustomerTestConstants.CUSTOMER_ID))
                                .thenThrow(new CustomerNotFoundException(
                                                CustomerTestConstants.CUSTOMER_ID));

                mockMvc.perform(get("/api/v1/customers/{id}",
                                CustomerTestConstants.CUSTOMER_ID))
                                .andExpect(status().isNotFound());

                verify(customerService)
                                .findById(CustomerTestConstants.CUSTOMER_ID);
        }

        @Test
        void update_shouldReturnOk() throws Exception {

                UpdateCustomerRequest request = UpdateCustomerRequestFactory.create();

                CustomerResponse response = CustomerResponseFactory.create();

                when(customerService.update(
                                eq(CustomerTestConstants.CUSTOMER_ID),
                                any(UpdateCustomerRequest.class)))
                                .thenReturn(response);

                mockMvc.perform(put("/api/v1/customers/{id}",
                                CustomerTestConstants.CUSTOMER_ID)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id")
                                                .value(CustomerTestConstants.CUSTOMER_ID.toString()))
                                .andExpect(jsonPath("$.email")
                                                .value(CustomerTestConstants.CUSTOMER_EMAIL));

                verify(customerService)
                                .update(eq(CustomerTestConstants.CUSTOMER_ID),
                                                any(UpdateCustomerRequest.class));
        }

        @Test
        void update_shouldReturnNotFound() throws Exception {

                UpdateCustomerRequest request = UpdateCustomerRequestFactory.create();

                when(customerService.update(
                                eq(CustomerTestConstants.CUSTOMER_ID),
                                any(UpdateCustomerRequest.class)))
                                .thenThrow(new CustomerNotFoundException(
                                                CustomerTestConstants.CUSTOMER_ID));

                mockMvc.perform(put("/api/v1/customers/{id}",
                                CustomerTestConstants.CUSTOMER_ID)
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                                .andExpect(status().isNotFound());

                verify(customerService)
                                .update(eq(CustomerTestConstants.CUSTOMER_ID),
                                                any(UpdateCustomerRequest.class));
        }

        @Test
        void delete_shouldReturnNoContent() throws Exception {

                doNothing().when(customerService)
                                .delete(CustomerTestConstants.CUSTOMER_ID);

                mockMvc.perform(delete("/api/v1/customers/{id}",
                                CustomerTestConstants.CUSTOMER_ID))
                                .andExpect(status().isNoContent());

                verify(customerService)
                                .delete(CustomerTestConstants.CUSTOMER_ID);
        }

        @Test
        void delete_shouldReturnNotFound() throws Exception {

                doThrow(new CustomerNotFoundException(
                                CustomerTestConstants.CUSTOMER_ID))
                                .when(customerService)
                                .delete(CustomerTestConstants.CUSTOMER_ID);

                mockMvc.perform(delete("/api/v1/customers/{id}",
                                CustomerTestConstants.CUSTOMER_ID))
                                .andExpect(status().isNotFound());

                verify(customerService)
                                .delete(CustomerTestConstants.CUSTOMER_ID);
        }
}
