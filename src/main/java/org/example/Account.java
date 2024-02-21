package org.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Account extends Model {
    private int id;
    private String name;

    private int userId;
    private int amount;

    public Account(Connection conn) {
        super(conn, "Account");
    }

    @Override
    public ResultSet create(HashMap<String, String> valList) throws SQLException {
        this.createUser(valList.get("username"), valList.get("password"));
        valList.remove("username");
        valList.remove("password");

        valList.put("user_id", String.valueOf(this.userId));
        ResultSet res = super.create(valList);

        try {
            while (res.next()) {
                this.id = res.getInt("id");
                this.name = res.getString("name");
                this.amount = res.getInt("amount");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return res;
    }

    public void createUser(String username, String password) throws SQLException {
        Users user = new Users(this.conn);
        ResultSet res = user.create(new HashMap<String, String>() {
            {
                put("username", username);
                put("password", password);
            }
        });
        this.userId = user.id;
    }

    public boolean deposit(int amount) throws SQLException {
        this.amount += amount;
        try{
            this.update(new HashMap<String, String>() {
                {
                    put("id", String.valueOf(Account.this.id));
                    put("amount", String.valueOf(Account.this.amount));
                }
            });
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // public boolean transfer(int amount, int toAccountId) throws SQLException {
    //     this.amount -= amount;
    //     try {
    //         this.update(new HashMap<String, String>() {
    //             {
    //                 put("id", String.valueOf(Account.this.id));
    //                 put("amount", String.valueOf(Account.this.amount));
    //             }
    //         });
    //         Account toAccount = new Account(this.conn);
    //         // toAccount.retrieve(toAccountId);
    //         toAccount.amount += amount;
    //         toAccount.update(new HashMap<String, String>() {
    //             {
    //                 put("id", String.valueOf(toAccount.id));
    //                 put("amount", String.valueOf(toAccount.amount));
    //             }
    //         });
    //         return true;
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         return false;
    //     }
    // }
}
