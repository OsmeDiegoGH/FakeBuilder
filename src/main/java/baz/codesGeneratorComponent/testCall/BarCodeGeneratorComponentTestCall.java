package baz.codesGeneratorComponent.testCall;

import baz.codesGeneratorComponent.controllers.PedidoBarCode;
import baz.codesGeneratorComponent.controllers.RetiroBarCode;
import baz.codesGeneratorComponent.entities.ResponseBean;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import java.io.IOException;

public class BarCodeGeneratorComponentTestCall {

    private static final PedidoBarCode pedidoBarCode = new PedidoBarCode();
    private static final RetiroBarCode retiroBarCode = new RetiroBarCode();


    public static String callGeneratePedidoBarCode(String tokenAcceso) throws Base64DecodingException, IOException {
        ResponseBean response = pedidoBarCode.generar(tokenAcceso, "1234", "123456789");

        if (response.getCodigo() == 200) {
            System.out.println("BarCode returned: " +  response.getData("pedido-codigobarras"));
            System.out.println("CreationTime returned: " +  response.getData("pedido-creationtime"));
            return (String)response.getData("pedido-codigobarras");
        } else {
            System.out.println(response.getMensaje());
            return null;
        }
    }
    
    public static void callGetPedidoBarCodeData(String barcodeNumber, String tokenAcceso) throws Base64DecodingException, IOException {
        ResponseBean response = pedidoBarCode.verContenido(tokenAcceso, barcodeNumber);

        if (response.getCodigo() == 200) {
            System.out.println("Original Data of BarCode(pedido): " +  response.getData("pedido-codigobarras-pedido"));
            System.out.println("Original Data of BarCode(sucursal): " +  response.getData("pedido-codigobarras-sucursal"));
        } else {
            System.out.println(response.getMensaje());
        }
    }
    
    public static void callValidatePedidoBarCodeData(String tokenAcceso) throws Base64DecodingException, IOException {
        ResponseBean response = pedidoBarCode.validar(tokenAcceso, "0b266c22zsb1l3v");
        

        if (response.getCodigo() == 200) {
            System.out.println("Should be invalid:" +  response.getData("codigobarras-es-valido"));
            
            response = pedidoBarCode.validar(tokenAcceso, callGeneratePedidoBarCode(tokenAcceso));
            
            if (response.getCodigo() == 200) {
                System.out.println("Should be valid:" + response.getData("codigobarras-es-valido"));
            } else {
                System.out.println(response.getMensaje());
            }
        } else {
            System.out.println(response.getMensaje());
        }
    }

    public static String callGenerateRetiroBarCode(String tokenAcceso) throws Base64DecodingException, IOException {
        ResponseBean response = retiroBarCode.generar(tokenAcceso, "12345678", "123456789012");

        if (response.getCodigo() == 200) {
            System.out.println("BarCode returned: " +  response.getData("retiro-codigobarras"));
            System.out.println("CreationTime returned: " +  response.getData("retiro-creationtime"));
            return (String)response.getData("retiro-codigobarras");
        } else {
            System.out.println(response.getMensaje());
            return null;
        }
    }
    
    public static void callGetRetiroBarCodeData(String barcodeNumber, String tokenAcceso) throws Base64DecodingException, IOException {
        ResponseBean response = retiroBarCode.verContenido(tokenAcceso, barcodeNumber);

        if (response.getCodigo() == 200) {
            System.out.println("Original Data of BarCode(monto): " +  response.getData("retiro-codigobarras-monto"));
            System.out.println("Original Data of BarCode(numero cuenta): " +  response.getData("retiro-codigobarras-numerocuenta"));
        } else {
            System.out.println(response.getMensaje());
        }
    }
    
    public static void callValidateRetiroBarCodeData(String tokenAcceso) throws Base64DecodingException, IOException {
        ResponseBean response = retiroBarCode.validar(tokenAcceso, "553904iy3oi8mrt5qwyl");
        

        if (response.getCodigo() == 200) {
            System.out.println("Should be invalid:" +  response.getData("codigobarras-es-valido"));
            
            response = retiroBarCode.validar(tokenAcceso, callGenerateRetiroBarCode(tokenAcceso));
            
            if (response.getCodigo() == 200) {
                System.out.println("Should be valid:" + response.getData("codigobarras-es-valido"));
            } else {
                System.out.println(response.getMensaje());
            }
        } else {
            System.out.println(response.getMensaje());
        }
    }
}
