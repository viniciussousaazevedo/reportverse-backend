package com.es.reportverse.service;

import com.es.reportverse.DTO.PublicationRequestDTO;
import com.es.reportverse.DTO.PublicationLocationDTO;
import com.es.reportverse.model.Publication;
import com.es.reportverse.service.publicationReactionLogic.PublicationReaction;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PublicationService {

    Publication registerPublication(PublicationRequestDTO publicationRequestDTO, HttpServletRequest request);

    void savePublication(Publication publication);

    Publication getPublication(Long id);

    List<PublicationLocationDTO> getPublicationsLocations();

    Publication manipulatePublicationReaction(PublicationReaction publicationReaction, Long publicationId);
}
