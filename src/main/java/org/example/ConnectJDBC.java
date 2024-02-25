package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectJDBC {
    private String hostName = "viaduct.proxy.rlwy.net";
    private String dbName = "cHGG15Ae22F4HF4hD5ebeH3f53HFcFFg";
    private String userName = "root";
    private String password = "cHGG15Ae22F4HF4hD5ebeH3f53HFcFFg";
    private String url = "jdbc:mysql://root:cHGG15Ae22F4HF4hD5ebeH3f53HFcFFg@viaduct.proxy.rlwy.net:48691/railway";

    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, userName, password);
            System.out.println("Connected to the database");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }
}
