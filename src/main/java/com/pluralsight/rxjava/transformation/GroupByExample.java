package com.pluralsight.rxjava.transformation;

import com.pluralsight.rxjava.util.DataGenerator;
import java.util.ArrayList;
import rx.Observable;

public class GroupByExample {

    public static void main(String[] args) {

        // Create an observable on top of our list of 0...199 integers
        Observable.from(DataGenerator.generateBigIntegerList())
                // Group based on odd or even using a string literal 
                // for the key
                .groupBy((i) -> {
                    return 0 == (i % 2) ? "EVEN" : "ODD";
                })
                // Subscribe to the Observable< GroupedObservable<String,Integer> >
                .subscribe((groupList) -> {

                    // For each group, spit out the key
                    System.out.println("Key: " + groupList.getKey() + " ------------------------");

                    // And subscribe to the items in the list...print them
                    // out with their key.
                    groupList.subscribe((x) -> {
                        System.out.println(groupList.getKey() + ": " + x);
                    });

                });

        // Interesting!  Odds and evens still come out mixed!  But notice that 
        // the keys are correct.  We must remember that once we are inside 
        // a lambda expression or closure that we are simply declaring what we
        // want to happen.  The order that things are called may not be what
        // they seem.
        
        
        // For the next example, create a list of even and odd numbers
        final ArrayList<Integer> evenList = new ArrayList<>();
        final ArrayList<Integer> oddList = new ArrayList<>();

        // We do the same thing again...
        Observable.from(DataGenerator.generateBigIntegerList())
                .groupBy((i) -> {
                    return 0 == (i % 2) ? "EVEN" : "ODD";
                })
                .subscribe((groupList) -> {

                    groupList.subscribe((x) -> {
                        
                        // ...but this time, we add to our lists so that we
                        // can show that things got grouped correctly more 
                        // easily.
                        if (groupList.getKey().equals("EVEN")) {
                            evenList.add(x);
                        } else {
                            oddList.add(x);
                        }
                    });

                });

        System.out.println();
        System.out.println();
        
        // Dump the even list
        System.out.println( "even list -------------------------------------" );
        evenList.forEach( (i) -> { System.out.println(i); } );

        // Dump the odd list
        System.out.println( "odd list -------------------------------------" );
        oddList.forEach( (i) -> { System.out.println(i); } );
        
        System.exit(0);
    }
}
