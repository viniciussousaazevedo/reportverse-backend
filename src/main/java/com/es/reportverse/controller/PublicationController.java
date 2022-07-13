package com.es.reportverse.controller;

import com.es.reportverse.DTO.MidiaDTO;
import com.es.reportverse.DTO.PublicationRequestDTO;
import com.es.reportverse.DTO.PublicationResponseDTO;
import com.es.reportverse.model.Midia;
import com.es.reportverse.model.appUserReaction.AppUserLike;
import com.es.reportverse.model.appUserReaction.AppUserReport;
import com.es.reportverse.service.PublicationService;
import com.es.reportverse.service.AppUserService;
import com.es.reportverse.service.MidiaService;
import com.es.reportverse.service.TokenManagerService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.es.reportverse.model.AppUser;
import com.es.reportverse.model.Publication;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(path = "/api/publicacao")
@AllArgsConstructor
public class PublicationController {

    PublicationService publicationService;

    AppUserService appUserService;

    MidiaService midiaService;

    TokenManagerService tokenManager;

    ModelMapper modelMapper;

    @PostMapping("/cadastro")
    public ResponseEntity<?> registerPublication(@RequestBody PublicationRequestDTO publicationRequestDTO, HttpServletRequest request) {
        Publication publication = this.publicationService.registerPublication(publicationRequestDTO, request);
        this.midiaService.registerMidias(publicationRequestDTO.getMidiasPathList(), publication.getId());

        return new ResponseEntity<>(buildPublicationReponseDTO(publication), HttpStatus.CREATED);
    }

    @GetMapping("/mapa")
    public ResponseEntity<?> getPublicationsLocations() {
        return new ResponseEntity<>(this.publicationService.getPublicationsLocations(), HttpStatus.OK);
    }

    @GetMapping("/{publicationId}")
    public ResponseEntity<?> getPublication(@PathVariable("publicationId") Long publicationId) {
        Publication publication = this.publicationService.getPublication(publicationId);


        // TODO testar e adicionar filtragem de visibilidade de publicação (nas duas UCs passadas)
        return new ResponseEntity<>(buildPublicationReponseDTO(publication), HttpStatus.OK);
    }

    @PostMapping("/curtir/{publicationId}")
    public ResponseEntity<?> manipulatePublicationLikes(@PathVariable("publicationId") Long publicationId, HttpServletRequest request) {
        AppUser user = this.tokenManager.decodeAppUserToken(request);
        Publication publication = this.publicationService.manipulatePublicationReactions(user, publicationId, new AppUserLike());


        return new ResponseEntity<>(buildPublicationReponseDTO(publication) , HttpStatus.OK);
    }

    @PostMapping("/reportar/{publicationId}")
    public ResponseEntity<?> manipulatePublicationReports(@PathVariable("publicationId") Long publicationId, HttpServletRequest request) {
        AppUser user = this.tokenManager.decodeAppUserToken(request);
        Publication publication = this.publicationService.manipulatePublicationReactions(user, publicationId, new AppUserReport());


        return new ResponseEntity<>(buildPublicationReponseDTO(publication) , HttpStatus.OK);
    }

    private PublicationResponseDTO buildPublicationReponseDTO(Publication publication) {

        List<Midia> medias = this.midiaService.getMidiasByPublicationId(publication.getId());
        PublicationResponseDTO publicationResponseDTO = this.modelMapper.map(publication, PublicationResponseDTO.class);
        publicationResponseDTO.setMedias(modelMapper.map(medias, new TypeToken<List<MidiaDTO>>() {}.getType()));

        return publicationResponseDTO;
    }
}
