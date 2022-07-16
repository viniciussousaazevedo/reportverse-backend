package com.es.reportverse.service;

import com.es.reportverse.model.Publication;

public interface EmailService {

    String sendPasswordRecoveryByEmail(String to, String recoveryLink);

    String notifyAdminsReportedPublication(Publication publication);
}
