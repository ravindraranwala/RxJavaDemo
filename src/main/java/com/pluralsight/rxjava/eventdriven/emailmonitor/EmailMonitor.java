package com.pluralsight.rxjava.eventdriven.emailmonitor;

import com.pluralsight.rxjava.eventdriven.emailservice.EmailService;
import com.pluralsight.rxjava.eventdriven.userservice.UserEvent;
import com.pluralsight.rxjava.eventdriven.userservice.UserService;
import java.util.ArrayList;

public class EmailMonitor {

    private final EmailService emailService;

    public EmailMonitor(EmailService emailService, UserService userService) {
        this.emailService = emailService;

        // Subscribe to UserEvents in the userService
        userService.subscribeToUserEvents(this::handleUserEvent);
    }

    private void handleUserEvent(UserEvent t) {
        System.out.println( "EmailMonitorServiceImpl::handleUserEvent - " + Thread.currentThread().getName() );
        
        ArrayList<String> recipList = new ArrayList<>();
        recipList.add(t.getEmailAddress());
        String text = "Hi " + t.getUsername() + ", Welcome to PluralSight!";
        emailService.sendEmail(recipList, "noreply@mySystem.com", "Welcome to PluralSight!", text);
    }

}
