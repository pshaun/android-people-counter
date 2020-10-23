package com.example.peoplecounter.model;

public class CounterModel {
    private int totalAmount;
    private int currentAmount;

    public CounterModel() {

    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public int getCurrentAmount() {
        return currentAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setCurrentAmount(int currentAmount) {
        this.currentAmount = currentAmount;
    }
}
