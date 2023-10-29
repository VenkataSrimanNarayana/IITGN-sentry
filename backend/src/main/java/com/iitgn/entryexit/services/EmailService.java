package com.iitgn.entryexit.services;

public interface EmailService {
    void sendEmail(String to, String subject, String text);
    String generateCommonLangPassword();
}
