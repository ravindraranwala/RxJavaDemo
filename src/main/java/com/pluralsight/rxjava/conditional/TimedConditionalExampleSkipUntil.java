package com.pluralsight.rxjava.conditional;

import com.pluralsight.rxjava.conditional.util.TimeTicker;
import com.pluralsight.rxjava.util.DataGenerator;
import com.pluralsight.rxjava.util.ThreadUtils;
import com.pluralsight.rxjava.util.TimedEventSequence;

public class TimedConditionalExampleSkipUntil {

    public static void main(String[] args) {

        // "skipUntil" example - Skip the emissions of the greek alphabet
        // until the timer goes off in 3 seconds...
        TimedEventSequence<String> sequence1 = new TimedEventSequence<>(DataGenerator.generateGreekAlphabet(), 50);
        TimeTicker ticker = new TimeTicker(3000);

        // Create a skipUntil operation around the two sequences
        sequence1.toObservable()
                .skipUntil(ticker.toObservable())
                .subscribe((ch) -> {
                    System.out.println(ch);
                });

        // Start the driver thread for each of these sequences.
        ticker.start();
        sequence1.start();

        // Wait for 6 seconds while things run...we should not see any 
        // output until 3 seconds have passed.
        ThreadUtils.sleep(6000);

        // Stop each sequence.
        sequence1.stop();
        ticker.stop();

        System.exit(0);
    }

}
