package pt.com.bank.banking_api.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import pt.com.bank.banking_api.dto.request.CreateCustomerRequest;
import pt.com.bank.banking_api.dto.request.UpdateCustomerRequest;
import pt.com.bank.banking_api.dto.response.CustomerResponse;
import pt.com.bank.banking_api.service.CustomerService;

@Tag(name = "Customers", description = "Customer management API")
@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @Operation(
        summary = "Create customer",
        description = "Creates a new customer."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Customer created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "409", description = "Email, phone or document already exists")
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse create(
            @Valid @RequestBody CreateCustomerRequest request) {

        return customerService.create(request);
    }

    @Operation(
        summary = "Find all customers"
    )
    @ApiResponse(responseCode = "200", description = "Customers found")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerResponse> findAll() {

        return customerService.findAll();
    }

    @Operation(
        summary = "Find customer by id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer found"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponse findById(
            @PathVariable UUID id) {

        return customerService.findById(id);
    }

    @Operation(
        summary = "Update customer"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Customer updated"),
            @ApiResponse(responseCode = "404", description = "Customer not found"),
            @ApiResponse(responseCode = "409", description = "Conflict")
    })
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponse update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateCustomerRequest request) {

        return customerService.update(id, request);
    }

    @Operation(
        summary = "Delete customer"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Customer deleted"),
            @ApiResponse(responseCode = "404", description = "Customer not found")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable UUID id) {

        customerService.delete(id);
    }
}