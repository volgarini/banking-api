package pt.com.bank.banking_api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import pt.com.bank.banking_api.dto.response.DocumentTypeResponse;
import pt.com.bank.banking_api.entity.DocumentType;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface DocumentTypeMapper {

    DocumentTypeResponse toResponse(DocumentType documentType);

}