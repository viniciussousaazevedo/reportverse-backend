package com.es.reportverse.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.es.reportverse.DTO.PasswordRecoveryDTO;
import com.es.reportverse.enums.UserRole;
import com.es.reportverse.exception.ApiRequestException;
import com.es.reportverse.model.AppUser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {PasswordServiceImpl.class, BCryptPasswordEncoder.class})
@ExtendWith(SpringExtension.class)
class PasswordServiceImplTest {
    @MockBean
    private AppUserService appUserService;

    @Autowired
    private PasswordServiceImpl passwordServiceImpl;

    /**
     * Method under test: {@link PasswordServiceImpl#getPasswordRecoveryLink(String)}
     */
    @Test
    void testGetPasswordRecoveryLink() {
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
        doNothing().when(appUserService).saveUser((AppUser) any());
        when(appUserService.getUser((String) any())).thenReturn(appUser);
        passwordServiceImpl.getPasswordRecoveryLink("janedoe");
        verify(appUserService).getUser((String) any());
        verify(appUserService).saveUser((AppUser) any());
    }

    /**
     * Method under test: {@link PasswordServiceImpl#getPasswordRecoveryLink(String)}
     */
    @Test
    void testGetPasswordRecoveryLink2() {
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
        doThrow(new ApiRequestException("An error occurred")).when(appUserService).saveUser((AppUser) any());
        when(appUserService.getUser((String) any())).thenReturn(appUser);
        assertThrows(ApiRequestException.class, () -> passwordServiceImpl.getPasswordRecoveryLink("janedoe"));
        verify(appUserService).getUser((String) any());
        verify(appUserService).saveUser((AppUser) any());
    }

    /**
     * Method under test: {@link PasswordServiceImpl#setNewPassword(PasswordRecoveryDTO, String)}
     */
    @Test
    void testSetNewPassword() {
        AppUser appUser = new AppUser();
        LocalDateTime atStartOfDayResult = LocalDate.of(2022, 8, 4).atStartOfDay();
        appUser.setCreationDate(Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        appUser.setEnabled(true);
        appUser.setId(123L);
        appUser.setLocked(true);
        appUser.setName("Name");
        appUser.setPassword("iloveyou");
        appUser.setRecoveryPasswordToken("ABC123");
        LocalDateTime atStartOfDayResult1 = LocalDate.of(2022, 8, 8).atStartOfDay();
        appUser.setRecoveryPasswordTokenExpiration(Date.from(atStartOfDayResult1.atZone(ZoneId.of("UTC")).toInstant()));
        appUser.setUserRole(UserRole.ADMINISTRADOR);
        appUser.setUsername("janedoe");
        when(appUserService.getUserByRecoveryPasswordToken((String) any())).thenReturn(appUser);
        passwordServiceImpl.setNewPassword(new PasswordRecoveryDTO("iloveyou", "Password Confirmation"), "ABC123");
    }

    /**
     * Method under test: {@link PasswordServiceImpl#setNewPassword(PasswordRecoveryDTO, String)}
     */
    @Test
    void testSetNewPassword2() {
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
        when(appUserService.getUserByRecoveryPasswordToken((String) any())).thenReturn(appUser);

        assertThrows(ApiRequestException.class, () -> passwordServiceImpl.setNewPassword(new PasswordRecoveryDTO("iloveyou", "Password Confirmation"), "ABC123"));

    }
}

