package baz.codesGeneratorComponent.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ArrayUtils {
    
    private static ArrayUtils INSTANCE = new ArrayUtils();
    
    private ArrayUtils(){
    }
    
    public static ArrayUtils getInstance(){
        return INSTANCE;
    }
    
    public byte[] inputStreamToByteArray(InputStream in) throws IOException{
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[16384];

        while ((nRead = in.read(data, 0, data.length)) != -1){
            buffer.write(data, 0, nRead);
        }
        try {
            buffer.flush();
        } catch (IOException ex) {
            System.out.println("WARNING: Unable to flush stream content:" + in.toString());
        }
        
        return buffer.toByteArray();       
    }
    
    public String[] concat(String[] arr1, String[] arr2){
        int totalElements = arr1.length + arr2.length;
        String[] result = new String[totalElements];
        
        System.arraycopy(arr1, 0, result, 0, arr1.length);  
        System.arraycopy(arr2, 0, result, arr1.length, arr2.length);
        
        return result;        
    }    
}
