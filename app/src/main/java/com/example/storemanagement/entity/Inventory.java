package com.example.storemanagement.entity;

public class Inventory {
    String oriId;
    String curId;
    int oriNumber;
    int curNumber;

    public Inventory(String oriId, String curId, int oriNumber, int curNumber) {
        this.oriId = oriId;
        this.curId = curId;
        this.oriNumber = oriNumber;
        this.curNumber = curNumber;
    }
}
