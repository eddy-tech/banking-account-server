package com.java.bankingaccount.utils;

import com.java.bankingaccount.enums.Roles;

public interface AuthenticationJWT {
      String AUTHORIZATION = "Authorization";
      String BEARER = "Bearer ";
      Integer SUB_STRING = 7;
      String ROLE_USER = "ROLE_" + Roles.USER.name();
      String ROLE_ADMIN = "ADMIN_" + Roles.ADMIN.name();

}
