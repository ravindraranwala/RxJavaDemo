package com.pluralsight.rxjava.creation;

import java.util.List;
import java.util.concurrent.FutureTask;

import com.pluralsight.rxjava.util.DataGenerator;

import rx.Observable;
import rx.schedulers.Schedulers;

public class FutureCreationExample {

    public static void main(String[] args) {

        Observable<List<Integer>> observableFutureList;

        // Create a FutureTask that returns a List<Integer>
        FutureTask<List<Integer>> future = new FutureTask<>(() -> {
            return DataGenerator.generateFibonacciList();
        });
        
        // Construct an observable...note that this only creates the
        // observable wrapper around the future.  The future still needs 
        // to be executed using it's "run" method, or by scheduling it to 
        // execute.
        observableFutureList = Observable.from(future);

        // Schedule this future to run on the computation scheduler
        // Call the FutureTask's run method
        Schedulers.computation().createWorker().schedule(future::run);

        // Subscribe to the list...when the list is ready through the 
        // future, iterate and print each element.
        observableFutureList.subscribe((list) -> {
            list.forEach((i) -> {
                System.out.println(i);
            });
        });

        System.exit(0);
    }
}
