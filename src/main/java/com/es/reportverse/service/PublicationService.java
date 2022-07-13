package com.es.reportverse.service;

import com.es.reportverse.DTO.PublicationRequestDTO;
import com.es.reportverse.DTO.PublicationLocationDTO;
import com.es.reportverse.model.AppUser;
import com.es.reportverse.model.Publication;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PublicationService {

    Publication registerPublication(PublicationRequestDTO publicationRequestDTO, HttpServletRequest request);

    Publication savePublication(Publication publication);

    Publication getPublication(Long id);

    List<PublicationLocationDTO> getPublicationsLocations();

    Publication manipulatePublicationLikes(AppUser user, Long publicationId);
}
