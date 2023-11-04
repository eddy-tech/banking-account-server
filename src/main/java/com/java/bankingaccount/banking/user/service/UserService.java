package com.java.bankingaccount.banking.user.service;

import com.java.bankingaccount.banking.authentication.auth.ChangePasswordRequest;
import com.java.bankingaccount.banking.user.dto.UserDto;

import java.security.Principal;

public interface UserService extends AbstractService<UserDto> {
    Integer validateAccount(Integer id);
    Integer invalidateAccount(Integer id);
    void changePassword(ChangePasswordRequest request, Principal connectedUser);

}
