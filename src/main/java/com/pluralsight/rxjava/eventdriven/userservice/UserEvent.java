package com.pluralsight.rxjava.eventdriven.userservice;

import java.util.Date;

public class UserEvent {

    private final String username;
    private final String emailAddress;
    private final Date eventDate;

    public UserEvent(String username, String emailAddress) {
        this.username = username;
        this.emailAddress = emailAddress;
        this.eventDate = new Date();
    }

    public String getUsername() {
        return username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Date getEventDate() {
        return new Date(eventDate.getTime());
    }
}
