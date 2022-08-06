package com.es.reportverse.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.es.reportverse.DTO.UserRegistrationDTO;
import com.es.reportverse.enums.UserRole;
import com.es.reportverse.exception.ApiRequestException;
import com.es.reportverse.model.AppUser;
import com.es.reportverse.repository.AppUserRepository;

import java.text.SimpleDateFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AppUserServiceImpl.class, BCryptPasswordEncoder.class})
@ExtendWith(SpringExtension.class)
class AppUserServiceImplTest {
    @MockBean
    private AppUserRepository appUserRepository;

    @Autowired
    private AppUserServiceImpl appUserServiceImpl;

    @MockBean
    private ModelMapper modelMapper;

    /**
     * Method under test: {@link AppUserServiceImpl#loadUserByUsername(String)}
     */
    @Test
    void testLoadUserByUsername() throws UsernameNotFoundException {
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
        Optional<AppUser> ofResult = Optional.of(appUser);
        when(appUserRepository.findByUsername((String) any())).thenReturn(ofResult);
        assertSame(appUser, appUserServiceImpl.loadUserByUsername("janedoe"));
        verify(appUserRepository).findByUsername((String) any());
    }

    /**
     * Method under test: {@link AppUserServiceImpl#loadUserByUsername(String)}
     */
    @Test
    void testLoadUserByUsername2() throws UsernameNotFoundException {
        when(appUserRepository.findByUsername((String) any())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> appUserServiceImpl.loadUserByUsername("janedoe"));
        verify(appUserRepository).findByUsername((String) any());
    }

    /**
     * Method under test: {@link AppUserServiceImpl#loadUserByUsername(String)}
     */
    @Test
    void testLoadUserByUsername3() throws UsernameNotFoundException {
        when(appUserRepository.findByUsername((String) any())).thenThrow(new ApiRequestException("An error occurred"));
        assertThrows(ApiRequestException.class, () -> appUserServiceImpl.loadUserByUsername("janedoe"));
        verify(appUserRepository).findByUsername((String) any());
    }

    /**
     * Method under test: {@link AppUserServiceImpl#registerUser(UserRegistrationDTO)}
     */
    @Test
    void testRegisterUser() {
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
        Optional<AppUser> ofResult = Optional.of(appUser);
        when(appUserRepository.findByUsername((String) any())).thenReturn(ofResult);
        assertThrows(ApiRequestException.class, () -> appUserServiceImpl
                .registerUser(new UserRegistrationDTO("Name", "janedoe", "iloveyou", "Password Confirmation")));
        verify(appUserRepository).findByUsername((String) any());
    }

    /**
     * Method under test: {@link AppUserServiceImpl#registerUser(UserRegistrationDTO)}
     */
    @Test
    void testRegisterUser2() {
        when(appUserRepository.findByUsername((String) any())).thenReturn(Optional.empty());
        assertThrows(ApiRequestException.class, () -> appUserServiceImpl
                .registerUser(new UserRegistrationDTO("Name", "janedoe", "iloveyou", "Password Confirmation")));
        verify(appUserRepository).findByUsername((String) any());
    }

    /**
     * Method under test: {@link AppUserServiceImpl#registerUser(UserRegistrationDTO)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testRegisterUser3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException
        //       at com.es.reportverse.service.AppUserServiceImpl.registerUser(AppUserServiceImpl.java:43)
        //   In order to prevent registerUser(UserRegistrationDTO)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   registerUser(UserRegistrationDTO).
        //   See https://diff.blue/R013 to resolve this issue.

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
        Optional<AppUser> ofResult = Optional.of(appUser);
        when(appUserRepository.findByUsername((String) any())).thenReturn(ofResult);
        appUserServiceImpl.registerUser(null);
    }

    /**
     * Method under test: {@link AppUserServiceImpl#registerUser(UserRegistrationDTO)}
     */
    @Test
    void testRegisterUser4() {
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
        when(appUserRepository.save((AppUser) any())).thenReturn(appUser);
        when(appUserRepository.findByUsername((String) any())).thenReturn(Optional.empty());

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
        when(modelMapper.map((Object) any(), (Class<AppUser>) any())).thenReturn(appUser1);
        AppUser actualRegisterUserResult = appUserServiceImpl
                .registerUser(new UserRegistrationDTO("Name", "janedoe", "Password Confirmation", "Password Confirmation"));
        assertSame(appUser1, actualRegisterUserResult);
        assertEquals(UserRole.UNIVERSITARIO, actualRegisterUserResult.getUserRole());
        verify(appUserRepository).save((AppUser) any());
        verify(appUserRepository).findByUsername((String) any());
        verify(modelMapper).map((Object) any(), (Class<AppUser>) any());
    }

