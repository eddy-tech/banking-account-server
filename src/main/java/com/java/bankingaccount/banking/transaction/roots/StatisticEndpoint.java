package com.java.bankingaccount.banking.transaction.roots;

public interface StatisticEndpoint {
    String STATISTIC_ENDPOINT = "/statistics";
    String SUM_BY_DATE_ENDPOINT = "/sum-by-date/{userId}";
    String ACCOUNT_BALANCE_ENDPOINT = "/account-balance/{userId}";
    String HIGHEST_TRANSFER_ENDPOINT = "/highest-transfer/{userId}";
    String HIGHEST_DEPOSIT_ENDPOINT = "/highest-deposit/{userId}";
}
