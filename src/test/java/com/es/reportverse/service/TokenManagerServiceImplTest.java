package com.es.reportverse.service;

import static com.es.reportverse.security.config.TokenConstants.SECRET_WORD_FOR_TOKEN_GENERATION;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.es.reportverse.enums.UserRole;
import com.es.reportverse.exception.ApiRequestException;
import com.es.reportverse.model.AppUser;
import com.es.reportverse.repository.AppUserRepository;

import java.io.IOException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
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
    void testDecodeToken() {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_WORD_FOR_TOKEN_GENERATION.getBytes());
        String mockToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZWRyb0BnbWFpbC5jb20iLCJpc3MiOiIvYXBpL2xvZ2luIiwiZXhwIjoxNjYxMDA1OTk0LCJ1c2VyUm9sZSI6WyJVTklWRVJTSVRBUklPIl19.d9_DY-3PDyy2QoZP8uTZ0HmiqIUdCWvb6nO6973l5u4";
        tokenManagerServiceImpl.decodeToken(mockToken, algorithm);
    }

    /**
     * Method under test: {@link TokenManagerServiceImpl#tokenDecodeError(Exception, HttpServletResponse)}
     */
    @Test
    void testTokenDecodeError() throws IOException {
        Exception e = new JWTVerificationException("message");
        HttpServletResponse response = new MockHttpServletResponse();
        this.tokenManagerServiceImpl.tokenDecodeError(e, response);
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
    void testDecodeAppUserToken2() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        String mockToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZWRyb0BnbWFpbC5jb20iLCJpc3MiOiIvYXBpL2xvZ2luIiwiZXhwIjoxNjYxMDA1OTk0LCJ1c2VyUm9sZSI6WyJVTklWRVJTSVRBUklPIl19.d9_DY-3PDyy2QoZP8uTZ0HmiqIUdCWvb6nO6973l5u4";
        String auth = "Bearer " + mockToken;
        request.addHeader("Authorization", auth);

        tokenManagerServiceImpl.decodeAppUserToken(request);
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

    /**
     * Method under test: {@link TokenManagerServiceImpl#refreshToken(HttpServletRequest, HttpServletResponse)}
     */
    @Test
    void testRefreshToken() throws IOException {
        // TODO: Complete this test.
        //   Reason: R020 Temporary files were created but not deleted.
        //   The method under test created the following temporary files without deleting
        //   them:
        //     /home/pedro/IdeaProjects/reportverse-backend/test.txt
        //   Please ensure that temporary files are deleted in the method under test.
        //   See https://diff.blue/R020


        MockHttpServletRequest request = new MockHttpServletRequest();
        String mockToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwZWRyb0BnbWFpbC5jb20iLCJpc3MiOiIvYXBpL2xvZ2luIiwiZXhwIjoxNjYxMDA1OTk0LCJ1c2VyUm9sZSI6WyJVTklWRVJTSVRBUklPIl19.d9_DY-3PDyy2QoZP8uTZ0HmiqIUdCWvb6nO6973l5u4";
        String auth = "Bearer " + mockToken;
        request.addHeader("Authorization", auth);

        HttpServletResponse response = new MockHttpServletResponse();

        this.tokenManagerServiceImpl.refreshToken(request, response);

    }
}

