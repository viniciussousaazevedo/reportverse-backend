package com.es.reportverse.repository;

import com.es.reportverse.model.media.MonthStatisticsDataPDF;
import org.springframework.stereotype.Repository;

import java.time.YearMonth;
import java.util.Date;
import java.util.Optional;

@Repository
public interface MonthStatisticsDataPDFRepository extends GenericMediaRepository<MonthStatisticsDataPDF> {

    Optional<MonthStatisticsDataPDF> findByYearMonth(YearMonth yearMonth);

}
