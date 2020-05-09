package com.example.storemanagement;

import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static android.content.ContentValues.TAG;

public class UserDao {
    JdbcUtil jdbcUtil;// = JdbcUtil.getInstance();
    //Connection conn = jdbcUtil.getConnection("storemanagement", "root", "123");
    Connection conn;
    public UserDao() {

    }

    public int getRestNumber(String Id) {
        int restNumber = -1;
        try {
            Connection c = jdbcUtil.createConnection();
            String sql = "select RestNumber from clothes where ID='"+Id+"';";
            Statement stmt = c.createStatement();
            ResultSet res = stmt.executeQuery(sql);
            while (res.next()) {
                restNumber = res.getInt("RestNumber");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restNumber;
    }

    public String getPosition(String Id){
        String position="";
        try {
            Connection c = jdbcUtil.createConnection();
            String sql = "select Shelf,position from ware where ID='"+Id+"';";
            Statement stmt = c.createStatement();
            ResultSet res = stmt.executeQuery(sql);
            while (res.next()) {
                position = res.getString("Shelf");
                position = position+"-";
                position += res.getString("position");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return position;
    }
}
