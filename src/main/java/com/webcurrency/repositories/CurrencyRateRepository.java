package com.webcurrency.repositories;

import com.webcurrency.models.account.CurrencyRate;
import com.webcurrency.models.account.CurrencyType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {
    boolean existsByDateBetweenAndCurrency(Date date1, Date date2, CurrencyType type);

    List<CurrencyRate> findByDateBetweenAndCurrency(Date startDate, Date endDate, CurrencyType type);
}
