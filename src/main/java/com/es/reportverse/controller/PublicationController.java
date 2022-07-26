package com.es.reportverse.controller;

import com.es.reportverse.DTO.CommentDTO;
import com.es.reportverse.DTO.MediaDTO;
import com.es.reportverse.DTO.PublicationRequestDTO;
import com.es.reportverse.DTO.PublicationResponseDTO;
import com.es.reportverse.model.Media;
import com.es.reportverse.model.AppUserComment;
import com.es.reportverse.model.appUserReaction.AppUserLike;
import com.es.reportverse.model.appUserReaction.AppUserReport;
import com.es.reportverse.service.PublicationService;
import com.es.reportverse.service.AppUserService;
import com.es.reportverse.service.MediaService;
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

import com.es.reportverse.utils.BadWordsFilter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(path = "/api/publicacao")
@AllArgsConstructor
public class PublicationController {

    PublicationService publicationService;

    AppUserService appUserService;

    MediaService mediaService;

    TokenManagerService tokenManager;

    ModelMapper modelMapper;

    @PostMapping("/cadastro")
    public ResponseEntity<?> registerPublication(@RequestBody PublicationRequestDTO publicationRequestDTO, HttpServletRequest request) {
        Publication publication = this.publicationService.registerPublication(publicationRequestDTO, request);
        this.mediaService.registerMedias(publicationRequestDTO.getMediasPathList(), publication.getId());

        return new ResponseEntity<>(buildPublicationReponseDTO(publication), HttpStatus.CREATED);
    }

    @GetMapping("/mapa")
    public ResponseEntity<?> getPublicationsLocations() {
        return new ResponseEntity<>(this.publicationService.getPublicationsLocations(), HttpStatus.OK);
    }

    @GetMapping("/{publicationId}")
    public ResponseEntity<?> getPublication(@PathVariable("publicationId") Long publicationId) {
        Publication publication = this.publicationService.getPublication(publicationId);

        // TODO adicionar filtragem de visibilidade de publicação
        // Ou criar novo endpoint apenas para get de publicações disponíveis
        return new ResponseEntity<>(buildPublicationReponseDTO(publication), HttpStatus.OK);
    }

    @PutMapping("/curtir/{publicationId}")
    public ResponseEntity<?> manipulatePublicationLikes(@PathVariable("publicationId") Long publicationId, HttpServletRequest request) {
        AppUser user = this.tokenManager.decodeAppUserToken(request);
        Publication publication = this.publicationService.manipulatePublicationReactions(user, publicationId, new AppUserLike());


        return new ResponseEntity<>(buildPublicationReponseDTO(publication) , HttpStatus.OK);
    }

    @PutMapping("/reportar/{publicationId}")
    public ResponseEntity<?> manipulatePublicationReports(@PathVariable("publicationId") Long publicationId, HttpServletRequest request) {
        AppUser user = this.tokenManager.decodeAppUserToken(request);
        Publication publication = this.publicationService.manipulatePublicationReactions(user, publicationId, new AppUserReport());


        return new ResponseEntity<>(buildPublicationReponseDTO(publication) , HttpStatus.OK);
    }

    @PutMapping("/comentar/{publicationId}")
    public ResponseEntity<?> manipulatePublicationComments(@PathVariable("publicationId") Long publicationId, @RequestBody CommentDTO commentDTO, HttpServletRequest request){
        AppUser user = this.tokenManager.decodeAppUserToken(request);
        BadWordsFilter.filterText(commentDTO.getText());
        Publication publication = this.publicationService.manipulatePublicationComments(user, publicationId, new AppUserComment(user,commentDTO.getText(),commentDTO.getIsAuthorAnonymous()));

        return new ResponseEntity<>(buildPublicationReponseDTO(publication) , HttpStatus.OK);

    }

    private PublicationResponseDTO buildPublicationReponseDTO(Publication publication) {

        List<Media> medias = this.mediaService.getMediasByPublicationId(publication.getId());
        PublicationResponseDTO publicationResponseDTO = this.modelMapper.map(publication, PublicationResponseDTO.class);
        publicationResponseDTO.setMedias(modelMapper.map(medias, new TypeToken<List<MediaDTO>>() {}.getType()));

        return publicationResponseDTO;
    }

    private List<PublicationResponseDTO> buildPublicationsListReponseDTO(Collection<Publication> collection) {
        List<PublicationResponseDTO> publicationsListResponseDTO = new ArrayList<>();

        for (Publication publication : collection) {
            publicationsListResponseDTO.add(buildPublicationReponseDTO(publication));
        }

        return publicationsListResponseDTO;
    }

    @GetMapping("/exibirDenunciasAutor")
    public ResponseEntity<?> getPublicationsByAuthorId(HttpServletRequest request) {
        AppUser user = this.tokenManager.decodeAppUserToken(request);
        return new ResponseEntity<>(this.publicationService.getPublicationsByAuthorId(user), HttpStatus.OK);
    }

    @PutMapping("/resolverDenuncia/{publicationId}")
    public ResponseEntity<?> resolvePublication(@PathVariable("publicationId") Long publicationId, HttpServletRequest request) {
        AppUser user = this.tokenManager.decodeAppUserToken(request);
        return new ResponseEntity<>(this.publicationService.resolvePublication(publicationId, user), HttpStatus.OK);
    }
  
    @GetMapping("/analise")
    public ResponseEntity<?> getPublicationsNeedReview() {
        return new ResponseEntity<>(buildPublicationsListReponseDTO(this.publicationService.findAllByNeedsReview(true)), HttpStatus.OK);
    }

    @DeleteMapping("/analisar/{publicationId}")
    public ResponseEntity<?> invalidatePublication(@PathVariable("publicationId") Long publicationId) {
        return new ResponseEntity<>(this.publicationService.invalidatePublication(publicationId), HttpStatus.OK);
    }

    @PutMapping("/analisar/{publicationId}")
    public ResponseEntity<?> validatePublication(@PathVariable("publicationId") Long publicationId) {
        return new ResponseEntity<>(this.publicationService.validatePublication(publicationId), HttpStatus.OK);
    }

    @GetMapping("/todas")
    public ResponseEntity<?> getAllPublicationsAvaliable() {
        return new ResponseEntity<>(buildPublicationsListReponseDTO(this.publicationService.getAllPublicationsAvaliable()), HttpStatus.OK);
    }
}