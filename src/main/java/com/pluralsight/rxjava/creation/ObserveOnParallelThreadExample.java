package com.pluralsight.rxjava.creation;

import java.util.List;

import com.pluralsight.rxjava.util.DataGenerator;
import com.pluralsight.rxjava.util.ThreadUtils;

import rx.Observable;
import rx.schedulers.Schedulers;

public class ObserveOnParallelThreadExample {

	public static void main(String[] args) {

		// Create and sync on an object that we will use to make sure we don't
		// hit the System.exit(0) call before our threads have had a chance
		// to complete.
		Object waitMonitor = new Object();
		synchronized (waitMonitor) {

			System.out
					.println("---------------------------------------------------------------------------------------");
			System.out.println("Creating an Observable that does not specify a subscribeOn or an observeOn Scheduler");
			System.out.println("driving thread: " + ThreadUtils.currentThreadName());
			System.out
					.println("---------------------------------------------------------------------------------------");

			// Create a big list of integers...
			List<Integer> emitList = DataGenerator.generateBigIntegerList();

			// Wrap it in an observable...
			Observable<Integer> observable = Observable.from(emitList);

			observable.flatMap(val -> Observable.<Integer> just(val).subscribeOn(Schedulers.io())
					.filter(num -> num % 2 == 0).doOnNext((xx) -> {
						System.out.println("parallel thread in: " + ThreadUtils.currentThreadName());
						System.out.println("parallel: " + xx);
						ThreadUtils.sleep(10); // Add a sleep to make sure we
												// have a chance to see
						// that the even number filter is executing in parallel.
						System.out.println("parallel thread out: " + ThreadUtils.currentThreadName());
					})).subscribe(
							// onNext function
							(i) -> {
								System.out.println("onNext thread entr: " + ThreadUtils.currentThreadName());
								System.out.println(i);
								System.out.println("onNext thread exit: " + ThreadUtils.currentThreadName());
							},
							// onError function
							(t) -> {
								t.printStackTrace();
							},
							// onCompleted
							() -> {
								System.out.println("onCompleted()");

								// Since we have completed...we sync on the
								// waitMonitor
								// and then call notify to wake up the "main"
								// thread.
								synchronized (waitMonitor) {
									waitMonitor.notify();
								}
							});

			// Wait until the onCompleted method wakes us up.
			ThreadUtils.wait(waitMonitor);
		}

		System.exit(0);
	}
}
