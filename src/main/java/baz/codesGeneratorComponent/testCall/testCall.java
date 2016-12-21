package baz.codesGeneratorComponent.testCall;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;

public class testCall {
    final static String TOKEN_ACCESO_QR_DEV = "7MpseRPbROhhNqcKHnPssVrxUfgZqonMkjpf4Zf+uZF5rbDw8EUgS5Mu4Y2H+EMsP74iWG+mcBvTijF5e5DNLseIbvhN18VJJRdtvtfz0SXUDUGW69wKC2qINJ4a0pG5T5znX1TW81VUw2EMFOhvJY5eErl5xxcWvt1g1XrYrk+UWmkh73ZRW14hIpaY6uReLbLueJy2zgH7Nk5BG+OX93BJWiaoRW69boIdPCmm5PA=";
    final static String TOKEN_ACCESO_QR_PROD = "7MpseRPbROhhNqcKHnPssdr4W0e0JXUOdtf5Z/jzivETc8THhtgIPqgu7PQ9zwIZ5J3KNYDsXSrhKpCK2SqKFSsQmfCAOrKQOEgZ1aa0uLnetLsEYu9Tx44WcXYyKK2yMHSQ6CDbWFCwaQr82XdBRHXI9eD8MMS/S7Kg0EyvRolT2mBig73axIIJk0rOi3i2OTTdMYVL5HO9Zyk1vFWO0f20V+7xWwYZ6L7/ldOhuTutGFoSQwviA5qdVsM+54wPFEbC6tFrpL+TZzTt8n9vbZc5if12HntOWBzFVVv5jXHMU/0a3FQ6llDbFPH87Bmark52mol0K5AijP4Ee8J5EPP+NoDw7OQUesiMli2PDlk=";
    
    final static String TOKEN_ACCESO_BARCODE_DEV = "7MpseRPbROhhNqcKHnPssc6R+nTRkZDelao1YGpTTU0MW3G4bYRQ0T/JSj1FdDnVx+tYPDxCnE4QHLOuxyzfk+jC0urd8ELVvjQLjLjDaFmk1pz7CRJEIp4FUFI8Qi5k2vc+3UJUcyNCfppaDpu0ZYtCUOrmhBUdwGvCJgb8U9B/82yjtoe/PuWW65aVDmyEnG8/eLI1LQMxOqJQq0hFAvw+nmM8lkX3OFchc6A3HIzweLJVpJuOG1pzvzmWNDENXcTfKKvcxub0ObeRG8d6Dw==";
    final static String TOKEN_ACCESO_BARCODE_PROD = "7MpseRPbROhhNqcKHnPssc6R+nTRkZDelao1YGpTTU0MW3G4bYRQ0T/JSj1FdDnVx+tYPDxCnE4QHLOuxyzfk+jC0urd8ELVvjQLjLjDaFmk1pz7CRJEIp4FUFI8Qi5kOQQt93OT6QDyUs+lpHLoDsClRRKfmU/6CAtlpZTaoO0qW9mGPaFe5fh8pBZfRM4KAzQ3H0HT/nLEhsjoVnEFohkg/9kkrkD98ZBzRU3WY7cI95taHwcjVEKUjUHj/lWfvYealNuwWovmG0kSW2T3yIqi6eSzvDlWydhbxvmdOaTr/5rd4+2S5Ng0Fxd/LsH6Yo98sOoEWUSE38ddh6I7zA==";
    
    private static String TOKEN_ACCESO_QR;    
    private static String TOKEN_ACCESO_BARCODE;    
    
