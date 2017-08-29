package com.pluralsight.rxjava.dataaccess.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import rx.Observable;

public class SQLHelper {

    public static int executeInsert(Connection connection, String sqlString) throws SQLException {
        // Initialize the database with some simple information
        Statement s = null;
        try {
            s = connection.createStatement();
            return s.executeUpdate(sqlString);

        } finally {
            if (s != null) {
                try {
                    s.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public static <T> Observable<T> executeQuery(ConnectionSubscription subscription, String sqlString, RowMapper<T> rowMapper) throws SQLException {

        ArrayList<T> workList = new ArrayList<>();

        Statement s = subscription.getConnection().createStatement();
        subscription.registerResourceForClose(s);
        ResultSet rs = s.executeQuery(sqlString);
        subscription.registerResourceForClose(rs);

        while (rs.next()) {
            workList.add(rowMapper.call(rs));
        }

        return Observable.from(workList);
    }
}