    /**
     * Method under test: {@link AppUserServiceImpl#registerUser(UserRegistrationDTO)}
     */
    @Test
    void testRegisterUser5() {
        when(appUserRepository.save((AppUser) any())).thenThrow(new ApiRequestException("An error occurred"));
        when(appUserRepository.findByUsername((String) any())).thenReturn(Optional.empty());

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
        when(modelMapper.map((Object) any(), (Class<AppUser>) any())).thenReturn(appUser);
        assertThrows(ApiRequestException.class, () -> appUserServiceImpl
                .registerUser(new UserRegistrationDTO("Name", "janedoe", "Password Confirmation", "Password Confirmation")));
        verify(appUserRepository).save((AppUser) any());
        verify(appUserRepository).findByUsername((String) any());
        verify(modelMapper).map((Object) any(), (Class<AppUser>) any());
    }

    /**
     * Method under test: {@link AppUserServiceImpl#saveUser(AppUser)}
     */
    @Test
    void testSaveUser() {
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
        when(appUserRepository.save((AppUser) any())).thenReturn(appUser);

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
        appUserServiceImpl.saveUser(appUser1);
        verify(appUserRepository).save((AppUser) any());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        assertEquals("1969-12-31", simpleDateFormat.format(appUser1.getCreationDate()));
        assertEquals("janedoe", appUser1.getUsername());
        assertEquals(UserRole.ADMINISTRADOR, appUser1.getUserRole());
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        assertEquals("1969-12-31", simpleDateFormat1.format(appUser1.getRecoveryPasswordTokenExpiration()));
        assertEquals(123L, appUser1.getId().longValue());
        assertEquals("iloveyou", appUser1.getPassword());
        assertEquals("Name", appUser1.getName());
        assertTrue(appUser1.getLocked());
        assertEquals("ABC123", appUser1.getRecoveryPasswordToken());
        assertTrue(appUser1.getEnabled());
    }

    /**
     * Method under test: {@link AppUserServiceImpl#saveUser(AppUser)}
     */
    @Test
    void testSaveUser2() {
        when(appUserRepository.save((AppUser) any())).thenThrow(new ApiRequestException("An error occurred"));

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
        assertThrows(ApiRequestException.class, () -> appUserServiceImpl.saveUser(appUser));
        verify(appUserRepository).save((AppUser) any());
    }

