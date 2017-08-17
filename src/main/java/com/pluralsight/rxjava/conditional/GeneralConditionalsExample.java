package com.pluralsight.rxjava.conditional;

import com.pluralsight.rxjava.util.DataGenerator;
import com.pluralsight.rxjava.util.MyEntry;

import rx.Observable;

public class GeneralConditionalsExample {

	public static void main(String[] args) {

		// defaultIfEmpty example - We create an empty observable
		// and then apply "defaultIfEmpty" and set the default to "Hello World".
		// Since the observable is empty, "Hello World" will be emitted as
		// the only event.
		Observable.empty().defaultIfEmpty("Hello World").subscribe((s) -> {
			System.out.println(s);
		});

		System.out.println();

		// defaultIfEmpty example 2 - We create an non-empty observable
		// and then apply "defaultIfEmpty" and set the default to "Hello World".
		// Since the observable is not empty, the list items will be emitted.
		Observable.from(DataGenerator.generateGreekAlphabet()).defaultIfEmpty("Hello World").first() // we
																										// just
																										// want
																										// to
																										// show
																										// that
																										// it
																										// isn't
																										// Hello
																										// World...
				.subscribe((s) -> {
					System.out.println(s);
				});

		System.out.println();

		Observable.from(DataGenerator.generateFibonacciList()).skipWhile((i) -> {
			return i < 8;
		}).subscribe((i) -> {
			System.out.println(i);
		});

		System.out.println();

		Observable.from(DataGenerator.generateFibonacciList()).takeWhile((i) -> {
			return i < 8;
		}).subscribe((i) -> {
			System.out.println(i);
		});

		System.out.println();

		int maxIndex = 3;

		Observable.from(DataGenerator.generateFibonacciList())
				.zipWith(Observable.range(0, maxIndex), (value, index) -> {
					return new MyEntry<Integer, Integer>(index, value);
				}).subscribe((i) -> {
					System.out.println(i.getValue());
				});

		System.out.println();

		System.exit(0);

	}

}
