package com.example.storemanagement;

import android.util.Log;
import android.util.Pair;

import com.example.storemanagement.entity.Clothes;
import com.example.storemanagement.entity.ClothesFactory;
import com.example.storemanagement.entity.Info;
import com.example.storemanagement.entity.Order;
import com.example.storemanagement.entity.Shelf;
import com.example.storemanagement.entity.Store;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static android.content.ContentValues.TAG;

public class UserDao {
    JdbcUtil jdbcUtil;
    Connection conn;

    public UserDao() {

    }

    public int getRestNumber(String Id) {
        int restNumber = -1;
        try {
            Connection c = jdbcUtil.createConnection();
            String sql = "select RestNumber from clothes where ID='" + Id + "';";
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

    public String getPosition(String Id) {
        String position = "";
        try {
            Connection c = jdbcUtil.createConnection();
            String sql = "select Shelf,position from ware where ID='" + Id + "';";
            Statement stmt = c.createStatement();
            ResultSet res = stmt.executeQuery(sql);
            while (res.next()) {
                position = res.getString("Shelf");
                position = position + "-";
                position += res.getString("position");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return position;
    }

    public Store getWare() {
        Store ware = new Store();
        ClothesFactory clothesFactory=new ClothesFactory();
        try {
            Connection c = jdbcUtil.createConnection();
            String sql = "select distinct * from ware,clothes where ware.ID=clothes.ID order by Shelf;";
            Statement stmt = c.createStatement();
            ResultSet res = stmt.executeQuery(sql);

            while (res.next()) {

                String shelfName = res.getNString("Shelf");
                if (!ware.getShelves().containsKey(shelfName)) {
                    ware.addShelf(new Shelf(shelfName));
                }
                int position = res.getInt("position");
                String id = res.getString("ID");
                int number = res.getInt("RestNumber");
                Shelf shelf = ware.getShelves().get(shelfName);
                shelf.getPiles().put(position, (Clothes) clothesFactory.produce(id,number));//new Clothes(id, number
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ware;
    }

    public int updateClothes(String oriId,String curId,int cruRestNumber){
        int result=0;
        try {
        Connection c = jdbcUtil.createConnection();
        String sql = "update clothes set ID='"+curId+"',RestNumber='"+cruRestNumber+"' where ID='"+oriId+"';";
        Statement stmt = c.createStatement();
        result = stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public int deleteClothes(String Id){
        int result=0;
        try {
            Connection c = jdbcUtil.createConnection();
            String sql = "delete from clothes where ID='"+Id+"';";
            Statement stmt = c.createStatement();
            result = stmt.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public Map<String, Info> getPositions(Order order){
        Map<String, Info> result = new TreeMap<>(); //Map< id, Map<positon,restNumber>>
        try {
            Connection c = jdbcUtil.createConnection();
            Statement stmt = c.createStatement();
            for(int i=0;i<order.getOderList().size();i++){
                String id = order.getOderList().get(i).getId();
                int needNumber = order.getOderList().get(i).getNumber();
                String sql = "select * from ware,clothes where ware.ID='"+id+"' and ware.ID=clothes.ID order by Shelf;";
                ResultSet res = stmt.executeQuery(sql);
                while (res.next()) {
                    String shelfName = res.getNString("Shelf");
                    int position = res.getInt("position");
                    String pos = shelfName+"-"+position;
                    int number = res.getInt("RestNumber");
                    //result.put(id,new Pair<>(pos, number));
                    result.put(pos,new Info(id,pos,number,needNumber));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}

