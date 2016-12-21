package baz.codesGeneratorComponent.controllers.test;

import acertum.secureRequestHandler.controllers.SecureRequestController;
import acertum.secureRequestHandler.entities.RequestResponse;
import baz.codesGeneratorComponent.config.ComponentConfig;
import baz.codesGeneratorComponent.config.Globals;
import baz.codesGeneratorComponent.controllers.QRCodeGeneratorController;
import baz.codesGeneratorComponent.entities.Perfil;
import baz.codesGeneratorComponent.entities.Presupuesto;
import baz.codesGeneratorComponent.entities.ProductoEtiqueta;
import baz.codesGeneratorComponent.entities.QRContent;
import baz.codesGeneratorComponent.entities.ServiceResponse;
import baz.codesGeneratorComponent.utils.JSONUtils;
import baz.codesGeneratorComponent.utils.QRUtils;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

/*@Ignore
@RunWith(JUnitParamsRunner.class)
public class QRCodeGeneratorControllerTest {

    private QRCodeGeneratorController contoller;
    @Mock
    private ComponentConfig mockComponentConfig;
    @Mock
    private SecureRequestController mockSecureRequestController;
    @Mock
    private MockEncryptionController mockEncryptionController;
    private final JSONUtils jsonUtils = JSONUtils.getInstance();
    
    private final static String GENERATE_QR_CONTENT_WS_METHOD = "Generate";
    private final static String DECIPHER_QR_CONTENT_WS_METHOD = "Decipher";
    private final static String GENERATE_PROFILEQR_CONTENT_WS_METHOD = "GenerateProfileQRContent";

    private class MockEncryptionController extends EncryptionController {

        public MockEncryptionController() {
            super(QRCodeGeneratorController.class);
        }
    }

    @Before
    public void init() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException {
        MockitoAnnotations.initMocks(this);

        Constructor constructor = QRCodeGeneratorController.class.getDeclaredConstructor(ComponentConfig.class, SecureRequestController.class, EncryptionController.class);
        constructor.setAccessible(true);

        contoller = (QRCodeGeneratorController) constructor.newInstance(mockComponentConfig, mockSecureRequestController, mockEncryptionController);
    }

    @Test
    public void itShouldPerformPOSTWithCorrectParameters_generateQR1() throws Exception {
        //Arrange
        Presupuesto presupuesto = new Presupuesto();
        presupuesto.setPresupuesto(123123);
        presupuesto.setSucursal(343);

        HashMap<String, String> fakeSecureParameters = new HashMap();
        fakeSecureParameters.put("encryptedContent", jsonUtils.ObjectToJSON(presupuesto));

        mockComponentConfig.URL_QR_CODE_WS = "hhtps://fake.url.com";

        final String fakeTokenAcceso = "sd$%$&;MDS=D";
        final int qrSize = 100;
        HashMap<String, String> fakeRawParameters = new HashMap() {
            {
                put("qrType", QRContent.QRType.QR1.name());
                put("qrSize", String.valueOf(qrSize));
                put("tokenAcceso", fakeTokenAcceso);
            }
        };

        String serviceResponseJSON = jsonUtils.ObjectToJSON(new ServiceResponse(200, ""));
        when(
                mockSecureRequestController.doSecurePOST(mockComponentConfig.URL_QR_CODE_WS + this.GENERATE_QR_CONTENT_WS_METHOD, fakeSecureParameters, fakeRawParameters)
        ).thenReturn(new RequestResponse(RequestResponse.RESPONSE_CODE.SUCCESS, serviceResponseJSON));

        //Act
        contoller.generateQR1(presupuesto, qrSize, fakeTokenAcceso);

        //Assert       
        verify(mockSecureRequestController, times(1)).doSecurePOST(mockComponentConfig.URL_QR_CODE_WS + this.GENERATE_QR_CONTENT_WS_METHOD, fakeSecureParameters, fakeRawParameters);
    }

    @Test
    public void itShouldReturnSuccessResponse_generateQR1() throws Exception {
        //Arrange
        Presupuesto presupuesto = new Presupuesto();
        presupuesto.setPresupuesto(123123);
        presupuesto.setSucursal(343);

        String expectedServiceResult = "Result of service";
        String expectedResultJSON = jsonUtils.ObjectToJSON(new ServiceResponse(200, expectedServiceResult));

        when(
                mockSecureRequestController.doSecurePOST(anyString(), Matchers.<HashMap<String, String>>any(), Matchers.<HashMap<String, String>>any())
        ).thenReturn(new RequestResponse(RequestResponse.RESPONSE_CODE.SUCCESS, expectedResultJSON));

        //Act
        RequestResponse response = contoller.generateQR1(presupuesto, 100, "");

        //Assert       
        assertEquals(RequestResponse.RESPONSE_CODE.SUCCESS, response.getCode());
        assertEquals(expectedServiceResult, response.getResult());
    }

    @Test
    @Parameters({"500", "501", "520", "510"})
    public void itShouldReturnErrorResponse_WhenPOSTRequestReturnsErrorCode_generateQR1(int errorCode) throws Exception {
        //Arrange
        Presupuesto presupuesto = new Presupuesto();
        presupuesto.setPresupuesto(123123);
        presupuesto.setSucursal(343);

        String serviceResponseJSON = jsonUtils.ObjectToJSON(new ServiceResponse(errorCode, ""));
        when(
                mockSecureRequestController.doSecurePOST(anyString(), Matchers.<HashMap<String, String>>any(), Matchers.<HashMap<String, String>>any())
        ).thenReturn(new RequestResponse(RequestResponse.RESPONSE_CODE.SUCCESS, serviceResponseJSON));

        //Act
        RequestResponse response = contoller.generateQR1(presupuesto, 100, "");

        //Assert       
        assertEquals(RequestResponse.RESPONSE_CODE.ERROR, response.getCode());
        assertTrue(response.getResult().contains("Error en servicio"));
    }

    @Test
    @Parameters({"500", "501", "520", "510"})
    public void itShouldReturnErrorResponse_WhenBridgeResponseReturnsError_generateQR1(int errorCode) throws Exception {
        //Arrange
        Presupuesto presupuesto = new Presupuesto();
        presupuesto.setPresupuesto(123123);
        presupuesto.setSucursal(343);

        String serviceResponseJSON = jsonUtils.ObjectToJSON(new ServiceResponse(errorCode, ""));
        when(
                mockSecureRequestController.doSecurePOST(anyString(), Matchers.<HashMap<String, String>>any(), Matchers.<HashMap<String, String>>any())
        ).thenReturn(new RequestResponse(RequestResponse.RESPONSE_CODE.ERROR, serviceResponseJSON));

        //Act
        RequestResponse response = contoller.generateQR1(presupuesto, 100, "");

        //Assert       
        assertEquals(RequestResponse.RESPONSE_CODE.ERROR, response.getCode());
        assertTrue(response.getResult().contains("Error en el puente"));
    }

    @Test
    @Parameters({"0,123", "-1,123", "123,0", "123, -1"})
    public void itShouldReturnErrorResponse_WhenPresupuestoHasUnexpectedParameterValues_generateQR1(int numeroPresupuesto, int tienda) throws Exception {
        //Arrange
        Presupuesto presupuesto = new Presupuesto();
        presupuesto.setPresupuesto(numeroPresupuesto);
        presupuesto.setSucursal(tienda);

        //Act
        RequestResponse response = contoller.generateQR1(presupuesto, 100, "");

        //Assert       
        assertEquals(RequestResponse.RESPONSE_CODE.ERROR, response.getCode());
        assertTrue(response.getResult().contains("presupuesto") || response.getResult().contains("sucursal"));
        assertTrue(response.getResult().contains("número mayor a 0"));
    }

    @Test
    @Parameters({"-20", "2000", "5000", "-1000"})
    public void itShouldReturnErrorResponse_WhenQRSizeIsUnexpectedNumber_generateQR1(int qrSize) throws Exception {
        //Arrange
        Presupuesto presupuesto = new Presupuesto();
        presupuesto.setPresupuesto(123);
        presupuesto.setSucursal(123);

        //Act
        RequestResponse response = contoller.generateQR1(presupuesto, qrSize, "");

        //Assert       
        assertEquals(RequestResponse.RESPONSE_CODE.ERROR, response.getCode());
        assertTrue(response.getResult().contains("tamaño de la imagen") && response.getResult().contains("número mayor a 0 y menor a 1024"));
    }

    @Test
    public void itShouldCallServiceWithCorrectParameters_generateQR2() throws Exception {
        //Arrange
        Presupuesto presupuesto = new Presupuesto();
        presupuesto.setPresupuesto(123123);
        presupuesto.setSucursal(343);

        HashMap<String, String> fakeSecureParameters = new HashMap();
        fakeSecureParameters.put("encryptedContent", jsonUtils.ObjectToJSON(presupuesto));

        mockComponentConfig.URL_QR_CODE_WS = "hhtps://fake.url.com";

        final String fakeTokenAcceso = "sd$%$&;MDS=D";
        final int qrSize = 100;
        HashMap<String, String> fakeRawParameters = new HashMap() {
            {
                put("qrType", QRContent.QRType.QR1.name());
                put("qrSize", String.valueOf(qrSize));
                put("tokenAcceso", fakeTokenAcceso);
            }
        };

        String serviceResponseJSON = jsonUtils.ObjectToJSON(new ServiceResponse(200, ""));
        when(
                mockSecureRequestController.doSecurePOST(mockComponentConfig.URL_QR_CODE_WS + this.GENERATE_QR_CONTENT_WS_METHOD, fakeSecureParameters, fakeRawParameters)
        ).thenReturn(new RequestResponse(RequestResponse.RESPONSE_CODE.SUCCESS, serviceResponseJSON));

        //Act
        contoller.generateQR2(presupuesto, qrSize, fakeTokenAcceso);

        //Assert       
        verify(mockSecureRequestController, times(1)).doSecurePOST(anyString(), Matchers.<HashMap<String, String>>any(), Matchers.<HashMap<String, String>>any());
    }

    @Test
    public void itShouldReturnSuccessResponse_generateQR2() throws Exception {
        //Arrange
        Presupuesto presupuesto = new Presupuesto();
        presupuesto.setPresupuesto(123123);
        presupuesto.setSucursal(343);

        ServiceResponse serviceResponse = new ServiceResponse(200, "Result of service");
        String expectedResultJSON = jsonUtils.ObjectToJSON(serviceResponse);

        when(
                mockSecureRequestController.doSecurePOST(anyString(), "application/x-www-form-urlencoded;charset=UTF-8", Matchers.<HashMap<String, String>>any(), Matchers.<HashMap<String, String>>any())
        ).thenReturn(new RequestResponse(RequestResponse.RESPONSE_CODE.SUCCESS, expectedResultJSON));

        //Act
        RequestResponse response = contoller.generateQR1(presupuesto, 100, "");

        //Assert       
        assertEquals(RequestResponse.RESPONSE_CODE.SUCCESS, response.getCode());
        assertEquals(serviceResponse.getResult(), response.getResult());
    }

    @Test
    @Parameters({"500", "501", "520", "510"})
    public void itShouldReturnErrorResponse_WhenPOSTRequestReturnsErrorCode_generateQR2(int errorCode) throws Exception {
        //Arrange
        Presupuesto presupuesto = new Presupuesto();
        presupuesto.setPresupuesto(123123);
        presupuesto.setSucursal(343);

        String serviceResponseJSON = jsonUtils.ObjectToJSON(new ServiceResponse(errorCode, ""));
        when(
                mockSecureRequestController.doSecurePOST(anyString(), "application/x-www-form-urlencoded;charset=UTF-8", Matchers.<HashMap<String, String>>any(), Matchers.<HashMap<String, String>>any())
        ).thenReturn(new RequestResponse(RequestResponse.RESPONSE_CODE.SUCCESS, serviceResponseJSON));

        //Act
        RequestResponse response = contoller.generateQR2(presupuesto, 100, "");

        //Assert       
        assertEquals(RequestResponse.RESPONSE_CODE.ERROR, response.getCode());
        assertTrue(response.getResult().contains("Error en servicio"));
    }

    @Test
    public void itShouldReturnErrorResponse_WhenBridgeResponseReturnsError_generateQR2() throws Exception {
        //Arrange
        Presupuesto presupuesto = new Presupuesto();
        presupuesto.setPresupuesto(123123);
        presupuesto.setSucursal(343);

        String serviceResponseJSON = jsonUtils.ObjectToJSON(new ServiceResponse(0, ""));
        when(
                mockSecureRequestController.doSecurePOST(anyString(), Matchers.<HashMap<String, String>>any(), Matchers.<HashMap<String, String>>any())
        ).thenReturn(new RequestResponse(RequestResponse.RESPONSE_CODE.ERROR, serviceResponseJSON));

        //Act
        RequestResponse response = contoller.generateQR2(presupuesto, 100, "");

        //Assert       
        assertEquals(RequestResponse.RESPONSE_CODE.ERROR, response.getCode());
        assertTrue(response.getResult().contains("Error en el puente"));
    }

    @Test
    @Parameters({"0,123", "-1,123", "123,0", "123, -1"})
    public void itShouldReturnErrorResponse_WhenPresupuestoHasUnexpectedParameterValues_generateQR2(int numeroPresupuesto, int tienda) throws Exception {
        //Arrange
        Presupuesto presupuesto = new Presupuesto();
        presupuesto.setPresupuesto(numeroPresupuesto);
        presupuesto.setSucursal(tienda);

        //Act
        RequestResponse response = contoller.generateQR1(presupuesto, 100, "");

        //Assert       
        assertEquals(RequestResponse.RESPONSE_CODE.ERROR, response.getCode());
        assertTrue(response.getResult().contains("presupuesto") || response.getResult().contains("sucursal"));
        assertTrue(response.getResult().contains("número mayor a 0"));
    }

    @Test
    @Parameters({"-20", "2000", "5000", "-1000"})
    public void itShouldReturnErrorResponse_WhenQRSizeIsUnexpectedNumber_generateQR2(int qrSize) throws Exception {
        //Arrange
        Presupuesto presupuesto = new Presupuesto();
        presupuesto.setPresupuesto(123);
        presupuesto.setSucursal(123);

        //Act
        RequestResponse response = contoller.generateQR2(presupuesto, qrSize, "");

        //Assert       
        assertEquals(RequestResponse.RESPONSE_CODE.ERROR, response.getCode());
        assertTrue(response.getResult().contains("tamaño de la imagen") && response.getResult().contains("número mayor a 0 y menor a 1024"));
    }

    @Test
    public void itShouldGenerateEncryptionQR_generateQR3() throws Exception {
        //Arrange
        ProductoEtiqueta producto = new ProductoEtiqueta();
        producto.setSKU("1232");
        producto.setTienda(1232);

        //TODO: generate random strings for this properties
        mockComponentConfig.CODES_ENCRYTION_AES_KEY = "2321h31ub3kb13";
        String fakeEncryptedContent = "asasd12123";

        String productJSON = jsonUtils.ObjectToJSON(producto);
        when(
                mockEncryptionController.AESencrypt(productJSON, mockComponentConfig.CODES_ENCRYTION_AES_KEY)
        ).thenReturn(fakeEncryptedContent);
        int qrSize = 100;

        String fakeQRContent = jsonUtils.ObjectToJSON(new QRContent(fakeEncryptedContent, QRContent.QRType.QR3));
        String expectedBase64QR = Base64.encode(QRUtils.getInstance().generateQRBytesArray(fakeQRContent, qrSize, "UTF-8", "png"));

        //Act
        RequestResponse response = contoller.generateQR3(producto, qrSize);

        //Assert       
        verify(mockEncryptionController).AESencrypt(productJSON, mockComponentConfig.CODES_ENCRYTION_AES_KEY);
        assertEquals(expectedBase64QR, response.getResult());
        assertEquals(RequestResponse.RESPONSE_CODE.SUCCESS, response.getCode());
    }

    @Test
    @Parameters({"-20", "2000", "5000", "-1000"})
    public void itShouldReturnErrorResponse_WhenQRSizeIsUnexpectedNumber_generateQR3(int qrSize) throws Exception {
        //Arrange
        ProductoEtiqueta producto = new ProductoEtiqueta();
        producto.setSKU("1232");
        producto.setTienda(1232);

        //Act
        RequestResponse response = contoller.generateQR3(producto, qrSize);

        //Assert       
        assertEquals(RequestResponse.RESPONSE_CODE.ERROR, response.getCode());
        assertTrue(response.getResult().contains("tamaño de la imagen") && response.getResult().contains("número mayor a 0 y menor a 1024"));
    }

    @Test
    @Parameters({",123", ",123", "123,-1", "123,0"})
    public void itShouldReturnErrorResponse_WhenProductoHasUnexpectedParameterValues_generateQR3(String sku, int tienda) throws Exception {
        //Arrange
        ProductoEtiqueta producto = new ProductoEtiqueta();
        producto.setSKU(sku);
        producto.setTienda(tienda);

        //Act
        RequestResponse response = contoller.generateQR3(producto, 100);

        //Assert       
        assertEquals(RequestResponse.RESPONSE_CODE.ERROR, response.getCode());
        assertTrue(response.getResult().contains("SKU") || response.getResult().contains("sucursal"));
        assertTrue(response.getResult().contains("número mayor a 0") || response.getResult().contains("vacío"));
    }

    @Test
    @Parameters({"QR1, 100", "QR2, 200", "QR3, 500", "QR5, 1000"})
    public void itShouldPerformPOSTWithCorrectParameters_decipherQRContent(final QRContent.QRType qrType, final int qrSize) throws Exception {
        //Arrange
        //TODO: generate random strings for this properties
        mockComponentConfig.CODES_ENCRYTION_AES_KEY = "2321h31ub3kb13";
        String fakeEncryptedContent = "asasd12123";

        String fakeQRContent = jsonUtils.ObjectToJSON(new QRContent(fakeEncryptedContent, qrType));

        HashMap<String, String> fakeSecureParameters = new HashMap();
        fakeSecureParameters.put("encryptedContent", fakeEncryptedContent);

        mockComponentConfig.URL_QR_CODE_WS = "hhtps://fake.url";

        final String fakeTokenAcceso = "sd$%$&;MDS=D";
        HashMap<String, String> fakeRawParameters = new HashMap() {
            {
                put("qrType", qrType.name());
                put("tokenAcceso", fakeTokenAcceso);
            }
        };

        String serviceResponseJSON = jsonUtils.ObjectToJSON(new ServiceResponse(200, ""));
        when(
                mockSecureRequestController.doSecurePOST(mockComponentConfig.URL_QR_CODE_WS + this.DECIPHER_QR_CONTENT_WS_METHOD, fakeSecureParameters, fakeRawParameters)
        ).thenReturn(new RequestResponse(RequestResponse.RESPONSE_CODE.SUCCESS, serviceResponseJSON));

        //Act
        contoller.decipherQRContent(fakeQRContent, fakeTokenAcceso);

        //Assert       
        verify(mockSecureRequestController, times(1)).doSecurePOST(mockComponentConfig.URL_QR_CODE_WS + this.DECIPHER_QR_CONTENT_WS_METHOD, fakeSecureParameters, fakeRawParameters);
    }

    @Test
    public void itShouldReturnSuccessResponse_decipherQRContent() throws Exception {
        //Arrange
        String fakeEncryptedContent = "asasd12123";

        String expectedServiceResult = "Result of service";
        String expectedResultJSON = jsonUtils.ObjectToJSON(new ServiceResponse(200, expectedServiceResult));
        when(
                mockSecureRequestController.doSecurePOST(anyString(), Matchers.<HashMap<String, String>>any(), Matchers.<HashMap<String, String>>any())
        ).thenReturn(new RequestResponse(RequestResponse.RESPONSE_CODE.SUCCESS, expectedResultJSON));

        String fakeQRContent = jsonUtils.ObjectToJSON(new QRContent(fakeEncryptedContent, QRContent.QRType.QR1));

        //Act
        RequestResponse response = contoller.decipherQRContent(fakeQRContent, "");

        //Assert       
        assertEquals(RequestResponse.RESPONSE_CODE.SUCCESS, response.getCode());
        assertEquals(expectedServiceResult, response.getResult());
    }

    @Test
    @Parameters({"QR1", "QR2", "QR5"})
    public void itShouldReturnErrorResponse_WhenQREncryptedContentIsEmpty_decipherQRContent(final QRContent.QRType qrType) throws Exception {
        //Arrange
        String fakeQRContent = jsonUtils.ObjectToJSON(new QRContent("", qrType));

        //Act
        RequestResponse response = contoller.decipherQRContent(fakeQRContent, "");

        //Assert       
        assertEquals(RequestResponse.RESPONSE_CODE.ERROR, response.getCode());
        assertTrue(response.getResult().contains("Contenido del QR no válido"));
    }

    @Test
    @Parameters({"500", "501", "520", "510"})
    public void itShouldReturnErrorResponse_WhenPOSTRequestReturnsErrorCode_decipherQRContent(int errorCode) throws Exception {
        //Arrange
        String serviceResponseJSON = jsonUtils.ObjectToJSON(new ServiceResponse(errorCode, ""));
        when(
                mockSecureRequestController.doSecurePOST(anyString(), Matchers.<HashMap<String, String>>any(), Matchers.<HashMap<String, String>>any())
        ).thenReturn(new RequestResponse(RequestResponse.RESPONSE_CODE.SUCCESS, serviceResponseJSON));

        String fakeQRContent = jsonUtils.ObjectToJSON(new QRContent("asdad", QRContent.QRType.QR3));

        //Act
        RequestResponse response = contoller.decipherQRContent(fakeQRContent, "");

        //Assert       
        assertEquals(RequestResponse.RESPONSE_CODE.ERROR, response.getCode());
        assertTrue(response.getResult().contains("Error en servicio"));
    }

    @Test
    public void itShouldReturnErrorResponse_WhenBridgeResponseReturnsError_decipherQRContent() throws Exception {
        //Arrange
        Presupuesto presupuesto = new Presupuesto();
        presupuesto.setPresupuesto(123123);
        presupuesto.setSucursal(343);

        String serviceResponseJSON = jsonUtils.ObjectToJSON(new ServiceResponse(0, ""));
        when(
                mockSecureRequestController.doSecurePOST(anyString(), Matchers.<HashMap<String, String>>any(), Matchers.<HashMap<String, String>>any())
        ).thenReturn(new RequestResponse(RequestResponse.RESPONSE_CODE.ERROR, serviceResponseJSON));

        String fakeQRContent = jsonUtils.ObjectToJSON(new QRContent("adasd", QRContent.QRType.QR3));

        //Act
        RequestResponse response = contoller.decipherQRContent(fakeQRContent, "");

        //Assert       
        assertEquals(RequestResponse.RESPONSE_CODE.ERROR, response.getCode());
        assertTrue(response.getResult().contains("Error en el puente"));
    }

    
    @Test
    public void itShouldPerformPOSTWithCorrectParameters_QRProfile() throws Exception {
        //Arrange(Given)
        Perfil perfil = new Perfil();
        perfil.setCteAlnova("ss4555");
        perfil.setICU("541sda");
        perfil.setNumTarjeta("10224552455");
        perfil.setNumSolicitudCentralizada("4253554");
        perfil.setNumSolicitudTienda("42442");
        perfil.setTipoSolicitud(2);
        perfil.setIdRegistroMensaje(6526);
      
        HashMap<String, String> fakeSecureParameters = new HashMap();
        fakeSecureParameters.put("encryptedContent", jsonUtils.ObjectToJSON(perfil));

        mockComponentConfig.URL_QR_CODE_WS = "hhtps://fake.url.com";

        final String fakeTokenAcceso = "sd$%$&;MDS=D";
        final int qrSize = 150;
        HashMap<String, String> fakeRawParameters = new HashMap() {
            {            
               put("tokenAcceso", fakeTokenAcceso);
            }
        };

        String serviceResponseJSON = jsonUtils.ObjectToJSON(new ServiceResponse(200, ""));
        when(
                mockSecureRequestController.doSecurePOST(mockComponentConfig.URL_QR_CODE_WS + this.GENERATE_PROFILEQR_CONTENT_WS_METHOD, fakeSecureParameters, fakeRawParameters)
        ).thenReturn(new RequestResponse(RequestResponse.RESPONSE_CODE.SUCCESS, serviceResponseJSON));

        //Act (When)
        contoller.generateQRProfile(perfil, fakeTokenAcceso);

        //Asset (Then)      
        verify(mockSecureRequestController, times(1)).doSecurePOST(mockComponentConfig.URL_QR_CODE_WS + this.GENERATE_PROFILEQR_CONTENT_WS_METHOD, fakeSecureParameters,  fakeRawParameters);

    }

    @Test
    public void itShouldReturnSuccessResponse_generateQRProfile() throws Exception {
         //Arrange (Given)
        Perfil perfil =new Perfil();
        perfil.setCteAlnova("4256sad");
        perfil.setICU("456456d");
        perfil.setNumSolicitudTienda("45445");
        perfil.setNumSolicitudCentralizada("45445");
        perfil.setNumTarjeta("75656");
        perfil.setIdRegistroMensaje(6526);
        perfil.setTipoSolicitud(2);

        String expectedServiceResult = "Result of service ";
        String expectedResultJSON = jsonUtils.ObjectToJSON(new ServiceResponse(200, expectedServiceResult));

        when(
             
                mockSecureRequestController.doSecurePOST(anyString(), Matchers.<HashMap<String, String>>any(), Matchers.<HashMap<String, String>>any())
        
        ).thenReturn(new RequestResponse(RequestResponse.RESPONSE_CODE.SUCCESS, expectedResultJSON));

        //when()
        RequestResponse response = contoller.generateQRProfile(perfil,  "");

        //Assert (then)        
        assertEquals(RequestResponse.RESPONSE_CODE.SUCCESS, response.getCode());
        assertEquals(expectedServiceResult, response.getResult());
    }
    
    @Parameters({"500", "501", "520", "510"})
    @Test
    public void itShouldReturnErrorResponse_WhenPOSTRequestReturnsErrorCode_generateQRProfile(int errorCode) throws Exception {
        //Arange (Give)
        Perfil perfil = new Perfil();
        perfil.setCteAlnova("456464");
        perfil.setICU("5656");  
        perfil.setNumSolicitudCentralizada("14235");    
        perfil.setNumSolicitudTienda("456565");
        perfil.setNumTarjeta("65266515445");
        perfil.setIdRegistroMensaje(6526);
        perfil.setTipoSolicitud(2);
        
        
      String serviceResponseJSON = jsonUtils.ObjectToJSON(new ServiceResponse(errorCode, ""));
        when(
                mockSecureRequestController.doSecurePOST(anyString(), Matchers.<HashMap<String, String>>any(), Matchers.<HashMap<String, String>>any())
        ).thenReturn(new RequestResponse(RequestResponse.RESPONSE_CODE.ERROR, serviceResponseJSON));

        //Act
        RequestResponse response = contoller.generateQRProfile(perfil,  "");

        //Assert       
        assertEquals(RequestResponse.RESPONSE_CODE.ERROR, response.getCode());
        assertTrue(response.getResult().contains("Error en el puente"));
        
        
        
    }
}*/
