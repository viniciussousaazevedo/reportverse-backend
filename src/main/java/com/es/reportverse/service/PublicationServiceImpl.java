package com.es.reportverse.service;

import com.es.reportverse.DTO.PublicationRequestDTO;
import com.es.reportverse.DTO.PublicationLocationDTO;
import com.es.reportverse.exception.ApiRequestException;
import com.es.reportverse.model.AppUser;
import com.es.reportverse.model.AppUserLike;
import com.es.reportverse.model.Publication;
import com.es.reportverse.repository.PublicationRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

@Service
@AllArgsConstructor
public class PublicationServiceImpl implements PublicationService {

    private final static String PUBLICATION_NOT_FOUND = "Publicação com id %s não encontrada";

    private PublicationRepository publicationRepository;

    private ModelMapper modelMapper;

    private TokenManagerService tokenDecoder;

    @Override
    public Publication registerPublication(PublicationRequestDTO publicationRegistrationDTO, HttpServletRequest request) {

        AppUser appUser = tokenDecoder.decodeAppUserToken(request);

        Publication publication = this.modelMapper.map(publicationRegistrationDTO, Publication.class);
        publication.setAuthorId(appUser.getId());
        publication.setIsAvailable(true);
        publication.setLikes(new ArrayList<>());

        this.savePublication(publication);
        return publication;
    }

    @Override
    public Publication savePublication(Publication publication) {
        return this.publicationRepository.save(publication);
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
    public Publication manipulatePublicationLikes(AppUser user, Long publicationId) {
        Publication publication = this.getPublication(publicationId);

        List<AppUserLike> userAppUserLike = publication.getLikes().stream().filter(
                l -> l.getAppUser().getId().equals(user.getId())
        ).collect(Collectors.toList());

        if (userAppUserLike.size() > 0) {
            publication.getLikes().remove(userAppUserLike.get(0));
        } else {
            publication.getLikes().add(new AppUserLike(user));
        }

        return this.savePublication(publication);
    }

}
