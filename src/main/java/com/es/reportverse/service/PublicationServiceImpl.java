package com.es.reportverse.service;

import com.es.reportverse.DTO.PublicationRequestDTO;
import com.es.reportverse.DTO.PublicationLocationDTO;
import com.es.reportverse.exception.ApiRequestException;
import com.es.reportverse.model.AppUser;
import com.es.reportverse.model.Publication;
import com.es.reportverse.repository.PublicationRepository;
import com.es.reportverse.service.publicationReactionLogic.PublicationReaction;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;

@Service
public class PublicationServiceImpl implements PublicationService {

    private final static String PUBLICATION_NOT_FOUND = "Publicação com id %s não encontrada";

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TokenManagerService tokenDecoder;

    @Override
    public Publication registerPublication(PublicationRequestDTO publicationRegistrationDTO, HttpServletRequest request) {

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

    @Override
    public List<PublicationLocationDTO> getPublicationsLocations() {
        List<PublicationLocationDTO> publicationLocations = new ArrayList<>();

        publicationRepository.findAll()
                .forEach(publication -> publicationLocations.add(
                        this.modelMapper.map(publication, PublicationLocationDTO.class)
                        )
                );

        return publicationLocations;
    }

    @Override
    public Publication manipulatePublicationReaction(PublicationReaction publicationReaction, Long publicationId) {
        return publicationReaction.manipulatePublicationReaction(publicationId);
    }

}
