package com.justdo.modules.email.service;

public interface EmailService {
    void sendMail(String to, String subject, String content, String... files);
}
