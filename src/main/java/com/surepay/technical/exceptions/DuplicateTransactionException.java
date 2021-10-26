package com.surepay.technical.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DuplicateTransactionException extends Exception{
    public DuplicateTransactionException(String message){
        log.error(message);
    }
}
