package com.pluralsight.rxjava.dataaccess;

import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.codehaus.jackson.map.ObjectMapper;

import rx.Observable;
import rx.Scheduler;
import rx.functions.Action0;
import rx.subjects.AsyncSubject;

public class HttpJsonClient<REQUEST_TYPE, RESPONSE_TYPE> {

    private final AsyncSubject<RESPONSE_TYPE> responseSubject;
    private Action0 task;
    
    public HttpJsonClient(String url, String method, REQUEST_TYPE requestPayload, Class<?> responseType) {

        this.responseSubject = AsyncSubject.create();

        // Create an FutureTask so that we can execute this in the background...
        task = () -> {

            HttpURLConnection connection = null;
            try {
                // Create a URL object so that we can open an HttpURLConnection...
                URL targetURL = new URL(url);
                connection = (HttpURLConnection)targetURL.openConnection();

                // Set the HTTP request method (GET,POST,PUT,DELETE,etc)
                connection.setRequestMethod(method);

                // If there is a payload, then set the connection to perform
                // output
                if (requestPayload != null) {
                    connection.setDoOutput(true);
                }

                // We always care about responses: so turn on input.
                connection.setDoInput(true);

                // Set the content-type to the standard JSON type and tell the 
                // server that we will accept JSON in return.
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");

                // Connect to the server
                connection.connect();

                // If there is a request payload...
                if (requestPayload != null) {

                    // Create an output stream and have Jackson JSON persist
                    // the request object into the stream.
                    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                    new ObjectMapper().writeValue(outStream, requestPayload);

                    // Write the contents of the byte stream out and flush to
                    // ensure that it is all pushed towards the wire.
                    connection.getOutputStream().write(outStream.toByteArray());
                    connection.getOutputStream().flush();
                }

                // Read the response, using a Jackson JSON ObjectMapper to read into our return bean type.
                RESPONSE_TYPE returnValue = (RESPONSE_TYPE)new ObjectMapper().readValue(connection.getInputStream(), responseType);
                
                // Publish the response to our subject so that anyone observing
                // gets an event.
                responseSubject.onNext(returnValue);
            }
            catch( Throwable t ) {
                // EXCEPTION!  Tell all subscribers that bad things have happened.
                responseSubject.onError(t);
            }
            finally {
                // Disconnect from the server
                if( connection != null ) {
                    connection.disconnect();
                }

                // At this point we are done, so tell an subscribers that we 
                // are "complete".
                responseSubject.onCompleted();
            }
        };
        
    }

    public void schedule( Scheduler runScheduler ) {
        // Schedule the action to run on the specified scheduler
        runScheduler.createWorker().schedule(task);
    }
    
    public Observable<RESPONSE_TYPE> toObservable() {
        return responseSubject;
    }
}
