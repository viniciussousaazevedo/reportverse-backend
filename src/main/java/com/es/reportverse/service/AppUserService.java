package com.es.reportverse.service;

import com.es.reportverse.DTO.UserRegistrationDTO;
import com.es.reportverse.model.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AppUserService extends UserDetailsService {

    AppUser rergisterUser(UserRegistrationDTO userRegistrationDTO);

    void saveUser(AppUser appUser);

    AppUser getUser(String username);
}
