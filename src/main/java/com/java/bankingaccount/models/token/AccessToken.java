package com.java.bankingaccount.models.token;

import com.java.bankingaccount.enums.TokenType;
import com.java.bankingaccount.models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
@SuperBuilder
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
