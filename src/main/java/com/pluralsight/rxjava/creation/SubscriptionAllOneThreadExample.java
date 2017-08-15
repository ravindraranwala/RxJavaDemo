package com.pluralsight.rxjava.creation;

import com.pluralsight.rxjava.util.DataGenerator;
import com.pluralsight.rxjava.util.ThreadUtils;
import java.util.List;
import rx.Observable;

public class SubscriptionAllOneThreadExample {

    public static void main(String[] args) {

        System.out.println("---------------------------------------------------------------------------------------");
        System.out.println("Creating an Observable that does not specify a subscribeOn or an observeOn Scheduler");
        System.out.println("driving thread: " + ThreadUtils.currentThreadName());
        System.out.println("---------------------------------------------------------------------------------------");

        // Get the list of integers that I seem to like best...
        List<Integer> intList = DataGenerator.generateFibonacciList();

        // Wrap the list in an Observable
        Observable<Integer> observable = Observable.from(intList);

        // Subscribe...
        observable.subscribe(
                // onNext function
                (i) -> {
                    // Println the name of the current thread on entry and exit so that we 
                    // can see a few interesting pieces of information...
                    System.out.println("onNext thread entr: " + ThreadUtils.currentThreadName());
                    System.out.println(i);
                    System.out.println("onNext thread exit: " + ThreadUtils.currentThreadName());
                },
                // onError function
                (t) -> {
                    t.printStackTrace(); // Should we get an error...just do the simplest thing.
                },
                // onCompleted function
                () -> {
                    System.out.println("onCompleted()");
                }
        );

        System.exit(0);
    }
}
