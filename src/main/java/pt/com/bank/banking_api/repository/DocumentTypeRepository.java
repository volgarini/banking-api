package pt.com.bank.banking_api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import pt.com.bank.banking_api.entity.DocumentType;

public interface DocumentTypeRepository extends JpaRepository<DocumentType, UUID> {
}
