package com.pluralsight.rxjava;

import java.util.function.Function;

public class FirstClassCitizenParameterIllustration {

    public static void main(String[] args) {

        // The concatAndTransform call illustrates how functions can be passed
        // as a parameter.  Functions (lambdas) can be passed directly or...
        System.out.println(concatAndTransform("Hello ", "World", (s) -> {
            return s.toUpperCase();
        }));

        // ...from a variable.
        Function<String, String> transformToLower = (s) -> {
            return s.toLowerCase();
        };
        System.out.println(concatAndTransform("Hello ", "World", transformToLower));
    }

    public static String concatAndTransform(String a, String b, Function<String, String> stringTransform) {

        // NOTE: Please see the "Optional" class in Java 8...it would be more
        // correct that what I'm doing here. I just didn't want to confuse
        // the point about functions as parameters.
        if (stringTransform != null) {
            a = stringTransform.apply(a);
            b = stringTransform.apply(b);
        }

        return a + b;
    }
}