    public static void main(String[] args) throws IOException, ParseException, Exception{
                
        Scanner reader = new Scanner(System.in); 
        
        boolean isValidEnviroment = false;
        String enviroment = null;
        
        
        if(args.length > 0 && (args[0].toLowerCase().equals("dev") || args[0].toLowerCase().equals("prod"))){
            enviroment = args[0].toLowerCase();
        }
        
        while(!isValidEnviroment){
            if(enviroment == null){
                showEnvConsoleOptions();
                enviroment = reader.next();
            }
            
            if(enviroment.equals("prod")){
                isValidEnviroment = true;
                TOKEN_ACCESO_QR = TOKEN_ACCESO_QR_PROD;
                TOKEN_ACCESO_BARCODE = TOKEN_ACCESO_BARCODE_PROD;
            }else if(enviroment.equals("dev")){
                isValidEnviroment = true;
                TOKEN_ACCESO_QR = TOKEN_ACCESO_QR_DEV;
                TOKEN_ACCESO_BARCODE = TOKEN_ACCESO_BARCODE_DEV;
            }else if(enviroment.equals("0")){
                return;
            }else{
                enviroment = null;
                System.out.println("Ambiente no válido");
            }
        }
        int execOperation = 0;
        boolean listening = true;
        boolean running = true;
        
        if(args.length > 1){
            listening = false;
            execOperation = Integer.valueOf(args[1]);
        }
        
        boolean processAll = false;
        
        do{
            if(listening){
                showConsoleOptions();
                execOperation = reader.nextInt();
            }     
            if(execOperation == -1){
                execOperation = 1;
                processAll = true;
            }
            switch (execOperation) {
                case 1:
                    QRCodeGeneratorComponentTestCall.callGenerateQR1(150, TOKEN_ACCESO_QR);
                    break;
                case 2:
                    QRCodeGeneratorComponentTestCall.callGenerateQR2(TOKEN_ACCESO_QR);
                    break;
                case 3:
                    QRCodeGeneratorComponentTestCall.callGenerateQR3();
                    break;
                case 4:
                    QRCodeGeneratorComponentTestCall.callGenerateQRProfile(150, TOKEN_ACCESO_QR);
                    break;
                case 5:
                    QRCodeGeneratorComponentTestCall.callDechiperQR(TOKEN_ACCESO_QR);
                    break;
                case 6:
                    BarCodeGeneratorComponentTestCall.callGeneratePedidoBarCode(TOKEN_ACCESO_BARCODE);
                    break;
                case 7:
                    BarCodeGeneratorComponentTestCall.callGetPedidoBarCodeData("0b266c22zsb2gdk", TOKEN_ACCESO_BARCODE);
                    break;
                case 8:
                    BarCodeGeneratorComponentTestCall.callValidatePedidoBarCodeData(TOKEN_ACCESO_BARCODE);
                    break;
                case 9:
                    BarCodeGeneratorComponentTestCall.callGenerateRetiroBarCode(TOKEN_ACCESO_BARCODE);
                    break;
                case 10:
                    BarCodeGeneratorComponentTestCall.callGetRetiroBarCodeData("52wvh9yq01pvdovkb2nj", TOKEN_ACCESO_BARCODE);
                    break;
                case 11:
                    BarCodeGeneratorComponentTestCall.callValidatePedidoBarCodeData(TOKEN_ACCESO_BARCODE);
                    break;
                case 12:
                    QRCodeGeneratorComponentTestCall.callGenerateQRCanalesATerceros();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Operacion no valida");
            }
            if(processAll && ++execOperation > 12){
                running = false;
            }
            if(!processAll && !listening){
                running = false;
            }
        }while(running);
        
        reader.close();
    }
    
    private static void showEnvConsoleOptions(){
        System.out.print("\n");
        System.out.println("Especifique el ambiente a probar(dev, prod):");
        System.out.println("0.- Salir");
    }
    
    private static void showConsoleOptions(){
        System.out.print("\n");
        System.out.println("Que operación quieres realizar:");
        System.out.println("1.- Generar QR1");
        System.out.println("2.- Generar QR2");
        System.out.println("3.- Generar QR3");
        System.out.println("4.- Generar QR Profile");
        System.out.println("5.- Descifrar QR");
        System.out.println("6.- Generar Codigo de Barras - Pedido");
        System.out.println("7.- Obtener Datos de Codigo de Barras - Pedido");
        System.out.println("8.- Validar Tiempo Codigo de Barras - Pedido");
        System.out.println("9.- Generar Codigo de Barras - Retiro");
        System.out.println("10.- Obtener Datos de Codigo de Barras - Retiro");
        System.out.println("11.- Validar Tiempo Codigo de Barras - Retiro");
        System.out.println("12.- Generar QR Canales a Terceros");
        System.out.println("0.- Salir");
    }
}
