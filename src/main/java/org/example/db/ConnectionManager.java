package org.example.db;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {
    private static ConnectionManager connectionManager = new ConnectionManager();
    private static HikariConfig config = new HikariConfig("/db.properties");
    private static HikariDataSource ds = new HikariDataSource( config );

    private ConnectionManager() {}

    public Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static ConnectionManager getInstance() {
        return connectionManager;
    }
}
