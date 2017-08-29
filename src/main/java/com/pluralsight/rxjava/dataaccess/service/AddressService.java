package com.pluralsight.rxjava.dataaccess.service;

import com.pluralsight.rxjava.dataaccess.server.TestDatabaseProcedures;
import com.pluralsight.rxjava.dataaccess.server.dto.Address;
import java.sql.SQLException;
import rx.Observable;

public class AddressService {

    private final TestDatabaseProcedures procedures = new TestDatabaseProcedures();

    public Observable<Address> fetchCustomerAddresses(long customerId) {
        try {
            return procedures.toSelectAddressObservable(customerId);
        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }
}
