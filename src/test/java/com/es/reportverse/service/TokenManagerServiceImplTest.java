package com.es.reportverse.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.auth0.jwt.algorithms.Algorithm;
import com.es.reportverse.enums.UserRole;
import com.es.reportverse.exception.ApiRequestException;
import com.es.reportverse.model.AppUser;
import com.es.reportverse.repository.AppUserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

@ContextConfiguration(classes = {TokenManagerServiceImpl.class})
@ExtendWith(SpringExtension.class)
class TokenManagerServiceImplTest {
    @MockBean
    private AppUserService appUserService;

    @Autowired
    private TokenManagerServiceImpl tokenManagerServiceImpl;

    /**
     * Method under test: {@link TokenManagerServiceImpl#decodeToken(String, Algorithm)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testDecodeToken() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalArgumentException: The Algorithm cannot be null.
        //       at com.auth0.jwt.JWTVerifier$BaseVerification.<init>(JWTVerifier.java:57)
        //       at com.auth0.jwt.JWTVerifier.init(JWTVerifier.java:46)
        //       at com.auth0.jwt.JWT.require(JWT.java:56)
        //       at com.es.reportverse.service.TokenManagerServiceImpl.decodeToken(TokenManagerServiceImpl.java:37)
        //   In order to prevent decodeToken(String, Algorithm)
        //   from throwing IllegalArgumentException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   decodeToken(String, Algorithm).
        //   See https://diff.blue/R013 to resolve this issue.

        tokenManagerServiceImpl.decodeToken("ABC123", null);
    }

    /**
     * Method under test: {@link TokenManagerServiceImpl#decodeToken(String, Algorithm)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testDecodeToken2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   com.auth0.jwt.exceptions.JWTDecodeException: The token was expected to have 3 parts, but got 1.
        //       at com.auth0.jwt.TokenUtils.splitToken(TokenUtils.java:21)
        //       at com.auth0.jwt.JWTDecoder.<init>(JWTDecoder.java:36)
        //       at com.auth0.jwt.JWTVerifier.verify(JWTVerifier.java:282)
        //       at com.es.reportverse.service.TokenManagerServiceImpl.decodeToken(TokenManagerServiceImpl.java:38)
        //   In order to prevent decodeToken(String, Algorithm)
        //   from throwing JWTDecodeException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   decodeToken(String, Algorithm).
        //   See https://diff.blue/R013 to resolve this issue.

        tokenManagerServiceImpl.decodeToken("ABC123", mock(Algorithm.class));
    }

    /**
     * Method under test: {@link TokenManagerServiceImpl#createAppUserToken(HttpServletRequest, Authentication)}
     */
    @Test
    void testCreateAppUserToken() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.ClassCastException: class java.lang.String cannot be cast to class com.es.reportverse.model.AppUser (java.lang.String is in module java.base of loader 'bootstrap'; com.es.reportverse.model.AppUser is in unnamed module of loader com.diffblue.cover.e.g @2264ddf9)
        //       at com.es.reportverse.service.TokenManagerServiceImpl.createAppUserToken(TokenManagerServiceImpl.java:56)
        //   In order to prevent createAppUserToken(HttpServletRequest, Authentication)
        //   from throwing ClassCastException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   createAppUserToken(HttpServletRequest, Authentication).
        //   See https://diff.blue/R013 to resolve this issue.

        ModelMapper modelMapper = mock(ModelMapper.class);
        doNothing().when(modelMapper).addConverter((Converter<Object, Object>) any());
        modelMapper.addConverter((Converter<Object, Object>) mock(Converter.class));
        AppUserRepository appUserRepository = mock(AppUserRepository.class);
        TokenManagerServiceImpl tokenManagerServiceImpl = new TokenManagerServiceImpl(
                new AppUserServiceImpl(appUserRepository, modelMapper, new BCryptPasswordEncoder()));
        MockHttpServletRequest request = new MockHttpServletRequest();

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
        tokenManagerServiceImpl.createAppUserToken(request, new TestingAuthenticationToken(appUser, "Credentials"));
        verify(modelMapper).addConverter((Converter<Object, Object>) any());
    }

    /**
     * Method under test: {@link TokenManagerServiceImpl#decodeAppUserToken(HttpServletRequest)}
     */
    @Test
    void testDecodeAppUserToken() {
        assertThrows(ApiRequestException.class,
                () -> tokenManagerServiceImpl.decodeAppUserToken(new MockHttpServletRequest()));
    }

    /**
     * Method under test: {@link TokenManagerServiceImpl#decodeAppUserToken(HttpServletRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testDecodeAppUserToken2() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //       at com.es.reportverse.service.TokenManagerServiceImpl.decodeAppUserToken(TokenManagerServiceImpl.java:68)
        //   In order to prevent decodeAppUserToken(HttpServletRequest)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   decodeAppUserToken(HttpServletRequest).
        //   See https://diff.blue/R013 to resolve this issue.

        tokenManagerServiceImpl.decodeAppUserToken(null);
    }

    /**
     * Method under test: {@link TokenManagerServiceImpl#decodeAppUserToken(HttpServletRequest)}
     */
    @Test
    void testDecodeAppUserToken3() {
        DefaultMultipartHttpServletRequest defaultMultipartHttpServletRequest = mock(
                DefaultMultipartHttpServletRequest.class);
        when(defaultMultipartHttpServletRequest.getHeader((String) any())).thenReturn("https://example.org/example");
        assertThrows(ApiRequestException.class,
                () -> tokenManagerServiceImpl.decodeAppUserToken(defaultMultipartHttpServletRequest));
        verify(defaultMultipartHttpServletRequest).getHeader((String) any());
    }

    /**
     * Method under test: {@link TokenManagerServiceImpl#decodeAppUserToken(HttpServletRequest)}
     */
    @Test
    void testDecodeAppUserToken4() {
        DefaultMultipartHttpServletRequest defaultMultipartHttpServletRequest = mock(
                DefaultMultipartHttpServletRequest.class);
        when(defaultMultipartHttpServletRequest.getHeader((String) any())).thenReturn("Bearer ");
        assertThrows(ApiRequestException.class,
                () -> tokenManagerServiceImpl.decodeAppUserToken(defaultMultipartHttpServletRequest));
        verify(defaultMultipartHttpServletRequest).getHeader((String) any());
    }
}

