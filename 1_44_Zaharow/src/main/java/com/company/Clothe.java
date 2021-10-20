package com.company;

public class Clothe {
    private String type;
    private String size;
    private String color;
    private int cost;

    public Clothe(String type, String size, String color, int cost) {
        this.type = type;
        this.size = size;
        this.color = color;
        this.cost = cost;
    }

    public String getType() {
        return type;
    }
    public String getSize() {
        return size;
    }
    public String getColor() {
        return color;
    }
    public int getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return type + ", " + size + ", " + color + ", " + cost;
    }
}
