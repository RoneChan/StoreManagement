package com.example.storemanagement.entity;

import android.content.Context;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FileDataStream {

    private Context context;
    private ArrayList<Shelf> shelves = new ArrayList<Shelf>();

    public FileDataStream(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<Shelf> getShelves() {
        return shelves;
    }

    public void setShelves(ArrayList<Shelf> shelves) {
        this.shelves = shelves;
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
            outputStream.writeObject(shelves);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Shelf> load() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(
                    context.openFileInput("Serializable.txt")
            );
            shelves = (ArrayList<Shelf>) inputStream.readObject();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return shelves;
    }
}
