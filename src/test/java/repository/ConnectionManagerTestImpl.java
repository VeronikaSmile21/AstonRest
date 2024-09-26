package repository;

import org.example.db.ConnectionManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManagerTestImpl implements ConnectionManager {
    private String url;
    private String user;
    private String password;

    public ConnectionManagerTestImpl(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    @Override
    public Connection getConnection() throws SQLException {

        return DriverManager.getConnection(url, user, password);
    }
}
