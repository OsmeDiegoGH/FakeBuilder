package baz.codesGeneratorComponent.utils;

public class NumberFormatUtils {
    
    private static NumberFormatUtils INSTANCE = new NumberFormatUtils();
    
    private NumberFormatUtils(){
    }
    
    public static NumberFormatUtils getInstance(){
        return INSTANCE;
    }
    
    public Integer tryParseInt(String str){
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException ex) {
            return null;
        }
    }
}
