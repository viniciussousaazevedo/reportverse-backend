package com.es.reportverse.controller;

import com.es.reportverse.DTO.*;
import com.es.reportverse.model.media.PublicationMedia;
import com.es.reportverse.model.AppUserComment;
import com.es.reportverse.model.appUserReaction.AppUserLike;
import com.es.reportverse.model.appUserReaction.AppUserReport;
import com.es.reportverse.service.PublicationService;
import com.es.reportverse.service.AppUserService;
import com.es.reportverse.service.PublicationMediaService;
import com.es.reportverse.service.TokenManagerService;
import com.es.reportverse.utils.CustomLogger;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.es.reportverse.model.AppUser;
import com.es.reportverse.model.Publication;
import javax.servlet.http.HttpServletRequest;

import com.es.reportverse.utils.BadWordsFilter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/api/publicacao")
@AllArgsConstructor
public class PublicationController {

    private final String CLASS = "PublicationController:";

    CustomLogger logger;

    PublicationService publicationService;

    AppUserService appUserService;

    PublicationMediaService publicationMediaService;

    TokenManagerService tokenManager;

    ModelMapper modelMapper;

    private String setContext(String method) {
        return CLASS + ":" + method;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<?> registerPublication(@ModelAttribute PublicationRequestDTO publicationRequestDTO, HttpServletRequest request) {
        logger.setContext(this.setContext("registerPublication"));
        logger.logMethodStart(publicationRequestDTO);

        Publication publication = this.publicationService.registerPublication(publicationRequestDTO, request);
        this.publicationMediaService.registerMedias(publicationRequestDTO.getMediasBytesList(), publication.getId());

        logger.logMethodEnd(publication);
        return new ResponseEntity<>(buildPublicationResponseDTO(publication), HttpStatus.CREATED);
    }

    @GetMapping("/mapa")
    public ResponseEntity<?> getPublicationsLocations() {
        logger.setContext(this.setContext("getPublicationsLocations"));
        logger.logMethodStart();
        List<PublicationLocationDTO> publicationLocationDTOS = this.publicationService.getPublicationsLocations();

        logger.logMethodEnd(publicationLocationDTOS);
        return new ResponseEntity<>(publicationLocationDTOS, HttpStatus.OK);
    }

    @GetMapping("/{publicationId}")
    public ResponseEntity<?> getPublication(@PathVariable("publicationId") Long publicationId) {
        logger.setContext(this.setContext("getPublication"));

        Publication publication = this.publicationService.getPublication(publicationId);
        PublicationResponseDTO publicationResponseDTO = buildPublicationResponseDTO(publication);
        logger.logMethodEnd(publicationResponseDTO);

        return new ResponseEntity<>(publicationResponseDTO, HttpStatus.OK);
    }

    @PutMapping("/{publicationId}/curtir")
    public ResponseEntity<?> manipulatePublicationLikes(@PathVariable("publicationId") Long publicationId, HttpServletRequest request) {
        logger.setContext(this.setContext("manipulatePublicationLikes"));
        logger.logMethodStart(publicationId);

        AppUser user = this.tokenManager.decodeAppUserToken(request);
        Publication publication = this.publicationService.manipulatePublicationReactions(user, publicationId, new AppUserLike());

        logger.logMethodEnd(publication);

        return new ResponseEntity<>(buildPublicationResponseDTO(publication) , HttpStatus.OK);
    }

    @PutMapping("/{publicationId}/reportar")
    public ResponseEntity<?> manipulatePublicationReports(@PathVariable("publicationId") Long publicationId, HttpServletRequest request) {
        logger.setContext(this.setContext("manipulatePublicationReports"));
        logger.logMethodStart(publicationId);

        AppUser user = this.tokenManager.decodeAppUserToken(request);
        Publication publication = this.publicationService.manipulatePublicationReactions(user, publicationId, new AppUserReport());

        logger.logMethodEnd(publication);

        return new ResponseEntity<>(buildPublicationResponseDTO(publication) , HttpStatus.OK);
    }

    @PostMapping("/{publicationId}/comentario")
    public ResponseEntity<?> addPublicationComment(@PathVariable("publicationId") Long publicationId, @RequestBody CommentRequestDTO commentRequestDTO, HttpServletRequest request){
        logger.setContext(this.setContext("addPublicationComment"));
        logger.logMethodStart(commentRequestDTO);

        AppUser user = this.tokenManager.decodeAppUserToken(request);
        BadWordsFilter.filterText(commentRequestDTO.getText());
        Publication publication = this.publicationService.addPublicationComment(publicationId, new AppUserComment(user, commentRequestDTO.getText(), commentRequestDTO.getIsAuthorAnonymous(),new Date()));

        logger.logMethodEnd(publication);
        return new ResponseEntity<>(buildPublicationResponseDTO(publication) , HttpStatus.OK);

    }

    @DeleteMapping("/{publicationId}/comentario/{commentId}")
    public ResponseEntity<?> deletePublicationComment(@PathVariable("publicationId")Long publicationId, @PathVariable("commentId") Long commentId,HttpServletRequest request){
        logger.setContext(this.setContext("deletePublicationComment"));
        logger.logMethodStart(commentId);

        AppUser user = this.tokenManager.decodeAppUserToken(request);
        Publication publication = this.publicationService.deletePublicationComment(user,publicationId,commentId);

        logger.logMethodEnd(publication);
        return new ResponseEntity<>(buildPublicationResponseDTO(publication) , HttpStatus.OK);

    }

    private PublicationResponseDTO buildPublicationResponseDTO(Publication publication) {

        List<PublicationMedia> publicationMedia = this.publicationMediaService.getMediasByPublicationId(publication.getId());
        PublicationResponseDTO publicationResponseDTO = this.modelMapper.map(publication, PublicationResponseDTO.class);

        publicationResponseDTO.setAuthorName(
                publication.getIsAuthorAnonymous() ?
                    null :
                    appUserService.getUser(publication.getAuthorId()).getName()
        );


        publicationResponseDTO.setMedias(modelMapper.map(publicationMedia, new TypeToken<List<MediaDTO>>() {}.getType()));

        return publicationResponseDTO;
    }

    private List<PublicationResponseDTO> buildPublicationsListResponseDTO(Collection<Publication> collection) {
        List<PublicationResponseDTO> publicationsListResponseDTO = new ArrayList<>();

        for (Publication publication : collection) {
            publicationsListResponseDTO.add(buildPublicationResponseDTO(publication));
        }

        return publicationsListResponseDTO;
    }

    @GetMapping("/exibirDenunciasAutor")
    public ResponseEntity<?> getPublicationsByAuthorId(HttpServletRequest request) {
        logger.setContext(this.setContext("getPublicationsByAuthorId"));
        logger.logMethodStart();

        AppUser user = this.tokenManager.decodeAppUserToken(request);
        List<Publication> publications = this.publicationService.getPublicationsByAuthorId(user);

        logger.logMethodEnd(publications);
        return new ResponseEntity<>(publications, HttpStatus.OK);
    }

    @PutMapping("/{publicationId}/resolverDenuncia")
    public ResponseEntity<?> resolvePublication(@PathVariable("publicationId") Long publicationId, HttpServletRequest request) {
        logger.setContext(this.setContext("resolvePublication"));
        logger.logMethodStart(publicationId);

        AppUser user = this.tokenManager.decodeAppUserToken(request);
        Publication publication = this.publicationService.resolvePublication(publicationId, user);

        logger.logMethodEnd(publication);
        return new ResponseEntity<>(publication, HttpStatus.OK);
    }
  
    @GetMapping("/analise")
    public ResponseEntity<?> getPublicationsNeedReview() {
        logger.setContext(this.setContext("getPublicationsNeedReview"));
        logger.logMethodStart();

        List<PublicationResponseDTO> publications = buildPublicationsListResponseDTO(this.publicationService.findAllByNeedsReview(true));

        logger.logMethodEnd(publications);

        return new ResponseEntity<>(publications, HttpStatus.OK);
    }

    @DeleteMapping("/{publicationId}/analisar")
    public ResponseEntity<?> invalidatePublication(@PathVariable("publicationId") Long publicationId) {
        logger.setContext(this.setContext("invalidatePublication"));
        logger.logMethodStart(publicationId);

        String invalidation = this.publicationService.invalidatePublication(publicationId);

        logger.logMethodEnd(invalidation);
        return new ResponseEntity<>(invalidation, HttpStatus.OK);
    }

    @PutMapping("/{publicationId}/analisar")
    public ResponseEntity<?> validatePublication(@PathVariable("publicationId") Long publicationId) {
        logger.setContext(this.setContext("validatePublication"));
        logger.logMethodStart(publicationId);

        String validation = this.publicationService.validatePublication(publicationId);

        logger.logMethodEnd(validation);
        return new ResponseEntity<>(validation, HttpStatus.OK);
    }

    @GetMapping("/todas")
    public ResponseEntity<?> getAllPublicationsAvaliable() {
        logger.setContext(this.setContext("getAllPublicationsAvaliable"));

        List<PublicationResponseDTO> publications = buildPublicationsListResponseDTO(this.publicationService.getAllPublicationsAvaliable());
        logger.logMethodEnd(publications);
        return new ResponseEntity<>(publications, HttpStatus.OK);
    }
}