package com.es.reportverse.service;

import com.es.reportverse.DTO.UserRegistrationDTO;
import com.es.reportverse.enums.UserRole;
import com.es.reportverse.model.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Collection;
import java.util.List;

public interface AppUserService extends UserDetailsService {

    AppUser registerUser(UserRegistrationDTO userRegistrationDTO);

    void saveUser(AppUser appUser);

    AppUser getUser(String username);

    AppUser getUser(Long id);

    AppUser getUserByRecoveryPasswordToken(String recoveryPasswordToken);

    void checkPasswordConfirmation(String password, String passwordConfirmation);

    Collection<AppUser> findAllByUserRole(UserRole userRole);

    List<AppUser> getUsersByYearAndMonth(int year, int month);

}
