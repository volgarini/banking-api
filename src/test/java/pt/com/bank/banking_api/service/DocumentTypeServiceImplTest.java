package pt.com.bank.banking_api.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pt.com.bank.banking_api.dto.response.DocumentTypeResponse;
import pt.com.bank.banking_api.entity.DocumentType;
import pt.com.bank.banking_api.exception.resources.DocumentTypeNotFoundException;
import pt.com.bank.banking_api.factory.constants.DocumentTypeTestConstants;
import pt.com.bank.banking_api.factory.entity.DocumentTypeFactory;
import pt.com.bank.banking_api.factory.response.DocumentTypeResponseFactory;
import pt.com.bank.banking_api.mapper.DocumentTypeMapper;
import pt.com.bank.banking_api.repository.DocumentTypeRepository;
import pt.com.bank.banking_api.service.impl.DocumentTypeServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class DocumentTypeServiceImplTest {

        @Mock
        private DocumentTypeRepository documentTypeRepository;

        @Mock
        private DocumentTypeMapper documentTypeMapper;

        @InjectMocks
        private DocumentTypeServiceImpl documentTypeService;

        @Test
        void findAll_shouldReturnAllDocumentTypes() {

                // Arrange
                DocumentType documentType = DocumentTypeFactory.create();

                DocumentTypeResponse response = DocumentTypeResponseFactory.from(documentType);

                when(documentTypeRepository.findAll())
                                .thenReturn(List.of(documentType));

                when(documentTypeMapper.toResponse(documentType))
                                .thenReturn(response);

                // Act
                List<DocumentTypeResponse> responses = documentTypeService.findAll();

                // Assert
                assertEquals(1, responses.size());

                assertEquals(
                                DocumentTypeTestConstants.DOCUMENT_TYPE_ID,
                                responses.getFirst().id());

                assertEquals(
                                DocumentTypeTestConstants.DEFAULT_DOCUMENT,
                                responses.getFirst().code());

                verify(documentTypeRepository).findAll();
                verify(documentTypeMapper).toResponse(documentType);
        }

        @Test
        void findAll_shouldReturnEmptyList() {

                // Arrange
                when(documentTypeRepository.findAll())
                                .thenReturn(List.of());

                // Act
                List<DocumentTypeResponse> responses = documentTypeService.findAll();

                // Assert
                assertEquals(0, responses.size());

                verify(documentTypeRepository).findAll();
        }

        @Test
        void findById_shouldReturnDocumentTypeSuccessfully() {

                // Arrange
                DocumentType documentType = DocumentTypeFactory.create();

                DocumentTypeResponse response = DocumentTypeResponseFactory.from(documentType);

                when(documentTypeRepository.findById(
                                DocumentTypeTestConstants.DOCUMENT_TYPE_ID))
                                .thenReturn(Optional.of(documentType));

                when(documentTypeMapper.toResponse(documentType))
                                .thenReturn(response);

                // Act
                DocumentTypeResponse result = documentTypeService.findById(
                                DocumentTypeTestConstants.DOCUMENT_TYPE_ID);

                // Assert
                assertEquals(
                                DocumentTypeTestConstants.DOCUMENT_TYPE_ID,
                                result.id());

                assertEquals(
                                DocumentTypeTestConstants.DEFAULT_DOCUMENT,
                                result.code());

                verify(documentTypeRepository)
                                .findById(DocumentTypeTestConstants.DOCUMENT_TYPE_ID);

                verify(documentTypeMapper)
                                .toResponse(documentType);
        }

        @Test
        void findById_shouldThrowDocumentTypeNotFoundException() {

                // Arrange
                when(documentTypeRepository.findById(
                                DocumentTypeTestConstants.DOCUMENT_TYPE_ID))
                                .thenReturn(Optional.empty());

                // Act + Assert
                assertThrows(
                                DocumentTypeNotFoundException.class,
                                () -> documentTypeService.findById(
                                                DocumentTypeTestConstants.DOCUMENT_TYPE_ID));

                verify(documentTypeRepository)
                                .findById(DocumentTypeTestConstants.DOCUMENT_TYPE_ID);
        }

}