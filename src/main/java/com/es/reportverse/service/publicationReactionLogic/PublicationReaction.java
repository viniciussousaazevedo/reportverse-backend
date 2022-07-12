package com.es.reportverse.service.publicationReactionLogic;

import com.es.reportverse.model.AppUser;
import com.es.reportverse.model.Publication;
import com.es.reportverse.service.AppUserService;
import com.es.reportverse.service.PublicationService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public abstract class PublicationReaction {

    protected Set<Long> reaction;

    protected int manipulation;

    protected AppUser user;

    @Autowired
    protected PublicationService publicationService;

    @Autowired
    protected AppUserService appUserService;

    public PublicationReaction(AppUser user) {

    }

    public Publication manipulatePublicationReaction(Long publicationId) {
        Publication publication = this.publicationService.getPublication(publicationId);

        if (reaction.contains(publicationId)) {
            manipulation = -1;
            reaction.remove(publicationId);
        } else {
            manipulation = 1;
            reaction.add(publicationId);
        }

        this.appUserService.saveUser(user);

        return publication;
    }

}
