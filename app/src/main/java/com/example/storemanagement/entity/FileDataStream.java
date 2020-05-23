package com.example.storemanagement.entity;

import android.content.Context;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FileDataStream {

    private Context context;
    private ArrayList<Order> orderArrayList = new ArrayList<Order>();

    public FileDataStream(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<Order> getShelves() {
        return orderArrayList;
    }

    public void setShelves(ArrayList<Order> orderArrayList) {
        this.orderArrayList = orderArrayList;
    }

    //保存数据
    public void save() {
        //不是sdk卡上的，所以不要用FileOutputStream
        //Serializable读取的是手机内存上的
        ObjectOutputStream outputStream = null;
        try {
            //写入文件
            outputStream = new ObjectOutputStream(
                    context.openFileOutput("OrderLists.txt", Context.MODE_PRIVATE)
            );
            outputStream.writeObject(orderArrayList);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Order> load() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(
                    context.openFileInput("OrderLists.txt")
            );
            orderArrayList = (ArrayList<Order>) inputStream.readObject();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderArrayList;
    }
}
