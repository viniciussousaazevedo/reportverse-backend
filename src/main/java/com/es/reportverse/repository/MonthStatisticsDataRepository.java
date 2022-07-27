package com.es.reportverse.repository;

import com.es.reportverse.model.media.MonthStatisticsData;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MonthStatisticsDataRepository extends GenericMediaRepository<MonthStatisticsData> {

    Optional<MonthStatisticsData> findByYearAndMonth(int year, int month);

}
