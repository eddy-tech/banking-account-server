package com.java.bankingaccount.repositories;

import com.java.bankingaccount.models.User;
import jakarta.transaction.UserTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAllByFirstName(String firstName);

    @Query("from User where firstName = ?1")
    List<User> getAllUsersByFirstName(String firstName);


    @Query("from User u inner join Account a on u.id = a.user.id and a.iban = ?1")
    List<User> findAllByAccountIban(String iban);

    @Query("from User where firstName =: fn")
    List<User> searchByFirstName(@Param("fn") String firstName);

    @Query(value = "select * from _users u inner join account a on u.id = a.user_id and a.iban = :iban", nativeQuery = true)
    List<User> searchByIbanNative(String iban);

}