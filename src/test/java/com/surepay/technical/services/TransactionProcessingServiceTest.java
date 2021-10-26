package com.surepay.technical.services;

import com.surepay.technical.entities.ReportTransactions;
import com.surepay.technical.entities.Transactions;
import com.surepay.technical.exceptions.DuplicateTransactionException;
import com.surepay.technical.repository.ReportTransactionsRepository;
import com.surepay.technical.repository.TransactionsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionProcessingServiceTest {

    @Mock
    private TransactionsRepository mockTransactionsRepository;
    @Mock
    private ReportTransactionsRepository mockReportTransactionsRepository;

    @InjectMocks
    private TransactionProcessingService transactionProcessingServiceUnderTest;

    @Test
    public void testProcessCsv() {
        // Setup
        when(mockTransactionsRepository.existsByReference(0L)).thenReturn(false);

        // Configure ReportTransactionsRepository.saveAll(...).
        final ReportTransactions reportTransactions1 = new ReportTransactions();
        reportTransactions1.setTransactionId(0L);
        reportTransactions1.setCreatedDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        reportTransactions1.setDescription("description");
        reportTransactions1.setReason("reason");
        final List<ReportTransactions> reportTransactions = Arrays.asList(reportTransactions1);
        when(mockReportTransactionsRepository.saveAll(Arrays.asList(new ReportTransactions()))).thenReturn(reportTransactions);

        // Configure TransactionsRepository.saveAll(...).
        final Transactions transactions = new Transactions();
        transactions.setReference(0L);
        transactions.setAccountNumber("accountNumber");
        transactions.setStartBalance(new BigDecimal("0.00"));
        transactions.setMutation(new BigDecimal("0.00"));
        transactions.setDescription("description");
        transactions.setEndBalance(new BigDecimal("0.00"));
        final List<Transactions> transactionsList = Arrays.asList(transactions);
        when(mockTransactionsRepository.saveAll(Arrays.asList(new Transactions()))).thenReturn(transactionsList);

        // Run the test
        transactionProcessingServiceUnderTest.processCsv("filePath");

        // Verify the results
        verify(mockReportTransactionsRepository).saveAll(Arrays.asList(new ReportTransactions()));
        verify(mockTransactionsRepository).saveAll(Arrays.asList(new Transactions()));
    }

    @Test
    public void testProcessCsv_ReportTransactionsRepositoryReturnsNoItems() {
        // Setup
        when(mockTransactionsRepository.existsByReference(0L)).thenReturn(false);
        when(mockReportTransactionsRepository.saveAll(Arrays.asList(new ReportTransactions()))).thenReturn(Collections.emptyList());

        // Configure TransactionsRepository.saveAll(...).
        final Transactions transactions = new Transactions();
        transactions.setReference(0L);
        transactions.setAccountNumber("accountNumber");
        transactions.setStartBalance(new BigDecimal("0.00"));
        transactions.setMutation(new BigDecimal("0.00"));
        transactions.setDescription("description");
        transactions.setEndBalance(new BigDecimal("0.00"));
        final List<Transactions> transactionsList = Arrays.asList(transactions);
        when(mockTransactionsRepository.saveAll(Arrays.asList(new Transactions()))).thenReturn(transactionsList);

        // Run the test
        transactionProcessingServiceUnderTest.processCsv("filePath");

        // Verify the results
        verify(mockReportTransactionsRepository).saveAll(Arrays.asList(new ReportTransactions()));
        verify(mockTransactionsRepository).saveAll(Arrays.asList(new Transactions()));
    }

    @Test
    public void testProcessCsv_TransactionsRepositorySaveAllReturnsNoItems() {
        // Setup
        when(mockTransactionsRepository.existsByReference(0L)).thenReturn(false);

        // Configure ReportTransactionsRepository.saveAll(...).
        final ReportTransactions reportTransactions1 = new ReportTransactions();
        reportTransactions1.setTransactionId(0L);
        reportTransactions1.setCreatedDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        reportTransactions1.setDescription("description");
        reportTransactions1.setReason("reason");
        final List<ReportTransactions> reportTransactions = Arrays.asList(reportTransactions1);
        when(mockReportTransactionsRepository.saveAll(Arrays.asList(new ReportTransactions()))).thenReturn(reportTransactions);

        when(mockTransactionsRepository.saveAll(Arrays.asList(new Transactions()))).thenReturn(Collections.emptyList());

        // Run the test
        transactionProcessingServiceUnderTest.processCsv("filePath");

        // Verify the results
        verify(mockReportTransactionsRepository).saveAll(Arrays.asList(new ReportTransactions()));
        verify(mockTransactionsRepository).saveAll(Arrays.asList(new Transactions()));
    }

    @Test
    public void testProcessJson() {
        // Setup
        when(mockTransactionsRepository.existsByReference(0L)).thenReturn(false);

        // Configure ReportTransactionsRepository.saveAll(...).
        final ReportTransactions reportTransactions1 = new ReportTransactions();
        reportTransactions1.setTransactionId(0L);
        reportTransactions1.setCreatedDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        reportTransactions1.setDescription("description");
        reportTransactions1.setReason("reason");
        final List<ReportTransactions> reportTransactions = Arrays.asList(reportTransactions1);
        when(mockReportTransactionsRepository.saveAll(Arrays.asList(new ReportTransactions()))).thenReturn(reportTransactions);

        // Configure TransactionsRepository.saveAll(...).
        final Transactions transactions = new Transactions();
        transactions.setReference(0L);
        transactions.setAccountNumber("accountNumber");
        transactions.setStartBalance(new BigDecimal("0.00"));
        transactions.setMutation(new BigDecimal("0.00"));
        transactions.setDescription("description");
        transactions.setEndBalance(new BigDecimal("0.00"));
        final List<Transactions> transactionsList = Arrays.asList(transactions);
        when(mockTransactionsRepository.saveAll(Arrays.asList(new Transactions()))).thenReturn(transactionsList);

        // Run the test
        transactionProcessingServiceUnderTest.processJson("filePath");

        // Verify the results
        verify(mockReportTransactionsRepository).saveAll(Arrays.asList(new ReportTransactions()));
        verify(mockTransactionsRepository).saveAll(Arrays.asList(new Transactions()));
    }

    @Test
    public void testProcessJson_ReportTransactionsRepositoryReturnsNoItems() {
        // Setup
        when(mockTransactionsRepository.existsByReference(0L)).thenReturn(false);
        when(mockReportTransactionsRepository.saveAll(Arrays.asList(new ReportTransactions()))).thenReturn(Collections.emptyList());

        // Configure TransactionsRepository.saveAll(...).
        final Transactions transactions = new Transactions();
        transactions.setReference(0L);
        transactions.setAccountNumber("accountNumber");
        transactions.setStartBalance(new BigDecimal("0.00"));
        transactions.setMutation(new BigDecimal("0.00"));
        transactions.setDescription("description");
        transactions.setEndBalance(new BigDecimal("0.00"));
        final List<Transactions> transactionsList = Arrays.asList(transactions);
        when(mockTransactionsRepository.saveAll(Arrays.asList(new Transactions()))).thenReturn(transactionsList);

        // Run the test
        transactionProcessingServiceUnderTest.processJson("filePath");

        // Verify the results
        verify(mockReportTransactionsRepository).saveAll(Arrays.asList(new ReportTransactions()));
        verify(mockTransactionsRepository).saveAll(Arrays.asList(new Transactions()));
    }

    @Test
    public void testProcessJson_TransactionsRepositorySaveAllReturnsNoItems() {
        // Setup
        when(mockTransactionsRepository.existsByReference(0L)).thenReturn(false);

        // Configure ReportTransactionsRepository.saveAll(...).
        final ReportTransactions reportTransactions1 = new ReportTransactions();
        reportTransactions1.setTransactionId(0L);
        reportTransactions1.setCreatedDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        reportTransactions1.setDescription("description");
        reportTransactions1.setReason("reason");
        final List<ReportTransactions> reportTransactions = Arrays.asList(reportTransactions1);
        when(mockReportTransactionsRepository.saveAll(Arrays.asList(new ReportTransactions()))).thenReturn(reportTransactions);

        when(mockTransactionsRepository.saveAll(Arrays.asList(new Transactions()))).thenReturn(Collections.emptyList());

        // Run the test
        transactionProcessingServiceUnderTest.processJson("filePath");

        // Verify the results
        verify(mockReportTransactionsRepository).saveAll(Arrays.asList(new ReportTransactions()));
        verify(mockTransactionsRepository).saveAll(Arrays.asList(new Transactions()));
    }

    @Test
    public void testCheckDuplicates() throws Exception {
        // Setup
        final Transactions transactions1 = new Transactions();
        transactions1.setReference(0L);
        transactions1.setAccountNumber("accountNumber");
        transactions1.setStartBalance(new BigDecimal("0.00"));
        transactions1.setMutation(new BigDecimal("0.00"));
        transactions1.setDescription("description");
        transactions1.setEndBalance(new BigDecimal("0.00"));
        final List<Transactions> transactions = Arrays.asList(transactions1);
        final Transactions transactions2 = new Transactions();
        transactions2.setReference(0L);
        transactions2.setAccountNumber("accountNumber");
        transactions2.setStartBalance(new BigDecimal("0.00"));
        transactions2.setMutation(new BigDecimal("0.00"));
        transactions2.setDescription("description");
        transactions2.setEndBalance(new BigDecimal("0.00"));
        final List<Transactions> expectedResult = Arrays.asList(transactions2);
        when(mockTransactionsRepository.existsByReference(0L)).thenReturn(false);

        // Configure ReportTransactionsRepository.saveAll(...).
        final ReportTransactions reportTransactions1 = new ReportTransactions();
        reportTransactions1.setTransactionId(0L);
        reportTransactions1.setCreatedDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        reportTransactions1.setDescription("description");
        reportTransactions1.setReason("reason");
        final List<ReportTransactions> reportTransactions = Arrays.asList(reportTransactions1);
        when(mockReportTransactionsRepository.saveAll(Arrays.asList(new ReportTransactions()))).thenReturn(reportTransactions);

        // Run the test
        final List<Transactions> result = transactionProcessingServiceUnderTest.checkDuplicates(transactions);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockReportTransactionsRepository).saveAll(Arrays.asList(new ReportTransactions()));
    }

    @Test
    public void testCheckDuplicates_ReportTransactionsRepositoryReturnsNoItems() throws Exception {
        // Setup
        final Transactions transactions1 = new Transactions();
        transactions1.setReference(0L);
        transactions1.setAccountNumber("accountNumber");
        transactions1.setStartBalance(new BigDecimal("0.00"));
        transactions1.setMutation(new BigDecimal("0.00"));
        transactions1.setDescription("description");
        transactions1.setEndBalance(new BigDecimal("0.00"));
        final List<Transactions> transactions = Arrays.asList(transactions1);
        final Transactions transactions2 = new Transactions();
        transactions2.setReference(0L);
        transactions2.setAccountNumber("accountNumber");
        transactions2.setStartBalance(new BigDecimal("0.00"));
        transactions2.setMutation(new BigDecimal("0.00"));
        transactions2.setDescription("description");
        transactions2.setEndBalance(new BigDecimal("0.00"));
        final List<Transactions> expectedResult = Arrays.asList(transactions2);
        when(mockTransactionsRepository.existsByReference(0L)).thenReturn(false);
        when(mockReportTransactionsRepository.saveAll(Arrays.asList(new ReportTransactions()))).thenReturn(Collections.emptyList());

        // Run the test
        final List<Transactions> result = transactionProcessingServiceUnderTest.checkDuplicates(transactions);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockReportTransactionsRepository).saveAll(Arrays.asList(new ReportTransactions()));
    }

    @Test
    public void testCheckDuplicates_ThrowsDuplicateTransactionException() {
        // Setup
        final Transactions transactions1 = new Transactions();
        transactions1.setReference(0L);
        transactions1.setAccountNumber("accountNumber");
        transactions1.setStartBalance(new BigDecimal("0.00"));
        transactions1.setMutation(new BigDecimal("0.00"));
        transactions1.setDescription("description");
        transactions1.setEndBalance(new BigDecimal("0.00"));
        final List<Transactions> transactions = Arrays.asList(transactions1);
        when(mockTransactionsRepository.existsByReference(0L)).thenReturn(false);

        // Configure ReportTransactionsRepository.saveAll(...).
        final ReportTransactions reportTransactions1 = new ReportTransactions();
        reportTransactions1.setTransactionId(0L);
        reportTransactions1.setCreatedDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        reportTransactions1.setDescription("description");
        reportTransactions1.setReason("reason");
        final List<ReportTransactions> reportTransactions = Arrays.asList(reportTransactions1);
        when(mockReportTransactionsRepository.saveAll(Arrays.asList(new ReportTransactions()))).thenReturn(reportTransactions);

        // Run the test
        assertThatThrownBy(() -> transactionProcessingServiceUnderTest.checkDuplicates(transactions)).isInstanceOf(DuplicateTransactionException.class);
        verify(mockReportTransactionsRepository).saveAll(Arrays.asList(new ReportTransactions()));
    }

    @Test
    public void testWriteToReports() {
        // Setup
        final Transactions transactions = new Transactions();
        transactions.setReference(0L);
        transactions.setAccountNumber("accountNumber");
        transactions.setStartBalance(new BigDecimal("0.00"));
        transactions.setMutation(new BigDecimal("0.00"));
        transactions.setDescription("description");
        transactions.setEndBalance(new BigDecimal("0.00"));
        final List<Transactions> reportedTransactions = Arrays.asList(transactions);

        // Configure ReportTransactionsRepository.saveAll(...).
        final ReportTransactions reportTransactions1 = new ReportTransactions();
        reportTransactions1.setTransactionId(0L);
        reportTransactions1.setCreatedDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        reportTransactions1.setDescription("description");
        reportTransactions1.setReason("reason");
        final List<ReportTransactions> reportTransactions = Arrays.asList(reportTransactions1);
        when(mockReportTransactionsRepository.saveAll(Arrays.asList(new ReportTransactions()))).thenReturn(reportTransactions);

        // Run the test
        transactionProcessingServiceUnderTest.writeToReports(reportedTransactions, "reason");

        // Verify the results
        verify(mockReportTransactionsRepository).saveAll(Arrays.asList(new ReportTransactions()));
    }

    @Test
    public void testWriteToReports_ReportTransactionsRepositoryReturnsNoItems() {
        // Setup
        final Transactions transactions = new Transactions();
        transactions.setReference(0L);
        transactions.setAccountNumber("accountNumber");
        transactions.setStartBalance(new BigDecimal("0.00"));
        transactions.setMutation(new BigDecimal("0.00"));
        transactions.setDescription("description");
        transactions.setEndBalance(new BigDecimal("0.00"));
        final List<Transactions> reportedTransactions = Arrays.asList(transactions);
        when(mockReportTransactionsRepository.saveAll(Arrays.asList(new ReportTransactions()))).thenReturn(Collections.emptyList());

        // Run the test
        transactionProcessingServiceUnderTest.writeToReports(reportedTransactions, "reason");

        // Verify the results
        verify(mockReportTransactionsRepository).saveAll(Arrays.asList(new ReportTransactions()));
    }

    @Test
    public void testWriteToTransactions() {
        // Setup
        final Transactions transactions1 = new Transactions();
        transactions1.setReference(0L);
        transactions1.setAccountNumber("accountNumber");
        transactions1.setStartBalance(new BigDecimal("0.00"));
        transactions1.setMutation(new BigDecimal("0.00"));
        transactions1.setDescription("description");
        transactions1.setEndBalance(new BigDecimal("0.00"));
        final List<Transactions> transactions = Arrays.asList(transactions1);

        // Configure TransactionsRepository.saveAll(...).
        final Transactions transactions2 = new Transactions();
        transactions2.setReference(0L);
        transactions2.setAccountNumber("accountNumber");
        transactions2.setStartBalance(new BigDecimal("0.00"));
        transactions2.setMutation(new BigDecimal("0.00"));
        transactions2.setDescription("description");
        transactions2.setEndBalance(new BigDecimal("0.00"));
        final List<Transactions> transactionsList = Arrays.asList(transactions2);
        when(mockTransactionsRepository.saveAll(Arrays.asList(new Transactions()))).thenReturn(transactionsList);

        // Run the test
        transactionProcessingServiceUnderTest.writeToTransactions(transactions);

        // Verify the results
        verify(mockTransactionsRepository).saveAll(Arrays.asList(new Transactions()));
    }

    @Test
    public void testWriteToTransactions_TransactionsRepositoryReturnsNoItems() {
        // Setup
        final Transactions transactions1 = new Transactions();
        transactions1.setReference(0L);
        transactions1.setAccountNumber("accountNumber");
        transactions1.setStartBalance(new BigDecimal("0.00"));
        transactions1.setMutation(new BigDecimal("0.00"));
        transactions1.setDescription("description");
        transactions1.setEndBalance(new BigDecimal("0.00"));
        final List<Transactions> transactions = Arrays.asList(transactions1);
        when(mockTransactionsRepository.saveAll(Arrays.asList(new Transactions()))).thenReturn(Collections.emptyList());

        // Run the test
        transactionProcessingServiceUnderTest.writeToTransactions(transactions);

        // Verify the results
        verify(mockTransactionsRepository).saveAll(Arrays.asList(new Transactions()));
    }

    @Test
    public void testValidateMutation() {
        // Setup
        final Transactions transactions1 = new Transactions();
        transactions1.setReference(0L);
        transactions1.setAccountNumber("accountNumber");
        transactions1.setStartBalance(new BigDecimal("0.00"));
        transactions1.setMutation(new BigDecimal("0.00"));
        transactions1.setDescription("description");
        transactions1.setEndBalance(new BigDecimal("0.00"));
        final List<Transactions> transactions = Arrays.asList(transactions1);
        final Transactions transactions2 = new Transactions();
        transactions2.setReference(0L);
        transactions2.setAccountNumber("accountNumber");
        transactions2.setStartBalance(new BigDecimal("0.00"));
        transactions2.setMutation(new BigDecimal("0.00"));
        transactions2.setDescription("description");
        transactions2.setEndBalance(new BigDecimal("0.00"));
        final List<Transactions> expectedResult = Arrays.asList(transactions2);

        // Configure ReportTransactionsRepository.saveAll(...).
        final ReportTransactions reportTransactions1 = new ReportTransactions();
        reportTransactions1.setTransactionId(0L);
        reportTransactions1.setCreatedDate(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        reportTransactions1.setDescription("description");
        reportTransactions1.setReason("reason");
        final List<ReportTransactions> reportTransactions = Arrays.asList(reportTransactions1);
        when(mockReportTransactionsRepository.saveAll(Arrays.asList(new ReportTransactions()))).thenReturn(reportTransactions);

        // Run the test
        final List<Transactions> result = transactionProcessingServiceUnderTest.validateMutation(transactions);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockReportTransactionsRepository).saveAll(Arrays.asList(new ReportTransactions()));
    }

    @Test
    public void testValidateMutation_ReportTransactionsRepositoryReturnsNoItems() {
        // Setup
        final Transactions transactions1 = new Transactions();
        transactions1.setReference(0L);
        transactions1.setAccountNumber("accountNumber");
        transactions1.setStartBalance(new BigDecimal("0.00"));
        transactions1.setMutation(new BigDecimal("0.00"));
        transactions1.setDescription("description");
        transactions1.setEndBalance(new BigDecimal("0.00"));
        final List<Transactions> transactions = Arrays.asList(transactions1);
        final Transactions transactions2 = new Transactions();
        transactions2.setReference(0L);
        transactions2.setAccountNumber("accountNumber");
        transactions2.setStartBalance(new BigDecimal("0.00"));
        transactions2.setMutation(new BigDecimal("0.00"));
        transactions2.setDescription("description");
        transactions2.setEndBalance(new BigDecimal("0.00"));
        final List<Transactions> expectedResult = Arrays.asList(transactions2);
        when(mockReportTransactionsRepository.saveAll(Arrays.asList(new ReportTransactions()))).thenReturn(Collections.emptyList());

        // Run the test
        final List<Transactions> result = transactionProcessingServiceUnderTest.validateMutation(transactions);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
        verify(mockReportTransactionsRepository).saveAll(Arrays.asList(new ReportTransactions()));
    }

    @Test
    public void testCalculate() {
        assertThat(transactionProcessingServiceUnderTest.calculate(new BigDecimal("0.00"), new BigDecimal("0.00"), new BigDecimal("0.00"))).isTrue();
    }
}
