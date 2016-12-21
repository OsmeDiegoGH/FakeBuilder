package baz.codesGeneratorComponent.entities;

public class QRContent {

    public enum QRType {

        QR1, QR2, QR3, QR5, QRProfile, QRCanalesTerceros
    }
    private QRType qrType;

    public enum QrBackColor {

        greenColor,
        grayColor,
        blackColor;
        
    }
    
        public enum CenterLogo{
        centerLogoQR1,
        centerLogoQR2;
       
    }
    
    private QrBackColor qrBackColor;
    private String encryptedContent;
    private String transportKey;

    public QRContent(String encryptedContent, String transportKey, QRType qrType) {
        this.encryptedContent = encryptedContent;
        this.transportKey = transportKey;
        this.qrType = qrType;
    }


    public QRContent(String encryptedContent, QRType qrType) {
        this.encryptedContent = encryptedContent;
        this.qrType = qrType;
    }


    public String getEncryptedContent() {
        return encryptedContent;
    }

    public void setEncryptedContent(String encryptedContent) {
        this.encryptedContent = encryptedContent;
    }

    public String getTransportKey() {
        return transportKey;
    }

    public void setTransportKey(String transportKey) {
        this.transportKey = transportKey;
    }

    public QRType getQrType() {
        return qrType;
    }

    public void setQrType(QRType qrType) {
        this.qrType = qrType;
    }
}
