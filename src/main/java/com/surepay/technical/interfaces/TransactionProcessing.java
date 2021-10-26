package com.surepay.technical.interfaces;

import com.surepay.technical.entities.Transactions;
import com.surepay.technical.exceptions.DuplicateTransactionException;

import java.util.List;

public interface TransactionProcessing {
    public void processCsv(String filePath);
    public void processJson(String filePath);
    public List<Transactions> checkDuplicates(List<Transactions> transactions) throws DuplicateTransactionException;
    public List<Transactions> validateMutation(List<Transactions> transactions);
}
