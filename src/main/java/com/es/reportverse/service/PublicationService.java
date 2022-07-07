package com.es.reportverse.service;

import com.es.reportverse.DTO.PublicationDTO;
import com.es.reportverse.DTO.PublicationLocationDTO;
import com.es.reportverse.model.Publication;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface PublicationService {

    Publication registerPublication(PublicationDTO publicationDTO, HttpServletRequest request);

    void savePublication(Publication publication);

    Publication getPublication(Long id);

    List<PublicationLocationDTO> getPublicationsLocations();
}
