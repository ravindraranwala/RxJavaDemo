package com.pluralsight.rxjava.dataaccess.server;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.apache.commons.io.FileUtils;

public class TestDatabase {

    public static void init() throws SQLException {

        File databaseDirectory = new File("./pluralSightTest_m5");
        try {
            FileUtils.deleteDirectory(databaseDirectory);
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }

        String driver = "org.apache.derby.jdbc.EmbeddedDriver";
        try {
            Class.forName(driver).newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }

        Connection c = null;
        try {
            DriverManager.getConnection("jdbc:derby:pluralSightTest_m5;create=true");
            c = DriverManager.getConnection("jdbc:derby:pluralSightTest_m5");
        } catch (SQLException ex) {

            throw new RuntimeException(ex.getMessage(), ex);
        }

        try {
            // Initialize the table structures that will be used.  You will normally want 
            // to setup foreign key indexes...I'm omitting them for brevity.
            SQLHelper.executeInsert(c, "CREATE TABLE CUSTOMER ( ID BIGINT , USERNAME VARCHAR(20) )");
            SQLHelper.executeInsert(c, "CREATE TABLE PRODUCT ( ID BIGINT , NAME VARCHAR(40) )");
            SQLHelper.executeInsert(c, "CREATE TABLE CUSTOMER_PRODUCT ( ID BIGINT , CUSTOMERID BIGINT , PRODUCTID BIGINT )");
            SQLHelper.executeInsert(c, "CREATE TABLE ADDRESS ( ID BIGINT , CUSTOMERID BIGINT , ADDRESS1 VARCHAR(60) , ADDRESS2 VARCHAR(60) , CITY VARCHAR(40), STATE VARCHAR(2) , POSTALCODE VARCHAR(10) )");

            // Initialize the customer table's data
            SQLHelper.executeInsert(c, "INSERT INTO CUSTOMER ( ID , USERNAME ) VALUES ( 1 , 'relledge' )");
            SQLHelper.executeInsert(c, "INSERT INTO CUSTOMER ( ID , USERNAME ) VALUES ( 2 , 'dvader' )");
            SQLHelper.executeInsert(c, "INSERT INTO CUSTOMER ( ID , USERNAME ) VALUES ( 3 , 'lskyskimmer' )");

            // Initialize the products table's data
            SQLHelper.executeInsert(c, "INSERT INTO PRODUCT ( ID , NAME ) VALUES ( 1 , 'Rubber Baseball Bat' )");
            SQLHelper.executeInsert(c, "INSERT INTO PRODUCT ( ID , NAME ) VALUES ( 2 , '+1 Fish Sword of Withering' )");
            SQLHelper.executeInsert(c, "INSERT INTO PRODUCT ( ID , NAME ) VALUES ( 3 , 'Armband Floaties of Dispair' )");
            SQLHelper.executeInsert(c, "INSERT INTO PRODUCT ( ID , NAME ) VALUES ( 4 , 'Flowpot of Juiciness' )");

            // Assign products to customers
            SQLHelper.executeInsert(c, "INSERT INTO CUSTOMER_PRODUCT ( ID , CUSTOMERID , PRODUCTID ) VALUES ( 1 , 1 , 2 )");
            SQLHelper.executeInsert(c, "INSERT INTO CUSTOMER_PRODUCT ( ID , CUSTOMERID , PRODUCTID ) VALUES ( 2 , 1 , 3 )");

            SQLHelper.executeInsert(c, "INSERT INTO CUSTOMER_PRODUCT ( ID , CUSTOMERID , PRODUCTID ) VALUES ( 3 , 2 , 1 )");
            SQLHelper.executeInsert(c, "INSERT INTO CUSTOMER_PRODUCT ( ID , CUSTOMERID , PRODUCTID ) VALUES ( 4 , 2 , 3 )");
            SQLHelper.executeInsert(c, "INSERT INTO CUSTOMER_PRODUCT ( ID , CUSTOMERID , PRODUCTID ) VALUES ( 5 , 2 , 4 )");

            SQLHelper.executeInsert(c, "INSERT INTO CUSTOMER_PRODUCT ( ID , CUSTOMERID , PRODUCTID ) VALUES ( 6 , 3 , 4 )");
            SQLHelper.executeInsert(c, "INSERT INTO CUSTOMER_PRODUCT ( ID , CUSTOMERID , PRODUCTID ) VALUES ( 7 , 3 , 1 )");

            // Initialize the data for the address table
            SQLHelper.executeInsert(c, "INSERT INTO ADDRESS ( ID , CUSTOMERID , ADDRESS1 , ADDRESS2 , CITY ,STATE , POSTALCODE ) VALUES "
                    + "( 1 , 1 , '123 South North Street' , NULL , 'Fort Wayne' , 'TX' , '76222' )");
            SQLHelper.executeInsert(c, "INSERT INTO ADDRESS ( ID , CUSTOMERID , ADDRESS1 , ADDRESS2 , CITY ,STATE , POSTALCODE ) VALUES "
                    + "( 2 , 1 , '7788 Fourth Blvd' , NULL , 'Carpentry' , 'TX' , '76122' )");

            SQLHelper.executeInsert(c, "INSERT INTO ADDRESS ( ID , CUSTOMERID , ADDRESS1 , ADDRESS2 , CITY ,STATE , POSTALCODE ) VALUES "
                    + "( 3 , 2 , '1313 Planet of Doom Rd' , 'Apt #33' , 'Aldeberon' , 'OH' , '54332' )");
            SQLHelper.executeInsert(c, "INSERT INTO ADDRESS ( ID , CUSTOMERID , ADDRESS1 , ADDRESS2 , CITY ,STATE , POSTALCODE ) VALUES "
                    + "( 4 , 2 , '4433 Sandy Hills Road' , NULL , 'Tatto' , 'VA' , '94231' )");

            SQLHelper.executeInsert(c, "INSERT INTO ADDRESS ( ID , CUSTOMERID , ADDRESS1 , ADDRESS2 , CITY ,STATE , POSTALCODE ) VALUES "
                    + "( 5 , 3 , '91122 Dirt Pile Way' , NULL , 'Tatto' , 'VA' , '94231' )");

        } finally {
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                }
            }
        }

    }

    public static ConnectionSubscription createSubscription() {
        return new ConnectionSubscription(createConnection());
    }

    public static Connection createConnection() {
        try {
            return DriverManager.getConnection("jdbc:derby:pluralSightTest_m5");
        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }
}
