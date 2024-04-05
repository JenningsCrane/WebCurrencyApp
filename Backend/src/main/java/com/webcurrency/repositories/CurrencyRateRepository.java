package com.webcurrency.repositories;

import com.webcurrency.models.currency.CurrencyRate;
import com.webcurrency.models.currency.CurrencyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {
    boolean existsByDateBetweenAndCurrency(Date date1, Date date2, CurrencyType type);

    List<CurrencyRate> findByDateBetweenAndCurrency(Date startDate, Date endDate, CurrencyType type);

    @Query("SELECT cr FROM CurrencyRate cr WHERE cr.currency = :currency " +
            "AND cr.date = (SELECT MAX(cr2.date) FROM CurrencyRate cr2 WHERE cr2.currency = :currency)")
    List<CurrencyRate> findLatestByCurrency(@Param("currency") CurrencyType currency);
}