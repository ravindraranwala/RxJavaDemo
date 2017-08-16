package com.pluralsight.rxjava.filtering;

import com.pluralsight.rxjava.util.DataGenerator;
import rx.Observable;

public class PositionalExampleFirstAndLast {

    public static void main(String[] args) {

        // Emit the greek alphabet, but only the first letter
        // Alpha
        Observable.from(DataGenerator.generateGreekAlphabet())
                .first()
                .subscribe((letter) -> {
                    System.out.println(letter);
                });

        System.out.println();

        // Emit the greek alphabet, but only the first 4
        // Alpha, Beta, Gamma, Delta
        Observable.from(DataGenerator.generateGreekAlphabet())
                .take(4)
                .subscribe((letter) -> {
                    System.out.println(letter);
                });

        System.out.println();

        // Emit the greek alphabet, but only the last letter
        // Omega
        Observable.from(DataGenerator.generateGreekAlphabet())
                .last()
                .subscribe((letter) -> {
                    System.out.println(letter);
                });

        System.out.println();

        // Emit the greek alphabet, but this time only the last 4
        // Phi, Chi, Psi, Omeaga
        Observable.from(DataGenerator.generateGreekAlphabet())
                .takeLast(4)
                .subscribe((letter) -> {
                    System.out.println(letter);
                });

        System.out.println();

        // firstOrDefault and lastOrDefault allows you to handle the case where there is 
        // an empty list.
        Observable.empty()
                .firstOrDefault("List is empty!")
                .subscribe((letter) -> {
                    System.out.println(letter);
                });
        Observable.empty()
                .lastOrDefault("List is empty!")
                .subscribe((letter) -> {
                    System.out.println(letter);
                });

        System.exit(0);
    }
}
