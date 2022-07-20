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
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;

@Service
@AllArgsConstructor
public class PublicationServiceImpl implements PublicationService {

    private final static String PUBLICATION_NOT_FOUND = "Publicação com id %s não encontrada";
    private final static String USER_IS_NOT_AUTHOR = "Usuário com id %s não é o dono da publicação, por isso não pode editá-la";
    private final static String USER_IS_NOT_ADMIN = "Usuário com id %s não é um administrador";

    private PublicationRepository publicationRepository;

    private ModelMapper modelMapper;

    private TokenManagerService tokenDecoder;

    private EmailService emailService;

    private AppUserService appUserService;

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
        return this.publicationRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException(String.format(PUBLICATION_NOT_FOUND, id)));
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
        boolean isReportRelated = false;
        List<AppUserReaction> reactionList;

        if (reaction instanceof AppUserLike) {
            reactionList = (List) publication.getLikes();
        } else {
            reactionList = (List) publication.getReports();
            isReportRelated = true;
        }


        List<AppUserReaction> userLike = reactionList.stream().filter(
                l -> l.getAppUser().getId().equals(user.getId())
        ).collect(Collectors.toList());

        if (userLike.isEmpty()) {
            reaction.setAppUser(user);
            reactionList.add(reaction);
        } else {
            reactionList.remove(userLike.get(0));
        }

        if (isReportRelated){
            if(reactionList.size() >= 5 && !publication.getNeedsReview()){
                publication.setNeedsReview(true);
                publication.setIsAvailable(false);
                this.emailService.notifyAdminsReportedPublication(publication);
                // TODO o dono da publicação não deveria ser notificado também? Levar essa ideia adiante junto do front.
            } else if(publication.getNeedsReview()){
                    publication.setNeedsReview(false);
                    publication.setIsAvailable(true);
            }
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

    @Override
    public Collection<Publication> findAllByNeedsReview(Boolean needsReview) {
            return this.publicationRepository.findAllByNeedsReview(needsReview);
    }

    @Override
    public String invalidatePublication(Long publicationId) {
        Publication publication = this.getPublication(publicationId);
        this.publicationRepository.delete(publication);

        String authorUsername = this.appUserService.getUser(publication.getAuthorId()).getUsername();
        return this.emailService.notifyExcludedPublicationAuthor(authorUsername, publication);
    }

    @Override
    public String validatePublication(Long publicationId) {
       Publication publication = this.getPublication(publicationId);
       publication.setIsAvailable(true);
       publication.setNeedsReview(false);
       publication.getReports().clear();

       this.savePublication(publication);
       String authorUsername = this.appUserService.getUser(publication.getAuthorId()).getUsername();
        return this.emailService.notifyAvailablePublicationAuthor(authorUsername, publication);
    }

    @Override
    public List<Publication> getAllPublicationsAvaliable() {
        return this.publicationRepository.findAllAvailable();
    }

}
