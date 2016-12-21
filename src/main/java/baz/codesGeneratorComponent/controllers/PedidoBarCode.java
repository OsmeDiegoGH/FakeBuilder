package baz.codesGeneratorComponent.controllers;

import acertum.secureRequestHandler.controllers.SecureRequestController;
import acertum.secureRequestHandler.entities.RequestResponse;
import baz.codesGeneratorComponent.config.ComponentConfig;
import baz.codesGeneratorComponent.entities.ResponseBean;
import baz.codesGeneratorComponent.handlers.CesarEncryptorHandler;
import baz.codesGeneratorComponent.handlers.CustomRSAEncryptorHandler;
import baz.codesGeneratorComponent.structures.PipedStringList;
import baz.codesGeneratorComponent.utils.NumberFormatUtils;
import baz.codesGeneratorComponent.utils.StringUtils;
import java.util.HashMap;

public class PedidoBarCode {

    private final StringUtils stringUtils = StringUtils.getInstance();
    private final NumberFormatUtils numberFormatUtils = NumberFormatUtils.getInstance();
    private final baz.codesGeneratorComponent.config.ComponentConfig componentConfig;
    
    private final SecureRequestController<CesarEncryptorHandler> secureRequestController;
    public final String FORM_URLENCODED_CONTENT_TYPE = "application/x-www-form-urlencoded;charset=UTF-8";

    public PedidoBarCode() {
        this(new ComponentConfig(), new SecureRequestController(new CesarEncryptorHandler()));
    }
    
    private PedidoBarCode(ComponentConfig componentConfig, SecureRequestController<CesarEncryptorHandler> secureRequestController) {
        this.componentConfig = componentConfig;
        this.secureRequestController = secureRequestController;
    }

    public ResponseBean generar(final String accessToken, String sucursal, String pedido) {
        Integer statusCode = null;
        
        try {
            this.componentConfig.loadConfig();
            
            if (stringUtils.isNullOrEmpty(pedido)) {
                throw new Exception("El número de pedido no puede ir vacío");
            }
            if (stringUtils.isNullOrEmpty(sucursal)) {
                throw new Exception("El número de sucursal no puede ir vacío");
            }

            final PipedStringList contentPipedList = new PipedStringList(new String[]{
                sucursal,  
                pedido                              
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
            String requestURL = this.componentConfig.URL_BARCODE_WS + "GeneratePedidoBarCode";
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
                put("pedido-codigobarras", pipedResultList.getElement(1));
                put("pedido-creationtime", pipedResultList.getElement(2));
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
            String requestURL = this.componentConfig.URL_BARCODE_WS + "GetPedidoBarCodeData";
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
                put("pedido-codigobarras-sucursal", pipedResultList.getElement(1));
                put("pedido-codigobarras-pedido", pipedResultList.getElement(2));
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
