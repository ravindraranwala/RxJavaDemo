package com.pluralsight.rxjava.dataaccess.server;

import com.pluralsight.rxjava.dataaccess.server.dto.Address;
import com.pluralsight.rxjava.dataaccess.server.dto.Customer;
import com.pluralsight.rxjava.dataaccess.server.dto.OwnedProduct;
import com.pluralsight.rxjava.dataaccess.service.CustomerService;
import java.sql.SQLException;
import rx.Observable;
import rx.schedulers.Schedulers;

public class EdgeServiceSimulation {

    public static void main(String[] args) {

        try {
            // Initialize the test database
            TestDatabase.init();

            // Create a CustomerService
            CustomerService customerService = new CustomerService();

            // Create a monitor so that we don't exit the application too soon.
            Object waitMonitor = new Object();
            synchronized (waitMonitor) {
                
                // Ask the CustomerService to fetch customer data, 
                // but also get addresses and owned products
                // from the other services.
                Observable<Customer> customerData = customerService.fetchCustomerWithAddressesAndOwnedProducts(1);
                
                customerData
                        .subscribeOn(Schedulers.newThread())
                        .subscribe(
                                // For each returned customer (there should be only one), emit information
                                // about the customer.
                                (customer) -> { // onNext
                                    System.out.println("------------------------------------------------------------");
                                    System.out.println(customer.getCustomerId() + " " + customer.getUsername());
                                    System.out.println();

                                    for (Address address : customer.getAddressList()) {
                                        System.out.println("    " + address.getAddress1());
                                        System.out.println("    " + address.getCity() + ", " + address.getState() + "  " + address.getPostalCode());
                                        System.out.println();
                                    }

                                    for (OwnedProduct product : customer.getOwnedProductList()) {
                                        System.out.println("    Product: " + product.getName());
                                    }
                                    System.out.println();
                                    System.out.println("------------------------------------------------------------");
                                },
                                (t) -> { // onError
                                    t.printStackTrace();
                                },
                                () -> { // onCompleted
                                    synchronized (waitMonitor) {
                                        waitMonitor.notify();
                                    }
                                });

                // This effectively waits for the onCompleted call to signal
                waitMonitor.wait();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.exit(0);
    }
}
