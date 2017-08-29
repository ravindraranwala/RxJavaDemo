package com.pluralsight.rxjava.dataaccess;

import com.pluralsight.rxjava.dataaccess.shared.JsonResponseBase;
import rx.schedulers.Schedulers;

public class HttpJsonClientTest {

    public static void main(String[] args) {
        try {

            HttpJsonClient<String, JsonResponseBase> testClient = new HttpJsonClient<>(
                    "http://localhost:4567/dummy", "GET", null, JsonResponseBase.class);

            Object monitor = new Object();

            testClient.toObservable().subscribe(
                    (response) -> {
                        System.out.println(response);
                    },
                    (t) -> {
                        t.printStackTrace();
                    },
                    () -> {
                        System.out.println("Completed.");

                        synchronized (monitor) {
                            monitor.notifyAll();
                        }

                    }
            );

            synchronized (monitor) {
                testClient.schedule(Schedulers.io());
                monitor.wait();
            }

        } catch (Throwable t) {
            t.printStackTrace();
        }

        System.exit(0);
    }
}
