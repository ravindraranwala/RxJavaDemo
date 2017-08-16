package com.pluralsight.rxjava.filtering;

import com.pluralsight.rxjava.util.TimeTicker;
import com.pluralsight.rxjava.util.ThreadUtils;
import java.util.concurrent.TimeUnit;

public class TimeBasedExampleTimeout {

    public static void main(String[] args) {

        // TimeTicker is a class that generates an event every
        // 100 milliseonds.  The event is a Long that represents
        // the current value of System.currentTimeMillis()
        TimeTicker ticker = new TimeTicker(100);
        ticker.start();

        try {
            // First, we get the observable event stream from the 
            // ticker.
            ticker.toObservable()
                    
                    // We will timeout if the ticker stops for more than 3 seconds...
                    // an onError will be generated
                    .timeout(3, TimeUnit.SECONDS)
                    .subscribe(
                            // onNext
                            (t) -> {
                                // Every second, we will emit the current value 
                                // of System.currentTimeMillis()
                                System.out.println("Tick: " + t);
                            },
                            // onError
                            (exception) -> { 
                                System.out.println( "TIMEOUT!" );
                                exception.printStackTrace();
                            }
                    );
            
            // Let it run for 1 seconds...it'll be spammy because the ticker
            // is in 100ms increments.
            ThreadUtils.sleep(1000);
            
            // ...then pause the ticker
            System.out.println( "Pausing ticker." );
            ticker.pause();

            // Wait another 5 seconds.  3 seconds to timeout and then another
            // 2 seconds for good measure.
            ThreadUtils.sleep(5000);
        } finally {
            // ...and then stop the ticker...which will also call 
            // onCompleted() on all observers.
            ticker.stop();
        }
        System.exit(0);

    }
}
