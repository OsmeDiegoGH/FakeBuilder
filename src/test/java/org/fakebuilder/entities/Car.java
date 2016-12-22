package org.fakebuilder.entities;

public class Car {
    private String carName; 
    private int model;
    private boolean isHybrid;
    private CAR_COLOR color;
    
    public enum CAR_COLOR{
        WHITE, BLACK, YELLOW, BLUE
    }

    public Car(String carName, int model, boolean isHybrid, CAR_COLOR color) {
        this.carName = carName;
        this.model = model;
        this.isHybrid = isHybrid;
        this.color = color;
    }

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
    }

    public boolean IsHybrid() {
        return isHybrid;
    }

    public void setIsHybrid(boolean isHybrid) {
        this.isHybrid = isHybrid;
    }

    public CAR_COLOR getColor() {
        return color;
    }

    public void setColor(CAR_COLOR color) {
        this.color = color;
    }
}
