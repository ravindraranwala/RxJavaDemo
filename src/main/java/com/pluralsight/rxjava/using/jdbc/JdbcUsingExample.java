package com.pluralsight.rxjava.using.jdbc;

import com.pluralsight.rxjava.util.ThreadUtils;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;

public class JdbcUsingExample {

	public static void main(String[] arg) {

		// Create a simple error handler that will emit the stack trace for a
		// given exception
		Action1<Throwable> simpleErrorHandler = (t) -> {
			t.printStackTrace();
		};

		// Initialize the test Derby database...this also resets its contents
		TestDatabase.init();

		// Resource Factory function to create a TestDatabase connection and
		// wrap it
		// in a ConnectionSubscription
		Func0<ConnectionSubscription> resourceFactory = () -> {
			return new ConnectionSubscription(TestDatabase.createConnection());
		};

		// Observable Factory function to create the resultset that we want.
		Func1<ConnectionSubscription, Observable<String>> greekAlphabetList = (connectionSubscription) -> {
			return TestDatabase.selectGreekAlphabet(connectionSubscription);
		};

		Observable<String> t = Observable.using(resourceFactory, greekAlphabetList, result -> {
			result.unsubscribe();
			System.out.println("disposeAction({})");
		});

		t.subscribe((letter) -> {
			System.out.println(ThreadUtils.currentThreadName() + " - " + letter);
		}, simpleErrorHandler, () -> {
			System.out.println("onCompleted");
		});

		System.exit(0);
	}
}
