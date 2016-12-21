package baz.codesGeneratorComponent.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ComponentConfig {
    
    private enum ENVIROMENT{
        DEV,
        PROD
    }
    
    private ENVIROMENT ENVIROMENT;
    public String URL_QR_CODE_WS;
    public String URL_BARCODE_WS;
    public String CODES_ENCRYTION_AES_KEY;
    public boolean IGNORE_SSL_ON_HTTP_REQUEST;
    
    public class LoadPropertiesException extends Exception{
        public LoadPropertiesException(String message){
            super(message);
        }
    }
    
    public ComponentConfig() {
    }
    
    public void loadConfig() throws LoadPropertiesException{
        InputStream inputFile = null;
        Properties prop = new Properties();
        final String propertiesFullPath = "properties/CodesGeneratorComponent.properties";
        
        try {
            inputFile = getClass().getClassLoader().getResourceAsStream(propertiesFullPath);
            prop.load(inputFile);
            
            ENVIROMENT = ENVIROMENT.valueOf(prop.getProperty("ENVIROMENT").toUpperCase());
            URL_QR_CODE_WS  = prop.getProperty("URL_QR_CODE_WS");
            URL_BARCODE_WS  = prop.getProperty("URL_BARCODE_WS");
            CODES_ENCRYTION_AES_KEY  = prop.getProperty("CODES_ENCRYTION_AES_KEY");
        } catch (Exception ex) {
            System.err.println(ex.toString());
            
            try {
                if(inputFile != null) inputFile.close();
            } catch (IOException e) {
                System.err.println(e.toString());
            }
            throw new LoadPropertiesException("Unable to load input stream properties file: " + propertiesFullPath);
        }
        
        final String sslPropertiesFullPath = "properties/ssl.properties";
        try {
            inputFile = getClass().getClassLoader().getResourceAsStream(sslPropertiesFullPath);
            prop.load(inputFile);
            
            IGNORE_SSL_ON_HTTP_REQUEST  = (prop.getProperty("IGNORE_SSL_ON_HTTP_REQUEST").toLowerCase().equals("true"));
        } catch (Exception ex) {
            System.err.println(ex.toString());
            
            try {
                if(inputFile != null) inputFile.close();
            } catch (IOException e) {
                System.err.println(e.toString());
            }
            throw new LoadPropertiesException("Unable to load input stream properties file: " + propertiesFullPath);
        }
    }
}
