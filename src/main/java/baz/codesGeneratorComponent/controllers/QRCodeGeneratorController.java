package baz.codesGeneratorComponent.controllers;

import baz.codesGeneratorComponent.entities.QRContent;
import acertum.secureRequestHandler.controllers.SecureRequestController;
import acertum.secureRequestHandler.entities.RequestResponse;
import acertum.secureRequestHandler.handlers.DefaultEncryptorHandler;
import acertum.secureRequestHandler.helpers.AESEncryptorHelper;
import baz.codesGeneratorComponent.entities.ServiceResponse;
import baz.codesGeneratorComponent.utils.JSONUtils;
import baz.codesGeneratorComponent.utils.QRUtils;
import baz.codesGeneratorComponent.config.ComponentConfig;
import baz.codesGeneratorComponent.entities.Perfil;
import baz.codesGeneratorComponent.entities.Presupuesto;
import baz.codesGeneratorComponent.entities.ProductoEtiqueta;
import baz.codesGeneratorComponent.entities.ProductoTerceros;
import baz.codesGeneratorComponent.testCall.QRCodeGeneratorComponentTestCall;
import baz.codesGeneratorComponent.utils.StringUtils;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import javax.imageio.ImageIO;

public class QRCodeGeneratorController {

    private final JSONUtils jsonUtils = JSONUtils.getInstance();
    private final StringUtils stringUtils = StringUtils.getInstance();
    private final baz.codesGeneratorComponent.config.ComponentConfig componentConfig;
    
    private final SecureRequestController<DefaultEncryptorHandler> secureRequestController;
    private final DefaultEncryptorHandler encryptorHandler;
    private final AESEncryptorHelper aesEncryptorHelper = new AESEncryptorHelper();
    
    private final String PUBLIC_SERVICE_RSA_KEY_PATH = "/crypto/public_service.der";
    private final String GENERATE_QR_CONTENT_WS_METHOD = "Generate";
    private final String DECIPHER_QR_CONTENT_WS_METHOD = "Decipher";
    private final String GENERATE_PROFILEQR_CONTENT_WS_METHOD = "GenerateProfileQRContent";
    public final String FORM_URLENCODED_CONTENT_TYPE = "application/x-www-form-urlencoded;charset=UTF-8";

    public QRCodeGeneratorController() {
        this(new ComponentConfig(), new SecureRequestController(QRCodeGeneratorController.class));
    }

    private QRCodeGeneratorController(ComponentConfig componentConfig, SecureRequestController secureRequestController) {
        this.componentConfig = componentConfig;
        this.secureRequestController = secureRequestController;
        this.encryptorHandler = this.secureRequestController.getHandler();
        
    }

    public RequestResponse generateQR1(Presupuesto presupuesto, final int size, final String tokenAcceso) {
        try {
            this.componentConfig.loadConfig();

            if (presupuesto.getPresupuesto() <= 0) {
                throw new Exception("Error: El presupuesto debe ser un número mayor a 0");
            }
            if (presupuesto.getSucursal() <= 0) {
                throw new Exception("Error: La sucursal debe ser un número mayor a 0");
            }
            if (size <= 0 || size > 1024) {
                throw new Exception("Error: El tamaño de la imagen debe ser un número mayor a 0 y menor a 1024");
            }
            this.encryptorHandler.setPublicRSAKeyPath(this.PUBLIC_SERVICE_RSA_KEY_PATH);

            final String jsonContent = this.jsonUtils.ObjectToJSON(presupuesto);
            HashMap<String, String> secureParameters = new HashMap() {
                {
                    put("encryptedContent", jsonContent);
                }
            };
            HashMap<String, String> rawParameters = new HashMap() {
                {
                    put("qrType", QRContent.QRType.QR1.name());
                    put("qrSize", String.valueOf(size));
                    put("tokenAcceso", tokenAcceso);
                    put("qrBackColor",QRContent.QrBackColor.blackColor.name());
                    put("centerLogo", QRContent.CenterLogo.centerLogoQR1.name());
                }
            };
            String requestURL = this.componentConfig.URL_QR_CODE_WS + this.GENERATE_QR_CONTENT_WS_METHOD;
            RequestResponse bridgeResponse = this.secureRequestController.doSecurePOST(
                    requestURL, 
                    this.FORM_URLENCODED_CONTENT_TYPE, 
                    secureParameters, 
                    rawParameters, 
                    this.componentConfig.IGNORE_SSL_ON_HTTP_REQUEST
            );

            if (bridgeResponse.getCode() != RequestResponse.RESPONSE_CODE.SUCCESS) {
                throw new Exception("Error en el puente del consumo del servicio: " + bridgeResponse.getResult());
            }

            final String decryptedServiceResult = bridgeResponse.getResult();
            ServiceResponse serviceResponse = this.jsonUtils.<ServiceResponse>JSONToObject(decryptedServiceResult, ServiceResponse.class);
            if (serviceResponse.getCode() >= 500) {
                throw new Exception("Error en servicio --- Code:" + String.valueOf(serviceResponse.getCode()) + ", Message:" + serviceResponse.getResult());
            }
            return new RequestResponse(RequestResponse.RESPONSE_CODE.SUCCESS, serviceResponse.getResult());
        } catch (Exception ex) {
            return new RequestResponse(RequestResponse.RESPONSE_CODE.ERROR, ex.getMessage());
        }
    }

