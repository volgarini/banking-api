package pt.com.bank.banking_api.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pt.com.bank.banking_api.dto.response.DocumentTypeResponse;
import pt.com.bank.banking_api.entity.DocumentType;
import pt.com.bank.banking_api.exception.DocumentTypeNotFoundException;
import pt.com.bank.banking_api.mapper.DocumentTypeMapper;
import pt.com.bank.banking_api.repository.DocumentTypeRepository;
import pt.com.bank.banking_api.service.DocumentTypeService;

@Service
@RequiredArgsConstructor
public class DocumentTypeServiceImpl implements DocumentTypeService {

    private final DocumentTypeRepository repository;

    private final DocumentTypeMapper mapper;

    @Override
    public List<DocumentTypeResponse> findAll() {

        return repository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public DocumentTypeResponse findById(UUID id) {

        DocumentType documentType = repository.findById(id)
                .orElseThrow(() -> new DocumentTypeNotFoundException(id));

        return mapper.toResponse(documentType);
    }
}