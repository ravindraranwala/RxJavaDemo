package com.pluralsight.rxjava.conditional;

import com.pluralsight.rxjava.util.DataGenerator;
import com.pluralsight.rxjava.util.ThreadUtils;
import com.pluralsight.rxjava.util.TimedEventSequence;
import rx.Observable;

public class TimedConditionalExampleAmbiguous {

    public static void main(String[] args) {

        // "Ambiguous" example - First, we create two TimedEventSequence 
        // instances...one that emits Greek letters with a shorter interval,
        // the other that emits English letters on a longer interval.
        TimedEventSequence<String> sequence1 = new TimedEventSequence<>(DataGenerator.generateGreekAlphabet(), 50);
        TimedEventSequence<String> sequence2 = new TimedEventSequence<>(DataGenerator.generateEnglishAlphabet(), 100);

        // Create an "ambiguous" observable from the two sequences
        Observable.amb(sequence1.toObservable(), sequence2.toObservable())
                .subscribe((s) -> {
                    System.out.println(s);
                });

        // Start the driver thread for each of these sequences.
        sequence1.start();
        sequence2.start();

        // Wait for 4 seconds while things run...we should see Greek letters
        // being emitted at 50ms intervals for 4 seconds since the first
        // sequence to emit events will be selected by the amb operator.
        ThreadUtils.sleep(4000);
        
        // Stop each sequence.
        sequence1.stop();
        sequence2.stop();
        
        System.exit(0);
    }

}
