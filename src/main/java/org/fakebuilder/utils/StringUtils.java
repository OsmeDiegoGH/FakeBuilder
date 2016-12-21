package org.fakebuilder.utils;

public class StringUtils {
    
    private static StringUtils INSTANCE = new StringUtils();
    
    private StringUtils(){}
    
    public static StringUtils getInstance(){
        return INSTANCE;
    }
    
    public boolean isNullOrEmpty(String str){
        return str == null || str.isEmpty();
    }    
    
    public String generateRandomString(int length) {
        char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
        String randomStr = "";
        for (int i = 0; i < length; i++) {
            randomStr += chars[0 + (int) (Math.random() * chars.length)];
        }
        return randomStr;
    }
}
