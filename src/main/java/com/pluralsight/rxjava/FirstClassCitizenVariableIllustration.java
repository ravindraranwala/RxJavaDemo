package com.pluralsight.rxjava;

import java.util.function.BiFunction;

public class FirstClassCitizenVariableIllustration {

    public static void main(String[] args) {

        // ---------------------------------------------------------------------
        // This example illustrates how to create an inline lambda expression
        // and store it in a variable.  It can then be called directly from
        // the variable.
        // ---------------------------------------------------------------------
        BiFunction<String, String, String> concatFunction = (s, t) -> {
            return s + t;
        };
        System.out.println(concatFunction.apply("Hello ", "World 1"));

        // ---------------------------------------------------------------------
        // This example illustrates how a static method that conforms to the
        // method signature of the BiFunction can be stored in a variable and
        // then invoked.
        // ---------------------------------------------------------------------
        concatFunction = FirstClassCitizenVariableIllustration::concatStrings;
        System.out.println(concatFunction.apply("Hello ", "World 2"));
        
        // ---------------------------------------------------------------------
        // This example illustrates how an instance method that conforms to the
        // method signature of the BiFunction can be stored in a variable and
        // then invoked.
        // ---------------------------------------------------------------------
        FirstClassCitizenVariableIllustration instance = new FirstClassCitizenVariableIllustration();
        concatFunction = instance::concatStrings2;
        System.out.println(concatFunction.apply("Hello ", "World 3"));
    }
    
    private static String concatStrings( String a , String b ) {
        return a + b;
    }
    
    /* package */ String concatStrings2( String a , String b ) {
        return a + b;
    }
}
