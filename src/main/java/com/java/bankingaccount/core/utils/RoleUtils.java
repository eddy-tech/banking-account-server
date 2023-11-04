package com.java.bankingaccount.core.utils;

public interface RoleUtils {
    String ADMIN = "hasRole('ADMIN')";
    String ADMIN_USER = "hasRole('ADMIN') || hasRole('USER')";
    String USER = "hasRole('USER')";
}
