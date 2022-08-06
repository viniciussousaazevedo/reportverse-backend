package com.es.reportverse.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.es.reportverse.DTO.PublicationLocationDTO;
import com.es.reportverse.DTO.PublicationRequestDTO;
import com.es.reportverse.enums.UserRole;
import com.es.reportverse.exception.ApiRequestException;
import com.es.reportverse.model.AppUser;
import com.es.reportverse.model.AppUserComment;
import com.es.reportverse.model.Publication;
import com.es.reportverse.model.appUserReaction.AppUserLike;
import com.es.reportverse.model.appUserReaction.AppUserReaction;
import com.es.reportverse.model.appUserReaction.AppUserReport;
import com.es.reportverse.repository.PublicationRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

@ContextConfiguration(classes = {PublicationServiceImpl.class})
@ExtendWith(SpringExtension.class)
class PublicationServiceImplTest {
    @MockBean
    private EmailService emailService;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private PublicationRepository publicationRepository;

    @Autowired
    private PublicationServiceImpl publicationServiceImpl;

    @MockBean
    private TokenManagerService tokenManagerService;

    /**
     * Method under test: {@link PublicationServiceImpl#registerPublication(PublicationRequestDTO, HttpServletRequest)}
     */
    @Test
    void testRegisterPublication() {

        PublicationRequestDTO publicationDTO = new PublicationRequestDTO(
                "uma publicacao",
                "uma localizacao",
                "69",
                "666",
                false,
                new ArrayList<MultipartFile>()
        );

        MockHttpServletRequest request = new MockHttpServletRequest();
        String mockToken ="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZWRyb0BnbWFpbC5jb20iLCJpc3MiOiIvYXBpL2xvZ2luIiwiZXhwIjoxNjYxMDE2NTg2LCJ1c2VyUm9sZSI6WyJVTklWRVJTSVRBUklPIl19.W9iw-NrUYA7i-q7sT0HutusDiQnGjxDaqaXYxDNBjMA";
        String auth = "Bearer " + mockToken;
        request.addHeader("Authorization", auth);

        publicationServiceImpl.registerPublication(publicationDTO, request);
    }

    /**
     * Method under test: {@link PublicationServiceImpl#savePublication(Publication)}
     */
    @Test
    void testSavePublication() {
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
        publication.setLatitude("Latitude");
        publication.setLikes(new ArrayList<>());
        publication.setLocationDescription("Location Description");
        publication.setLongitude("Longitude");
        publication.setNeedsReview(true);
        publication.setReports(new ArrayList<>());
        when(publicationRepository.save((Publication) any())).thenReturn(publication);

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
        publication1.setLatitude("Latitude");
        publication1.setLikes(new ArrayList<>());
        publication1.setLocationDescription("Location Description");
        publication1.setLongitude("Longitude");
        publication1.setNeedsReview(true);
        publication1.setReports(new ArrayList<>());
        assertSame(publication, publicationServiceImpl.savePublication(publication1));
        verify(publicationRepository).save((Publication) any());
    }

    /**
     * Method under test: {@link PublicationServiceImpl#savePublication(Publication)}
     */
    @Test
    void testSavePublication2() {
        when(publicationRepository.save((Publication) any())).thenThrow(new ApiRequestException("An error occurred"));

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
        publication.setLatitude("Latitude");
        publication.setLikes(new ArrayList<>());
        publication.setLocationDescription("Location Description");
        publication.setLongitude("Longitude");
        publication.setNeedsReview(true);
        publication.setReports(new ArrayList<>());
        assertThrows(ApiRequestException.class, () -> publicationServiceImpl.savePublication(publication));
        verify(publicationRepository).save((Publication) any());
    }

