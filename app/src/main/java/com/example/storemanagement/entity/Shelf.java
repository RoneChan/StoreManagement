package com.example.storemanagement.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Shelf implements Serializable {
    private String name;
    private int number;
    private ArrayList<Clothes> shelf;

    public Shelf(int number,String name) {
        this.number = number;
        this.name=name;
        shelf = new ArrayList<>(number);
    }

    //查看该书架某层的服装
    public Clothes getClothesInfo(int number){
        return shelf.get(number-1);
    }

    public int FindRestNumber(String id){
        int pliesSize=shelf.size();
        for(int i=0;i<pliesSize;i++){
            if(shelf.get(i).getId().equals(id)){
                return shelf.get(i).getNumber();
            }
        }
        return -1;
    }
}
