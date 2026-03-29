package com.example.seedsmartapp.models;

public class HistoryModel {
    int id;
    String crop, fertilizer, cost, time;

    public HistoryModel(int id, String crop, String fertilizer, String cost, String time) {
        this.id = id;
        this.crop = crop;
        this.fertilizer = fertilizer;
        this.cost = cost;
        this.time = time;
    }

    public int getId() { return id; }
    public String getCrop() { return crop; }
    public String getFertilizer() { return fertilizer; }
    public String getCost() { return cost; }
    public String getTime() { return time; }
}