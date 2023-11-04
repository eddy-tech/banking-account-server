package com.java.bankingaccount.banking.user.roots;

public interface UserEndPoint {
    String USER_ENDPOINT = "/users";
    String USER_ID_ENDPOINT = "/{userId}";
    String USER_VALIDATE_ENDPOINT = "/validate/{userId}";
    String USER_INVALIDATE_ENDPOINT = "/invalidate/{userId}";
    String USER_CHANGE_PASSWORD_ENDPOINT = "/change-password";
    String LOGOUT_ENDPOINT = "/logout";


}
