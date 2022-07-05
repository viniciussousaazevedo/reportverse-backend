package com.es.reportverse.service;

public interface EmailService {

    String sendPasswordRecoveryByEmail(String to, String recoveryLink);
}
