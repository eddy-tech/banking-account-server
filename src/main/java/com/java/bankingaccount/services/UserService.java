package com.java.bankingaccount.services;

import com.java.bankingaccount.auth.ChangePasswordRequest;
import com.java.bankingaccount.dto.UserDto;

import java.security.Principal;

public interface UserService extends AbstractService<UserDto> {
    Integer validateAccount(Integer id);
    Integer invalidateAccount(Integer id);
    void changePassword(ChangePasswordRequest request, Principal connectedUser);

}
