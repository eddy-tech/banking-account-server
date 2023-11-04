package com.java.bankingaccount.banking.user.service;

import java.util.List;

public interface AbstractService <T>{
    T save(T dto);
    List<T> getAll();
    T getById(Integer id);
    void delete(Integer id);
}
