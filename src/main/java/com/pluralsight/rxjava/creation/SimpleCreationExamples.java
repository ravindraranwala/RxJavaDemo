package com.pluralsight.rxjava.creation;

import com.pluralsight.rxjava.util.DataGenerator;
import rx.Observable;

public class SimpleCreationExamples {

    public static void main(String[] args) {
        Observable<Integer> observable = null;

        System.out.println("---------------------------------------------");
        System.out.println("Observable creation from a single value");
        System.out.println("---------------------------------------------");
        observable = Observable.just(Integer.valueOf(42));
        observable.subscribe((i) -> {
            System.out.println(i);
        });

        System.out.println("---------------------------------------------");
        System.out.println("Observable creation from an Iterable");
        System.out.println("---------------------------------------------");
        observable = Observable.from(DataGenerator.generateFibonacciList());
        observable.subscribe((i) -> {
            System.out.println(i);
        });

        System.out.println("---------------------------------------------");
        System.out.println("Observable creation from an Array");
        System.out.println("---------------------------------------------");
        observable = Observable.from(DataGenerator.generateFibonacciArray());
        observable.subscribe((i) -> {
            System.out.println(i);
        });

        System.exit(0);
    }
}
