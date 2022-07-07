package com.es.reportverse.service;

import com.es.reportverse.DTO.UserRegistrationDTO;
import com.es.reportverse.enums.UserRole;
import com.es.reportverse.exception.ApiRequestException;
import com.es.reportverse.model.AppUser;
import com.es.reportverse.repository.AppUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AppUserServiceImpl implements AppUserService {

    private final static String USER_NOT_FOUND = "Usuário não encontrado";
    private final static String USERNAME_ALREADY_TAKEN = "e-mail %s já se encontra cadastrado";
    private final String UNMATCHED_PASSWORDS = "A senha informada não coincide com a confirmação de senha";

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
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
        appUser.setUserRole(UserRole.UNIVERSITARIO);

        this.saveUser(appUser);
        return appUser;
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
        Optional<AppUser> appUserOp = this.appUserRepository.findByUsername(username);

        if (appUserOp.isEmpty()) {
            throw new ApiRequestException(USER_NOT_FOUND);
        }

        return appUserOp.get();
    }

    @Override
    public AppUser getUserByRecoveryPasswordToken(String recoveryPasswordToken) {
        Optional<AppUser> appUserOp = this.appUserRepository.findByRecoveryPasswordToken(recoveryPasswordToken);

        if (appUserOp.isEmpty()) {
            throw new ApiRequestException(USER_NOT_FOUND);
        }

        return appUserOp.get();
    }

    @Override
    public void checkPasswordConfirmation(String password, String passwordConfirmation) {
        if (!password.equals(passwordConfirmation)) {
            throw new ApiRequestException(UNMATCHED_PASSWORDS);
        }
    }
}
