package com.pluralsight.rxjava.connectable;

import com.pluralsight.rxjava.connectable.util.TimeTicker;
import com.pluralsight.rxjava.util.ThreadUtils;
import rx.observables.ConnectableObservable;
import rx.schedulers.Schedulers;

public class ParallelObserveOnConnectableExample {

    public static void main(String[] args) {

        // Create a ticker that goes off every 1/2 second.
        TimeTicker ticker = new TimeTicker(500).start();
        
        // From the ticker, we create a connectable observable by 
        // get the observable and then calling its "publish" method.
        ConnectableObservable<Long> connectable = ticker
                .toObservable()
                .publish();

        // Next we do our subscription....
        connectable
                // We want to run this on a different thread than the ticker thread
                .observeOn(Schedulers.computation())
                .subscribe(
                        (t) -> {
                            System.out.println("Tick: " + ThreadUtils.currentThreadName() + " " + t);
                        }
                );
        
        connectable
                // We want to run this on a different thread than the ticker thread
                .observeOn(Schedulers.computation())
                .subscribe(
                        (t) -> {
                            System.out.println("Tick2: " + ThreadUtils.currentThreadName() + " " + t);
                        }
                );

        // But notice how for 3 seconds nothing happens until we call "connect"
        System.out.println("Sleeping for 3 seconds...");
        ThreadUtils.sleep(3000);

        // Now we call "connect" on the connectable observable...and things start happening...
        System.out.println("Connecting...");
        connectable.connect();

        // Let the ticker do its thing for 3 seconds so we can see events
        // flowing...
        // Notice that we are now getting parallel behavior between the observer
        // threads.
        ThreadUtils.sleep(3000);
        System.out.println("Three seconds are up!");
        
        // Stop the ticker and kill the example's VM
        ticker.stop();
        System.exit(0);
    }
}
