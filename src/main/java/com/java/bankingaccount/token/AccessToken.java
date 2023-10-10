package com.java.bankingaccount.token;

import com.java.bankingaccount.enums.TokenType;
import com.java.bankingaccount.models.AbstractEntity;
import com.java.bankingaccount.models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class AccessToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    private Integer tokenId;
    private String accessToken;
    @Enumerated(EnumType.ORDINAL)
    private TokenType tokenType;
    private boolean expired;
    private boolean revoked;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
