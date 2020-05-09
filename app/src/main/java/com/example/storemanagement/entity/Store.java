package com.example.storemanagement.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class Store implements Serializable {
    private ArrayList<Shelf> shelves;

    public Store(ArrayList<Shelf> shelves) {
        this.shelves = shelves;
    }

    public void addShelf(int number, String name){
        shelves.add(new Shelf(number,name));
    }

    public Clothes getClothesInfo(char shelfNumber,int pliesNumber){
        return shelves.get(shelfNumber-'A'-1).getClothesInfo(pliesNumber);
    }

    //根据Id查询剩余库存
    public int findRestNumber(String id){
        int shelfSize = shelves.size();
        for(int i=0;i<shelfSize;i++){
            Shelf shelf = shelves.get(i);
            int pliesNumber = shelf.FindRestNumber(id);//若pliesNumber=-1则该货架上没有该服装。
            if(pliesNumber != -1){
                return pliesNumber;
            }
        }
        return 0;
    }


}
