package com.es.reportverse.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.es.reportverse.enums.UserRole;
import com.es.reportverse.model.AppUser;
import com.es.reportverse.model.AppUserComment;
import com.es.reportverse.model.Publication;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {EmailServiceImpl.class})
@ExtendWith(SpringExtension.class)
class EmailServiceImplTest {
    @MockBean
    private AppUserService appUserService;

    @Autowired
    private EmailServiceImpl emailServiceImpl;

    @MockBean
    private JavaMailSender javaMailSender;

    /**
     * Method under test: {@link EmailServiceImpl#sendPasswordRecoveryByEmail(String, String)}
     */
    @Test
    void testSendPasswordRecoveryByEmail() throws MailException {
        doNothing().when(javaMailSender).send((SimpleMailMessage) any());
        assertEquals("Email foi enviado com sucesso",
                emailServiceImpl.sendPasswordRecoveryByEmail("alice.liddell@example.org", "Recovery Link"));
        verify(javaMailSender).send((SimpleMailMessage) any());
    }

    /**
     * Method under test: {@link EmailServiceImpl#notifyAdminsReportedPublication(Publication)}
     */
    @Test
    void testNotifyAdminsReportedPublication() {
        ArrayList<AppUser> appUserList = new ArrayList<>();
        when(appUserService.findAllByUserRole((UserRole) any())).thenReturn(appUserList);

        Publication publication = new Publication();
        publication.setAuthorId(123L);
        ArrayList<AppUserComment> appUserCommentList = new ArrayList<>();
        publication.setComments(appUserCommentList);
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        publication.setCreationDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        publication.setDescription("The characteristics of someone or something");
        publication.setId(123L);
        publication.setIsAuthorAnonymous(true);
        publication.setIsAvailable(true);
        publication.setIsResolved(true);
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        publication.setIsResolvedDate(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        publication.setLatitude("Latitude");
        publication.setLikes(new ArrayList<>());
        publication.setLocationDescription("Location Description");
        publication.setLongitude("Longitude");
        publication.setNeedsReview(true);
        publication.setReports(new ArrayList<>());
        emailServiceImpl.notifyAdminsReportedPublication(publication);
        verify(appUserService).findAllByUserRole((UserRole) any());
        assertEquals(123L, publication.getAuthorId().longValue());
        assertEquals(appUserCommentList, publication.getReports());
        assertTrue(publication.getNeedsReview());
        assertEquals("Longitude", publication.getLongitude());
        assertEquals("Location Description", publication.getLocationDescription());
        assertEquals(appUserCommentList, publication.getLikes());
        assertEquals("Latitude", publication.getLatitude());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        assertEquals("1969-12-31", simpleDateFormat.format(publication.getIsResolvedDate()));
        assertTrue(publication.getIsResolved());
        assertTrue(publication.getIsAvailable());
        assertTrue(publication.getIsAuthorAnonymous());
        assertEquals(123L, publication.getId().longValue());
        assertEquals("The characteristics of someone or something", publication.getDescription());
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        assertEquals("1969-12-31", simpleDateFormat1.format(publication.getCreationDate()));
        assertEquals(appUserList, publication.getComments());
    }

    /**
     * Method under test: {@link EmailServiceImpl#notifyAdminsReportedPublication(Publication)}
     */
    @Test
    void testNotifyAdminsReportedPublication2() throws MailException {
        doNothing().when(javaMailSender).send((SimpleMailMessage) any());

        AppUser appUser = new AppUser();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        appUser.setCreationDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        appUser.setEnabled(true);
        appUser.setId(123L);
        appUser.setLocked(true);
        appUser.setName("Name");
        appUser.setPassword("iloveyou");
        appUser.setRecoveryPasswordToken("ABC123");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        appUser.setRecoveryPasswordTokenExpiration(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        appUser.setUserRole(UserRole.ADMINISTRADOR);
        appUser.setUsername("janedoe");

        ArrayList<AppUser> appUserList = new ArrayList<>();
        appUserList.add(appUser);
        when(appUserService.findAllByUserRole((UserRole) any())).thenReturn(appUserList);

        Publication publication = new Publication();
        publication.setAuthorId(123L);
        publication.setComments(new ArrayList<>());
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        publication.setCreationDate(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        publication.setDescription("The characteristics of someone or something");
        publication.setId(123L);
        publication.setIsAuthorAnonymous(true);
        publication.setIsAvailable(true);
        publication.setIsResolved(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        publication.setIsResolvedDate(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        publication.setLatitude("Latitude");
        publication.setLikes(new ArrayList<>());
        publication.setLocationDescription("Location Description");
        publication.setLongitude("Longitude");
        publication.setNeedsReview(true);
        publication.setReports(new ArrayList<>());
        emailServiceImpl.notifyAdminsReportedPublication(publication);
        verify(javaMailSender).send((SimpleMailMessage) any());
        verify(appUserService).findAllByUserRole((UserRole) any());
    }

    /**
     * Method under test: {@link EmailServiceImpl#notifyExcludedPublicationAuthor(Publication)}
     */
    @Test
    void testNotifyExcludedPublicationAuthor() throws MailException {
        doNothing().when(javaMailSender).send((SimpleMailMessage) any());

        AppUser appUser = new AppUser();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        appUser.setCreationDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        appUser.setEnabled(true);
        appUser.setId(123L);
        appUser.setLocked(true);
        appUser.setName("Name");
        appUser.setPassword("iloveyou");
        appUser.setRecoveryPasswordToken("ABC123");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        appUser.setRecoveryPasswordTokenExpiration(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        appUser.setUserRole(UserRole.ADMINISTRADOR);
        appUser.setUsername("janedoe");
        when(appUserService.getUser((Long) any())).thenReturn(appUser);

        Publication publication = new Publication();
        publication.setAuthorId(123L);
        publication.setComments(new ArrayList<>());
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        publication.setCreationDate(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        publication.setDescription("The characteristics of someone or something");
        publication.setId(123L);
        publication.setIsAuthorAnonymous(true);
        publication.setIsAvailable(true);
        publication.setIsResolved(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        publication.setIsResolvedDate(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        publication.setLatitude("Latitude");
        publication.setLikes(new ArrayList<>());
        publication.setLocationDescription("Location Description");
        publication.setLongitude("Longitude");
        publication.setNeedsReview(true);
        publication.setReports(new ArrayList<>());
        assertEquals("O autor da publicação foi notificado e a publicação foi excluída da plataforma.",
                emailServiceImpl.notifyExcludedPublicationAuthor(publication));
        verify(javaMailSender).send((SimpleMailMessage) any());
        verify(appUserService).getUser((Long) any());
    }

    /**
     * Method under test: {@link EmailServiceImpl#notifyAvailablePublicationAuthor(Publication)}
     */
    @Test
    void testNotifyAvailablePublicationAuthor() throws MailException {
        doNothing().when(javaMailSender).send((SimpleMailMessage) any());

        AppUser appUser = new AppUser();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        appUser.setCreationDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        appUser.setEnabled(true);
        appUser.setId(123L);
        appUser.setLocked(true);
        appUser.setName("Name");
        appUser.setPassword("iloveyou");
        appUser.setRecoveryPasswordToken("ABC123");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        appUser.setRecoveryPasswordTokenExpiration(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        appUser.setUserRole(UserRole.ADMINISTRADOR);
        appUser.setUsername("janedoe");
        when(appUserService.getUser((Long) any())).thenReturn(appUser);

        Publication publication = new Publication();
        publication.setAuthorId(123L);
        publication.setComments(new ArrayList<>());
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        publication.setCreationDate(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        publication.setDescription("The characteristics of someone or something");
        publication.setId(123L);
        publication.setIsAuthorAnonymous(true);
        publication.setIsAvailable(true);
        publication.setIsResolved(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        publication.setIsResolvedDate(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        publication.setLatitude("Latitude");
        publication.setLikes(new ArrayList<>());
        publication.setLocationDescription("Location Description");
        publication.setLongitude("Longitude");
        publication.setNeedsReview(true);
        publication.setReports(new ArrayList<>());
        assertEquals("O autor da publicação foi notificado e a publicação voltou ao ar na plataforma",
                emailServiceImpl.notifyAvailablePublicationAuthor(publication));
        verify(javaMailSender).send((SimpleMailMessage) any());
        verify(appUserService).getUser((Long) any());
    }

    /**
     * Method under test: {@link EmailServiceImpl#notifyAuthorReportedPublication(Publication)}
     */
    @Test
    void testNotifyAuthorReportedPublication() throws MailException {
        doNothing().when(javaMailSender).send((SimpleMailMessage) any());

        AppUser appUser = new AppUser();
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        appUser.setCreationDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        appUser.setEnabled(true);
        appUser.setId(123L);
        appUser.setLocked(true);
        appUser.setName("Name");
        appUser.setPassword("iloveyou");
        appUser.setRecoveryPasswordToken("ABC123");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(1970, 1, 1).atStartOfDay();
        appUser.setRecoveryPasswordTokenExpiration(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        appUser.setUserRole(UserRole.ADMINISTRADOR);
        appUser.setUsername("janedoe");
        when(appUserService.getUser((Long) any())).thenReturn(appUser);

        Publication publication = new Publication();
        publication.setAuthorId(123L);
        publication.setComments(new ArrayList<>());
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        publication.setCreationDate(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        publication.setDescription("The characteristics of someone or something");
        publication.setId(123L);
        publication.setIsAuthorAnonymous(true);
        publication.setIsAvailable(true);
        publication.setIsResolved(true);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        publication.setIsResolvedDate(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        publication.setLatitude("Latitude");
        publication.setLikes(new ArrayList<>());
        publication.setLocationDescription("Location Description");
        publication.setLongitude("Longitude");
        publication.setNeedsReview(true);
        publication.setReports(new ArrayList<>());
        emailServiceImpl.notifyAuthorReportedPublication(publication);
        verify(javaMailSender).send((SimpleMailMessage) any());
        verify(appUserService).getUser((Long) any());
    }
}

