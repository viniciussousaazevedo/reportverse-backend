package com.es.reportverse.service;

import com.es.reportverse.exception.ApiRequestException;
import com.es.reportverse.model.Publication;
import com.es.reportverse.model.media.MonthStatisticsData;
import com.es.reportverse.repository.MonthStatisticsDataRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;

@Service
@AllArgsConstructor
public class MonthStatisticsDataServiceImpl implements MonthStatisticsDataService {

    private MonthStatisticsDataRepository monthStatisticsDataRepository;

    private PublicationService publicationService;

    private AppUserService appUserService;

    private MediaService mediaService;

    private static final int RELEASE_STATISTICS_DATE = 25;

    private static final String CURRENT_MONTH_DATA_IS_NOT_READY = "Os dados do mês atual ainda não foram coletados " +
            "a ponto de realizar uma comparação estatística. " +
            "Por favor, aguarde até pelo menos o 25º dia para novas análises.";

    @Override
    public String getPDF() throws Exception {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);

        return getPDF(year, month);
    }

    private String getPDF(int year, int month) throws Exception {
        Calendar currentDate = Calendar.getInstance();

        if (year == currentDate.get(Calendar.YEAR) &&
                month == currentDate.get(Calendar.MONTH) &&
                currentDate.get(Calendar.DAY_OF_MONTH) < RELEASE_STATISTICS_DATE) {
            throw new ApiRequestException(CURRENT_MONTH_DATA_IS_NOT_READY);
        }

        Optional<MonthStatisticsData> statisticsDataOp = monthStatisticsDataRepository.findByYearAndMonth(year, month);
        MonthStatisticsData monthStatisticsData =
                statisticsDataOp.isEmpty() ?
                        saveMonthStatisticsDataPDF(this.createMonthStatisticsData(year, month)) :
                        statisticsDataOp.get();


        return monthStatisticsData.getCode();
    }

    @Override
    public MonthStatisticsData saveMonthStatisticsDataPDF(MonthStatisticsData monthStatisticsData) {
        return this.monthStatisticsDataRepository.save(monthStatisticsData);
    }

    @Override
    public MonthStatisticsData createMonthStatisticsData(int year, int month) throws Exception {
        String pdfContent = getPDFContent(year, month);
        String pdfCode = createPDF(pdfContent);
        return new MonthStatisticsData(pdfCode, year, month);
    }

    private String createPDF(String content) throws Exception {
        String encodedMedia = null;
        String fileName = "file.pdf";
        Path path = Paths.get(fileName);
        try {
            Document document = new Document();

            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            document.add(new Paragraph(content, font));
            document.close();
            encodedMedia = this.mediaService.encodeMedia(Files.readAllBytes(path));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Files.delete(path);
        }

        return encodedMedia;
    }

    private Calendar getLastMonthCalendar(int year, int month) {
        if (month-- == -1) {
            year--;
            month = 11;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        return calendar;
    }

    private String getPDFContent(int year, int month) {
        List<Publication> monthPublications = getMonthPublications(year, month);
        int monthPublicationsNum = monthPublications.size();
        int monthResolvedPublications = getMonthResolvedPublicationsNum(year, month);
        List<String[]> publicationCoords = getPublicationCoords(monthPublications);
        Publication[] mainPublications = getMainPublications(monthPublications);
        int newUsers = appUserService.getUsersByYearAndMonth(year, month+1).size();

        Calendar lastMonthCalendar = getLastMonthCalendar(year, month);
        int lastMonth = lastMonthCalendar.get(Calendar.MONTH);
        int yearOfLastMonth = lastMonthCalendar.get(Calendar.YEAR);

        List<Publication> lastMonthPublications = getMonthPublications(yearOfLastMonth, lastMonth);
        int lastMonthPublicationsNum = lastMonthPublications.size();
        int lastMonthResolvedPublicationsNum = getMonthResolvedPublicationsNum(yearOfLastMonth, lastMonth);
        int lastMonthNewUsers = appUserService.getUsersByYearAndMonth(yearOfLastMonth, lastMonth+1).size();

        return "Segue dados coletados na plataforma referentes ao mês " + (month+1) + " de " + year +":\n\n" +
                "- Número de novas publicações: " + monthPublicationsNum + " " + getStringComparation(monthPublicationsNum, lastMonthPublicationsNum) + "\n" +
                "- Número de publicações resolvidas no mês: " + monthResolvedPublications + " " + getStringComparation(monthResolvedPublications, lastMonthResolvedPublicationsNum) + "\n" +
                "- Número de novos usuários no mês: " + newUsers + " " + getStringComparation(newUsers, lastMonthNewUsers) + "\n\n" +
                "- Principais publicações do mês: \n" + printMainPublications(mainPublications) + "\n\n" +
                "- Coordenadas das publicações no mês: \n" + printPublicationCoords(publicationCoords);
    }

    private String printPublicationCoords(List<String[]> publicationCoords) {
        String text = "";

        for (int i = 0; i < publicationCoords.size(); i++) {
            text += "\t" + (i+1) + "- " + "Latitude: " + publicationCoords.get(i)[0] + ", Latitude: " + publicationCoords.get(i)[1];
        }

        return text;
    }

    private String printMainPublications(Publication[] mainPublications) {
        String text = "";
        for (int i = 0; i < mainPublications.length; i++) {
            Publication p = mainPublications[i];
            text += "\t" + (i+1) + "- \"" + p.getDescription() + "\". Com " + p.getLikes().size() + " like(s), " +
                    p.getReports().size() + " denúncia(s) e autor " + (p.getIsAuthorAnonymous() ? "anônimo." : "de nome " + appUserService.getUser(p.getAuthorId()).getName());
        }

        return text;
    }

    private String getStringComparation(int num1, int num2) {
        DecimalFormat df = new DecimalFormat("0.00");
        double qttTimesBigger;

        if (num1 == 0 && num2 == 0) {
            qttTimesBigger = 1;
        } else if (num2 == 0) {
            return "(Aumento com relação ao mês anterior, que possuía zero)";
        } else {
            qttTimesBigger = ((double) num1) / ((double) num2);
        }

        String text;

        if (qttTimesBigger < 1) {
            text = "(Decaimento aproximado de " + df.format((1 - qttTimesBigger) * 100) + "% com relação ao mês anterior)";
        } else {
            text = "(Aumento aproximado de " + df.format((qttTimesBigger - 1) * 100) + "% com relação ao mês anterior)";
        }

        return text;
    }

    //// MÉTODOS DE BUSCA DE DADOS PARA COMPARAÇÃO ESTATÍSTICA ////
    private List<Publication> getMonthPublications(int year, int month) {
        return publicationService.getAllByYearAndMonthOrderedByLikes(year, month+1);
    }
    private int getMonthResolvedPublicationsNum(int year, int month) {
        return publicationService.getAllByIsResolvedYearAndMonth(year, month+1).size();
    }
    private List<String[]> getPublicationCoords(List<Publication> publications) {
        List<String[]> publicationCoords = new ArrayList<>();

        publications.forEach(
                publication -> publicationCoords.add(
                        new String[]{publication.getLatitude(), publication.getLongitude()}
                )
        );
        return publicationCoords;
    }
    private Publication[] getMainPublications(List<Publication> monthPublicationsOrderedByLikes) {
        Publication[] mainPublications =
                monthPublicationsOrderedByLikes.size() < 3 ?
                    new Publication[monthPublicationsOrderedByLikes.size()] :
                    new Publication[3];

        Collections.reverse(monthPublicationsOrderedByLikes);
        for (int i = 0; i < mainPublications.length; i++) {
            mainPublications[i] = monthPublicationsOrderedByLikes.get(i);
        }

        return mainPublications;
    }

}
