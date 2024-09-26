package org.example.db;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManagerImpl implements ConnectionManager {
    private static ConnectionManagerImpl connectionManager = new ConnectionManagerImpl();
    private static HikariConfig config = new HikariConfig("/db.properties");
    private static HikariDataSource ds = new HikariDataSource( config );

    private ConnectionManagerImpl() {}

    @Override
    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static ConnectionManagerImpl getInstance() {
        return connectionManager;
    }
}
