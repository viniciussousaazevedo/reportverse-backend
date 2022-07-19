package com.es.reportverse.service;

import com.es.reportverse.DTO.PublicationRequestDTO;
import com.es.reportverse.DTO.PublicationLocationDTO;
import com.es.reportverse.model.AppUser;
import com.es.reportverse.model.Publication;
import com.es.reportverse.model.appUserReaction.AppUserReaction;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;

public interface PublicationService {

    Publication registerPublication(PublicationRequestDTO publicationRequestDTO, HttpServletRequest request);

    Publication savePublication(Publication publication);

    Publication getPublication(Long id);

    List<PublicationLocationDTO> getPublicationsLocations();

    Publication manipulatePublicationReactions(AppUser user, Long publicationId, AppUserReaction reaction);

    List<Publication> getPublicationsByAuthorId(AppUser user);

    Publication resolvePublication(Long id, AppUser user);

    Collection<Publication> findAllByNeedsReview(Boolean needsReview);

    String invalidatePublication(Long publicationId);
}
