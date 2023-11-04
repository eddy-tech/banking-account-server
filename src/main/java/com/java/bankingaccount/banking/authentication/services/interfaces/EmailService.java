package com.java.bankingaccount.banking.authentication.services.interfaces;

public interface EmailService {
    void sendSimpleMailMessage(String firstName, String lastName, String to, String token);
}
