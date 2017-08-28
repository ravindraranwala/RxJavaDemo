package com.pluralsight.rxjava.eventdriven.emailservice;

import java.util.List;

public interface EmailService {

    void sendEmail(List<String> recipientList, String fromEmail, String subject, String text);
    
}
