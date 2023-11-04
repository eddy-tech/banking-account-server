package com.java.bankingaccount.banking.core.token;

import com.java.bankingaccount.core.enums.TokenType;
import com.java.bankingaccount.banking.core.models.AbstractEntity;
import com.java.bankingaccount.banking.core.models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
@SuperBuilder
public class AccessToken extends AbstractEntity {
    private String accessToken;
    @Enumerated(EnumType.ORDINAL)
    private TokenType tokenType;
    private boolean expired;
    private boolean revoked;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public AccessToken(User user, String accessToken){
        this.user = user;
        this.accessToken = accessToken;
    }

}
