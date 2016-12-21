package baz.codesGeneratorComponent.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import javax.imageio.ImageIO;

public class QRUtils {

    private final static QRUtils INSTANCE = new QRUtils();
    private final static Color greenColor = new Color (70,169,119);

    private QRUtils() {
    }

    public static QRUtils getInstance() {
        return INSTANCE;
    }

    public byte[] generateQRBytesArray(String content, int imgSize, final String charsetImg, String fileExtension) throws WriterException, IOException {
        return generateQRBytesArray(content, imgSize, charsetImg, fileExtension, null);
    }

    public byte[] generateQRBytesArray(String content, int imgSize, final String charsetImg, String fileExtension, final Image centerLogo) throws WriterException, IOException {
        Hashtable<EncodeHintType, Object> hintOptions = new Hashtable<EncodeHintType, Object>();
        hintOptions.put(EncodeHintType.CHARACTER_SET, charsetImg);
        hintOptions.put(EncodeHintType.ERROR_CORRECTION, (centerLogo != null) ? ErrorCorrectionLevel.H : ErrorCorrectionLevel.M);
        
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix byteMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, imgSize, imgSize, hintOptions);

        //image processing
        int matrixWidth = byteMatrix.getWidth();

        BufferedImage bufferedImage = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
        bufferedImage.createGraphics();
        Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, matrixWidth, matrixWidth);
        graphics.setColor(Color.BLACK);

        ImageObserver imageObserver = new ImageObserver() {
            @Override
            public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };

        for (int bgX = 0; bgX < matrixWidth; bgX++) {
            for (int bgY = 0; bgY < matrixWidth; bgY++) {
                if (byteMatrix.get(bgX, bgY)) {
                    graphics.fillRect(bgX, bgY, 1, 1);
                }
            }
        }

        if (centerLogo != null) {
            int centerSquareWidth = (int) (matrixWidth / 3);
            double centerSquarePadding = centerSquareWidth * 1;
            graphics.drawImage(centerLogo, (int) centerSquarePadding, (int) centerSquarePadding, centerSquareWidth, centerSquareWidth, imageObserver);
        }

        byte[] result;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, fileExtension, outStream);
        result = outStream.toByteArray();

        return result;
    }

    public static String readContent(File file) throws Exception {
        BufferedImage image = null;
        BinaryBitmap bitmap = null;
        Result result = null;

        try {
            image = ImageIO.read(file);
            int[] pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
            RGBLuminanceSource source = new RGBLuminanceSource(image.getWidth(), image.getHeight(), pixels);
            bitmap = new BinaryBitmap(new HybridBinarizer(source));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (bitmap == null) {
            return null;
        }

        try {
            QRCodeReader reader = new QRCodeReader();
            result = reader.decode(bitmap);
            return result.getText();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }
}
