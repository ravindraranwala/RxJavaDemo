package com.pluralsight.rxjava.eventdriven.userservice;

import rx.Observer;
import rx.functions.Action1;

public interface UserService {

    void addUser(String username, String emailAddress);

    void subscribeToUserEvents(Observer<UserEvent> subscriber);
    void subscribeToUserEvents(Action1<UserEvent> onNext);
}
