package com.pluralsight.rxjava.eventdriven;

import com.pluralsight.rxjava.eventdriven.emailmonitor.EmailMonitor;
import com.pluralsight.rxjava.eventdriven.emailservice.EmailService;
import com.pluralsight.rxjava.eventdriven.emailservice.EmailServiceImpl;
import com.pluralsight.rxjava.eventdriven.userservice.UserService;
import com.pluralsight.rxjava.eventdriven.userservice.UserServiceImpl;

public class EventDrivenExample {

    public static void main(String[] args) {
        try {

            // Create an EmailService
            EmailService emailService = new EmailServiceImpl();

            // Create a UserService
            UserService userService = new UserServiceImpl();

            // Create an EmailMonitor...no need to track a reference.
            new EmailMonitor(emailService, userService);

            // Create a user...
            userService.addUser("relledge", "relledge@nosuchemail.com");
            
            // Wait for a few moments...
            Thread.sleep(2000);
            
            // Exit
            System.exit(0);

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
