package com.pluralsight.rxjava.using.jdbc;

import com.pluralsight.rxjava.util.DataGenerator;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import org.apache.commons.io.FileUtils;
import rx.Observable;

public class TestDatabase {

    public static void init() {

        File databaseDirectory = new File("./pluralSightTest");
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
            DriverManager.getConnection("jdbc:derby:pluralSightTest;create=true");
            c = DriverManager.getConnection("jdbc:derby:pluralSightTest");
        } catch (SQLException ex) {
            
            throw new RuntimeException(ex.getMessage(), ex);
        }

        // Initialize the database with some simple information
        Statement s = null;
        try { 
            s = c.createStatement();
            String sql = "CREATE TABLE GREEK_ALPHABET ( ID BIGINT, LETTER VARCHAR(20) )";
            s.execute(sql);
            
            int id = 1;
            for( String nextLetter : DataGenerator.generateGreekAlphabet() ) {
                sql = "INSERT INTO GREEK_ALPHABET ( ID , LETTER ) VALUES ( " + (id++) + ",\'" + nextLetter + "\' )";
                s.execute(sql);
            }
        }
        catch( SQLException e ) { 
            e.printStackTrace();
        }
        finally {
            if( s != null ) {
                try { s.close(); } catch( SQLException e ) {}
            }
            if( c != null ) {
                try { c.close(); } catch( SQLException e ) {}
            }
        }
        
    }

    public static Connection createConnection() {
        try {
            return DriverManager.getConnection("jdbc:derby:pluralSightTest");
        } catch (SQLException ex) {
            throw new RuntimeException(ex.getMessage(), ex);
        }
    }
    
    public static Observable<String> selectGreekAlphabet( ConnectionSubscription connSubscription ) {
        
        try {
            Statement s = connSubscription.getConnection().createStatement();
            connSubscription.registerResourceForClose(s);
            
            ResultSet rs = s.executeQuery("SELECT LETTER FROM GREEK_ALPHABET");
            connSubscription.registerResourceForClose(rs);
            
            ArrayList<String> returnList = new ArrayList<>();
            while( rs.next() ) {
                returnList.add( rs.getString( "LETTER" ) );
            }
    
            return Observable.from(returnList);
        }
        catch( SQLException e ) {
            throw new RuntimeException(e.getMessage(),e);
        }
    }
}
