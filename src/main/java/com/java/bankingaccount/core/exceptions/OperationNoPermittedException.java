package com.java.bankingaccount.core.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OperationNoPermittedException extends RuntimeException{
    private final String errorMessage;
    private final String operationId;
    private final String source;
    private final String dependency;
}
