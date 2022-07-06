package com.es.reportverse.controller;

import com.es.reportverse.DTO.PublicationDTO;
import com.es.reportverse.service.PublicationService;
import com.es.reportverse.service.AppUserService;
import com.es.reportverse.service.MidiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.es.reportverse.model.AppUser;
import com.es.reportverse.model.Publication;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(path = "/api/publicacao")
public class PublicationController {

    @Autowired
    PublicationService publicationService;

    @Autowired
    AppUserService appUserService;

    @Autowired
    MidiaService midiaService;

    @PostMapping("/cadastro")
    public ResponseEntity<?> registerPublication(@RequestBody PublicationDTO publicationDTO, HttpServletRequest request) {

        Publication publication = this.publicationService.rergisterPublication(publicationDTO, request);

        this.midiaService.registerMidias(publicationDTO.getMidiasPathList(), publication.getId());

        return new ResponseEntity<>(publicationDTO, HttpStatus.CREATED);
    }
}
