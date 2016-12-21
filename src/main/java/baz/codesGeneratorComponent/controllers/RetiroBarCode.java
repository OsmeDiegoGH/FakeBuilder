package baz.codesGeneratorComponent.controllers;

import acertum.secureRequestHandler.controllers.SecureRequestController;
import acertum.secureRequestHandler.entities.RequestResponse;
import baz.codesGeneratorComponent.config.ComponentConfig;
import baz.codesGeneratorComponent.entities.ResponseBean;
import baz.codesGeneratorComponent.handlers.CesarEncryptorHandler;
import baz.codesGeneratorComponent.structures.PipedStringList;
import baz.codesGeneratorComponent.utils.NumberFormatUtils;
import java.util.HashMap;

public class RetiroBarCode {

    private final NumberFormatUtils numberFormatUtils = NumberFormatUtils.getInstance();
    private final baz.codesGeneratorComponent.config.ComponentConfig componentConfig;
    
    private final SecureRequestController<CesarEncryptorHandler> secureRequestController;
    
    public final String FORM_URLENCODED_CONTENT_TYPE = "application/x-www-form-urlencoded;charset=UTF-8";

    public RetiroBarCode() {
        this(new ComponentConfig(), new SecureRequestController(new CesarEncryptorHandler()));
    }
    
    private RetiroBarCode(ComponentConfig componentConfig, SecureRequestController secureRequestController) {
        this.componentConfig = componentConfig;
        this.secureRequestController = secureRequestController;
    }

    public ResponseBean generar(final String accessToken, String monto, String numeroCuentaOTarjeta) {
        Integer statusCode = null;
        
        try {
            this.componentConfig.loadConfig();
           
            final PipedStringList contentPipedList = new PipedStringList(new String[]{
                monto,  
                numeroCuentaOTarjeta                                  
            });
            HashMap<String, String> secureParameters = new HashMap() {
                {
                    put("encryptedContent", contentPipedList.toString());
                }
            };
            HashMap<String, String> rawParameters = new HashMap() {
                {
                    put("accessToken", accessToken);
                }
            };
            String requestURL = this.componentConfig.URL_BARCODE_WS + "GenerateRetiroBarCode";
            final RequestResponse bridgeResponse = this.secureRequestController.doSecureRequest(
                    requestURL, 
                    "POST", 
                    this.FORM_URLENCODED_CONTENT_TYPE, 
                    secureParameters, 
                    rawParameters, 
                    this.componentConfig.IGNORE_SSL_ON_HTTP_REQUEST
            );

            if (bridgeResponse.getCode() != RequestResponse.RESPONSE_CODE.SUCCESS) {
                throw new Exception("Error en el puente del consumo del servicio: " + bridgeResponse.getResult());
            }

            final PipedStringList pipedResultList = new PipedStringList(bridgeResponse.getResult());
            final String statusCodeStr = pipedResultList.getElement(0);
            statusCode = numberFormatUtils.tryParseInt( statusCodeStr );
            
            if (statusCode == null || statusCode >= 500) {
                throw new Exception("Error en servicio --- Code:" + statusCodeStr + ", Message:" + bridgeResponse.getResult());
            }
            HashMap<String, Object> data = new HashMap(){{
                put("retiro-codigobarras", pipedResultList.getElement(1));
                put("retiro-creationtime", pipedResultList.getElement(2));
            }};
            return new ResponseBean(200, data);
        } catch (Exception ex) {
            if(statusCode == null){
                statusCode = 500;
            }
            return new ResponseBean(statusCode, "Error: " + ex.getMessage());
        }
    }
    
    public ResponseBean verContenido(final String accessToken, final String barcode) {
        Integer statusCode = null;
        
        try {
            this.componentConfig.loadConfig();
            
            if(barcode == null){
                throw new Exception("Código de barras vacío");
            }
            
            HashMap<String, String> secureParameters = new HashMap() {
                {
                    put("encryptedContent", barcode);
                }
            };
            HashMap<String, String> rawParameters = new HashMap() {
                {
                    put("accessToken", accessToken);
                }
            };
            String requestURL = this.componentConfig.URL_BARCODE_WS + "GetRetiroBarCodeData";
            final RequestResponse bridgeResponse = this.secureRequestController.doSecureRequest(
                    requestURL, 
                    "POST", 
                    this.FORM_URLENCODED_CONTENT_TYPE, 
                    secureParameters, 
                    rawParameters, 
                    this.componentConfig.IGNORE_SSL_ON_HTTP_REQUEST
            );

            if (bridgeResponse.getCode() != RequestResponse.RESPONSE_CODE.SUCCESS) {
                throw new Exception("Error en el puente del consumo del servicio: " + bridgeResponse.getResult());
            }
            
            final PipedStringList pipedResultList = new PipedStringList(bridgeResponse.getResult());
            final String statusCodeStr = pipedResultList.getElement(0);
            statusCode = numberFormatUtils.tryParseInt( statusCodeStr );
            
            if (statusCode == null || statusCode >= 500) {
                throw new Exception("Error en servicio --- Code:" + statusCodeStr + ", Message:" + bridgeResponse.getResult());
            }
            HashMap<String, Object> data = new HashMap(){{
                put("retiro-codigobarras-monto", pipedResultList.getElement(1));
                put("retiro-codigobarras-numerocuenta", pipedResultList.getElement(2));
            }};
            return new ResponseBean(200, data);
        } catch (Exception ex) {
            if(statusCode == null){
                statusCode = 500;
            }
            return new ResponseBean(statusCode, "Error: " + ex.getMessage());
        }
    }
    
    public ResponseBean validar(final String accessToken, final String barcode) {
        Integer statusCode = null;
        
        try {
            this.componentConfig.loadConfig();
            
            if(barcode == null){
                throw new Exception("Código de barras vacío");
            }

            HashMap<String, String> secureParameters = new HashMap() {
                {
                    put("encryptedContent", barcode);
                }
            };
            HashMap<String, String> rawParameters = new HashMap() {
                {
                    put("accessToken", accessToken);
                }
            };
            String requestURL = this.componentConfig.URL_BARCODE_WS + "ValidateBarCode";
            final RequestResponse bridgeResponse = this.secureRequestController.doSecureRequest(
                    requestURL, 
                    "POST", 
                    this.FORM_URLENCODED_CONTENT_TYPE, 
                    secureParameters, 
                    rawParameters, 
                    this.componentConfig.IGNORE_SSL_ON_HTTP_REQUEST
            );

            if (bridgeResponse.getCode() != RequestResponse.RESPONSE_CODE.SUCCESS) {
                throw new Exception("Error en el puente del consumo del servicio: " + bridgeResponse.getResult());
            }
            
            
            final PipedStringList pipedResultList = new PipedStringList(bridgeResponse.getResult());
            final String statusCodeStr = pipedResultList.getElement(0);
            statusCode = numberFormatUtils.tryParseInt( statusCodeStr );
            
            if (statusCode == null || statusCode >= 500) {
                throw new Exception("Error en servicio --- Code:" + statusCodeStr + ", Message:" + bridgeResponse.getResult());
            }
            HashMap<String, Object> data = new HashMap(){{
                put("codigobarras-es-valido", pipedResultList.getElement(1));
            }};
            return new ResponseBean(200, data);
        } catch (Exception ex) {
            if(statusCode == null){
                statusCode = 500;
            }
            return new ResponseBean(statusCode, "Error: " + ex.getMessage());
        }
    }
}
