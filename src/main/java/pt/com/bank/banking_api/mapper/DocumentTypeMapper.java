package pt.com.bank.banking_api.mapper;

import org.mapstruct.Mapper;

import pt.com.bank.banking_api.dto.response.DocumentTypeResponse;
import pt.com.bank.banking_api.entity.DocumentType;

@Mapper(componentModel = "spring")
public interface DocumentTypeMapper {

    DocumentTypeResponse toResponse(DocumentType documentType);

}