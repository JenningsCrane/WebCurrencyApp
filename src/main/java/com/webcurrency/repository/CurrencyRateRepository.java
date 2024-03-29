package com.webcurrency.repository;

import com.webcurrency.model.account.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {
    boolean existsByDateBetween(Date date1, Date date2);

    List<CurrencyRate> findByDateBetween(Date startDate, Date endDate);
}