    public RequestResponse generateQR2(Presupuesto presupuesto, final int size, final String tokenAcceso) throws Exception {
        try {
            this.componentConfig.loadConfig();

            if (presupuesto.getPresupuesto() <= 0) {
                throw new Exception("Error: El presupuesto debe ser un número mayor a 0");
            }
            if (presupuesto.getSucursal() <= 0) {
                throw new Exception("Error: La sucursal debe ser un número mayor a 0");
            }
            if (size <= 0 || size > 1024) {
                throw new Exception("Error: El tamaño de la imagen debe ser un número mayor a 0 y menor a 1024");
            }

            this.encryptorHandler.setPublicRSAKeyPath(this.PUBLIC_SERVICE_RSA_KEY_PATH);

            final String jsonContent = this.jsonUtils.ObjectToJSON(presupuesto);
            HashMap<String, String> secureParameters = new HashMap() {
                {
                    put("encryptedContent", jsonContent);
                }
            };
            HashMap<String, String> rawParameters = new HashMap() {
                {
                    put("qrType", QRContent.QRType.QR2.name());
                    put("qrSize", String.valueOf(size));
                    put("tokenAcceso", tokenAcceso);
                    put("qrBackColor",QRContent.QrBackColor.grayColor.name());
                    put("centerLogo", QRContent.CenterLogo.centerLogoQR2.name());
                }
            };
            String requestURL = this.componentConfig.URL_QR_CODE_WS + this.GENERATE_QR_CONTENT_WS_METHOD;
            RequestResponse bridgeResponse = this.secureRequestController.doSecurePOST(
                    requestURL, 
                    this.FORM_URLENCODED_CONTENT_TYPE, 
                    secureParameters, 
                    rawParameters, 
                    this.componentConfig.IGNORE_SSL_ON_HTTP_REQUEST
            );

            if (bridgeResponse.getCode() != RequestResponse.RESPONSE_CODE.SUCCESS) {
                throw new Exception("Error en el puente del consumo del servicio: " + bridgeResponse.getResult());
            }

            final String decryptedServiceResult = bridgeResponse.getResult();
            ServiceResponse serviceResponse = this.jsonUtils.<ServiceResponse>JSONToObject(decryptedServiceResult, ServiceResponse.class);
            if (serviceResponse.getCode() >= 500) {
                throw new Exception("Error en servicio --- Code:" + String.valueOf(serviceResponse.getCode()) + ", Message:" + serviceResponse.getResult());
            }
            return new RequestResponse(RequestResponse.RESPONSE_CODE.SUCCESS, serviceResponse.getResult());
        } catch (Exception ex) {
            return new RequestResponse(RequestResponse.RESPONSE_CODE.ERROR, ex.getMessage());
        }
    }

