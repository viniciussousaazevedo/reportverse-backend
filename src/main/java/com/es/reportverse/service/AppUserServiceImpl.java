package com.es.reportverse.service;

import com.es.reportverse.DTO.UserRegistrationDTO;
import com.es.reportverse.enums.UserRole;
import com.es.reportverse.exception.ApiRequestException;
import com.es.reportverse.model.AppUser;
import com.es.reportverse.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final static String USER_NOT_FOUND = "Usuário não encontrado";
    private final static String USERNAME_ALREADY_TAKEN = "e-mail %s já se encontra cadastrado";
    private final String UNMATCHED_PASSWORDS = "A senha informada não coincide com a confirmação de senha";

    private AppUserRepository appUserRepository;

    private ModelMapper modelMapper;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepository.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(USER_NOT_FOUND));
    }

    @Override
    public AppUser registerUser(UserRegistrationDTO userRegistrationDTO) {
        this.checkUsername(userRegistrationDTO.getUsername());
        this.checkPasswordConfirmation(userRegistrationDTO.getPassword(), userRegistrationDTO.getPasswordConfirmation());

        userRegistrationDTO.setPassword(bCryptPasswordEncoder.encode(userRegistrationDTO.getPassword()));

        AppUser appUser = this.modelMapper.map(userRegistrationDTO, AppUser.class);
        setAppUserDefaultValues(appUser);

        this.saveUser(appUser);
        return appUser;
    }

    private void setAppUserDefaultValues(AppUser appUser) {
        appUser.setUserRole(UserRole.UNIVERSITARIO);
        appUser.setCreationDate(new Date());
    }

    private void checkUsername(String username) {
        if (this.appUserRepository.findByUsername(username).isPresent()) {
            throw new ApiRequestException(String.format(USERNAME_ALREADY_TAKEN, username));
        }
    }

    @Override
    public void saveUser(AppUser appUser) {
        this.appUserRepository.save(appUser);
    }

    @Override
    public AppUser getUser(String username) {
        return this.appUserRepository.findByUsername(username)
                .orElseThrow(() -> new ApiRequestException(USER_NOT_FOUND));
    }

    @Override
    public AppUser getUser(Long id) {
        return this.appUserRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException(USER_NOT_FOUND));
    }

    @Override
    public AppUser getUserByRecoveryPasswordToken(String recoveryPasswordToken) {
        return this.appUserRepository.findByRecoveryPasswordToken(recoveryPasswordToken)
                .orElseThrow(() -> new ApiRequestException(USER_NOT_FOUND));
    }

    @Override
    public void checkPasswordConfirmation(String password, String passwordConfirmation) {
        if (!password.equals(passwordConfirmation)) {
            throw new ApiRequestException(UNMATCHED_PASSWORDS);
        }
    }

    @Override
    public Collection<AppUser> findAllByUserRole(UserRole userRole) {
        return this.appUserRepository.findAllByUserRole(userRole);
    }

    @Override
    public List<AppUser> getUsersByYearAndMonth(int year, int month) {
        return this.appUserRepository.findAllByYearAndMonth(year, month);
    }
}
