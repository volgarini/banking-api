package pt.com.bank.banking_api.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import pt.com.bank.banking_api.dto.response.DocumentTypeResponse;
import pt.com.bank.banking_api.entity.DocumentType;
import pt.com.bank.banking_api.factory.constants.DocumentTypeTestConstants;
import pt.com.bank.banking_api.factory.entity.DocumentTypeFactory;

class DocumentTypeMapperTest {

    private final DocumentTypeMapper mapper =
            Mappers.getMapper(DocumentTypeMapper.class);

    @Test
    void toResponse_shouldMapEntityToResponse() {

        // Arrange
        DocumentType documentType = DocumentTypeFactory.create();

        // Act
        DocumentTypeResponse response = mapper.toResponse(documentType);

        // Assert
        assertNotNull(response);

        assertEquals(
                DocumentTypeTestConstants.DOCUMENT_TYPE_ID,
                response.id());

        assertEquals(
                DocumentTypeTestConstants.DEFAULT_DOCUMENT,
                response.code());

        assertEquals(
                DocumentTypeTestConstants.DEFAULT_DESCRIPTION,
                response.description());
    }

}
