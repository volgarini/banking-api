package pt.com.bank.banking_api.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import pt.com.bank.banking_api.dto.response.DocumentTypeResponse;
import pt.com.bank.banking_api.entity.DocumentType;
import pt.com.bank.banking_api.exception.DocumentTypeNotFoundException;
import pt.com.bank.banking_api.mapper.DocumentTypeMapper;
import pt.com.bank.banking_api.repository.DocumentTypeRepository;
import pt.com.bank.banking_api.service.impl.DocumentTypeServiceImpl;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DocumentTypeServiceImplTest {

    @Mock
    private DocumentTypeRepository repository;

    private final DocumentTypeMapper mapper =
            Mappers.getMapper(DocumentTypeMapper.class);

    @InjectMocks
    private DocumentTypeServiceImpl service;

    @Test
    void shouldFindAllDocumentTypes() {

        UUID id = UUID.randomUUID();

        DocumentType type = new DocumentType();
        type.setId(id);
        type.setCode("CPF");
        type.setDescription("Brazilian CPF");

        when(repository.findAll())
                .thenReturn(List.of(type));

        DocumentTypeServiceImpl service =
                new DocumentTypeServiceImpl(repository, mapper);

        List<DocumentTypeResponse> result = service.findAll();

        assertThat(result).hasSize(1);
        assertThat(result.getFirst().code()).isEqualTo("CPF");

        verify(repository).findAll();
    }

    @Test
    void shouldFindDocumentTypeById() {

        UUID id = UUID.randomUUID();

        DocumentType type = new DocumentType();
        type.setId(id);
        type.setCode("PASSPORT");
        type.setDescription("Passport");

        when(repository.findById(id))
                .thenReturn(Optional.of(type));

        DocumentTypeServiceImpl service =
                new DocumentTypeServiceImpl(repository, mapper);

        DocumentTypeResponse response = service.findById(id);

        assertThat(response.id()).isEqualTo(id);
        assertThat(response.code()).isEqualTo("PASSPORT");

        verify(repository).findById(id);
    }

    @Test
    void shouldThrowExceptionWhenDocumentTypeDoesNotExist() {

        UUID id = UUID.randomUUID();

        when(repository.findById(id))
                .thenReturn(Optional.empty());

        DocumentTypeServiceImpl service =
                new DocumentTypeServiceImpl(repository, mapper);

        assertThatThrownBy(() -> service.findById(id))
                .isInstanceOf(DocumentTypeNotFoundException.class)
                .hasMessageContaining(id.toString());

        verify(repository).findById(id);
    }

}