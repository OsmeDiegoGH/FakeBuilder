package baz.codesGeneratorComponent.handlers;

import acertum.secureRequestHandler.handlers.IRequestHandler;
import java.util.HashMap;

public class CesarEncryptorHandler implements IRequestHandler{
    private static final int[] ENCODE_CHARS = { 40, 106, 30, 64, 56, 122, 37, 92, 108, 46, 126, 53, 5, 88, 69, 110, 107, 120, 65, 35, 48, 91, 73, 61, 20, 87, 50, 19, 29, 74, 86, 117, 95, 52, 18, 41, 62, 45, 77, 112, 36, 116, 104, 43, 97, 105, 55, 101, 59, 123, 96, 26, 82, 72, 22, 0, 118, 79, 121, 13, 111, 24, 68, 93, 100, 34, 47, 11, 114, 21, 49, 109, 80, 67, 85, 63, 90, 8, 23, 115, 60, 42, 99, 44, 76, 102, 28, 12, 10, 14, 84, 113, 4, 6, 66, 25, 51, 54, 58, 38, 15, 71, 57, 31, 103, 16, 70, 89, 2, 119, 39, 75, 3, 78, 127, 17, 32, 83, 1, 7, 124, 33, 9, 98, 94, 27, 81, 125 };
    private static final int[] DECODE_CHARS = { 55, 118, 108, 112, 92, 12, 93, 119, 77, 122, 88, 67, 87, 59, 89, 100, 105, 115, 34, 27, 24, 69, 54, 78, 61, 95, 51, 125, 86, 28, 2, 103, 116, 121, 65, 19, 40, 6, 99, 110, 0, 35, 81, 43, 83, 37, 9, 66, 20, 70, 26, 96, 33, 11, 97, 46, 4, 102, 98, 48, 80, 23, 36, 75, 3, 18, 94, 73, 62, 14, 106, 101, 53, 22, 29, 111, 84, 38, 113, 57, 72, 126, 52, 117, 90, 74, 30, 25, 13, 107, 76, 21, 7, 63, 124, 32, 50, 44, 123, 82, 64, 47, 85, 104, 42, 45, 1, 16, 8, 71, 15, 60, 39, 91, 68, 79, 41, 31, 56, 109, 17, 58, 5, 49, 120, 127, 10, 114 };

    @Override
    public void prepare(String requestUrl, String httpMethod, String contentType, HashMap<String,String> secureParameters, HashMap<String,String> rawParameters) throws Exception {
    }
    
    @Override
    public String encrypt(String plainText) throws Exception {
        String encryptedText = "";
        
        for( int i = 0; i < plainText.length(); i++ ){
            int c = plainText.charAt( i );
            encryptedText+= ENCODE_CHARS[ c ] + "-";
        }
        return encryptedText.substring(0, encryptedText.length() - 1);
    }
    
    @Override
    public String decrypt(String encryptedResponse) throws Exception {
        String[] encryptedText = encryptedResponse.split("-");
        String decryptedText = "";
        
        for( int i = 0; i < encryptedText.length; i++ ){
            int c = Integer.valueOf( encryptedText[ i ] );
            decryptedText+= (char) DECODE_CHARS[ c ];
        }
        return decryptedText;          
    }   
}
