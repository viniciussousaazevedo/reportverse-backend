package com.es.reportverse.controller;


import com.es.reportverse.DTO.UserRegistrationDTO;
import com.es.reportverse.DTO.UserDTO;
import com.es.reportverse.model.AppUser;
import com.es.reportverse.service.TokenManagerService;
import com.es.reportverse.service.AppUserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping(path = "/api/usuario")
@AllArgsConstructor
public class AppUserController {

    AppUserService appUserService;

    ModelMapper modelMapper;

    TokenManagerService tokenDecoder;

    @PostMapping("/cadastro")
    public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDTO userRegistrationDTO) {

        AppUser appUser = this.appUserService.registerUser(userRegistrationDTO);
        UserDTO userDTO = this.modelMapper.map(appUser, UserDTO.class);

        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{appUserId}")
    public ResponseEntity<?> getUserById(@PathVariable("appUserId") Long appUserId) {
        return new ResponseEntity<>(modelMapper.map(appUserService.getUser(appUserId), UserDTO.class), HttpStatus.OK);
    }

    @GetMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.tokenDecoder.refreshToken(request, response);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getAuthenticatedUser(HttpServletRequest request){
        AppUser user = this.tokenDecoder.decodeAppUserToken(request);
        return new ResponseEntity<>(modelMapper.map(user, UserDTO.class), HttpStatus.OK);
        // ALTERAÇÃO AQUI!
    }
}
