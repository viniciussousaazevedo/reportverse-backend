package com.es.reportverse.service;

import com.es.reportverse.model.Publication;

public interface EmailService {

    String sendPasswordRecoveryByEmail(String to, String recoveryLink);

    void notifyAdminsReportedPublication(Publication publication);

    String notifyExcludedPublicationAuthor(String authorUsername, Publication publication);

    String notifyAvailablePublicationAuthor(String authorUsername, Publication publication);
}
