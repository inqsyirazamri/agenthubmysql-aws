package src.g11.agenthub.db_connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbConnection implements AutoCloseable {
    private String dbUrl;
    private String dbUser;
    private String dbPass;
    private String dbDriver;
    private Connection conn;

    public DbConnection() {
        // this.dbUrl = "jdbc:mysql://localhost:3306/agenthub";
        this.dbUrl = "agenthubmysql.c45zcri8wojr.us-east-1.rds.amazonaws.com";
        this.dbUser = "root";
        this.dbPass = "Pa$$w0rd";
        this.dbDriver = "com.mysql.cj.jdbc.Driver";
    }

    public Connection getConnection() throws SQLException {
        try {
            Class.forName(dbDriver);
            conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Error: Driver class not found: " + e.getMessage());
        }
        return conn;
    }

    @Override
    public void close() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new SQLException("Error closing connection: " + e.getMessage());
            }
        }
    }

    public boolean checkLogin(String username, String password) throws SQLException {
        String query = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ? LIMIT 1";
        try (Connection connection = getConnection();
                PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    public String getRole(String username) throws SQLException {
        String query = "SELECT role FROM users WHERE username = ? LIMIT 1";
        try (Connection connection = getConnection();
                PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("role");
            }
        }
        return null;
    }
}
