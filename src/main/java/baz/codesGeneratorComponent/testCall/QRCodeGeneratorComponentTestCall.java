package baz.codesGeneratorComponent.testCall;

import baz.codesGeneratorComponent.controllers.QRCodeGeneratorController;
import acertum.secureRequestHandler.entities.RequestResponse;
import baz.codesGeneratorComponent.entities.Presupuesto;
import baz.codesGeneratorComponent.entities.ProductoEtiqueta;
import baz.codesGeneratorComponent.utils.QRUtils;
import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import baz.codesGeneratorComponent.entities.Perfil;
import baz.codesGeneratorComponent.entities.ProductoTerceros;

public class QRCodeGeneratorComponentTestCall {

    private static final QRCodeGeneratorController qrCodeGeneratorController = new QRCodeGeneratorController();
    
    public static void callGenerateQR1(int size, String tokenAcceso) throws Base64DecodingException, IOException {
        Presupuesto presupuesto = new Presupuesto();
        presupuesto.setPresupuesto(11234);
        presupuesto.setSucursal(111100);
        RequestResponse response = qrCodeGeneratorController.generateQR1(presupuesto, size, tokenAcceso);

        if (response.getCode() == RequestResponse.RESPONSE_CODE.SUCCESS) {
            byte[] QRBytes = Base64.decode(response.getResult());
            OutputStream stream = new FileOutputStream("QR1_" + size + ".png");
            stream.write(QRBytes);
            System.out.println("QR1 Code generated successfully");
        } else {
            System.out.println(response.getResult());
        }
    }

    public static void callGenerateQR2(String tokenAcceso) throws Base64DecodingException, IOException, ParseException, Exception {
        Presupuesto presupuesto = new Presupuesto();
        presupuesto.setPresupuesto(111111);
        presupuesto.setSucursal(12345);
        
        RequestResponse response = qrCodeGeneratorController.generateQR2(presupuesto, 200, tokenAcceso);

        if (response.getCode() == RequestResponse.RESPONSE_CODE.SUCCESS) {
            byte[] QRBytes = Base64.decode(response.getResult());
            OutputStream stream = new FileOutputStream("QR2.png");
            stream.write(QRBytes);
            System.out.println("QR2 Code generated successfully");
        } else {
            System.out.println(response.getResult());
        }
    }

    public static void callGenerateQR3() throws Base64DecodingException, IOException, Exception {
        ProductoEtiqueta producto = new ProductoEtiqueta();
        producto.setSKU("4152");
        producto.setTienda(14752);
        RequestResponse response = qrCodeGeneratorController.generateQR3(producto, 200);

        if (response.getCode() == RequestResponse.RESPONSE_CODE.SUCCESS) {
            byte[] QRBytes = Base64.decode(response.getResult());
            OutputStream stream = new FileOutputStream("QR3.png");
            stream.write(QRBytes);
            System.out.println("QR3 Code generated successfully");
        } else {
            System.out.println(response.getResult());
        }
    }

    public static void callGenerateQRProfile(int size, String tokenAcceso) throws Base64DecodingException, IOException, ParseException, Exception {
        Perfil perfil = new Perfil();
        perfil.setICU("5485");
        perfil.setCteAlnova("56454");
        perfil.setNumSolicitudCentralizada("2");
        perfil.setNumSolicitudTienda("45585");
        perfil.setTipoSolicitud(1);
        perfil.setIdRegistroMensaje(23);
        RequestResponse response = qrCodeGeneratorController.generateQRProfile(perfil, tokenAcceso);

        if (response.getCode() == RequestResponse.RESPONSE_CODE.SUCCESS) {
            String cadenaJson = response.getResult();
            byte[] QRBytes = QRUtils.getInstance().generateQRBytesArray(cadenaJson, size, "UTF-8", "png");
            OutputStream stream = new FileOutputStream("QRProfile_" + size + ".png");
            stream.write(QRBytes);
            System.out.println("QRProfile Code generated successfully");
        } else {
            System.out.println(response.getResult());
        }
    }

    public static void callDechiperQR(String tokenAcceso) throws Base64DecodingException, IOException, Exception {
        //String fileContent = QRUtils.readContent(new File("QRProfile_150.png"));
        String[] mockContents = new String[]{
            "{'qrType':'QRCanalesTerceros','encryptedContent':'YdWSVZVdZ5NBBq/H3i33Iot9+i15Zp37pdVzwPcmBl/P1aT+3UOmYj55IUtvNnDd'}",
            "{'qrType':'QRProfile','encryptedContent':'dW1QbDdJNC0+1F3g/sWv4/Uq2mrr8Zzq0XDVphMZGDSLRIuh0BfmvKAUKSucdybZtIs6VewoZ0Hr\nYD5N2MUNduMKbNz/7z8Oxlo59nHJ7cO4HmbjjvMrPHU+K6H2UiHqgeOPMEij3vHmKhu8//WdSIto\nO9EkqscBS2/y0JilXBtxCKQTxIn6lglaLckRD0C1'}"
        };
        for(int i = 0; i < mockContents.length; i++){
            RequestResponse response = qrCodeGeneratorController.decipherQRContent(mockContents[i], tokenAcceso);
            if (response.getCode() == RequestResponse.RESPONSE_CODE.SUCCESS) {
                System.out.println("QR Content: " + response.getResult());
            } else {
                System.out.println(response.getResult());
            }
        }
    }
    
    public static void callGenerateQRCanalesATerceros() throws Base64DecodingException, IOException, Exception {
        ProductoTerceros producto = new ProductoTerceros();
        producto.setSKU("4152");
        producto.setPrecio(1475.0);
        producto.setId_Canal(12);
        
        RequestResponse response = qrCodeGeneratorController.generateQRCanalesATerceros(producto, 150);

        if (response.getCode() == RequestResponse.RESPONSE_CODE.SUCCESS) {
            byte[] QRBytes = Base64.decode(response.getResult());
            OutputStream stream = new FileOutputStream("QRCanalesTerceros.png");
            stream.write(QRBytes);
            System.out.println("QRCanalesATerceros Code generated successfully");
        } else {
            System.out.println(response.getResult());
        }
    }
}
