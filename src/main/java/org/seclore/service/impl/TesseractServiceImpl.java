package org.seclore.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.seclore.service.OcrService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@Service("tesseract")
@Slf4j
public class TesseractServiceImpl implements OcrService {

    @Override
    public String extractTextFromImage(byte[] imageData) {
        ITesseract tesseract = new Tesseract();
        try {
            ClassPathResource resource = new ClassPathResource("tessdata");
            tesseract.setDatapath(resource.getFile().getAbsolutePath());
            tesseract.setLanguage("eng");
            ByteArrayInputStream inputStream = new ByteArrayInputStream(imageData);
            BufferedImage image = ImageIO.read(inputStream);
            log.info("Starting OCR process...");
            String text = tesseract.doOCR(image);
            log.info("OCR process completed successfully.");
            return text;
        } catch (TesseractException | IOException e) {
            log.error("Error during OCR process", e);
            return "Error: Unable to extract text.";
        }
    }
}
