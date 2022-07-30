package com.es.reportverse.service;

import com.es.reportverse.model.Publication;

public interface EmailService {

    String sendPasswordRecoveryByEmail(String to, String recoveryLink);

    void notifyAdminsReportedPublication(Publication publication);

    String notifyExcludedPublicationAuthor(Publication publication);

    String notifyAvailablePublicationAuthor(Publication publication);

    void notifyAuthorReportedPublication(Publication publication);
}
