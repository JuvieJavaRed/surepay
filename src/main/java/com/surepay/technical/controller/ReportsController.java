package com.surepay.technical.controller;


import com.surepay.technical.entities.ReportTransactions;
import com.surepay.technical.repository.ReportTransactionsRepository;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin(origins = "*")
public class ReportsController {

    @Autowired
    private ReportTransactionsRepository reportTransactionsRepository;

    @GetMapping("/retrieveAllDuplicates")
    @ApiOperation(notes = "Endpoint to retrieve all bad transactions recorded",
            value = "retrieveAllBadTransactions", nickname = "Bad Transactions Report",
            tags = {"Sure Pay Assessment"})
    public ResponseEntity<List<ReportTransactions>> retrieveAllBadTransactions(){
        return new ResponseEntity<List<ReportTransactions>>(reportTransactionsRepository.findAll(), HttpStatus.OK);
    }
}
