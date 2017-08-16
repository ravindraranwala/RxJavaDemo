package com.pluralsight.rxjava.filtering;

import java.util.concurrent.TimeUnit;

import com.pluralsight.rxjava.util.ThreadUtils;
import com.pluralsight.rxjava.util.TimeTicker;

public class TimeBasedExampleSample {

    public static void main(String[] args) {

        // TimeTicker is a class that generates an event every
        // 10 milliseonds.  The event is a Long that represents
        // the current value of System.currentTimeMillis()
        TimeTicker ticker = new TimeTicker(10);
        ticker.start();
        
        
        try {
            // First, we get the observable event stream from the 
            // ticker.
            ticker.toObservable()
                    // Next, we tell the observable to give us samples
                    // every one second.
                    .sample(1, TimeUnit.SECONDS)
                    .subscribe((t) -> {
                        // Every second, we will emit the current value 
                        // of System.currentTimeMillis()
                        System.out.println("Tick: " + t);
                    });

            // We do this for 10 seconds...
            ThreadUtils.sleep(10000);
        } finally {
            // ...and then stop the ticker...which will also call 
            // onCompleted() on all observers.
            ticker.stop();
        }
        System.exit(0);

    }
}
