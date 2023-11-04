package com.java.bankingaccount.banking.transaction.roots;

public interface TransactionEndpoint {
    String TRANSACTION_ENDPOINT = "/transactions";
    String TRANSACTION_ID_ENDPOINT = "/{transactionId}";
    String TRANSACTION_USER_ENDPOINT = "/users/{userId}";
}
