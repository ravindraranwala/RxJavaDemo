package com.pluralsight.rxjava.dataaccess.server;

import com.pluralsight.rxjava.dataaccess.server.dto.Address;
import com.pluralsight.rxjava.dataaccess.server.dto.Customer;
import com.pluralsight.rxjava.dataaccess.server.dto.OwnedProduct;
import java.sql.SQLException;
import rx.Observable;

public class TestDatabaseProcedures {

	public Observable<Customer> toSelectCustomersObservable() throws SQLException {

		return Observable.using(TestDatabase::createSubscription, (subscription) -> {
			try {
				return SQLHelper.executeQuery(subscription, "SELECT ID, USERNAME FROM CUSTOMER", (resultSet) -> {
					return new Customer(resultSet.getLong("ID"), resultSet.getString("USERNAME"));
				});
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}, ConnectionSubscription::unsubscribe);
	}

	public Observable<Customer> toSelectCustomersObservable(long customerId) throws SQLException {

		return Observable.using(TestDatabase::createSubscription, (subscription) -> {
			try {
				System.out.println("Select Customer: " + Thread.currentThread().getName());
				return SQLHelper.executeQuery(subscription,
						"SELECT ID, USERNAME FROM CUSTOMER WHERE ID = " + customerId, (resultSet) -> {
							return new Customer(resultSet.getLong("ID"), resultSet.getString("USERNAME"));
						});
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}, ConnectionSubscription::unsubscribe);
	}

	public Observable<OwnedProduct> toSelectOwnedProductObservable(long customerId) throws SQLException {

		return Observable.using(TestDatabase::createSubscription, (subscription) -> {
			try {
				System.out.println("Select Products: " + Thread.currentThread().getName());
				return SQLHelper
						.executeQuery(subscription,
								"SELECT CUSTOMERID , PRODUCTID , NAME FROM CUSTOMER_PRODUCT CP "
										+ "JOIN PRODUCT P ON P.ID = CP.PRODUCTID WHERE CUSTOMERID = " + customerId,
								(rs) -> {
									return new OwnedProduct(rs.getLong("CUSTOMERID"), rs.getLong("PRODUCTID"),
											rs.getString("NAME"));
								});
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}, ConnectionSubscription::unsubscribe);
	}

	public Observable<Address> toSelectAddressObservable(long customerId) throws SQLException {

		return Observable.using(TestDatabase::createSubscription, (subscription) -> {
			try {
				System.out.println("Select Address: " + Thread.currentThread().getName());
				return SQLHelper.executeQuery(subscription,
						"SELECT ID,CUSTOMERID,ADDRESS1,ADDRESS2,CITY,STATE,POSTALCODE FROM ADDRESS WHERE CUSTOMERID = "
								+ customerId,
						(rs) -> {
							return new Address(rs.getLong("ID"), rs.getLong("CUSTOMERID"), rs.getString("ADDRESS1"),
									rs.getString("ADDRESS2"), rs.getString("CITY"), rs.getString("STATE"),
									rs.getString("POSTALCODE"));
						});
			} catch (SQLException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}, ConnectionSubscription::unsubscribe);
	}
}
