package com.pluralsight.rxjava.dataaccess.service;

import com.pluralsight.rxjava.dataaccess.server.TestDatabaseProcedures;
import com.pluralsight.rxjava.dataaccess.server.dto.OwnedProduct;
import java.sql.SQLException;
import rx.Observable;

public class ProductService {

    private final TestDatabaseProcedures procedures = new TestDatabaseProcedures();

    public Observable<OwnedProduct> fetchOwnedProducts(long customerId) {
        try {
            return procedures.toSelectOwnedProductObservable(customerId);
        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }
}
