package com.es.reportverse.service;

import com.es.reportverse.exception.ApiRequestException;
import com.es.reportverse.model.AppUser;
import com.es.reportverse.model.Publication;
import com.es.reportverse.model.media.MonthStatisticsDataPDF;
import com.es.reportverse.repository.AppUserRepository;
import com.es.reportverse.repository.MonthStatisticsDataPDFRepository;
import com.es.reportverse.repository.PublicationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MonthStatisticsDataServiceImpl implements MonthStatisticsDataService {

    private MonthStatisticsDataPDFRepository monthStatisticsDataPDFRepository;

    private PublicationRepository publicationRepository;

    private AppUserRepository appUserRepository;

    private final String BRAZIL_ZONE_ID = "UTC-03:00";

    private final int RELEASE_STATISTICS_DATE = 25;

    private final String CURRENT_MONTH_DATA_IS_NOT_READY = "Os dados do mês atual ainda não foram coletados " +
            "a ponto de realizar uma comparação estatística. Por favor, aguarde até pelo menos o 25º dia para novas análises.";

    @Override
    public MonthStatisticsDataPDF getPDF() {
        return getPDF(YearMonth.now(ZoneId.of(BRAZIL_ZONE_ID)));
    }

    private MonthStatisticsDataPDF getPDF(YearMonth yearMonth) {
        if (yearMonth.equals(YearMonth.now()) &&
                LocalDate.now(ZoneId.of(BRAZIL_ZONE_ID)).getDayOfMonth() < RELEASE_STATISTICS_DATE) {
            throw new ApiRequestException(CURRENT_MONTH_DATA_IS_NOT_READY);
        }

        return monthStatisticsDataPDFRepository.findByYearMonth(yearMonth)
                .orElse(saveMonthStatisticsDataPDF(this.createPDF(yearMonth)));
    }

    @Override
    public MonthStatisticsDataPDF saveMonthStatisticsDataPDF(MonthStatisticsDataPDF monthStatisticsDataPDF) {
        return this.monthStatisticsDataPDFRepository.save(monthStatisticsDataPDF);
    }

    @Override
    public MonthStatisticsDataPDF createPDF(YearMonth yearMonth) {
        List<Publication> monthPublications = publicationRepository.findAllByYearMonth(yearMonth);
        List<AppUser> newUsers = appUserRepository.findAllByYearMonth(yearMonth);
        List<String[]> publicationCoords = new ArrayList<>();
        Publication[] mainTopics = new Publication[3];

        monthPublications.forEach(
                publication -> publicationCoords.add(
                        new String[]{publication.getLatitude(), publication.getLongitude()}
                )
        );

        return null;
    }

    //  2.2. criar arquivo e retornar array de bytes do conteúdo
}
