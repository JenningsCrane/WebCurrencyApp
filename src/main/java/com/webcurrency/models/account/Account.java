package com.webcurrency.models.account;

import com.webcurrency.models.currency.CurrencyType;
import com.webcurrency.models.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.*;

import java.math.BigDecimal;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts", indexes = {
        @Index(name = "idx_user", columnList = "user_id"),
        @Index(name = "idx_currency", columnList = "currency")
})
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    private CurrencyType currencyType;

    @Column(name = "balance", nullable = false)
    @DecimalMin(value = "0.0000", inclusive = true, message = "Balance must be greater than or equal to 0")
    private BigDecimal balance;
}
