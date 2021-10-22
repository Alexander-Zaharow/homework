package model;

public class ClothesOnSale {
    private int id;
    private String type;
    private String size;
    private String color;
    private int cost;

    public ClothesOnSale() {}

    public ClothesOnSale(String type, String size, String color, int cost) {
        this.type = type;
        this.size = size;
        this.color = color;
        this.cost = cost;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getSize() {
        return size;
    }
    public void setSize(String size) {
        this.size = size;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public int getCost() {
        return cost;
    }
    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Id: " + id + ", Type: " + type + ", Size: " + size + ", Color: " + color + ", Cost: " + cost;
    }
}