package com.es.reportverse.controller;

import com.es.reportverse.DTO.PublicationDTO;
import com.es.reportverse.service.PublicationService;
import com.es.reportverse.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.es.reportverse.model.AppUser;

@RestController
@RequestMapping(path = "/api/publicacao")
public class PublicationController {

    @Autowired
    PublicationService publicationService;

    @Autowired
    AppUserService appUserService;

    @PostMapping("/cadastro")
    public ResponseEntity<?> registerPublication(@RequestBody PublicationDTO publicationDTO) {

        this.publicationService.rergisterPublication(publicationDTO);

        return new ResponseEntity<>(publicationDTO, HttpStatus.CREATED);
    }
}
