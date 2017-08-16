package com.pluralsight.rxjava.filtering;

import com.pluralsight.rxjava.util.DataGenerator;
import rx.Observable;

public class PositionalExampleFirstAndLastWithPredicate {
    
    public static void main(String[] args) {

        // Emit the greek alphabet, but only the first letter
        // that matches our predicate
        Observable.from(DataGenerator.generateGreekAlphabet())
                .first( (letter) -> { return letter.equals( "Beta" ); } )
                .subscribe((letter) -> {
                    System.out.println(letter);
                });

        System.out.println();

        // Emit the greek alphabet, but only the last letter
        // that matches our predicate
        Observable.from(DataGenerator.generateGreekAlphabet())
                .last( (letter) -> { return letter.equals( "Gamma" ); } )
                .subscribe((letter) -> {
                    System.out.println(letter);
                });

        System.out.println();

        
        System.exit(0);
    }
}
