package com.es.reportverse.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.es.reportverse.enums.UserRole;
import com.es.reportverse.exception.ApiRequestException;
import com.es.reportverse.model.AppUser;
import com.es.reportverse.model.AppUserComment;
import com.es.reportverse.model.Publication;
import com.es.reportverse.model.appUserReaction.AppUserLike;
import com.es.reportverse.model.appUserReaction.AppUserReport;
import com.es.reportverse.model.media.MonthStatisticsData;
import com.es.reportverse.repository.MonthStatisticsDataRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {MonthStatisticsDataServiceImpl.class})
@ExtendWith(SpringExtension.class)
class MonthStatisticsDataServiceImplTest {
    @MockBean
    private AppUserService appUserService;

    @MockBean
    private MediaService mediaService;

    @MockBean
    private MonthStatisticsDataRepository monthStatisticsDataRepository;

    @Autowired
    private MonthStatisticsDataServiceImpl monthStatisticsDataServiceImpl;

    @MockBean
    private PublicationService publicationService;

    /**
     * Method under test: {@link MonthStatisticsDataServiceImpl#getPDF()}
     */
    @Test
    void testGetPDF() throws Exception {
        assertThrows(ApiRequestException.class, () -> monthStatisticsDataServiceImpl.getPDF());
    }

    /**
     * Method under test: {@link MonthStatisticsDataServiceImpl#saveMonthStatisticsDataPDF(MonthStatisticsData)}
     */
    @Test
    void testSaveMonthStatisticsDataPDF() {
        MonthStatisticsData monthStatisticsData = new MonthStatisticsData();
        monthStatisticsData.setCode("Code");
        monthStatisticsData.setId(123L);
        monthStatisticsData.setMonth(1);
        monthStatisticsData.setYear(1);
        when(monthStatisticsDataRepository.save((MonthStatisticsData) any())).thenReturn(monthStatisticsData);

        MonthStatisticsData monthStatisticsData1 = new MonthStatisticsData();
        monthStatisticsData1.setCode("Code");
        monthStatisticsData1.setId(123L);
        monthStatisticsData1.setMonth(1);
        monthStatisticsData1.setYear(1);
        assertSame(monthStatisticsData, monthStatisticsDataServiceImpl.saveMonthStatisticsDataPDF(monthStatisticsData1));
        verify(monthStatisticsDataRepository).save((MonthStatisticsData) any());
    }

    /**
     * Method under test: {@link MonthStatisticsDataServiceImpl#saveMonthStatisticsDataPDF(MonthStatisticsData)}
     */
    @Test
    void testSaveMonthStatisticsDataPDF2() {
        when(monthStatisticsDataRepository.save((MonthStatisticsData) any()))
                .thenThrow(new ApiRequestException("An error occurred"));

        MonthStatisticsData monthStatisticsData = new MonthStatisticsData();
        monthStatisticsData.setCode("Code");
        monthStatisticsData.setId(123L);
        monthStatisticsData.setMonth(1);
        monthStatisticsData.setYear(1);
        assertThrows(ApiRequestException.class,
                () -> monthStatisticsDataServiceImpl.saveMonthStatisticsDataPDF(monthStatisticsData));
        verify(monthStatisticsDataRepository).save((MonthStatisticsData) any());
    }

    /**
     * Method under test: {@link MonthStatisticsDataServiceImpl#createMonthStatisticsData(int, int)}
     */
    @Test
    void testCreateMonthStatisticsData() throws Exception {
        when(publicationService.getAllByIsResolvedYearAndMonth(anyInt(), anyInt())).thenReturn(new ArrayList<>());
        when(publicationService.getAllByYearAndMonthOrderedByLikes(anyInt(), anyInt())).thenReturn(new ArrayList<>());
        when(appUserService.getUsersByYearAndMonth(anyInt(), anyInt())).thenReturn(new ArrayList<>());
        MonthStatisticsData monthStatisticsData =  monthStatisticsDataServiceImpl.createMonthStatisticsData(2022, 0);
    }

