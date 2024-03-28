package ru.jenningc.webcurrency.model.currency;

import jakarta.persistence.*;
import lombok.*;
import ru.jenningc.webcurrency.model.account.CurrencyType;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "currency_rates")
public class CurrencyRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency", nullable = false)
    private CurrencyType currency;

    @Column(name = "date")
    private Date date;

    @Column(name = "value")
    private double value;

}