    public RequestResponse generateQR3(ProductoEtiqueta producto, int size) {
        String resultQR = "";
        try {
            this.componentConfig.loadConfig();
            
            if (stringUtils.isNullOrEmpty(producto.getSKU())) {
                throw new Exception("Error: el SKU no debe ir vacío");
            }
            if (!stringUtils.containsOnlyNumbers(producto.getSKU())) {
                throw new Exception("Error: El SKU debe contener unicamente números");
            }
            if (producto.getTienda() <= 0) {
                throw new Exception("Error: La sucursal debe ser un número mayor a 0");
            }
            if (size <= 0 || size > 1024) {
                throw new Exception("Error: El tamaño de la imagen debe ser un número mayor a 0 y menor a 1024");
            }

            String cadena = jsonUtils.ObjectToJSON(producto);
            String result =   aesEncryptorHelper.encrypt(cadena, this.componentConfig.CODES_ENCRYTION_AES_KEY);
            String jsonQR = jsonUtils.ObjectToJSON(new QRContent(result, QRContent.QRType.QR3));
            
            String centerImgPath = "/images/100x100n.png";
            BufferedImage centerLogo = ImageIO.read(QRCodeGeneratorComponentTestCall.class.getResourceAsStream(centerImgPath));
            
            byte[] bytesQR = QRUtils.getInstance().generateQRBytesArray(jsonQR, size, "UTF-8", "png", centerLogo);
            resultQR = Base64.encode(bytesQR);

            if (stringUtils.isNullOrEmpty(resultQR)) {
                throw new Exception("Error: al generar QR en base64");
            }
            return new RequestResponse(RequestResponse.RESPONSE_CODE.SUCCESS, resultQR);
        } catch (Exception ex) {
            return new RequestResponse(RequestResponse.RESPONSE_CODE.ERROR, ex.getMessage());
        }
    }

    public RequestResponse generateQRProfile(Perfil perfil, final String tokenAcceso) {
        try {
            this.componentConfig.loadConfig();

            if (stringUtils.isNullOrEmpty(perfil.getICU())) {
                throw new Exception("Error: El cliente unico debe ser un número mayor a 0");
            }
            if (stringUtils.isNullOrEmpty(perfil.getCteAlnova())) {
                throw new Exception("Error: El cliente ALnova debe ser un número mayor a 0");
            }
            if (stringUtils.isNullOrEmpty(perfil.getNumSolicitudCentralizada())){
                throw new Exception("Error: El número de solicitud centralizada no debe ir vacio");
            }
            if (stringUtils.isNullOrEmpty(perfil.getNumSolicitudTienda())){
                throw new Exception("Error: El número de solicitud tienda no debe ir vacio");
            }
            if (perfil.getIdRegistroMensaje() <= 0) {
                throw new Exception("Error: El id de registro del mensaje, debe ser un numero mayor a 0");
            }
            if (perfil.getTipoSolicitud() <= 0 || perfil.getTipoSolicitud() > 2) {
                throw new Exception("Error: el tipo de solicitud es inválido. Tipos de solicitudes válidas credito = 1 , captación = 2");
            }

            this.encryptorHandler.setPublicRSAKeyPath(this.PUBLIC_SERVICE_RSA_KEY_PATH);

            final String jsonContent = jsonUtils.ObjectToJSON(perfil);
            HashMap<String, String> secureParameters = new HashMap() {
                {
                    put("encryptedContent", jsonContent);
                }
            };
            HashMap<String, String> rawParameters = new HashMap() {
                {
                    put("tokenAcceso", tokenAcceso);
                }
            };
            String requestURL = this.componentConfig.URL_QR_CODE_WS + this.GENERATE_PROFILEQR_CONTENT_WS_METHOD;
            RequestResponse bridgeResponse = this.secureRequestController.doSecurePOST(
                    requestURL, 
                    this.FORM_URLENCODED_CONTENT_TYPE, 
                    secureParameters, 
                    rawParameters, 
                    this.componentConfig.IGNORE_SSL_ON_HTTP_REQUEST
            );

            if (bridgeResponse.getCode() != RequestResponse.RESPONSE_CODE.SUCCESS) {
                throw new Exception("Error en el puente del consumo del servicio: " + bridgeResponse.getResult());
            }

            final String decryptedServiceResult = bridgeResponse.getResult();
            ServiceResponse serviceResponse = jsonUtils.<ServiceResponse>JSONToObject(decryptedServiceResult, ServiceResponse.class);
            if (serviceResponse.getCode() > 500) {
                throw new Exception("Error en servicio --- Code:" + String.valueOf(serviceResponse.getCode()) + ", Message:" + serviceResponse.getResult());
            }
            return new RequestResponse(RequestResponse.RESPONSE_CODE.SUCCESS, serviceResponse.getResult());
        } catch (Exception ex) {
            return new RequestResponse(RequestResponse.RESPONSE_CODE.ERROR, ex.getMessage());
        }
    }

