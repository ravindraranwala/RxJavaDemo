package com.pluralsight.rxjava.transformation;

import com.pluralsight.rxjava.util.DataGenerator;
import rx.Observable;

public class MapExample {

    public static void main(String[] args) {

        // Simple map example...transform every greek letter string
        // into upper case.
        Observable.from(DataGenerator.generateGreekAlphabet())
                .map((letterString) -> {
                    return letterString.toUpperCase();
                })
                .subscribe((letterString) -> {
                    System.out.println(letterString);
                });

        System.out.println("--------------------------------------------------");

        // flatMap -> Each greek letter is emitted as all upper and 
        // all lower case...doubling the output.  One item in the origin
        // list generates multiple items.
        Observable.from(DataGenerator.generateGreekAlphabet())
                .flatMap((letterString) -> {

                    String[] returnStrings = {letterString.toUpperCase(),
                        letterString.toLowerCase()
                    };

                    return Observable.from(returnStrings);
                })
                .subscribe((letterString) -> {
                    System.out.println(letterString);
                });

        System.exit(0);
    }
}
