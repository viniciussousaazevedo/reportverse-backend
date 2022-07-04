package com.es.reportverse.service;

public interface EmailService {

    String sendPasswordRecoveryEmail(String to, String recoveryLink);
}
