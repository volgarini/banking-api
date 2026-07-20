package pt.com.bank.banking_api.service;

import java.util.List;
import java.util.UUID;

import pt.com.bank.banking_api.dto.response.DocumentTypeResponse;

public interface DocumentTypeService {

    List<DocumentTypeResponse> findAll();

    DocumentTypeResponse findById(UUID id);

}