package org.example.transferservice.persistance;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<TransferEntity, String> {
}