    /**
     * Method under test: {@link PublicationServiceImpl#getPublication(Long)}
     */
    @Test
    void testGetPublication() {
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
        publication.setLatitude("Latitude");
        publication.setLikes(new ArrayList<>());
        publication.setLocationDescription("Location Description");
        publication.setLongitude("Longitude");
        publication.setNeedsReview(true);
        publication.setReports(new ArrayList<>());
        Optional<Publication> ofResult = Optional.of(publication);
        when(publicationRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(publication, publicationServiceImpl.getPublication(123L));
        verify(publicationRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link PublicationServiceImpl#getPublication(Long)}
     */
    @Test
    void testGetPublication2() {
        when(publicationRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(ApiRequestException.class, () -> publicationServiceImpl.getPublication(123L));
        verify(publicationRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link PublicationServiceImpl#getPublication(Long)}
     */
    @Test
    void testGetPublication3() {
        when(publicationRepository.findById((Long) any())).thenThrow(new ApiRequestException("An error occurred"));
        assertThrows(ApiRequestException.class, () -> publicationServiceImpl.getPublication(123L));
        verify(publicationRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link PublicationServiceImpl#getPublicationsLocations()}
     */
    @Test
    void testGetPublicationsLocations() {
        when(publicationRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(publicationServiceImpl.getPublicationsLocations().isEmpty());
        verify(publicationRepository).findAll();
    }

    /**
     * Method under test: {@link PublicationServiceImpl#getPublicationsLocations()}
     */
    @Test
    void testGetPublicationsLocations2() {
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
        publication.setLatitude("Latitude");
        publication.setLikes(new ArrayList<>());
        publication.setLocationDescription("Location Description");
        publication.setLongitude("Longitude");
        publication.setNeedsReview(true);
        publication.setReports(new ArrayList<>());

        ArrayList<Publication> publicationList = new ArrayList<>();
        publicationList.add(publication);
        when(publicationRepository.findAll()).thenReturn(publicationList);
        when(modelMapper.map((Object) any(), (Class<PublicationLocationDTO>) any()))
                .thenReturn(new PublicationLocationDTO());
        assertEquals(1, publicationServiceImpl.getPublicationsLocations().size());
        verify(publicationRepository).findAll();
        verify(modelMapper).map((Object) any(), (Class<PublicationLocationDTO>) any());
    }

    /**
     * Method under test: {@link PublicationServiceImpl#getPublicationsLocations()}
     */
    @Test
    void testGetPublicationsLocations3() {
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
        publication.setLatitude("Latitude");
        publication.setLikes(new ArrayList<>());
        publication.setLocationDescription("Location Description");
        publication.setLongitude("Longitude");
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
        publication1.setLatitude("Latitude");
        publication1.setLikes(new ArrayList<>());
        publication1.setLocationDescription("Location Description");
        publication1.setLongitude("Longitude");
        publication1.setNeedsReview(true);
        publication1.setReports(new ArrayList<>());

        ArrayList<Publication> publicationList = new ArrayList<>();
        publicationList.add(publication1);
        publicationList.add(publication);
        when(publicationRepository.findAll()).thenReturn(publicationList);
        when(modelMapper.map((Object) any(), (Class<PublicationLocationDTO>) any()))
                .thenReturn(new PublicationLocationDTO());
        assertEquals(2, publicationServiceImpl.getPublicationsLocations().size());
        verify(publicationRepository).findAll();
        verify(modelMapper, atLeast(1)).map((Object) any(), (Class<PublicationLocationDTO>) any());
    }

    /**
     * Method under test: {@link PublicationServiceImpl#getPublicationsLocations()}
     */
    @Test
    void testGetPublicationsLocations4() {
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
        publication.setLatitude("Latitude");
        publication.setLikes(new ArrayList<>());
        publication.setLocationDescription("Location Description");
        publication.setLongitude("Longitude");
        publication.setNeedsReview(true);
        publication.setReports(new ArrayList<>());

        ArrayList<Publication> publicationList = new ArrayList<>();
        publicationList.add(publication);
        when(publicationRepository.findAll()).thenReturn(publicationList);
        when(modelMapper.map((Object) any(), (Class<PublicationLocationDTO>) any()))
                .thenThrow(new ApiRequestException("An error occurred"));
        assertThrows(ApiRequestException.class, () -> publicationServiceImpl.getPublicationsLocations());
        verify(publicationRepository).findAll();
        verify(modelMapper).map((Object) any(), (Class<PublicationLocationDTO>) any());
    }

    /**
     * Method under test: {@link PublicationServiceImpl#manipulatePublicationReactions(AppUser, Long, AppUserReaction)}
     */
    @Test
    void testManipulatePublicationReactions() {
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
        publication.setLatitude("Latitude");
        publication.setLikes(new ArrayList<>());
        publication.setLocationDescription("Location Description");
        publication.setLongitude("Longitude");
        publication.setNeedsReview(true);
        publication.setReports(new ArrayList<>());
        Optional<Publication> ofResult = Optional.of(publication);

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
        publication1.setLatitude("Latitude");
        publication1.setLikes(new ArrayList<>());
        publication1.setLocationDescription("Location Description");
        publication1.setLongitude("Longitude");
        publication1.setNeedsReview(true);
        publication1.setReports(new ArrayList<>());
        when(publicationRepository.save((Publication) any())).thenReturn(publication1);
        when(publicationRepository.findById((Long) any())).thenReturn(ofResult);

        AppUser appUser = new AppUser();
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        appUser.setCreationDate(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        appUser.setEnabled(true);
        appUser.setId(123L);
        appUser.setLocked(true);
        appUser.setName("Name");
        appUser.setPassword("iloveyou");
        appUser.setRecoveryPasswordToken("ABC123");
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        appUser.setRecoveryPasswordTokenExpiration(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        appUser.setUserRole(UserRole.ADMINISTRADOR);
        appUser.setUsername("janedoe");
        AppUserLike appUserLike = new AppUserLike();
        assertSame(publication1, publicationServiceImpl.manipulatePublicationReactions(appUser, 123L, appUserLike));
        verify(publicationRepository).save((Publication) any());
        verify(publicationRepository).findById((Long) any());
        assertSame(appUser, appUserLike.getAppUser());
    }

    /**
     * Method under test: {@link PublicationServiceImpl#manipulatePublicationReactions(AppUser, Long, AppUserReaction)}
     */
    @Test
    void testManipulatePublicationReactions2() {
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
        publication.setLatitude("Latitude");
        publication.setLikes(new ArrayList<>());
        publication.setLocationDescription("Location Description");
        publication.setLongitude("Longitude");
        publication.setNeedsReview(true);
        publication.setReports(new ArrayList<>());
        Optional<Publication> ofResult = Optional.of(publication);
        when(publicationRepository.save((Publication) any())).thenThrow(new ApiRequestException("An error occurred"));
        when(publicationRepository.findById((Long) any())).thenReturn(ofResult);

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
        assertThrows(ApiRequestException.class,
                () -> publicationServiceImpl.manipulatePublicationReactions(appUser, 123L, new AppUserLike()));
        verify(publicationRepository).save((Publication) any());
        verify(publicationRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link PublicationServiceImpl#manipulatePublicationReactions(AppUser, Long, AppUserReaction)}
     */
    @Test
    void testManipulatePublicationReactions3() {
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

        AppUserLike appUserLike = new AppUserLike();
        appUserLike.setAppUser(appUser);
        appUserLike.setId(123L);

        ArrayList<AppUserLike> appUserLikeList = new ArrayList<>();
        appUserLikeList.add(appUserLike);

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
        publication.setLikes(appUserLikeList);
        publication.setLocationDescription("Location Description");
        publication.setLongitude("Longitude");
        publication.setNeedsReview(true);
        publication.setReports(new ArrayList<>());
        Optional<Publication> ofResult = Optional.of(publication);

        Publication publication1 = new Publication();
        publication1.setAuthorId(123L);
        publication1.setComments(new ArrayList<>());
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        publication1.setCreationDate(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        publication1.setDescription("The characteristics of someone or something");
        publication1.setId(123L);
        publication1.setIsAuthorAnonymous(true);
        publication1.setIsAvailable(true);
        publication1.setIsResolved(true);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        publication1.setIsResolvedDate(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        publication1.setLatitude("Latitude");
        publication1.setLikes(new ArrayList<>());
        publication1.setLocationDescription("Location Description");
        publication1.setLongitude("Longitude");
        publication1.setNeedsReview(true);
        publication1.setReports(new ArrayList<>());
        when(publicationRepository.save((Publication) any())).thenReturn(publication1);
        when(publicationRepository.findById((Long) any())).thenReturn(ofResult);

        AppUser appUser1 = new AppUser();
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        appUser1.setCreationDate(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        appUser1.setEnabled(true);
        appUser1.setId(123L);
        appUser1.setLocked(true);
        appUser1.setName("Name");
        appUser1.setPassword("iloveyou");
        appUser1.setRecoveryPasswordToken("ABC123");
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        appUser1.setRecoveryPasswordTokenExpiration(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        appUser1.setUserRole(UserRole.ADMINISTRADOR);
        appUser1.setUsername("janedoe");
        assertSame(publication1,
                publicationServiceImpl.manipulatePublicationReactions(appUser1, 123L, new AppUserLike()));
        verify(publicationRepository).save((Publication) any());
        verify(publicationRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link PublicationServiceImpl#manipulatePublicationReactions(AppUser, Long, AppUserReaction)}
     */
    @Test
    void testManipulatePublicationReactions4() {
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

        AppUserLike appUserLike = new AppUserLike();
        appUserLike.setAppUser(appUser);
        appUserLike.setId(123L);

        AppUser appUser1 = new AppUser();
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        appUser1.setCreationDate(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        appUser1.setEnabled(true);
        appUser1.setId(123L);
        appUser1.setLocked(true);
        appUser1.setName("Name");
        appUser1.setPassword("iloveyou");
        appUser1.setRecoveryPasswordToken("ABC123");
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        appUser1.setRecoveryPasswordTokenExpiration(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        appUser1.setUserRole(UserRole.ADMINISTRADOR);
        appUser1.setUsername("janedoe");

        AppUserLike appUserLike1 = new AppUserLike();
        appUserLike1.setAppUser(appUser1);
        appUserLike1.setId(123L);

        ArrayList<AppUserLike> appUserLikeList = new ArrayList<>();
        appUserLikeList.add(appUserLike1);
        appUserLikeList.add(appUserLike);

        Publication publication = new Publication();
        publication.setAuthorId(123L);
        publication.setComments(new ArrayList<>());
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        publication.setCreationDate(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        publication.setDescription("The characteristics of someone or something");
        publication.setId(123L);
        publication.setIsAuthorAnonymous(true);
        publication.setIsAvailable(true);
        publication.setIsResolved(true);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        publication.setIsResolvedDate(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        publication.setLatitude("Latitude");
        publication.setLikes(appUserLikeList);
        publication.setLocationDescription("Location Description");
        publication.setLongitude("Longitude");
        publication.setNeedsReview(true);
        publication.setReports(new ArrayList<>());
        Optional<Publication> ofResult = Optional.of(publication);

        Publication publication1 = new Publication();
        publication1.setAuthorId(123L);
        publication1.setComments(new ArrayList<>());
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        publication1.setCreationDate(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        publication1.setDescription("The characteristics of someone or something");
        publication1.setId(123L);
        publication1.setIsAuthorAnonymous(true);
        publication1.setIsAvailable(true);
        publication1.setIsResolved(true);
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        publication1.setIsResolvedDate(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        publication1.setLatitude("Latitude");
        publication1.setLikes(new ArrayList<>());
        publication1.setLocationDescription("Location Description");
        publication1.setLongitude("Longitude");
        publication1.setNeedsReview(true);
        publication1.setReports(new ArrayList<>());
        when(publicationRepository.save((Publication) any())).thenReturn(publication1);
        when(publicationRepository.findById((Long) any())).thenReturn(ofResult);

        AppUser appUser2 = new AppUser();
        LocalDateTime atStartOfDayResult8 = LocalDate.of(1970, 1, 1).atStartOfDay();
        appUser2.setCreationDate(Date.from(atStartOfDayResult8.atZone(ZoneId.of("UTC")).toInstant()));
        appUser2.setEnabled(true);
        appUser2.setId(123L);
        appUser2.setLocked(true);
        appUser2.setName("Name");
        appUser2.setPassword("iloveyou");
        appUser2.setRecoveryPasswordToken("ABC123");
        LocalDateTime atStartOfDayResult9 = LocalDate.of(1970, 1, 1).atStartOfDay();
        appUser2.setRecoveryPasswordTokenExpiration(Date.from(atStartOfDayResult9.atZone(ZoneId.of("UTC")).toInstant()));
        appUser2.setUserRole(UserRole.ADMINISTRADOR);
        appUser2.setUsername("janedoe");
        assertSame(publication1,
                publicationServiceImpl.manipulatePublicationReactions(appUser2, 123L, new AppUserLike()));
        verify(publicationRepository).save((Publication) any());
        verify(publicationRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link PublicationServiceImpl#manipulatePublicationReactions(AppUser, Long, AppUserReaction)}
     */
    @Test
    void testManipulatePublicationReactions5() {
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
        publication.setLatitude("Latitude");
        publication.setLikes(new ArrayList<>());
        publication.setLocationDescription("Location Description");
        publication.setLongitude("Longitude");
        publication.setNeedsReview(true);
        publication.setReports(new ArrayList<>());
        when(publicationRepository.save((Publication) any())).thenReturn(publication);
        when(publicationRepository.findById((Long) any())).thenReturn(Optional.empty());

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
        assertThrows(ApiRequestException.class,
                () -> publicationServiceImpl.manipulatePublicationReactions(appUser, 123L, new AppUserLike()));
        verify(publicationRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link PublicationServiceImpl#addPublicationComment(Long, AppUserComment)}
     */
    @Test
    void testAddPublicationComment() {
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
        publication.setLatitude("Latitude");
        publication.setLikes(new ArrayList<>());
        publication.setLocationDescription("Location Description");
        publication.setLongitude("Longitude");
        publication.setNeedsReview(true);
        publication.setReports(new ArrayList<>());
        Optional<Publication> ofResult = Optional.of(publication);

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
        publication1.setLatitude("Latitude");
        publication1.setLikes(new ArrayList<>());
        publication1.setLocationDescription("Location Description");
        publication1.setLongitude("Longitude");
        publication1.setNeedsReview(true);
        publication1.setReports(new ArrayList<>());
        when(publicationRepository.save((Publication) any())).thenReturn(publication1);
        when(publicationRepository.findById((Long) any())).thenReturn(ofResult);

        AppUser appUser = new AppUser();
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        appUser.setCreationDate(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        appUser.setEnabled(true);
        appUser.setId(123L);
        appUser.setLocked(true);
        appUser.setName("Name");
        appUser.setPassword("iloveyou");
        appUser.setRecoveryPasswordToken("ABC123");
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        appUser.setRecoveryPasswordTokenExpiration(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        appUser.setUserRole(UserRole.ADMINISTRADOR);
        appUser.setUsername("janedoe");

        AppUserComment appUserComment = new AppUserComment();
        appUserComment.setAppUser(appUser);
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        appUserComment.setCreationDate(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        appUserComment.setId(123L);
        appUserComment.setIsAuthorAnonymous(true);
        appUserComment.setText("Text");
        assertSame(publication1, publicationServiceImpl.addPublicationComment(123L, appUserComment));
        verify(publicationRepository).save((Publication) any());
        verify(publicationRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link PublicationServiceImpl#addPublicationComment(Long, AppUserComment)}
     */
    @Test
    void testAddPublicationComment2() {
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
        publication.setLatitude("Latitude");
        publication.setLikes(new ArrayList<>());
        publication.setLocationDescription("Location Description");
        publication.setLongitude("Longitude");
        publication.setNeedsReview(true);
        publication.setReports(new ArrayList<>());
        Optional<Publication> ofResult = Optional.of(publication);
        when(publicationRepository.save((Publication) any())).thenThrow(new ApiRequestException("An error occurred"));
        when(publicationRepository.findById((Long) any())).thenReturn(ofResult);

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

        AppUserComment appUserComment = new AppUserComment();
        appUserComment.setAppUser(appUser);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        appUserComment.setCreationDate(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        appUserComment.setId(123L);
        appUserComment.setIsAuthorAnonymous(true);
        appUserComment.setText("Text");
        assertThrows(ApiRequestException.class, () -> publicationServiceImpl.addPublicationComment(123L, appUserComment));
        verify(publicationRepository).save((Publication) any());
        verify(publicationRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link PublicationServiceImpl#addPublicationComment(Long, AppUserComment)}
     */
    @Test
    void testAddPublicationComment3() {
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
        publication.setLatitude("Latitude");
        publication.setLikes(new ArrayList<>());
        publication.setLocationDescription("Location Description");
        publication.setLongitude("Longitude");
        publication.setNeedsReview(true);
        publication.setReports(new ArrayList<>());
        when(publicationRepository.save((Publication) any())).thenReturn(publication);
        when(publicationRepository.findById((Long) any())).thenReturn(Optional.empty());

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

        AppUserComment appUserComment = new AppUserComment();
        appUserComment.setAppUser(appUser);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        appUserComment.setCreationDate(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        appUserComment.setId(123L);
        appUserComment.setIsAuthorAnonymous(true);
        appUserComment.setText("Text");
        assertThrows(ApiRequestException.class, () -> publicationServiceImpl.addPublicationComment(123L, appUserComment));
        verify(publicationRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link PublicationServiceImpl#deletePublicationComment(AppUser, Long, Long)}
     */
    @Test
    void testDeletePublicationComment() {
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
        publication.setLatitude("Latitude");
        publication.setLikes(new ArrayList<>());
        publication.setLocationDescription("Location Description");
        publication.setLongitude("Longitude");
        publication.setNeedsReview(true);
        publication.setReports(new ArrayList<>());
        Optional<Publication> ofResult = Optional.of(publication);

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
        publication1.setLatitude("Latitude");
        publication1.setLikes(new ArrayList<>());
        publication1.setLocationDescription("Location Description");
        publication1.setLongitude("Longitude");
        publication1.setNeedsReview(true);
        publication1.setReports(new ArrayList<>());
        when(publicationRepository.save((Publication) any())).thenReturn(publication1);
        when(publicationRepository.findById((Long) any())).thenReturn(ofResult);

        AppUser appUser = new AppUser();
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        appUser.setCreationDate(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        appUser.setEnabled(true);
        appUser.setId(123L);
        appUser.setLocked(true);
        appUser.setName("Name");
        appUser.setPassword("iloveyou");
        appUser.setRecoveryPasswordToken("ABC123");
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        appUser.setRecoveryPasswordTokenExpiration(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        appUser.setUserRole(UserRole.ADMINISTRADOR);
        appUser.setUsername("janedoe");
        assertSame(publication1, publicationServiceImpl.deletePublicationComment(appUser, 123L, 123L));
        verify(publicationRepository).save((Publication) any());
        verify(publicationRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link PublicationServiceImpl#deletePublicationComment(AppUser, Long, Long)}
     */
    @Test
    void testDeletePublicationComment2() {
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
        publication.setLatitude("Latitude");
        publication.setLikes(new ArrayList<>());
        publication.setLocationDescription("Location Description");
        publication.setLongitude("Longitude");
        publication.setNeedsReview(true);
        publication.setReports(new ArrayList<>());
        Optional<Publication> ofResult = Optional.of(publication);
        when(publicationRepository.save((Publication) any())).thenThrow(new ApiRequestException("An error occurred"));
        when(publicationRepository.findById((Long) any())).thenReturn(ofResult);

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
        assertThrows(ApiRequestException.class,
                () -> publicationServiceImpl.deletePublicationComment(appUser, 123L, 123L));
        verify(publicationRepository).save((Publication) any());
        verify(publicationRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link PublicationServiceImpl#deletePublicationComment(AppUser, Long, Long)}
     */
    @Test
    void testDeletePublicationComment3() {
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

        AppUserComment appUserComment = new AppUserComment();
        appUserComment.setAppUser(appUser);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        appUserComment.setCreationDate(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        appUserComment.setId(123L);
        appUserComment.setIsAuthorAnonymous(true);
        appUserComment.setText("Text");

        ArrayList<AppUserComment> appUserCommentList = new ArrayList<>();
        appUserCommentList.add(appUserComment);

        Publication publication = new Publication();
        publication.setAuthorId(123L);
        publication.setComments(appUserCommentList);
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        publication.setCreationDate(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        publication.setDescription("The characteristics of someone or something");
        publication.setId(123L);
        publication.setIsAuthorAnonymous(true);
        publication.setIsAvailable(true);
        publication.setIsResolved(true);
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        publication.setIsResolvedDate(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        publication.setLatitude("Latitude");
        publication.setLikes(new ArrayList<>());
        publication.setLocationDescription("Location Description");
        publication.setLongitude("Longitude");
        publication.setNeedsReview(true);
        publication.setReports(new ArrayList<>());
        Optional<Publication> ofResult = Optional.of(publication);

        Publication publication1 = new Publication();
        publication1.setAuthorId(123L);
        publication1.setComments(new ArrayList<>());
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        publication1.setCreationDate(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        publication1.setDescription("The characteristics of someone or something");
        publication1.setId(123L);
        publication1.setIsAuthorAnonymous(true);
        publication1.setIsAvailable(true);
        publication1.setIsResolved(true);
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        publication1.setIsResolvedDate(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        publication1.setLatitude("Latitude");
        publication1.setLikes(new ArrayList<>());
        publication1.setLocationDescription("Location Description");
        publication1.setLongitude("Longitude");
        publication1.setNeedsReview(true);
        publication1.setReports(new ArrayList<>());
        when(publicationRepository.save((Publication) any())).thenReturn(publication1);
        when(publicationRepository.findById((Long) any())).thenReturn(ofResult);

        AppUser appUser1 = new AppUser();
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        appUser1.setCreationDate(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        appUser1.setEnabled(true);
        appUser1.setId(123L);
        appUser1.setLocked(true);
        appUser1.setName("Name");
        appUser1.setPassword("iloveyou");
        appUser1.setRecoveryPasswordToken("ABC123");
        LocalDateTime atStartOfDayResult8 = LocalDate.of(1970, 1, 1).atStartOfDay();
        appUser1.setRecoveryPasswordTokenExpiration(Date.from(atStartOfDayResult8.atZone(ZoneId.of("UTC")).toInstant()));
        appUser1.setUserRole(UserRole.ADMINISTRADOR);
        appUser1.setUsername("janedoe");
        assertSame(publication1, publicationServiceImpl.deletePublicationComment(appUser1, 123L, 123L));
        verify(publicationRepository).save((Publication) any());
        verify(publicationRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link PublicationServiceImpl#deletePublicationComment(AppUser, Long, Long)}
     */
    @Test
    void testDeletePublicationComment4() {
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

        AppUserComment appUserComment = new AppUserComment();
        appUserComment.setAppUser(appUser);
        LocalDateTime atStartOfDayResult2 = LocalDate.of(1970, 1, 1).atStartOfDay();
        appUserComment.setCreationDate(Date.from(atStartOfDayResult2.atZone(ZoneId.of("UTC")).toInstant()));
        appUserComment.setId(123L);
        appUserComment.setIsAuthorAnonymous(true);
        appUserComment.setText("Text");

        AppUser appUser1 = new AppUser();
        LocalDateTime atStartOfDayResult3 = LocalDate.of(1970, 1, 1).atStartOfDay();
        appUser1.setCreationDate(Date.from(atStartOfDayResult3.atZone(ZoneId.of("UTC")).toInstant()));
        appUser1.setEnabled(true);
        appUser1.setId(123L);
        appUser1.setLocked(true);
        appUser1.setName("janedoe");
        appUser1.setPassword("iloveyou");
        appUser1.setRecoveryPasswordToken("ABC123");
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        appUser1.setRecoveryPasswordTokenExpiration(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        appUser1.setUserRole(UserRole.ADMINISTRADOR);
        appUser1.setUsername("janedoe");

        AppUserComment appUserComment1 = new AppUserComment();
        appUserComment1.setAppUser(appUser1);
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        appUserComment1.setCreationDate(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        appUserComment1.setId(123L);
        appUserComment1.setIsAuthorAnonymous(true);
        appUserComment1.setText("janedoe");

        ArrayList<AppUserComment> appUserCommentList = new ArrayList<>();
        appUserCommentList.add(appUserComment1);
        appUserCommentList.add(appUserComment);

        Publication publication = new Publication();
        publication.setAuthorId(123L);
        publication.setComments(appUserCommentList);
        LocalDateTime atStartOfDayResult6 = LocalDate.of(1970, 1, 1).atStartOfDay();
        publication.setCreationDate(Date.from(atStartOfDayResult6.atZone(ZoneId.of("UTC")).toInstant()));
        publication.setDescription("The characteristics of someone or something");
        publication.setId(123L);
        publication.setIsAuthorAnonymous(true);
        publication.setIsAvailable(true);
        publication.setIsResolved(true);
        LocalDateTime atStartOfDayResult7 = LocalDate.of(1970, 1, 1).atStartOfDay();
        publication.setIsResolvedDate(Date.from(atStartOfDayResult7.atZone(ZoneId.of("UTC")).toInstant()));
        publication.setLatitude("Latitude");
        publication.setLikes(new ArrayList<>());
        publication.setLocationDescription("Location Description");
        publication.setLongitude("Longitude");
        publication.setNeedsReview(true);
        publication.setReports(new ArrayList<>());
        Optional<Publication> ofResult = Optional.of(publication);

        Publication publication1 = new Publication();
        publication1.setAuthorId(123L);
        publication1.setComments(new ArrayList<>());
        LocalDateTime atStartOfDayResult8 = LocalDate.of(1970, 1, 1).atStartOfDay();
        publication1.setCreationDate(Date.from(atStartOfDayResult8.atZone(ZoneId.of("UTC")).toInstant()));
        publication1.setDescription("The characteristics of someone or something");
        publication1.setId(123L);
        publication1.setIsAuthorAnonymous(true);
        publication1.setIsAvailable(true);
        publication1.setIsResolved(true);
        LocalDateTime atStartOfDayResult9 = LocalDate.of(1970, 1, 1).atStartOfDay();
        publication1.setIsResolvedDate(Date.from(atStartOfDayResult9.atZone(ZoneId.of("UTC")).toInstant()));
        publication1.setLatitude("Latitude");
        publication1.setLikes(new ArrayList<>());
        publication1.setLocationDescription("Location Description");
        publication1.setLongitude("Longitude");
        publication1.setNeedsReview(true);
        publication1.setReports(new ArrayList<>());
        when(publicationRepository.save((Publication) any())).thenReturn(publication1);
        when(publicationRepository.findById((Long) any())).thenReturn(ofResult);

        AppUser appUser2 = new AppUser();
        LocalDateTime atStartOfDayResult10 = LocalDate.of(1970, 1, 1).atStartOfDay();
        appUser2.setCreationDate(Date.from(atStartOfDayResult10.atZone(ZoneId.of("UTC")).toInstant()));
        appUser2.setEnabled(true);
        appUser2.setId(123L);
        appUser2.setLocked(true);
        appUser2.setName("Name");
        appUser2.setPassword("iloveyou");
        appUser2.setRecoveryPasswordToken("ABC123");
        LocalDateTime atStartOfDayResult11 = LocalDate.of(1970, 1, 1).atStartOfDay();
        appUser2.setRecoveryPasswordTokenExpiration(Date.from(atStartOfDayResult11.atZone(ZoneId.of("UTC")).toInstant()));
        appUser2.setUserRole(UserRole.ADMINISTRADOR);
        appUser2.setUsername("janedoe");
        assertSame(publication1, publicationServiceImpl.deletePublicationComment(appUser2, 123L, 123L));
        verify(publicationRepository).save((Publication) any());
        verify(publicationRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link PublicationServiceImpl#deletePublicationComment(AppUser, Long, Long)}
     */
    @Test
    void testDeletePublicationComment5() {
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
        publication.setLatitude("Latitude");
        publication.setLikes(new ArrayList<>());
        publication.setLocationDescription("Location Description");
        publication.setLongitude("Longitude");
        publication.setNeedsReview(true);
        publication.setReports(new ArrayList<>());
        when(publicationRepository.save((Publication) any())).thenReturn(publication);
        when(publicationRepository.findById((Long) any())).thenReturn(Optional.empty());

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
        assertThrows(ApiRequestException.class,
                () -> publicationServiceImpl.deletePublicationComment(appUser, 123L, 123L));
        verify(publicationRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link PublicationServiceImpl#getPublicationsByAuthorId(AppUser)}
     */
    @Test
    void testGetPublicationsByAuthorId() {
        ArrayList<Publication> publicationList = new ArrayList<>();
        when(publicationRepository.findAll()).thenReturn(publicationList);

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
        List<Publication> actualPublicationsByAuthorId = publicationServiceImpl.getPublicationsByAuthorId(appUser);
        assertSame(publicationList, actualPublicationsByAuthorId);
        assertTrue(actualPublicationsByAuthorId.isEmpty());
        verify(publicationRepository).findAll();
    }

    /**
     * Method under test: {@link PublicationServiceImpl#getPublicationsByAuthorId(AppUser)}
     */
    @Test
    void testGetPublicationsByAuthorId2() {
        ArrayList<Publication> publicationList = new ArrayList<>();
        when(publicationRepository.findAll()).thenReturn(publicationList);
        AppUser appUser = mock(AppUser.class);
        when(appUser.getUserRole()).thenReturn(UserRole.ADMINISTRADOR);
        doNothing().when(appUser).setCreationDate((Date) any());
        doNothing().when(appUser).setEnabled((Boolean) any());
        doNothing().when(appUser).setLocked((Boolean) any());
        doNothing().when(appUser).setName((String) any());
        doNothing().when(appUser).setPassword((String) any());
        doNothing().when(appUser).setRecoveryPasswordToken((String) any());
        doNothing().when(appUser).setRecoveryPasswordTokenExpiration((Date) any());
        doNothing().when(appUser).setUserRole((UserRole) any());
        doNothing().when(appUser).setUsername((String) any());
        doNothing().when(appUser).setId((Long) any());
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
        List<Publication> actualPublicationsByAuthorId = publicationServiceImpl.getPublicationsByAuthorId(appUser);
        assertSame(publicationList, actualPublicationsByAuthorId);
        assertTrue(actualPublicationsByAuthorId.isEmpty());
        verify(publicationRepository).findAll();
        verify(appUser).getUserRole();
        verify(appUser).setCreationDate((Date) any());
        verify(appUser).setEnabled((Boolean) any());
        verify(appUser).setLocked((Boolean) any());
        verify(appUser).setName((String) any());
        verify(appUser).setPassword((String) any());
        verify(appUser).setRecoveryPasswordToken((String) any());
        verify(appUser).setRecoveryPasswordTokenExpiration((Date) any());
        verify(appUser).setUserRole((UserRole) any());
        verify(appUser).setUsername((String) any());
        verify(appUser).setId((Long) any());
    }

    /**
     * Method under test: {@link PublicationServiceImpl#getPublicationsByAuthorId(AppUser)}
     */
    @Test
    void testGetPublicationsByAuthorId3() {
        ArrayList<Publication> publicationList = new ArrayList<>();
        when(publicationRepository.findByAuthorId((Long) any())).thenReturn(publicationList);
        when(publicationRepository.findAll()).thenReturn(new ArrayList<>());
        AppUser appUser = mock(AppUser.class);
        when(appUser.getId()).thenReturn(123L);
        when(appUser.getUserRole()).thenReturn(UserRole.UNIVERSITARIO);
        doNothing().when(appUser).setCreationDate((Date) any());
        doNothing().when(appUser).setEnabled((Boolean) any());
        doNothing().when(appUser).setLocked((Boolean) any());
        doNothing().when(appUser).setName((String) any());
        doNothing().when(appUser).setPassword((String) any());
        doNothing().when(appUser).setRecoveryPasswordToken((String) any());
        doNothing().when(appUser).setRecoveryPasswordTokenExpiration((Date) any());
        doNothing().when(appUser).setUserRole((UserRole) any());
        doNothing().when(appUser).setUsername((String) any());
        doNothing().when(appUser).setId((Long) any());
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
        List<Publication> actualPublicationsByAuthorId = publicationServiceImpl.getPublicationsByAuthorId(appUser);
        assertSame(publicationList, actualPublicationsByAuthorId);
        assertTrue(actualPublicationsByAuthorId.isEmpty());
        verify(publicationRepository).findByAuthorId((Long) any());
        verify(appUser, atLeast(1)).getUserRole();
        verify(appUser).getId();
        verify(appUser).setCreationDate((Date) any());
        verify(appUser).setEnabled((Boolean) any());
        verify(appUser).setLocked((Boolean) any());
        verify(appUser).setName((String) any());
        verify(appUser).setPassword((String) any());
        verify(appUser).setRecoveryPasswordToken((String) any());
        verify(appUser).setRecoveryPasswordTokenExpiration((Date) any());
        verify(appUser).setUserRole((UserRole) any());
        verify(appUser).setUsername((String) any());
        verify(appUser).setId((Long) any());
    }

    /**
     * Method under test: {@link PublicationServiceImpl#getPublicationsByAuthorId(AppUser)}
     */
    @Test
    void testGetPublicationsByAuthorId4() {
        when(publicationRepository.findByAuthorId((Long) any())).thenReturn(new ArrayList<>());
        when(publicationRepository.findAll()).thenReturn(new ArrayList<>());
        AppUser appUser = mock(AppUser.class);
        when(appUser.getId()).thenThrow(new ApiRequestException("An error occurred"));
        when(appUser.getUserRole()).thenReturn(UserRole.UNIVERSITARIO);
        doNothing().when(appUser).setCreationDate((Date) any());
        doNothing().when(appUser).setEnabled((Boolean) any());
        doNothing().when(appUser).setLocked((Boolean) any());
        doNothing().when(appUser).setName((String) any());
        doNothing().when(appUser).setPassword((String) any());
        doNothing().when(appUser).setRecoveryPasswordToken((String) any());
        doNothing().when(appUser).setRecoveryPasswordTokenExpiration((Date) any());
        doNothing().when(appUser).setUserRole((UserRole) any());
        doNothing().when(appUser).setUsername((String) any());
        doNothing().when(appUser).setId((Long) any());
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
        assertThrows(ApiRequestException.class, () -> publicationServiceImpl.getPublicationsByAuthorId(appUser));
        verify(appUser, atLeast(1)).getUserRole();
        verify(appUser).getId();
        verify(appUser).setCreationDate((Date) any());
        verify(appUser).setEnabled((Boolean) any());
        verify(appUser).setLocked((Boolean) any());
        verify(appUser).setName((String) any());
        verify(appUser).setPassword((String) any());
        verify(appUser).setRecoveryPasswordToken((String) any());
        verify(appUser).setRecoveryPasswordTokenExpiration((Date) any());
        verify(appUser).setUserRole((UserRole) any());
        verify(appUser).setUsername((String) any());
        verify(appUser).setId((Long) any());
    }

    /**
     * Method under test: {@link PublicationServiceImpl#resolvePublication(Long, AppUser)}
     */
    @Test
    void testResolvePublication() {
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
        publication.setLatitude("Latitude");
        publication.setLikes(new ArrayList<>());
        publication.setLocationDescription("Location Description");
        publication.setLongitude("Longitude");
        publication.setNeedsReview(true);
        publication.setReports(new ArrayList<>());
        Optional<Publication> ofResult = Optional.of(publication);

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
        publication1.setLatitude("Latitude");
        publication1.setLikes(new ArrayList<>());
        publication1.setLocationDescription("Location Description");
        publication1.setLongitude("Longitude");
        publication1.setNeedsReview(true);
        publication1.setReports(new ArrayList<>());
        when(publicationRepository.save((Publication) any())).thenReturn(publication1);
        when(publicationRepository.findById((Long) any())).thenReturn(ofResult);

        AppUser appUser = new AppUser();
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        appUser.setCreationDate(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        appUser.setEnabled(true);
        appUser.setId(123L);
        appUser.setLocked(true);
        appUser.setName("Name");
        appUser.setPassword("iloveyou");
        appUser.setRecoveryPasswordToken("ABC123");
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        appUser.setRecoveryPasswordTokenExpiration(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        appUser.setUserRole(UserRole.ADMINISTRADOR);
        appUser.setUsername("janedoe");
        Publication actualResolvePublicationResult = publicationServiceImpl.resolvePublication(123L, appUser);
        assertSame(publication, actualResolvePublicationResult);
        assertTrue(actualResolvePublicationResult.getIsResolved());
        assertFalse(actualResolvePublicationResult.getIsAvailable());
        verify(publicationRepository).save((Publication) any());
        verify(publicationRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link PublicationServiceImpl#resolvePublication(Long, AppUser)}
     */
    @Test
    void testResolvePublication2() {
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
        publication.setLatitude("Latitude");
        publication.setLikes(new ArrayList<>());
        publication.setLocationDescription("Location Description");
        publication.setLongitude("Longitude");
        publication.setNeedsReview(true);
        publication.setReports(new ArrayList<>());
        Optional<Publication> ofResult = Optional.of(publication);
        when(publicationRepository.save((Publication) any())).thenThrow(new ApiRequestException("An error occurred"));
        when(publicationRepository.findById((Long) any())).thenReturn(ofResult);

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
        assertThrows(ApiRequestException.class, () -> publicationServiceImpl.resolvePublication(123L, appUser));
        verify(publicationRepository).save((Publication) any());
        verify(publicationRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link PublicationServiceImpl#resolvePublication(Long, AppUser)}
     */
    @Test
    void testResolvePublication3() {
        Publication publication = mock(Publication.class);
        when(publication.getAuthorId()).thenReturn(1L);
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
        publication.setLatitude("Latitude");
        publication.setLikes(new ArrayList<>());
        publication.setLocationDescription("Location Description");
        publication.setLongitude("Longitude");
        publication.setNeedsReview(true);
        publication.setReports(new ArrayList<>());
        Optional<Publication> ofResult = Optional.of(publication);

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
        publication1.setLatitude("Latitude");
        publication1.setLikes(new ArrayList<>());
        publication1.setLocationDescription("Location Description");
        publication1.setLongitude("Longitude");
        publication1.setNeedsReview(true);
        publication1.setReports(new ArrayList<>());
        when(publicationRepository.save((Publication) any())).thenReturn(publication1);
        when(publicationRepository.findById((Long) any())).thenReturn(ofResult);

        AppUser appUser = new AppUser();
        LocalDateTime atStartOfDayResult4 = LocalDate.of(1970, 1, 1).atStartOfDay();
        appUser.setCreationDate(Date.from(atStartOfDayResult4.atZone(ZoneId.of("UTC")).toInstant()));
        appUser.setEnabled(true);
        appUser.setId(123L);
        appUser.setLocked(true);
        appUser.setName("Name");
        appUser.setPassword("iloveyou");
        appUser.setRecoveryPasswordToken("ABC123");
        LocalDateTime atStartOfDayResult5 = LocalDate.of(1970, 1, 1).atStartOfDay();
        appUser.setRecoveryPasswordTokenExpiration(Date.from(atStartOfDayResult5.atZone(ZoneId.of("UTC")).toInstant()));
        appUser.setUserRole(UserRole.ADMINISTRADOR);
        appUser.setUsername("janedoe");
        assertThrows(ApiRequestException.class, () -> publicationServiceImpl.resolvePublication(123L, appUser));
        verify(publicationRepository).findById((Long) any());
        verify(publication).getAuthorId();
        verify(publication).setId((Long) any());
        verify(publication).setAuthorId((Long) any());
        verify(publication).setComments((List<AppUserComment>) any());
        verify(publication).setCreationDate((Date) any());
        verify(publication).setDescription((String) any());
        verify(publication).setIsAuthorAnonymous((Boolean) any());
        verify(publication).setIsAvailable((Boolean) any());
        verify(publication).setIsResolved((Boolean) any());
        verify(publication).setIsResolvedDate((Date) any());
        verify(publication).setLatitude((String) any());
        verify(publication).setLikes((List<AppUserLike>) any());
        verify(publication).setLocationDescription((String) any());
        verify(publication).setLongitude((String) any());
        verify(publication).setNeedsReview((Boolean) any());
        verify(publication).setReports((List<AppUserReport>) any());
    }

    /**
     * Method under test: {@link PublicationServiceImpl#findAllByNeedsReview(Boolean)}
     */
    @Test
    void testFindAllByNeedsReview() {
        ArrayList<Publication> publicationList = new ArrayList<>();
        when(publicationRepository.findAllByNeedsReview((Boolean) any())).thenReturn(publicationList);
        Collection<Publication> actualFindAllByNeedsReviewResult = publicationServiceImpl.findAllByNeedsReview(true);
        assertSame(publicationList, actualFindAllByNeedsReviewResult);
        assertTrue(actualFindAllByNeedsReviewResult.isEmpty());
        verify(publicationRepository).findAllByNeedsReview((Boolean) any());
    }

    /**
     * Method under test: {@link PublicationServiceImpl#findAllByNeedsReview(Boolean)}
     */
    @Test
    void testFindAllByNeedsReview2() {
        when(publicationRepository.findAllByNeedsReview((Boolean) any()))
                .thenThrow(new ApiRequestException("An error occurred"));
        assertThrows(ApiRequestException.class, () -> publicationServiceImpl.findAllByNeedsReview(true));
        verify(publicationRepository).findAllByNeedsReview((Boolean) any());
    }

    /**
     * Method under test: {@link PublicationServiceImpl#invalidatePublication(Long)}
     */
    @Test
    void testInvalidatePublication() {
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
        publication.setLatitude("Latitude");
        publication.setLikes(new ArrayList<>());
        publication.setLocationDescription("Location Description");
        publication.setLongitude("Longitude");
        publication.setNeedsReview(true);
        publication.setReports(new ArrayList<>());
        Optional<Publication> ofResult = Optional.of(publication);
        doNothing().when(publicationRepository).delete((Publication) any());
        when(publicationRepository.findById((Long) any())).thenReturn(ofResult);
        when(emailService.notifyExcludedPublicationAuthor((Publication) any())).thenReturn("JaneDoe");
        assertEquals("JaneDoe", publicationServiceImpl.invalidatePublication(123L));
        verify(publicationRepository).findById((Long) any());
        verify(publicationRepository).delete((Publication) any());
        verify(emailService).notifyExcludedPublicationAuthor((Publication) any());
    }

    /**
     * Method under test: {@link PublicationServiceImpl#invalidatePublication(Long)}
     */
    @Test
    void testInvalidatePublication2() {
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
        publication.setLatitude("Latitude");
        publication.setLikes(new ArrayList<>());
        publication.setLocationDescription("Location Description");
        publication.setLongitude("Longitude");
        publication.setNeedsReview(true);
        publication.setReports(new ArrayList<>());
        Optional<Publication> ofResult = Optional.of(publication);
        doNothing().when(publicationRepository).delete((Publication) any());
        when(publicationRepository.findById((Long) any())).thenReturn(ofResult);
        when(emailService.notifyExcludedPublicationAuthor((Publication) any()))
                .thenThrow(new ApiRequestException("An error occurred"));
        assertThrows(ApiRequestException.class, () -> publicationServiceImpl.invalidatePublication(123L));
        verify(publicationRepository).findById((Long) any());
        verify(publicationRepository).delete((Publication) any());
        verify(emailService).notifyExcludedPublicationAuthor((Publication) any());
    }

    /**
     * Method under test: {@link PublicationServiceImpl#invalidatePublication(Long)}
     */
    @Test
    void testInvalidatePublication3() {
        doNothing().when(publicationRepository).delete((Publication) any());
        when(publicationRepository.findById((Long) any())).thenReturn(Optional.empty());
        when(emailService.notifyExcludedPublicationAuthor((Publication) any())).thenReturn("JaneDoe");
        assertThrows(ApiRequestException.class, () -> publicationServiceImpl.invalidatePublication(123L));
        verify(publicationRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link PublicationServiceImpl#validatePublication(Long)}
     */
    @Test
    void testValidatePublication() {
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
        publication.setLatitude("Latitude");
        publication.setLikes(new ArrayList<>());
        publication.setLocationDescription("Location Description");
        publication.setLongitude("Longitude");
        publication.setNeedsReview(true);
        publication.setReports(new ArrayList<>());
        Optional<Publication> ofResult = Optional.of(publication);

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
        publication1.setLatitude("Latitude");
        publication1.setLikes(new ArrayList<>());
        publication1.setLocationDescription("Location Description");
        publication1.setLongitude("Longitude");
        publication1.setNeedsReview(true);
        publication1.setReports(new ArrayList<>());
        when(publicationRepository.save((Publication) any())).thenReturn(publication1);
        when(publicationRepository.findById((Long) any())).thenReturn(ofResult);
        when(emailService.notifyAvailablePublicationAuthor((Publication) any())).thenReturn("JaneDoe");
        assertEquals("JaneDoe", publicationServiceImpl.validatePublication(123L));
        verify(publicationRepository).save((Publication) any());
        verify(publicationRepository).findById((Long) any());
        verify(emailService).notifyAvailablePublicationAuthor((Publication) any());
    }

    /**
     * Method under test: {@link PublicationServiceImpl#validatePublication(Long)}
     */
    @Test
    void testValidatePublication2() {
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
        publication.setLatitude("Latitude");
        publication.setLikes(new ArrayList<>());
        publication.setLocationDescription("Location Description");
        publication.setLongitude("Longitude");
        publication.setNeedsReview(true);
        publication.setReports(new ArrayList<>());
        Optional<Publication> ofResult = Optional.of(publication);

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
        publication1.setLatitude("Latitude");
        publication1.setLikes(new ArrayList<>());
        publication1.setLocationDescription("Location Description");
        publication1.setLongitude("Longitude");
        publication1.setNeedsReview(true);
        publication1.setReports(new ArrayList<>());
        when(publicationRepository.save((Publication) any())).thenReturn(publication1);
        when(publicationRepository.findById((Long) any())).thenReturn(ofResult);
        when(emailService.notifyAvailablePublicationAuthor((Publication) any()))
                .thenThrow(new ApiRequestException("An error occurred"));
        assertThrows(ApiRequestException.class, () -> publicationServiceImpl.validatePublication(123L));
        verify(publicationRepository).save((Publication) any());
        verify(publicationRepository).findById((Long) any());
        verify(emailService).notifyAvailablePublicationAuthor((Publication) any());
    }

    /**
     * Method under test: {@link PublicationServiceImpl#validatePublication(Long)}
     */
    @Test
    void testValidatePublication3() {
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
        publication.setLatitude("Latitude");
        publication.setLikes(new ArrayList<>());
        publication.setLocationDescription("Location Description");
        publication.setLongitude("Longitude");
        publication.setNeedsReview(true);
        publication.setReports(new ArrayList<>());
        when(publicationRepository.save((Publication) any())).thenReturn(publication);
        when(publicationRepository.findById((Long) any())).thenReturn(Optional.empty());
        when(emailService.notifyAvailablePublicationAuthor((Publication) any())).thenReturn("JaneDoe");
        assertThrows(ApiRequestException.class, () -> publicationServiceImpl.validatePublication(123L));
        verify(publicationRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link PublicationServiceImpl#getAllPublicationsAvaliable()}
     */
    @Test
    void testGetAllPublicationsAvaliable() {
        ArrayList<Publication> publicationList = new ArrayList<>();
        when(publicationRepository.findAllAvailable()).thenReturn(publicationList);
        List<Publication> actualAllPublicationsAvaliable = publicationServiceImpl.getAllPublicationsAvaliable();
        assertSame(publicationList, actualAllPublicationsAvaliable);
        assertTrue(actualAllPublicationsAvaliable.isEmpty());
        verify(publicationRepository).findAllAvailable();
    }

    /**
     * Method under test: {@link PublicationServiceImpl#getAllPublicationsAvaliable()}
     */
    @Test
    void testGetAllPublicationsAvaliable2() {
        when(publicationRepository.findAllAvailable()).thenThrow(new ApiRequestException("An error occurred"));
        assertThrows(ApiRequestException.class, () -> publicationServiceImpl.getAllPublicationsAvaliable());
        verify(publicationRepository).findAllAvailable();
    }

    /**
     * Method under test: {@link PublicationServiceImpl#getAllByYearAndMonthOrderedByLikes(int, int)}
     */
    @Test
    void testGetAllByYearAndMonthOrderedByLikes() {
        ArrayList<Publication> publicationList = new ArrayList<>();
        when(publicationRepository.findAllByYearAndMonthOrderedByLikes(anyInt(), anyInt())).thenReturn(publicationList);
        List<Publication> actualAllByYearAndMonthOrderedByLikes = publicationServiceImpl
                .getAllByYearAndMonthOrderedByLikes(1, 1);
        assertSame(publicationList, actualAllByYearAndMonthOrderedByLikes);
        assertTrue(actualAllByYearAndMonthOrderedByLikes.isEmpty());
        verify(publicationRepository).findAllByYearAndMonthOrderedByLikes(anyInt(), anyInt());
    }

    /**
     * Method under test: {@link PublicationServiceImpl#getAllByYearAndMonthOrderedByLikes(int, int)}
     */
    @Test
    void testGetAllByYearAndMonthOrderedByLikes2() {
        when(publicationRepository.findAllByYearAndMonthOrderedByLikes(anyInt(), anyInt()))
                .thenThrow(new ApiRequestException("An error occurred"));
        assertThrows(ApiRequestException.class, () -> publicationServiceImpl.getAllByYearAndMonthOrderedByLikes(1, 1));
        verify(publicationRepository).findAllByYearAndMonthOrderedByLikes(anyInt(), anyInt());
    }

    /**
     * Method under test: {@link PublicationServiceImpl#getAllByIsResolvedYearAndMonth(int, int)}
     */
    @Test
    void testGetAllByIsResolvedYearAndMonth() {
        ArrayList<Publication> publicationList = new ArrayList<>();
        when(publicationRepository.findAllByIsResolvedYearAndMonth(anyInt(), anyInt())).thenReturn(publicationList);
        List<Publication> actualAllByIsResolvedYearAndMonth = publicationServiceImpl.getAllByIsResolvedYearAndMonth(1, 1);
        assertSame(publicationList, actualAllByIsResolvedYearAndMonth);
        assertTrue(actualAllByIsResolvedYearAndMonth.isEmpty());
        verify(publicationRepository).findAllByIsResolvedYearAndMonth(anyInt(), anyInt());
    }

    /**
     * Method under test: {@link PublicationServiceImpl#getAllByIsResolvedYearAndMonth(int, int)}
     */
    @Test
    void testGetAllByIsResolvedYearAndMonth2() {
        when(publicationRepository.findAllByIsResolvedYearAndMonth(anyInt(), anyInt()))
                .thenThrow(new ApiRequestException("An error occurred"));
        assertThrows(ApiRequestException.class, () -> publicationServiceImpl.getAllByIsResolvedYearAndMonth(1, 1));
        verify(publicationRepository).findAllByIsResolvedYearAndMonth(anyInt(), anyInt());
    }
}

