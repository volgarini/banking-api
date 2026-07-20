package pt.com.bank.banking_api.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import pt.com.bank.banking_api.dto.response.DocumentTypeResponse;
import pt.com.bank.banking_api.service.DocumentTypeService;

@WebMvcTest(DocumentTypeController.class)
class DocumentTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DocumentTypeService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldReturnAllDocumentTypes() throws Exception {

        UUID id = UUID.randomUUID();

        when(service.findAll())
                .thenReturn(List.of(
                        new DocumentTypeResponse(
                                id,
                                "CPF",
                                "Brazilian CPF"
                        )
                ));

        mockMvc.perform(get("/api/v1/document-types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code").value("CPF"))
                .andExpect(jsonPath("$[0].description").value("Brazilian CPF"));
    }

    @Test
    void shouldReturnDocumentTypeById() throws Exception {

        UUID id = UUID.randomUUID();

        when(service.findById(id))
                .thenReturn(
                        new DocumentTypeResponse(
                                id,
                                "PASSPORT",
                                "Passport"
                        )
                );

        mockMvc.perform(get("/api/v1/document-types/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value("PASSPORT"))
                .andExpect(jsonPath("$.description").value("Passport"));
    }

}