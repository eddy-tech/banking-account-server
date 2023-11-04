package com.java.bankingaccount.banking.authentication.repository;

import com.java.bankingaccount.banking.core.token.AccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AccessTokenRepository extends JpaRepository<AccessToken, Integer> {
    @Query(
            "select t from AccessToken t " +
                    "inner join User u on t.user.id = u.id " +
                    "where u.id = ?1 and (t.expired = false or t.revoked = false)"
    )
    List<AccessToken>findAllValidAccessTokenByUser(Integer userId);
    Optional<AccessToken> findByAccessToken(String accessToken);
    AccessToken findAccessTokenByAccessToken(String token);

}