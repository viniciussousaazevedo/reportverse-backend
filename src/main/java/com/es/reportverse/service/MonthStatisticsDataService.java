package com.es.reportverse.service;


import com.es.reportverse.model.media.MonthStatisticsDataPDF;

import java.time.YearMonth;

public interface MonthStatisticsDataService {

    MonthStatisticsDataPDF getPDF();

    MonthStatisticsDataPDF saveMonthStatisticsDataPDF(MonthStatisticsDataPDF monthStatisticsDataPDF);

    MonthStatisticsDataPDF createPDF(YearMonth yearMonth);
}