    /**
     * Method under test: {@link AppUserServiceImpl#getUser(Long)}
     */
    @Test
    void testGetUser() {
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
        Optional<AppUser> ofResult = Optional.of(appUser);
        when(appUserRepository.findById((Long) any())).thenReturn(ofResult);
        assertSame(appUser, appUserServiceImpl.getUser(123L));
        verify(appUserRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link AppUserServiceImpl#getUser(Long)}
     */
    @Test
    void testGetUser2() {
        when(appUserRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(ApiRequestException.class, () -> appUserServiceImpl.getUser(123L));
        verify(appUserRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link AppUserServiceImpl#getUser(Long)}
     */
    @Test
    void testGetUser3() {
        when(appUserRepository.findById((Long) any())).thenThrow(new ApiRequestException("An error occurred"));
        assertThrows(ApiRequestException.class, () -> appUserServiceImpl.getUser(123L));
        verify(appUserRepository).findById((Long) any());
    }

    /**
     * Method under test: {@link AppUserServiceImpl#getUser(String)}
     */
    @Test
    void testGetUser4() {
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
        Optional<AppUser> ofResult = Optional.of(appUser);
        when(appUserRepository.findByUsername((String) any())).thenReturn(ofResult);
        assertSame(appUser, appUserServiceImpl.getUser("janedoe"));
        verify(appUserRepository).findByUsername((String) any());
    }

    /**
     * Method under test: {@link AppUserServiceImpl#getUser(String)}
     */
    @Test
    void testGetUser5() {
        when(appUserRepository.findByUsername((String) any())).thenReturn(Optional.empty());
        assertThrows(ApiRequestException.class, () -> appUserServiceImpl.getUser("janedoe"));
        verify(appUserRepository).findByUsername((String) any());
    }

    /**
     * Method under test: {@link AppUserServiceImpl#getUser(String)}
     */
    @Test
    void testGetUser6() {
        when(appUserRepository.findByUsername((String) any())).thenThrow(new ApiRequestException("An error occurred"));
        assertThrows(ApiRequestException.class, () -> appUserServiceImpl.getUser("janedoe"));
        verify(appUserRepository).findByUsername((String) any());
    }

    /**
     * Method under test: {@link AppUserServiceImpl#getUserByRecoveryPasswordToken(String)}
     */
    @Test
    void testGetUserByRecoveryPasswordToken() {
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
        Optional<AppUser> ofResult = Optional.of(appUser);
        when(appUserRepository.findByRecoveryPasswordToken((String) any())).thenReturn(ofResult);
        assertSame(appUser, appUserServiceImpl.getUserByRecoveryPasswordToken("ABC123"));
        verify(appUserRepository).findByRecoveryPasswordToken((String) any());
    }

    /**
     * Method under test: {@link AppUserServiceImpl#getUserByRecoveryPasswordToken(String)}
     */
    @Test
    void testGetUserByRecoveryPasswordToken2() {
        when(appUserRepository.findByRecoveryPasswordToken((String) any())).thenReturn(Optional.empty());
        assertThrows(ApiRequestException.class, () -> appUserServiceImpl.getUserByRecoveryPasswordToken("ABC123"));
        verify(appUserRepository).findByRecoveryPasswordToken((String) any());
    }

    /**
     * Method under test: {@link AppUserServiceImpl#getUserByRecoveryPasswordToken(String)}
     */
    @Test
    void testGetUserByRecoveryPasswordToken3() {
        when(appUserRepository.findByRecoveryPasswordToken((String) any()))
                .thenThrow(new ApiRequestException("An error occurred"));
        assertThrows(ApiRequestException.class, () -> appUserServiceImpl.getUserByRecoveryPasswordToken("ABC123"));
        verify(appUserRepository).findByRecoveryPasswordToken((String) any());
    }

    /**
     * Method under test: {@link AppUserServiceImpl#checkPasswordConfirmation(String, String)}
     */
    @Test
    void testCheckPasswordConfirmation() {
        assertThrows(ApiRequestException.class,
                () -> appUserServiceImpl.checkPasswordConfirmation("iloveyou", "Password Confirmation"));
    }

    /**
     * Method under test: {@link AppUserServiceImpl#checkPasswordConfirmation(String, String)}
     */
    @Test
    void testCheckPasswordConfirmation2() {
        // TODO: Complete this test.
        //   Reason: R004 No meaningful assertions found.
        //   Diffblue Cover was unable to create an assertion.
        //   Make sure that fields modified by checkPasswordConfirmation(String, String)
        //   have package-private, protected, or public getters.
        //   See https://diff.blue/R004 to resolve this issue.

        appUserServiceImpl.checkPasswordConfirmation("Password Confirmation", "Password Confirmation");
    }

    /**
     * Method under test: {@link AppUserServiceImpl#findAllByUserRole(UserRole)}
     */
    @Test
    void testFindAllByUserRole() {
        ArrayList<AppUser> appUserList = new ArrayList<>();
        when(appUserRepository.findAllByUserRole((UserRole) any())).thenReturn(appUserList);
        Collection<AppUser> actualFindAllByUserRoleResult = appUserServiceImpl.findAllByUserRole(UserRole.ADMINISTRADOR);
        assertSame(appUserList, actualFindAllByUserRoleResult);
        assertTrue(actualFindAllByUserRoleResult.isEmpty());
        verify(appUserRepository).findAllByUserRole((UserRole) any());
    }

    /**
     * Method under test: {@link AppUserServiceImpl#findAllByUserRole(UserRole)}
     */
    @Test
    void testFindAllByUserRole2() {
        ArrayList<AppUser> appUserList = new ArrayList<>();
        when(appUserRepository.findAllByUserRole((UserRole) any())).thenReturn(appUserList);
        Collection<AppUser> actualFindAllByUserRoleResult = appUserServiceImpl.findAllByUserRole(UserRole.UNIVERSITARIO);
        assertSame(appUserList, actualFindAllByUserRoleResult);
        assertTrue(actualFindAllByUserRoleResult.isEmpty());
        verify(appUserRepository).findAllByUserRole((UserRole) any());
    }

    /**
     * Method under test: {@link AppUserServiceImpl#findAllByUserRole(UserRole)}
     */
    @Test
    void testFindAllByUserRole3() {
        when(appUserRepository.findAllByUserRole((UserRole) any()))
                .thenThrow(new ApiRequestException("An error occurred"));
        assertThrows(ApiRequestException.class, () -> appUserServiceImpl.findAllByUserRole(UserRole.ADMINISTRADOR));
        verify(appUserRepository).findAllByUserRole((UserRole) any());
    }

    /**
     * Method under test: {@link AppUserServiceImpl#getUsersByYearAndMonth(int, int)}
     */
    @Test
    void testGetUsersByYearAndMonth() {
        ArrayList<AppUser> appUserList = new ArrayList<>();
        when(appUserRepository.findAllByYearAndMonth(anyInt(), anyInt())).thenReturn(appUserList);
        List<AppUser> actualUsersByYearAndMonth = appUserServiceImpl.getUsersByYearAndMonth(1, 1);
        assertSame(appUserList, actualUsersByYearAndMonth);
        assertTrue(actualUsersByYearAndMonth.isEmpty());
        verify(appUserRepository).findAllByYearAndMonth(anyInt(), anyInt());
    }

    /**
     * Method under test: {@link AppUserServiceImpl#getUsersByYearAndMonth(int, int)}
     */
    @Test
    void testGetUsersByYearAndMonth2() {
        when(appUserRepository.findAllByYearAndMonth(anyInt(), anyInt()))
                .thenThrow(new ApiRequestException("An error occurred"));
        assertThrows(ApiRequestException.class, () -> appUserServiceImpl.getUsersByYearAndMonth(1, 1));
        verify(appUserRepository).findAllByYearAndMonth(anyInt(), anyInt());
    }
}

