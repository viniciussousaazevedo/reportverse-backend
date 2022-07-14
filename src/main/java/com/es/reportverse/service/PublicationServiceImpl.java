package com.es.reportverse.service;

import com.es.reportverse.DTO.PublicationRequestDTO;
import com.es.reportverse.DTO.PublicationLocationDTO;
import com.es.reportverse.enums.UserRole;
import com.es.reportverse.exception.ApiRequestException;
import com.es.reportverse.model.AppUser;
import com.es.reportverse.model.appUserReaction.AppUserLike;
import com.es.reportverse.model.Publication;
import com.es.reportverse.model.appUserReaction.AppUserReaction;
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
    private final static String USER_IS_NOT_AUTHOR = "Usuário com id %s não é o dono da publicação, por isso não pode editá-la";

    private PublicationRepository publicationRepository;

    private ModelMapper modelMapper;

    private TokenManagerService tokenDecoder;

    @Override
    public Publication registerPublication(PublicationRequestDTO publicationRegistrationDTO, HttpServletRequest request) {

        AppUser user = tokenDecoder.decodeAppUserToken(request);

        Publication publication = this.modelMapper.map(publicationRegistrationDTO, Publication.class);
        publication.setAuthorId(user.getId());
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
    public Publication manipulatePublicationReactions(AppUser user, Long publicationId, AppUserReaction reaction) {
        Publication publication = this.getPublication(publicationId);

        @SuppressWarnings("unchecked")
        List<AppUserReaction> reactionList =
                reaction instanceof AppUserLike ?
                        (List) publication.getLikes() :
                        (List) publication.getReports();

        List<AppUserReaction> userLike = reactionList.stream().filter(
                l -> l.getAppUser().getId().equals(user.getId())
        ).collect(Collectors.toList());

        if (userLike.isEmpty()) {
            reaction.setAppUser(user);
            reactionList.add(reaction);
        } else {
            reactionList.remove(userLike.get(0));
        }

        return this.savePublication(publication);
    }

    @Override
    public List<Publication> getPublicationsByAuthorId(AppUser user) {

        List<Publication> publicationsList = new ArrayList<>();

        if (user.getUserRole() == UserRole.ADMINISTRADOR) {
            publicationsList = publicationRepository.findAll();
        } else if (user.getUserRole() == UserRole.UNIVERSITARIO) {
            publicationsList = publicationRepository.findByAuthorId(user.getId());
        }

        return publicationsList;
    }

    @Override
    public Publication resolvePublication(Long id, AppUser user) {

        Publication publication = this.getPublication(id);

        if (user.getId().equals(publication.getAuthorId())) {
            publication.setIsResolved(true);
            publication.setIsAvailable(false);
            this.savePublication(publication);
        } else {
            throw new ApiRequestException(String.format(USER_IS_NOT_AUTHOR, user.getId()));
        }
        
        return publication;
    }

}
