<<<<<<<< HEAD:src/main/java/ru/jenningc/webcurrency/model/account/Account.java
package ru.jenningc.webcurrency.model.account;
========
package com.webcurrency.model;
>>>>>>>> develop/dariogla:src/main/java/com/webcurrency/model/Account.java


import jakarta.persistence.*;
import lombok.*;
import ru.jenningc.webcurrency.model.user.User;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    private CurrencyType currency;

    @Column(name = "balance", nullable = false)
    private double balance;
}
