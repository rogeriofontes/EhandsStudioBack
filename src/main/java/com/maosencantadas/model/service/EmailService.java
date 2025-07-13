package com.maosencantadas.model.service;

public interface EmailService {
    void publish(String name, String to, String subject, boolean isAssociate, String validatedToken) throws Exception;
   // void publishPasswordRecovery(String name, String to, String subject, String newPassword) throws Exception;
}
