package com.pluralsight.rxjava.dataaccess.server;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import rx.Subscription;

public class ConnectionSubscription implements Subscription {
    
    private final Connection connection;
    private final HashSet<Statement> statements = new HashSet<>();
    private final HashSet<ResultSet> resultSets = new HashSet<>();
    private boolean ubsubscribed = false;

    public ConnectionSubscription(Connection connection) {
        this.connection = connection;
    }
    
    @Override
    public void unsubscribe() {
    	this.ubsubscribed = true;
        resultSets.stream().forEach((rs) -> {
            try { rs.close(); } catch( SQLException t ) {}
        });
        statements.stream().forEach((s) -> {
            try { s.close(); } catch( SQLException t ) {}
        });
        
        if( connection != null ) {
            try { connection.close(); } catch( SQLException e ) {}
        }
    }

    public Connection getConnection() {
        return connection;
    }
    
    public void registerResourceForClose( Statement s ) {
        if( s == null ) {
            throw new IllegalArgumentException();
        }
        
        statements.add(s);
    }

    public void registerResourceForClose( ResultSet rs ) {
        if( rs == null ) {
            throw new IllegalArgumentException();
        }
        resultSets.add(rs);
    }

	@Override
	public boolean isUnsubscribed() {
		return this.ubsubscribed;
	}
}
