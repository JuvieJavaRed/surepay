package com.surepay.technical.repository;

import com.surepay.technical.entities.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionsRepository extends JpaRepository<Transactions, Long> {
    Boolean existsByReference(Long Reference);
}
