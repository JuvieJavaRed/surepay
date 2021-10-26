package com.surepay.technical.services;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.apache.commons.math3.util.Precision;
import com.surepay.technical.entities.ReportTransactions;
import com.surepay.technical.entities.Transactions;
import com.surepay.technical.exceptions.DuplicateTransactionException;
import com.surepay.technical.interfaces.TransactionProcessing;
import com.surepay.technical.repository.ReportTransactionsRepository;
import com.surepay.technical.repository.TransactionsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
@Slf4j
public class TransactionProcessingService implements TransactionProcessing {

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Autowired
    private ReportTransactionsRepository reportTransactionsRepository;


    public void processCsv(String filePath) {
        try{
            List<Transactions> transactionsList = new ArrayList<>();
            // Read all lines in from CSV file and add to studentList
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = null;
            String headerLine = bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {
                String[] temp = line.split(",");
                Transactions transactions = new Transactions();
                transactions.setReference(Long.parseLong(temp[0]));
                transactions.setAccountNumber(temp[1]);
                transactions.setDescription(temp[2]);
                transactions.setStartBalance(new BigDecimal(temp[3]));
                transactions.setMutation(new BigDecimal(temp[4]));
                transactions.setEndBalance(new BigDecimal(temp[5]));

                transactionsList.add(transactions);
            }
            bufferedReader.close();
            log.info("Final arraylist from csv to be processed is "+transactionsList.toString());
            //eliminate those transactions that already exist
            List<Transactions> nonDuplicateTransactions = checkDuplicates(transactionsList);
            //check for balance....
            List<Transactions> validBalanceTransactions = validateMutation(nonDuplicateTransactions);
            writeToTransactions(validBalanceTransactions);
            log.info("Transactions successfully written to database......");
        }catch(FileNotFoundException | DuplicateTransactionException ex){
            log.error("The file could not be found because:"+ex.getLocalizedMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void processJson(String filePath) {
        Type REVIEW_TYPE = new TypeToken<List<Transactions>>() {
        }.getType();
        try{

            Gson gson = new Gson();
            JsonReader reader = new JsonReader(new FileReader(filePath));
            List<Transactions> receivedTransactions = gson.fromJson(reader, REVIEW_TYPE); // contains the whole reviews list
            log.info(receivedTransactions.toString());
            //eliminate those transactions that already exist
            List<Transactions> nonDuplicateTransactions = checkDuplicates(receivedTransactions);
            //check for balance....
            List<Transactions> validBalanceTransactions = validateMutation(nonDuplicateTransactions);
            writeToTransactions(validBalanceTransactions);
            log.info("Transactions successfully written to database......");
        }catch(FileNotFoundException | DuplicateTransactionException ex){
            log.error("The file could not be found because:"+ex.getLocalizedMessage());
        }
    }


    public List<Transactions> checkDuplicates(List<Transactions> transactions) throws DuplicateTransactionException {
        try{
            List<Transactions> nonValidTransactions = new ArrayList<>();
            List<Transactions> nonDuplicateTransactions = Lists.newArrayList(Sets.newLinkedHashSet(transactions));
            log.info("Array after running through duplicate removal process now "+nonDuplicateTransactions.toString());
            for(Transactions loopTransactions: nonDuplicateTransactions){
                Boolean validateTransaction = transactionsRepository.existsByReference(loopTransactions.getReference());
                if(validateTransaction){
                    //keep a record of non valide transactions
                    nonValidTransactions.add(loopTransactions);
                    log.info("Now removing the record "+loopTransactions.toString());
                    //remove invalid transactions from the arraylist we will write to the database
                    nonDuplicateTransactions.remove(loopTransactions);

                }
            }
            writeToReports(nonValidTransactions, "Transactions already exist");
            log.info("The remaining array list of size "+transactions.size()+ " and has the elements "+transactions.toString());
            return  nonDuplicateTransactions;
        }catch(Exception e){
            throw new DuplicateTransactionException("The duplicate transactions could not be validated "+e.toString());
        }

    }

    public void writeToReports(List<Transactions> reportedTransactions, String reason){
        List<ReportTransactions> reportTransactionsList = new ArrayList<>();
        for(Transactions transactions:reportedTransactions){
            ReportTransactions reportTransactions = new ReportTransactions();
            reportTransactions.setTransactionId(transactions.getReference());
            reportTransactions.setReason(reason);
            reportTransactions.setCreatedDate(new Date());
            reportTransactions.setDescription(transactions.getDescription());
            reportTransactionsList.add(reportTransactions);
        }
        reportTransactionsRepository.saveAll(reportTransactionsList);
    }

    public void writeToTransactions(List<Transactions> transactions){
        transactionsRepository.saveAll(transactions);
    }

    public List<Transactions>  validateMutation(List<Transactions> transactions) {
        List<Transactions> nonValidTransactions = new ArrayList<>();
        for(Transactions nonDuplicateTransactions:transactions){
            boolean isValid = calculate(nonDuplicateTransactions.getMutation(), nonDuplicateTransactions.getStartBalance(), nonDuplicateTransactions.getEndBalance());
            if(isValid == false){
                //add it to nonValidTransactions
                nonValidTransactions.add(nonDuplicateTransactions);

                //transactions.remove(nonDuplicateTransactions);
            }
        }
        //remove the bad transaction from the bunch
        transactions.removeAll(nonValidTransactions);
        log.info("Writing bad transactions to report database "+nonValidTransactions.toString());
        writeToReports(nonValidTransactions, "The final balance does not match");
        log.info("The remaining transactions with valid balances are "+transactions.toString());
        return transactions;
    }

    public boolean calculate(BigDecimal mutation, BigDecimal startBalance, BigDecimal endBalance){
        boolean validBalance = false;
        BigDecimal calculatedEndBalance = startBalance.add(mutation);
        int result = endBalance.compareTo(calculatedEndBalance);
        if(result == 0){
            validBalance = true;
        }
        return validBalance;
    }
}
