package org.fakebuilder.utils;

public class NumberUtils {
    
    private final static NumberUtils INSTANCE = new NumberUtils();
    
    private NumberUtils(){}
    
    public static NumberUtils getInstance(){
        return INSTANCE;
    }
    
    public int generateRandomNumber(int min, int max) {
        return min + (int) (Math.random() * max);
    }
}
