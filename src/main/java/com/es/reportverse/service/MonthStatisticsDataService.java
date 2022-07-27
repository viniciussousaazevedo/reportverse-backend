package com.es.reportverse.service;


import com.es.reportverse.model.media.MonthStatisticsData;

public interface MonthStatisticsDataService {

    String getPDF() throws Exception;

    MonthStatisticsData saveMonthStatisticsDataPDF(MonthStatisticsData monthStatisticsData);

    MonthStatisticsData createMonthStatisticsData(int year, int month) throws Exception;
}
