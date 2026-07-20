package pt.com.bank.banking_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import pt.com.bank.banking_api.dto.request.CreateCustomerRequest;
import pt.com.bank.banking_api.dto.request.UpdateCustomerRequest;
import pt.com.bank.banking_api.dto.response.CustomerResponse;
import pt.com.bank.banking_api.entity.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "documentType", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Customer toEntity(CreateCustomerRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "documentType", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(UpdateCustomerRequest request,
                      @MappingTarget Customer customer);

    @Mapping(source = "documentType.id", target = "documentTypeId")
    @Mapping(source = "documentType.description", target = "documentType")
    CustomerResponse toResponse(Customer customer);
}
