package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.*;

public class Model {
    private String _name;
    Connection conn;
    private Statement statement;

    public Model(Connection conn, String name) {
        this.conn = conn;
        this._name = name;
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

        String query = "SELECT * FROM " + this._name + " WHERE ";
        for (int i = 0; i < keyList.size(); i++) {
            query += keyList.get(i) + " = " + "'" + valueList.get(i) + "'";
            if (i < keyList.size() - 1) {
                query += " AND ";
            }
        }

        try {
            res = this.statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

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

        String query = "INSERT INTO " + this._name + " (";
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
            res = this.statement.executeQuery("SELECT * FROM " + this._name + " WHERE id = LAST_INSERT_ID()");

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return res;

    }

    public boolean update(HashMap<String, String> valList) throws SQLException {
        ArrayList<ArrayList<String>> data = fieldValueSplit(valList);
        ArrayList<String> keyList = data.get(0);
        ArrayList<String> valueList = data.get(1);

        String query = "UPDATE " + this._name + " SET ";
        for (int i = 0; i < keyList.size(); i++) {
            query += keyList.get(i) + " = " + "'" + valueList.get(i) + "'";
            if (i < keyList.size() - 1) {
                query += ", ";
            }
        }
        query += " WHERE id = " + valueList.get(0);

        try {
            this.statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean delete(long[] ids) throws SQLException {
        String query = "DELETE FROM " + this._name + " WHERE id IN (";
        for (int i = 0; i < ids.length; i++) {
            query += ids[i];
            if (i < ids.length - 1) {
                query += ", ";
            }
        }
        query += ")";

        try {
            this.statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean deleteAll() throws SQLException {
        String query = "DELETE FROM " + this._name;

        try {
            this.statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
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
