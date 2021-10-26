package com.surepay.technical.repository;

import com.surepay.technical.entities.ReportTransactions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportTransactionsRepository extends JpaRepository<ReportTransactions, Long> {
}
