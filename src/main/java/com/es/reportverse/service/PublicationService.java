package com.es.reportverse.service;

import com.es.reportverse.DTO.PublicationDTO;
import com.es.reportverse.model.Publication;

public interface PublicationService {

    Publication rergisterPublication(PublicationDTO publicationDTO);

    void savePublication(Publication publication);

    Publication getPublication(Long id);
}