    /**
     * Method under test: {@link MonthStatisticsDataServiceImpl#createMonthStatisticsData(int, int)}
     */
    @Test
    void testCreateMonthStatisticsData2() throws Exception {
        when(publicationService.getAllByIsResolvedYearAndMonth(anyInt(), anyInt())).thenReturn(new ArrayList<>());
        when(publicationService.getAllByYearAndMonthOrderedByLikes(anyInt(), anyInt())).thenReturn(new ArrayList<>());
        when(appUserService.getUsersByYearAndMonth(anyInt(), anyInt()))
                .thenThrow(new ApiRequestException("An error occurred"));
        assertThrows(ApiRequestException.class, () -> monthStatisticsDataServiceImpl.createMonthStatisticsData(1, 1));
        verify(publicationService).getAllByIsResolvedYearAndMonth(anyInt(), anyInt());
        verify(publicationService).getAllByYearAndMonthOrderedByLikes(anyInt(), anyInt());
        verify(appUserService).getUsersByYearAndMonth(anyInt(), anyInt());
    }

    /**
     * Method under test: {@link MonthStatisticsDataServiceImpl#createMonthStatisticsData(int, int)}
     */
    @Test
    void testCreateMonthStatisticsData3() throws Exception {

        Publication publication = new Publication();
        publication.setAuthorId(123L);
        publication.setComments(new ArrayList<>());
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        publication.setCreationDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        publication.setDescription("The characteristics of someone or something");
        publication.setId(123L);
        publication.setIsAuthorAnonymous(true);
        publication.setIsAvailable(true);
        publication.setIsResolved(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        publication.setIsResolvedDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        publication.setLatitude("0.00");
        publication.setLikes(new ArrayList<>());
        publication.setLocationDescription("0.00");
        publication.setLongitude("0.00");
        publication.setNeedsReview(true);
        publication.setReports(new ArrayList<>());

        ArrayList<Publication> publicationList = new ArrayList<>();
        publicationList.add(publication);
        when(publicationService.getAllByIsResolvedYearAndMonth(anyInt(), anyInt())).thenReturn(publicationList);
        when(publicationService.getAllByYearAndMonthOrderedByLikes(anyInt(), anyInt())).thenReturn(new ArrayList<>());
        when(appUserService.getUsersByYearAndMonth(anyInt(), anyInt())).thenReturn(new ArrayList<>());
        MonthStatisticsData monthStatisticsData = monthStatisticsDataServiceImpl.createMonthStatisticsData(1, 1);
    }

    /**
     * Method under test: {@link MonthStatisticsDataServiceImpl#createMonthStatisticsData(int, int)}
     */
    @Test
    void testCreateMonthStatisticsData4() throws Exception {
        Publication publication = new Publication();
        publication.setAuthorId(123L);
        publication.setComments(new ArrayList<>());
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        publication.setCreationDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        publication.setDescription("The characteristics of someone or something");
        publication.setId(123L);
        publication.setIsAuthorAnonymous(true);
        publication.setIsAvailable(true);
        publication.setIsResolved(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        publication.setIsResolvedDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        publication.setLatitude("0.00");
        publication.setLikes(new ArrayList<>());
        publication.setLocationDescription("0.00");
        publication.setLongitude("0.00");
        publication.setNeedsReview(true);
        publication.setReports(new ArrayList<>());

        ArrayList<Publication> publicationList = new ArrayList<>();
        publicationList.add(publication);
        when(publicationService.getAllByIsResolvedYearAndMonth(anyInt(), anyInt())).thenReturn(new ArrayList<>());
        when(publicationService.getAllByYearAndMonthOrderedByLikes(anyInt(), anyInt())).thenReturn(publicationList);
        when(appUserService.getUsersByYearAndMonth(anyInt(), anyInt())).thenReturn(new ArrayList<>());
        MonthStatisticsData monthStatisticsData = monthStatisticsDataServiceImpl.createMonthStatisticsData(1, 1);
    }

    /**
     * Method under test: {@link MonthStatisticsDataServiceImpl#createMonthStatisticsData(int, int)}
     */
    @Test
    void testCreateMonthStatisticsData5() throws Exception {

        Publication publication = new Publication();
        publication.setAuthorId(123L);
        publication.setComments(new ArrayList<>());
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        publication.setCreationDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        publication.setDescription("The characteristics of someone or something");
        publication.setId(123L);
        publication.setIsAuthorAnonymous(true);
        publication.setIsAvailable(true);
        publication.setIsResolved(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        publication.setIsResolvedDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        publication.setLatitude("0.00");
        publication.setLikes(new ArrayList<>());
        publication.setLocationDescription("0.00");
        publication.setLongitude("0.00");
        publication.setNeedsReview(true);
        publication.setReports(new ArrayList<>());

        Publication publication1 = new Publication();
        publication1.setAuthorId(123L);
        publication1.setComments(new ArrayList<>());
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        publication1.setCreationDate(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        publication1.setDescription("The characteristics of someone or something");
        publication1.setId(123L);
        publication1.setIsAuthorAnonymous(true);
        publication1.setIsAvailable(true);
        publication1.setIsResolved(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        publication1.setIsResolvedDate(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        publication1.setLatitude("0.00");
        publication1.setLikes(new ArrayList<>());
        publication1.setLocationDescription("0.00");
        publication1.setLongitude("0.00");
        publication1.setNeedsReview(true);
        publication1.setReports(new ArrayList<>());

        ArrayList<Publication> publicationList = new ArrayList<>();
        publicationList.add(publication1);
        publicationList.add(publication);
        when(publicationService.getAllByIsResolvedYearAndMonth(anyInt(), anyInt())).thenReturn(new ArrayList<>());
        when(publicationService.getAllByYearAndMonthOrderedByLikes(anyInt(), anyInt())).thenReturn(publicationList);
        when(appUserService.getUsersByYearAndMonth(anyInt(), anyInt())).thenReturn(new ArrayList<>());
        MonthStatisticsData monthStatisticsData = monthStatisticsDataServiceImpl.createMonthStatisticsData(1, 1);
    }

    /**
     * Method under test: {@link MonthStatisticsDataServiceImpl#createMonthStatisticsData(int, int)}
     */
    @Test
    void testCreateMonthStatisticsData6() throws Exception {
        when(publicationService.getAllByIsResolvedYearAndMonth(anyInt(), anyInt())).thenReturn(new ArrayList<>());
        when(publicationService.getAllByYearAndMonthOrderedByLikes(anyInt(), anyInt())).thenReturn(new ArrayList<>());
        when(appUserService.getUsersByYearAndMonth(anyInt(), anyInt())).thenReturn(new ArrayList<>());
        MonthStatisticsData monthStatisticsData = monthStatisticsDataServiceImpl.createMonthStatisticsData(1, -1);
    }

    /**
     * Method under test: {@link MonthStatisticsDataServiceImpl#createMonthStatisticsData(int, int)}
     */
    @Test
    void testCreateMonthStatisticsData7() throws Exception {

        Publication publication = new Publication();
        publication.setAuthorId(123L);
        publication.setComments(new ArrayList<>());
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        publication.setCreationDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        publication.setDescription("The characteristics of someone or something");
        publication.setId(123L);
        publication.setIsAuthorAnonymous(true);
        publication.setIsAvailable(true);
        publication.setIsResolved(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        publication.setIsResolvedDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        publication.setLatitude("0.00");
        publication.setLikes(new ArrayList<>());
        publication.setLocationDescription("0.00");
        publication.setLongitude("0.00");
        publication.setNeedsReview(true);
        publication.setReports(new ArrayList<>());

        Publication publication1 = new Publication();
        publication1.setAuthorId(123L);
        publication1.setComments(new ArrayList<>());
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        publication1.setCreationDate(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        publication1.setDescription("The characteristics of someone or something");
        publication1.setId(123L);
        publication1.setIsAuthorAnonymous(true);
        publication1.setIsAvailable(true);
        publication1.setIsResolved(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        publication1.setIsResolvedDate(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        publication1.setLatitude("0.00");
        publication1.setLikes(new ArrayList<>());
        publication1.setLocationDescription("0.00");
        publication1.setLongitude("0.00");
        publication1.setNeedsReview(true);
        publication1.setReports(new ArrayList<>());

        Publication publication2 = new Publication();
        publication2.setAuthorId(123L);
        publication2.setComments(new ArrayList<>());
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        publication2.setCreationDate(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        publication2.setDescription("The characteristics of someone or something");
        publication2.setId(123L);
        publication2.setIsAuthorAnonymous(true);
        publication2.setIsAvailable(true);
        publication2.setIsResolved(true);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        publication2.setIsResolvedDate(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        publication2.setLatitude("0.00");
        publication2.setLikes(new ArrayList<>());
        publication2.setLocationDescription("0.00");
        publication2.setLongitude("0.00");
        publication2.setNeedsReview(true);
        publication2.setReports(new ArrayList<>());

        ArrayList<Publication> publicationList = new ArrayList<>();
        publicationList.add(publication2);
        publicationList.add(publication1);
        publicationList.add(publication);
        when(publicationService.getAllByIsResolvedYearAndMonth(anyInt(), anyInt())).thenReturn(new ArrayList<>());
        when(publicationService.getAllByYearAndMonthOrderedByLikes(anyInt(), anyInt())).thenReturn(publicationList);
        when(appUserService.getUsersByYearAndMonth(anyInt(), anyInt())).thenReturn(new ArrayList<>());
        monthStatisticsDataServiceImpl.createMonthStatisticsData(1, 1);
    }

    /**
     * Method under test: {@link MonthStatisticsDataServiceImpl#createMonthStatisticsData(int, int)}
     */
    @Test
    void testCreateMonthStatisticsData8() throws Exception {

        Publication publication = mock(Publication.class);
        when(publication.getIsAuthorAnonymous()).thenReturn(false);
        when(publication.getAuthorId()).thenReturn(123L);
        when(publication.getDescription()).thenReturn("The characteristics of someone or something");
        when(publication.getLatitude()).thenReturn("Latitude");
        when(publication.getLongitude()).thenReturn("Longitude");
        when(publication.getLikes()).thenReturn(new ArrayList<>());
        when(publication.getReports()).thenReturn(new ArrayList<>());
        doNothing().when(publication).setId((Long) any());
        doNothing().when(publication).setAuthorId((Long) any());
        doNothing().when(publication).setComments((List<AppUserComment>) any());
        doNothing().when(publication).setCreationDate((Date) any());
        doNothing().when(publication).setDescription((String) any());
        doNothing().when(publication).setIsAuthorAnonymous((Boolean) any());
        doNothing().when(publication).setIsAvailable((Boolean) any());
        doNothing().when(publication).setIsResolved((Boolean) any());
        doNothing().when(publication).setIsResolvedDate((Date) any());
        doNothing().when(publication).setLatitude((String) any());
        doNothing().when(publication).setLikes((List<AppUserLike>) any());
        doNothing().when(publication).setLocationDescription((String) any());
        doNothing().when(publication).setLongitude((String) any());
        doNothing().when(publication).setNeedsReview((Boolean) any());
        doNothing().when(publication).setReports((List<AppUserReport>) any());
        publication.setAuthorId(123L);
        publication.setComments(new ArrayList<>());
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        publication.setCreationDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        publication.setDescription("The characteristics of someone or something");
        publication.setId(123L);
        publication.setIsAuthorAnonymous(true);
        publication.setIsAvailable(true);
        publication.setIsResolved(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        publication.setIsResolvedDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        publication.setLatitude("0.00");
        publication.setLikes(new ArrayList<>());
        publication.setLocationDescription("0.00");
        publication.setLongitude("0.00");
        publication.setNeedsReview(true);
        publication.setReports(new ArrayList<>());

        ArrayList<Publication> publicationList = new ArrayList<>();
        publicationList.add(publication);
        when(publicationService.getAllByIsResolvedYearAndMonth(anyInt(), anyInt())).thenReturn(new ArrayList<>());
        when(publicationService.getAllByYearAndMonthOrderedByLikes(anyInt(), anyInt())).thenReturn(publicationList);

        AppUser appUser = new AppUser();
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        appUser.setCreationDate(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        appUser.setEnabled(true);
        appUser.setId(123L);
        appUser.setLocked(true);
        appUser.setName("Name");
        appUser.setPassword("iloveyou");
        appUser.setRecoveryPasswordToken("ABC123");
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        appUser.setRecoveryPasswordTokenExpiration(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        appUser.setUserRole(UserRole.ADMINISTRADOR);
        appUser.setUsername("janedoe");
        when(appUserService.getUser((Long) any())).thenReturn(appUser);
        when(appUserService.getUsersByYearAndMonth(anyInt(), anyInt())).thenReturn(new ArrayList<>());
        MonthStatisticsData monthStatisticsData = monthStatisticsDataServiceImpl.createMonthStatisticsData(1, 1);
    }
}

