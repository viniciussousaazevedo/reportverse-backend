package com.es.reportverse.service;

import com.es.reportverse.DTO.PublicationDTO;
import com.es.reportverse.exception.ApiRequestException;
import com.es.reportverse.model.AppUser;
import com.es.reportverse.model.Publication;
import com.es.reportverse.repository.PublicationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;

@Service
@Transactional
public class PublicationServiceImpl implements PublicationService {

    private final static String PUBLICATION_NOT_FOUND = "Publicação com id %s não encontrada";

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TokenDecoderService tokenDecoder;

    @Override
    public Publication registerPublication(PublicationDTO publicationRegistrationDTO, HttpServletRequest request) {

        AppUser appUser = tokenDecoder.decodeAppUserToken(request);

        Publication publication = this.modelMapper.map(publicationRegistrationDTO, Publication.class);
        publication.setAuthorId(appUser.getId());
        publication.setIsAvailable(true);
        publication.setQttComplaints(0);
        publication.setQttLikes(0);

        this.savePublication(publication);
        return publication;
    }

    @Override
    public void savePublication(Publication publication) {
        this.publicationRepository.save(publication);
    }

    @Override
    public Publication getPublication(Long id) {
        Optional<Publication> publicationOp = this.publicationRepository.findById(id);

        if (publicationOp.isEmpty()) {
            throw new ApiRequestException(String.format(PUBLICATION_NOT_FOUND, id));
        }

        return publicationOp.get();
    }

}
