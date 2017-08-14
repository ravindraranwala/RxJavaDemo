package com.pluralsight.rxjava;

public class SideEffectIllustration {
    
    private int state = 0;
    
    /**
     * This is an example of a pure function which is free from any side effects.
     * @param x
     * @return
     */
    public int f1( int x ) { 
        return x * 2;
    }
    
    /**
     * This is an impure function. You need to handle threading issues here.
     * @param x
     * @return
     */
    public int f2( int x ) {
        state = (++state % 3);
        return x * 2 + state;
    }
    
    public static void main( String[] args ) {
        
        SideEffectIllustration v = new SideEffectIllustration();
        
        // I can call the f1 method any number of times, with any number of threads
        // and I will always get the same answer so long as I provide the same 
        // parameters because f1's result is totally determined by the incoming
        // parameters and nothing else.
        for( int i = 0 ; i < 100 ; i++ ) {
            if( v.f1(5) != 10 ) {
                throw new IllegalStateException();
            }
        }
        System.out.println( "f1(5) = 10 for all calls" );
        
        // If I call the f2 method, I can never be sure of what I'm going to 
        // get because there is a side effect that effects the value returned.
        // If another thread or call somewhere else in the code gets to it 
        // before I do, then the value will not be what I expect.
        for( int i = 0 ; i < 100 ; i++ ) {
            System.out.println( "f2(5) = " + v.f2(5));
        }
    }
}
