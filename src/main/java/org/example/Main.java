package org.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws SQLException {
        ConnectJDBC connectJDBC = new ConnectJDBC();
        Connection conn = connectJDBC.connect();
        Account account = new Account(conn);
        account.create(new HashMap<String, String>() {{
            put("name", "Savings");
            put("amount", "1000");
            put("username", "user1");
            put("password", "pass1");
        }});

//        Statement stm = conn.createStatement();
//        stm.executeUpdate("DELETE FROM Account");
//        stm.executeUpdate("DELETE FROM Users");

        conn.close();


    }
}