package com.pluralsight.rxjava.dataaccess.shared;

// Mark this as a part of the "Users" collection using Morphia
public class User {

    private String lastName;
    private String firstName;
    private String username;
    private UserSecurityLevel securityLevel;

    public User() {
    }

    public User(String lastName, String firstName, String username, UserSecurityLevel securityLevel) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.username = username;
        this.securityLevel = securityLevel;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getUsername() {
        return username;
    }

    public UserSecurityLevel getSecurityLevel() {
        return securityLevel;
    }
}
