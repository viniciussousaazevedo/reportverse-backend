package com.es.reportverse.service;

import com.es.reportverse.DTO.PublicationDTO;
import com.es.reportverse.DTO.PublicationLocationDTO;
import com.es.reportverse.exception.ApiRequestException;
import com.es.reportverse.model.AppUser;
import com.es.reportverse.model.Publication;
import com.es.reportverse.repository.PublicationRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import com.es.reportverse.service.TokenManagerService;

@Service
@Transactional
public class PublicationServiceImpl implements PublicationService {

    private final static String PUBLICATION_NOT_FOUND = "Publicação com id %s não encontrada";

    @Autowired
    private PublicationRepository publicationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private TokenManagerService tokenDecoder;

    @Autowired
    private AppUserService appUserService;

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
    public Publication manipulatePublicationReports(AppUser user, Long publicationId) {
        Publication publication = this.getPublication(publicationId);
        Set<Long> reportedPublicationsIds = user.getReportedPublicationsIds();
        int manipulation;

        if (reportedPublicationsIds.contains(publicationId)) {
            manipulation = -1;
            reportedPublicationsIds.remove(publicationId);
        } else {
            manipulation = 1;
            reportedPublicationsIds.add(publicationId);
        }

        publication.setQttComplaints(publication.getQttComplaints() + manipulation);
        this.savePublication(publication);
        this.appUserService.saveUser(user);


        return publication;
    }

}
