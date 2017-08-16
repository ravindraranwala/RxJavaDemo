package com.pluralsight.rxjava.composition;

import com.pluralsight.rxjava.util.ThreadUtils;
import rx.Observable;
import rx.schedulers.Schedulers;

public class CompositionExample {

	public static void main(String[] args) {

		// Create and sync on an object that we will use to make sure we don't
		// hit the System.exit(0) call before our threads have had a chance
		// to complete.
		Object waitMonitor = new Object();
		synchronized (waitMonitor) {

			UserService userService = new UserService();

			// Write out a little snippet to make the json look nice...just as
			// an example
			System.out.println("{ \"userList\" : [ ");

			// Call the user service and fetch a list of users.
			Observable.from(userService.fetchUserList()).flatMap(user -> {
                // Go through the events asynchronously and eliminate administrators.
				return Observable.just(user).subscribeOn(Schedulers.computation())
						.filter(u -> user.getSecurityStatus() != UserSecurityStatus.ADMINISTRATOR);
			}).toSortedList((user1, user2) -> {
				// Sort the remaining users by security status.
				return user1.getSecurityStatus().compareTo(user2.getSecurityStatus());
			})
					// Make the observable run on the io scheduler since the
					// userservice
					// might have to go over the wire (it doesn't in this
					// example)
					.subscribeOn(Schedulers.io()).doOnCompleted(() -> {
						// Since we have completed...we sync on the waitMonitor
						// and then call notify to wake up the "main" thread.
						synchronized (waitMonitor) {
							waitMonitor.notify();
						}
					}).subscribe(
							// onNext function - receives a sorted list of users
							// due to the "toSortedList" operation
							(userList) -> {
								userList.forEach((user) -> {
									System.out.println(user.toJSON());
								});
							});

			// Wait until the onCompleted method wakes us up.
			ThreadUtils.wait(waitMonitor);

			// Close the json
			System.out.println("] }");
		}
		System.exit(0);
	}
}
