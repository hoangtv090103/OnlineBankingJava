package org.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.*;

public class Model {
    private String name;
    Connection conn;
    private Statement statement;



    public Model(Connection conn, String name) {
        this.conn = conn;
        this.name = name;
        try {
            this.statement = conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ResultSet filter(HashMap<String, String> conditions) throws SQLException {
        ResultSet res = null;
        List<String> keyList = new ArrayList<>();
        List<String> valueList = new ArrayList<>();
        ArrayList<ArrayList<String>> data = fieldValueSplit(conditions);
        keyList = data.get(0);
        valueList = data.get(1);

        String query = new String("SELECT * FROM " + this.name + " WHERE ");


        for (int i = 0; i < keyList.size(); i++) {
            query += keyList.get(i) + " = " + "'" + valueList.get(i) + "'";
            if (i < keyList.size() - 1) {
                query += " AND ";
            }
        }

        res = statement.executeQuery(query);
        return res;
    }

    public String Or(String a, String b) {
        return a + " OR " + b;
    }

    public String And(String a, String b) {
        return a + " AND " + b;
    }


    public ResultSet create(HashMap<String, String> valList) throws SQLException {
        ResultSet res = null;
        ArrayList<ArrayList<String>> data = fieldValueSplit(valList);
        ArrayList<String> keyList = data.get(0);
        ArrayList<String> valueList = data.get(1);

        String query = "INSERT INTO " + this.name + " (";
        for (int i = 0; i < keyList.size(); i++) {
            query += keyList.get(i);
            if (i < keyList.size() - 1) {
                query += ", ";
            }
        }
        query += ") VALUES (";
        for (int i = 0; i < valueList.size(); i++) {
            query += "'" + valueList.get(i) + "'";
            if (i < valueList.size() - 1) {
                query += ", ";
            }
        }
        query += ")";

        try {
            this.statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            res = this.statement.executeQuery("SELECT * FROM " + this.name + " WHERE id = LAST_INSERT_ID()");

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return res;


    }

    public ArrayList<ArrayList<String>> fieldValueSplit(HashMap<String, String> valList) {
        ArrayList<String> keyList = new ArrayList<>();
        ArrayList<String> valueList = new ArrayList<>();
        for (Map.Entry<String, String> entry : valList.entrySet()) {
            keyList.add(entry.getKey());
            valueList.add(entry.getValue());
        }
        ArrayList<ArrayList<String>> res = new ArrayList<>();
        res.add(keyList);
        res.add(valueList);

        return res;

    }
}
