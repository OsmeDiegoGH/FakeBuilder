package baz.codesGeneratorComponent.utils;

public class StringUtils {
    
    private static StringUtils INSTANCE = new StringUtils();
    
    private StringUtils(){
    }
    
    public static StringUtils getInstance(){
        return INSTANCE;
    }
    
    public boolean containsOnlyNumbers(String str) {
        String patron = "[0-9]*";
        return str.matches(patron);
    }
    
    public boolean isNullOrEmpty(String str)
    {
       return str == null || str.isEmpty();
    }
}
