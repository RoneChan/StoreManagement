package com.example.storemanagement.entity;

import android.content.Context;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FileDataStream {


    private Context context;
    private ArrayList<Clothes> clothes = new ArrayList<Clothes>();

    public FileDataStream(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<Clothes> getGoods() {
        return clothes;
    }

    public void setBooks(ArrayList<Clothes> clothes) {
        this.clothes = clothes;
    }

    //保存数据
    public void save() {
        //不是sdk卡上的，所以不要用FileOutputStream
        //Serializable读取的是手机内存上的
        ObjectOutputStream outputStream = null;
        try {
            //写入文件
            outputStream = new ObjectOutputStream(
                    context.openFileOutput("Storemangement.txt", Context.MODE_PRIVATE)
            );
            outputStream.writeObject(clothes);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Clothes> load() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(
                    context.openFileInput("Serializable.txt")
            );
            clothes = (ArrayList<Clothes>) inputStream.readObject();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clothes;
    }
}
