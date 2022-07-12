package com.es.reportverse.controller;

import com.es.reportverse.DTO.PublicationDTO;
import com.es.reportverse.enums.UserRole;
import com.es.reportverse.service.PublicationService;
import com.es.reportverse.service.AppUserService;
import com.es.reportverse.service.MediaService;
import com.es.reportverse.service.TokenManagerService;
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
    MediaService mediaService;

    @Autowired
    TokenManagerService tokenManager;

    @PostMapping("/cadastro")
    public ResponseEntity<?> registerPublication(@RequestBody PublicationDTO publicationDTO, HttpServletRequest request) {

        Publication publication = this.publicationService.registerPublication(publicationDTO, request);

        this.mediaService.registerMedias(publicationDTO.getMediasPathList(), publication.getId());

        return new ResponseEntity<>(publicationDTO, HttpStatus.CREATED);
    }

    @GetMapping("/mapa")
    public ResponseEntity<?> getPublicationsLocations() {
        return new ResponseEntity<>(this.publicationService.getPublicationsLocations(), HttpStatus.OK);
    }

    @GetMapping("/{publicationId}")
    public ResponseEntity<?> getPublication(@PathVariable("publicationId") Long publicationId) {
        return new ResponseEntity<>(this.publicationService.getPublication(publicationId), HttpStatus.OK);
    }

    @PostMapping("/denunciar/{publicationId}")
    public ResponseEntity<?> manipulatePublicationReports(@PathVariable("publicationId") Long publicationId, HttpServletRequest request) {
        AppUser user = this.tokenManager.decodeAppUserToken(request);
        return new ResponseEntity<>(this.publicationService.manipulatePublicationReports(user, publicationId), HttpStatus.OK);
    }

    @GetMapping("/exibirDenunciasAutor")
    public ResponseEntity<?> getPublicationsByAuthorId(HttpServletRequest request) {
        AppUser user = this.tokenManager.decodeAppUserToken(request);
        return new ResponseEntity<>(this.publicationService.getPublicationsByAuthorId(user), HttpStatus.OK);
    }

    @PostMapping("/resolverDenuncia/{publicationId}")
    public ResponseEntity<?> resolvePublication(@PathVariable("publicationId") Long publicationId, HttpServletRequest request) {
        AppUser user = this.tokenManager.decodeAppUserToken(request);
        return new ResponseEntity<>(this.publicationService.resolvePublication(publicationId, user), HttpStatus.OK);
    }
}