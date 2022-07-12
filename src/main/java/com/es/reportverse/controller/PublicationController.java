package com.es.reportverse.controller;

import com.es.reportverse.DTO.MidiaDTO;
import com.es.reportverse.DTO.PublicationRequestDTO;
import com.es.reportverse.DTO.PublicationResponseDTO;
import com.es.reportverse.model.Midia;
import com.es.reportverse.service.PublicationService;
import com.es.reportverse.service.AppUserService;
import com.es.reportverse.service.MidiaService;
import com.es.reportverse.service.TokenManagerService;
import com.es.reportverse.service.publicationReactionLogic.PublicationReport;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import com.es.reportverse.model.AppUser;
import com.es.reportverse.model.Publication;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(path = "/api/publicacao")
public class PublicationController {

    @Autowired
    PublicationService publicationService;

    @Autowired
    AppUserService appUserService;

    @Autowired
    MidiaService midiaService;

    @Autowired
    TokenManagerService tokenManager;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/cadastro")
    public ResponseEntity<?> registerPublication(@RequestBody PublicationRequestDTO publicationRequestDTO, HttpServletRequest request) {

        Publication publication = this.publicationService.registerPublication(publicationRequestDTO, request);

        this.midiaService.registerMidias(publicationRequestDTO.getMidiasPathList(), publication.getId());

        return new ResponseEntity<>(publicationRequestDTO, HttpStatus.CREATED);
    }

    @GetMapping("/mapa")
    public ResponseEntity<?> getPublicationsLocations() {
        return new ResponseEntity<>(this.publicationService.getPublicationsLocations(), HttpStatus.OK);
    }

    @GetMapping("/{publicationId}")
    public ResponseEntity<?> getPublication(@PathVariable("publicationId") Long publicationId) {
        Publication publication = this.publicationService.getPublication(publicationId);
        List<Midia> medias = this.midiaService.getMidiasByPublicationId(publicationId);

        PublicationResponseDTO publicationDTO = this.modelMapper.map(publication, PublicationResponseDTO.class);
        medias.forEach(
                media -> publicationDTO.getMedias().add(modelMapper.map(media, MidiaDTO.class))
        );
        // TODO testar e adicionar filtragem de visibilidade de publicação (nas duas UCs passadas)
        return new ResponseEntity<>(publicationDTO, HttpStatus.OK);
    }

    @PostMapping("/denuncia/{publicationId}")
    public ResponseEntity<?> manipulatePublicationReports(@PathVariable("publicationId") Long publicationId, HttpServletRequest request) {
        AppUser user = this.tokenManager.decodeAppUserToken(request);
        Publication publication = this.publicationService.manipulatePublicationReaction(new PublicationReport(user), publicationId);

        return new ResponseEntity<>(publication , HttpStatus.OK);
    }
}