    public RequestResponse decipherQRContent(String QRContent, final String tokenAcceso) {
        try {
            this.componentConfig.loadConfig();

            final QRContent qrJSONContent = this.jsonUtils.JSONToObject(QRContent, QRContent.class);

            if (stringUtils.isNullOrEmpty(qrJSONContent.getEncryptedContent())) {
                throw new Exception("Contenido del QR no válido, verifique que contenga el attributo 'encryptedContent'");
            }

            this.encryptorHandler.setPublicRSAKeyPath(this.PUBLIC_SERVICE_RSA_KEY_PATH);
           
            HashMap<String, String> rawParameters = new HashMap() {
                {
                    put("qrType", qrJSONContent.getQrType().name());
                    put("tokenAcceso", tokenAcceso);
                    put("encryptedContent", qrJSONContent.getEncryptedContent());
                }
            };
            String requestURL = this.componentConfig.URL_QR_CODE_WS + this.DECIPHER_QR_CONTENT_WS_METHOD;
            RequestResponse bridgeResponse = this.secureRequestController.doSecurePOST(
                    requestURL, 
                    this.FORM_URLENCODED_CONTENT_TYPE, 
                    new HashMap<String, String>(), 
                    rawParameters, 
                    this.componentConfig.IGNORE_SSL_ON_HTTP_REQUEST
            );

            if (bridgeResponse.getCode() != RequestResponse.RESPONSE_CODE.SUCCESS) {
                throw new Exception("Error en el puente del consumo del servicio: " + bridgeResponse.getResult());
            }

            final String decryptedServiceResult = bridgeResponse.getResult();
            ServiceResponse serviceResponse = this.jsonUtils.<ServiceResponse>JSONToObject(decryptedServiceResult, ServiceResponse.class);
            if (serviceResponse.getCode() >= 500) {
                throw new Exception("Error en servicio --- Code:" + serviceResponse.getCode() + ", Message:" + serviceResponse.getResult());
            }

            return new RequestResponse(RequestResponse.RESPONSE_CODE.SUCCESS, serviceResponse.getResult());
        } catch (Exception ex) {
            return new RequestResponse(RequestResponse.RESPONSE_CODE.ERROR, ex.getMessage());
        }
    }
    
     public RequestResponse generateQRCanalesATerceros(ProductoTerceros producto, int size) {
        String resultQR = "";
        try {
            this.componentConfig.loadConfig();
            
            if (stringUtils.isNullOrEmpty(producto.getSKU())) {
                throw new Exception("Error: el SKU no debe ir vacío");
            }
            if (!stringUtils.containsOnlyNumbers(producto.getSKU())) {
                throw new Exception("Error: El SKU debe contener unicamente números");
            }
            if (producto.getPrecio() <= 0) {
                throw new Exception("Error: El precio debe ser un número mayor a 0");
            }
            if (producto.getId_Canal() <= 0) {
                throw new Exception("Error: El id del canal a terceros debe ser un número mayor a 0");
            }
            if (size <= 0 || size > 1024) {
                throw new Exception("Error: El tamaño de la imagen debe ser un número mayor a 0 y menor a 1024");
            }

            String cadena = jsonUtils.ObjectToJSON(producto);
            String result =   aesEncryptorHelper.encrypt(cadena, this.componentConfig.CODES_ENCRYTION_AES_KEY);
            String jsonQR = jsonUtils.ObjectToJSON(new QRContent(result, QRContent.QRType.QRCanalesTerceros));
            
            String centerImgPath = "/images/100x100n.png";
            BufferedImage centerLogo = ImageIO.read(QRCodeGeneratorComponentTestCall.class.getResourceAsStream(centerImgPath));
            
            byte[] bytesQR = QRUtils.getInstance().generateQRBytesArray(jsonQR, size, "UTF-8", "png", centerLogo);
            resultQR = Base64.encode(bytesQR);

            if (stringUtils.isNullOrEmpty(resultQR)) {
                throw new Exception("Error: al generar QR en base64");
            }
            return new RequestResponse(RequestResponse.RESPONSE_CODE.SUCCESS, resultQR);
        } catch (Exception ex) {
            return new RequestResponse(RequestResponse.RESPONSE_CODE.ERROR, ex.getMessage());
        }
    }
}
