package com.freshfood.util;
import com.freshfood.service.ProductService;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.*;
import com.google.zxing.common.HybridBinarizer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@RequiredArgsConstructor
@Service
public class QRCodeScanner {
    private final ProductService productService;
    public String decodeQRCode(String filePath) {
        try {
            BufferedImage bufferedImage = ImageIO.read(new File(filePath));
            LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            Result result = new MultiFormatReader().decode(bitmap);
            return result.getText();
        } catch (Exception e) {
            return "Lỗi khi quét mã QR: " + e.getMessage();
        }
    }
    public String decodeQRCode(MultipartFile file) {
        try {
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            Result result = new MultiFormatReader().decode(bitmap);

            String qrText = result.getText();

            // Kiểm tra định dạng /product/{id}
            if (qrText != null && qrText.matches("^/product/\\d+$")) {
                int productId = Integer.parseInt(qrText.substring(qrText.lastIndexOf("/") + 1));
                if (productService.getProduct(productId) == null) {
                    return "/product-not-found";
                }
            }

            return qrText;

        } catch (NotFoundException e) {
            return "Không tìm thấy mã QR trong ảnh.";
        } catch (IOException e) {
            return "Không thể đọc ảnh: " + e.getMessage();
        } catch (Exception e) {
            return "Lỗi không xác định khi quét QR: " + e.getMessage();
        }
    }

    public static void main(String[] args) {

    }
}
