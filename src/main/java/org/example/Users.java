package org.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Users extends Model {
    int id;
    String userName;
    String password;

    public Users(Connection conn) {
        super(conn, "Users");
    }

    @Override
    public ResultSet create(HashMap<String, String> valList) throws SQLException {
        // Check if the user exists
        ResultSet user = this.filter(new HashMap<String, String>() {
            {
                put("username", valList.get("username"));
            }
        });

        if (user.next()) {
            return null;
        }
        ResultSet res = super.create(valList);

        try {
            while (res.next()) {
                this.id = res.getInt("id");
                this.userName = res.getString("username");
                this.password = res.getString("password");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public ResultSet login(String username, String password) throws SQLException {
        ResultSet res = null;
        HashMap<String, String> conditions = new HashMap<String, String>() {
            {
                put("username", username);
                put("password", password);
            }
        };
        res = super.filter(conditions);
        return res;
    }
}
