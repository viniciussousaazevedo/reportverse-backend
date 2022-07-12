package com.es.reportverse.service.publicationReactionLogic;

import com.es.reportverse.model.AppUser;
import com.es.reportverse.model.Publication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PublicationReport extends PublicationReaction {

    @Autowired
    public PublicationReport(AppUser user) { // FIXME
        super(user);
        super.reaction = user.getReportedPublicationsIds();
    }

    @Override
    public Publication manipulatePublicationReaction(Long publicationId) {
        Publication publication = super.manipulatePublicationReaction(publicationId);
        publication.setQttComplaints(publication.getQttComplaints() + manipulation);
        publicationService.savePublication(publication);
        return publication;
    }
}
