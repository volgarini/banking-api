package pt.com.bank.banking_api.controller;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import pt.com.bank.banking_api.dto.response.DocumentTypeResponse;
import pt.com.bank.banking_api.entity.DocumentType;
import pt.com.bank.banking_api.exception.resources.DocumentTypeNotFoundException;
import pt.com.bank.banking_api.factory.constants.DocumentTypeTestConstants;
import pt.com.bank.banking_api.factory.entity.DocumentTypeFactory;
import pt.com.bank.banking_api.service.DocumentTypeService;

@WebMvcTest(DocumentTypeController.class)
class DocumentTypeControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockitoBean
        private DocumentTypeService documentTypeService;

        @Test
        void findAll_shouldReturnOk() throws Exception {

                // Arrange
                DocumentType documentType = DocumentTypeFactory.create();

                List<DocumentTypeResponse> responses = List.of(
                                new DocumentTypeResponse(
                                                documentType.getId(),
                                                documentType.getCode(),
                                                documentType.getDescription()));

                when(documentTypeService.findAll())
                                .thenReturn(responses);

                // Act & Assert
                mockMvc.perform(get("/api/v1/document-types"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$").isArray())
                                .andExpect(jsonPath("$.length()").value(1))
                                .andExpect(jsonPath("$[0].id")
                                                .value(DocumentTypeTestConstants.DOCUMENT_TYPE_ID.toString()))
                                .andExpect(jsonPath("$[0].code")
                                                .value(DocumentTypeTestConstants.DEFAULT_DOCUMENT));

                verify(documentTypeService).findAll();
        }

        @Test
        void findById_shouldReturnOk() throws Exception {

                // Arrange
                DocumentType documentType = DocumentTypeFactory.create();

                DocumentTypeResponse response = new DocumentTypeResponse(
                                documentType.getId(),
                                documentType.getCode(),
                                documentType.getDescription());

                when(documentTypeService.findById(
                                DocumentTypeTestConstants.DOCUMENT_TYPE_ID))
                                .thenReturn(response);

                // Act & Assert
                mockMvc.perform(get("/api/v1/document-types/{id}",
                                DocumentTypeTestConstants.DOCUMENT_TYPE_ID))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id")
                                                .value(DocumentTypeTestConstants.DOCUMENT_TYPE_ID.toString()))
                                .andExpect(jsonPath("$.code")
                                                .value(DocumentTypeTestConstants.DEFAULT_DOCUMENT));

                verify(documentTypeService)
                                .findById(DocumentTypeTestConstants.DOCUMENT_TYPE_ID);
        }

        @Test
        void findById_shouldReturnNotFound() throws Exception {

                // Arrange
                when(documentTypeService.findById(
                                DocumentTypeTestConstants.DOCUMENT_TYPE_ID))
                                .thenThrow(new DocumentTypeNotFoundException(
                                                DocumentTypeTestConstants.DOCUMENT_TYPE_ID));

                // Act & Assert
                mockMvc.perform(get("/api/v1/document-types/{id}",
                                DocumentTypeTestConstants.DOCUMENT_TYPE_ID))
                                .andExpect(status().isNotFound());

                verify(documentTypeService)
                                .findById(DocumentTypeTestConstants.DOCUMENT_TYPE_ID);
        }
}