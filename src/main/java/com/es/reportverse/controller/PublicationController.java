package com.es.reportverse.controller;

import com.es.reportverse.DTO.CommentRequestDTO;
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
@CrossOrigin
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

        return new ResponseEntity<>(buildPublicationResponseDTO(publication), HttpStatus.CREATED);
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
        return new ResponseEntity<>(buildPublicationResponseDTO(publication), HttpStatus.OK);
    }

    @PutMapping("/{publicationId}/curtir")
    public ResponseEntity<?> manipulatePublicationLikes(@PathVariable("publicationId") Long publicationId, HttpServletRequest request) {
        AppUser user = this.tokenManager.decodeAppUserToken(request);
        Publication publication = this.publicationService.manipulatePublicationReactions(user, publicationId, new AppUserLike());


        return new ResponseEntity<>(buildPublicationResponseDTO(publication) , HttpStatus.OK);
    }

    @PutMapping("/{publicationId}/reportar")
    public ResponseEntity<?> manipulatePublicationReports(@PathVariable("publicationId") Long publicationId, HttpServletRequest request) {
        AppUser user = this.tokenManager.decodeAppUserToken(request);
        Publication publication = this.publicationService.manipulatePublicationReactions(user, publicationId, new AppUserReport());


        return new ResponseEntity<>(buildPublicationResponseDTO(publication) , HttpStatus.OK);
    }

    @PostMapping("/{publicationId}/comentario")
    public ResponseEntity<?> addPublicationComment(@PathVariable("publicationId") Long publicationId, @RequestBody CommentRequestDTO commentRequestDTO, HttpServletRequest request){
        AppUser user = this.tokenManager.decodeAppUserToken(request);
        BadWordsFilter.filterText(commentRequestDTO.getText());
        Publication publication = this.publicationService.addPublicationComment(publicationId, new AppUserComment(user, commentRequestDTO.getText(), commentRequestDTO.getIsAuthorAnonymous()));

        return new ResponseEntity<>(buildPublicationResponseDTO(publication) , HttpStatus.OK);

    }

    @DeleteMapping("/{publicationId}/comentario/{commentId}")
    public ResponseEntity<?> deletePublicationComment(@PathVariable("publicationId")Long publicationId, @PathVariable("commentId") Long commentId,HttpServletRequest request){
        AppUser user = this.tokenManager.decodeAppUserToken(request);
        Publication publication = this.publicationService.deletePublicationComment(user,publicationId,commentId);
        return new ResponseEntity<>(buildPublicationResponseDTO(publication) , HttpStatus.OK);

    }

    private PublicationResponseDTO buildPublicationResponseDTO(Publication publication) {

        List<Media> medias = this.mediaService.getMediasByPublicationId(publication.getId());
        PublicationResponseDTO publicationResponseDTO = this.modelMapper.map(publication, PublicationResponseDTO.class);
        publicationResponseDTO.setMedias(modelMapper.map(medias, new TypeToken<List<MediaDTO>>() {}.getType()));

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
        AppUser user = this.tokenManager.decodeAppUserToken(request);
        return new ResponseEntity<>(this.publicationService.getPublicationsByAuthorId(user), HttpStatus.OK);
    }

    @PutMapping("/{publicationId}/resolverDenuncia")
    public ResponseEntity<?> resolvePublication(@PathVariable("publicationId") Long publicationId, HttpServletRequest request) {
        AppUser user = this.tokenManager.decodeAppUserToken(request);
        return new ResponseEntity<>(this.publicationService.resolvePublication(publicationId, user), HttpStatus.OK);
    }
  
    @GetMapping("/analise")
    public ResponseEntity<?> getPublicationsNeedReview() {
        return new ResponseEntity<>(buildPublicationsListResponseDTO(this.publicationService.findAllByNeedsReview(true)), HttpStatus.OK);
    }

    @DeleteMapping("/{publicationId}/analisar")
    public ResponseEntity<?> invalidatePublication(@PathVariable("publicationId") Long publicationId) {
        return new ResponseEntity<>(this.publicationService.invalidatePublication(publicationId), HttpStatus.OK);
    }

    @PutMapping("/{publicationId}/analisar")
    public ResponseEntity<?> validatePublication(@PathVariable("publicationId") Long publicationId) {
        return new ResponseEntity<>(this.publicationService.validatePublication(publicationId), HttpStatus.OK);
    }

    @GetMapping("/todas")
    public ResponseEntity<?> getAllPublicationsAvaliable() {
        return new ResponseEntity<>(buildPublicationsListResponseDTO(this.publicationService.getAllPublicationsAvaliable()), HttpStatus.OK);
    }
}