package com.java.bankingaccount.services;

import com.java.bankingaccount.dto.UserDto;

public interface UserService extends AbstractService<UserDto> {
    Integer validateAccount(Integer id);
    Integer invalidateAccount(Integer id);

}
