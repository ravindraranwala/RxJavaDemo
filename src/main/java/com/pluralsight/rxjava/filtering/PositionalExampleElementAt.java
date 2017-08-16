package com.pluralsight.rxjava.filtering;

import com.pluralsight.rxjava.util.DataGenerator;
import rx.Observable;

public class PositionalExampleElementAt {
    
    public static void main(String[] args) {

        // Emit the 3rd letter in the greek alphabet
        // Gamma
        Observable.from(DataGenerator.generateGreekAlphabet())
                .elementAt(2)
                .subscribe((letter) -> {
                    System.out.println(letter);
                });

        System.out.println();
        
        // Emit the 50th letter in the greek alphabet
        // ...there isn't a 50th letter, so we want to get "Unknown"
        Observable.from(DataGenerator.generateGreekAlphabet())
                .elementAtOrDefault(50, "Unknown")
                .subscribe((letter) -> {
                    System.out.println(letter);
                });

        System.out.println();
        
        
        
        System.exit(0);
    }
}
